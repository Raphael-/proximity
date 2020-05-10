package gr.rmagkos.proximity.repository;

import java.util.List;

import gr.rmagkos.proximity.model.Point;

/**
 * Defines the set of operations a repository of this type should support
 */
public interface PointRepository {
	/**
	 * Retrieves a {@link Point} that is closest to <code>pointToExamine</code>
	 * 
	 * @param pointToExamine
	 * @return said {@link Point}
	 */
	Point fetchNearestPointTo(Point pointToExamine);

	/**
	 * Updates the hit counter of a {@link Point}
	 * 
	 * @param pointToUpdate
	 */
	void updateHitCount(Point pointToUpdate);

	/**
	 * Retrieves a list of {@link Point} that have hit count >=
	 * <code>hitsCount</code>
	 * 
	 * @param hitsCount
	 * @return
	 */
	List<Point> fetchPointsHavingHitsMoreThan(int hitsCount);
}
