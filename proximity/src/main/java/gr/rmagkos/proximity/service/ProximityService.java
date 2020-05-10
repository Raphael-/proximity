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
	/**
	 * Retrieves the label (friendly name) of a {@link Point} that is nearest to the
	 * <code>pointUnderCheck</code>
	 * 
	 * @param pointUnderCheck
	 * @return
	 */
	String getLabelOfNearestPoint(Point pointUnderCheck);

	/**
	 * Retrieves a list of {@link Point} that have hit count >=
	 * <code>hitsCount</code>
	 * 
	 * @param hitsCount
	 * @return
	 */
	List<Point> fetchPointsHavingHitsMoreThan(int hitsCount);
}
