package gr.rmagkos.proximity.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for {@link Point} that focuses on equals and hashCode functionality
 */
public class PointTest {

	@Test
	public void equalsTest() {
		Point a = new Point();
		a.setId(1);
		a.setLatitude(10.10);
		a.setLongitude(11.10);

		assertTrue(a.equals(a));
		assertTrue(a.hashCode() == a.hashCode());

		assertFalse(a.equals(null));
		assertFalse(a.equals(new Integer(1)) && new Integer(1).equals(a));

		Point b = new Point();
		b.setId(2);
		b.setLatitude(10.10);
		b.setLongitude(11.10);

		assertFalse(a.equals(b) && b.equals(a));
		assertFalse(a.hashCode() == b.hashCode());

		Point c = new Point();
		c.setId(2);
		c.setLatitude(10.10);
		c.setLongitude(11.10);

		assertTrue(b.equals(c) && c.equals(b));
		assertTrue(b.hashCode() == c.hashCode());

		Point d = new Point();
		d.setId(2);
		d.setLatitude(11.10);
		d.setLongitude(11.10);

		assertFalse(d.equals(c) && c.equals(d));
		assertFalse(c.hashCode() == d.hashCode());

		Point e = new Point();
		e.setId(2);
		e.setLatitude(11.10);
		e.setLongitude(12.10);

		assertFalse(e.equals(d) && d.equals(e));
		assertFalse(e.hashCode() == d.hashCode());
	}
}
