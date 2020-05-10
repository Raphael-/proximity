package gr.rmagkos.proximity.repository;

import java.util.List;

import gr.rmagkos.proximity.model.Point;

/**
 * Defines the set of operations a repository of this type should support
 */
public interface PointRepository {
	Point fetchNearestPointTo(Point pointToExamine);

	void updateHitCount(Point pointToUpdate);

	List<Point> fetchPointsHavingHitsMoreThan(int hitsCount);
}
