package gr.rmagkos.proximity.service;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.jws.WebService;

import gr.rmagkos.proximity.model.Point;
import gr.rmagkos.proximity.repository.PointRepository;

/**
 * Exposes a SOAP API that implements all methods of the interface
 */
@WebService
public class ProximitySoap implements ProximityService {

	private PointRepository pointRepository;

	@Inject
	public ProximitySoap(PointRepository pointRepository) {
		this.pointRepository = pointRepository;
	}

	public String getLabelOfNearestPoint(Point pointUnderCheck) {
		Optional<Point> nearestPoint = Optional.ofNullable(pointRepository.fetchNearestPointTo(pointUnderCheck));
		nearestPoint.ifPresent(point -> pointRepository.updateHitCount(point));
		return nearestPoint.map(point -> point.getLabel()).orElse(null);
	}

	public List<Point> fetchPointsHavingHitsMoreThan(int hitsCount) {
		return pointRepository.fetchPointsHavingHitsMoreThan(hitsCount);
	}

}
