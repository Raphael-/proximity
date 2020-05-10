package gr.rmagkos.proximity.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.cache.annotation.CacheKey;
import javax.cache.annotation.CacheResult;
import javax.ejb.Stateless;
import javax.sql.DataSource;

import org.h2gis.utilities.SFSUtilities;
import org.h2gis.utilities.SpatialResultSet;
import org.locationtech.jts.geom.Coordinate;

import gr.rmagkos.proximity.model.Point;

/**
 * Implements the {@link PointRepository} contract, using a database
 */
@Stateless
public class DatabasePointRepository implements PointRepository {

	private DataSource dataSource;

	@PostConstruct
	protected void initRepository() {
		dataSource = SFSUtilities.wrapSpatialDataSource(dataSource);
	}

	/**
	 * Fetches from database the {@link Point} that is closest to the provided
	 * {@link Point}
	 * 
	 * @param pointToExamine the point that will be examined
	 * @return the closest {@link Point} to the provided one, or null if none found
	 */
	@CacheResult(cacheName = "pointsCache")
	public Point fetchNearestPointTo(@CacheKey Point pointToExamine) {
		System.out.println("Fetching nearest point to " + pointToExamine + " from database.");
		Point nearestPoint = null;
		try (Connection connection = dataSource.getConnection()) {
			String sqlQuery = buildFetchNearestQuery(pointToExamine);
			SpatialResultSet resultSet = connection.prepareStatement(sqlQuery).executeQuery()
					.unwrap(SpatialResultSet.class);
			nearestPoint = fetchPointFromResultSet(resultSet);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return nearestPoint;
	}

	/**
	 * Increases hit counter of a {@link Point} by 1. This update operation uses
	 * {@link Point#getId()} as key to identify the row to update
	 * 
	 * @param pointToUpdate {@link Point} that will have its hit counter increased
	 */
	public void updateHitCount(Point pointToUpdate) {
		System.out.println("Updating hit count for " + pointToUpdate);
		try (Connection connection = dataSource.getConnection()) {
			PreparedStatement updateStatement = connection.prepareStatement(Query.INCREMENT_POINT_HITS.sql());
			updateStatement.setLong(1, pointToUpdate.getId());
			updateStatement.execute();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Fetches a list of {@link Point} that have more than {@code hitsCount}
	 * 
	 * @param hitsCount hit count threshold
	 * @return list of {@link Point} that satisfy the threshold condition, or an
	 *         empty list if none are found.
	 */
	public List<Point> fetchPointsHavingHitsMoreThan(int hitsCount) {
		System.out.println("Fetching points with more than " + hitsCount + " hits");
		List<Point> pointsToRetun = new ArrayList<Point>();
		try (Connection connection = dataSource.getConnection()) {
			PreparedStatement fetchPointByHitsStatement = buildFetchPointByHitsStatement(connection, hitsCount);
			SpatialResultSet resultSet = fetchPointByHitsStatement.executeQuery().unwrap(SpatialResultSet.class);
			fetchPointsFromResultSet(resultSet, pointsToRetun);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return pointsToRetun;
	}

	@Resource(name = "geodb")
	protected void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	private String buildFetchNearestQuery(Point pointToExamine) {
		return Query.FETCH_NEAREST_POINT.sql().replace(":latitude", String.valueOf(pointToExamine.getLatitude()))
				.replace(":longitude", String.valueOf(pointToExamine.getLongitude()));
	}

	private PreparedStatement buildFetchPointByHitsStatement(Connection connection, int hitsCount) throws SQLException {
		PreparedStatement fetchPointByHitsStatement = connection.prepareStatement(Query.FETCH_POINTS_BY_HITS.sql());
		fetchPointByHitsStatement.setInt(1, hitsCount);
		fetchPointByHitsStatement.executeQuery();
		return fetchPointByHitsStatement;
	}

	private Point fetchPointFromResultSet(SpatialResultSet resultSet) throws SQLException {
		Point pointInResultSet = null;
		while (resultSet.next()) {
			pointInResultSet = buildPointFromResultSet(resultSet);
		}
		return pointInResultSet;
	}

	private List<Point> fetchPointsFromResultSet(SpatialResultSet resultSet, List<Point> listToPopulate)
			throws SQLException {
		while (resultSet.next()) {
			listToPopulate.add(buildPointFromResultSet(resultSet));
		}
		return listToPopulate;
	}

	private Point buildPointFromResultSet(SpatialResultSet resultSet) throws SQLException {
		Point point = new Point();
		Coordinate coordinate = resultSet.getGeometry("COORDINATES").getCoordinate();
		point.setLatitude(coordinate.getOrdinate(Coordinate.X));
		point.setLongitude(coordinate.getOrdinate(Coordinate.Y));
		point.setLabel(resultSet.getString("NAME"));
		point.setHits(resultSet.getInt(("HITS")));
		point.setId(resultSet.getLong("ID"));
		return point;
	}
}
