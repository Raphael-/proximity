package gr.rmagkos.proximity.service;

import java.util.List;

import javax.jws.WebService;

import gr.rmagkos.proximity.model.Point;

/**
 * Defines a set of methods that should be implemented in order to have a
 * functional proximity service
 */
@WebService
interface ProximityService {
	String getLabelOfNearestPoint(Point pointUnderCheck);

	List<Point> fetchPointsHavingHitsMoreThan(int hitsCount);
}
