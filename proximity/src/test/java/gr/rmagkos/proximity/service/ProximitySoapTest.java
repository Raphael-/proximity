package gr.rmagkos.proximity.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;

import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import gr.rmagkos.proximity.model.Point;
import gr.rmagkos.proximity.repository.PointRepository;

/**
 * Unit test for {@link ProximitySoap}
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ProximitySoapTest {

	@Mock
	PointRepository repository;

	@Test
	public void whenNoPointIsFoundInDatabase_thenServiceReturnsNull() {
		repository = Mockito.mock(PointRepository.class);
		ProximityService service = new ProximitySoap(repository);

		Mockito.when(repository.fetchNearestPointTo(Matchers.any(Point.class))).thenReturn(null);
		
		assertEquals(null, service.getLabelOfNearestPoint(new Point()));
	}
	
	@Test
	public void whenPointIsFoundInDatabase_thenServiceUpdateHitcount_andReturnPointLabel() {
		repository = Mockito.mock(PointRepository.class);
		ProximityService service = new ProximitySoap(repository);
		
		Point pointFoundInDatabase = new Point();
		pointFoundInDatabase.setId(1L);
		pointFoundInDatabase.setHits(0);
		pointFoundInDatabase.setLabel("Test Point");
		pointFoundInDatabase.setLatitude(11.10);
		pointFoundInDatabase.setLongitude(10.11);

		Mockito.when(repository.fetchNearestPointTo(Matchers.any(Point.class))).thenReturn(pointFoundInDatabase);
		
		assertEquals(pointFoundInDatabase.getLabel(), service.getLabelOfNearestPoint(new Point()));
		ArgumentCaptor<Point> pointCaptor = ArgumentCaptor.forClass(Point.class);
		Mockito.verify(repository, times(1)).updateHitCount(pointCaptor.capture());
		
		assertEquals(pointFoundInDatabase, pointCaptor.getValue());
		
	}
	
	@Test
	public void whenServiceCalled_thenDelegatesToRepository() {
		repository = Mockito.mock(PointRepository.class);
		ProximityService service = new ProximitySoap(repository);
		
		Integer targetHitCount = 12;
		
		Mockito.when(repository.fetchPointsHavingHitsMoreThan(Matchers.anyInt())).thenReturn(Collections.<Point>emptyList());
		
		assertTrue(service.fetchPointsHavingHitsMoreThan(targetHitCount).isEmpty());
		ArgumentCaptor<Integer> pointCaptor = ArgumentCaptor.forClass(Integer.class);
		Mockito.verify(repository, times(1)).fetchPointsHavingHitsMoreThan(pointCaptor.capture());
		
		assertEquals(targetHitCount, pointCaptor.getValue());
		
	}
}
