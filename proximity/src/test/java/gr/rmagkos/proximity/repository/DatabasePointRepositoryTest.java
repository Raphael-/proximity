package gr.rmagkos.proximity.repository;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.Before;
import org.junit.Test;

import gr.rmagkos.proximity.model.Point;

/**
 * Integration test for {@link DatabasePointRepository}
 */
public class DatabasePointRepositoryTest {
	private DatabasePointRepository pointRepository;

	private static final String TEST_DATABASE_INIT_SCRIPT_PATH = "/scripts/test_init.sql";

	@Before
	public void setup() {
		JdbcDataSource testDataSource = new JdbcDataSource();
		testDataSource.setURL("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1");
		
		try (Connection connection = testDataSource.getConnection()) {
			connection.createStatement().execute(new String(Files.readAllBytes(Paths.get(getClass().getResource(TEST_DATABASE_INIT_SCRIPT_PATH).toURI()))));
		} catch (SQLException | IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		pointRepository = new DatabasePointRepository();
		pointRepository.setDataSource(testDataSource);
		pointRepository.initRepository();
	}
	
	@Test
	public void nearby() {
		Point point = new Point();
		point.setLabel("Piraeus - Port");
		point.setLatitude(37.947916);
		point.setLongitude(23.641884);
		assertEquals("Alimos - Marine", pointRepository.fetchNearestPointTo(point).getLabel());
	}

	@Test
	public void pointsWithMoreThanZeroHitsReturned() {
		assertEquals(2, pointRepository.fetchPointsHavingHitsMoreThan(0).size());
	}

	@Test
	public void checkUpdateHitCount() {
		Point pointToUpdate = new Point();
		pointToUpdate.setId(1L);
		pointRepository.updateHitCount(pointToUpdate);
		assertEquals(1, pointRepository.fetchPointsHavingHitsMoreThan(1).size());
	}
	
	
}
