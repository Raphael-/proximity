package gr.rmagkos.proximity.repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Enumeration that holds all SQL queries that will be used
 */
public enum Query {
	/**
	 * Query that finds the point that is closest to a given Point, by comparing it
	 * to the Points in database
	 */
	FETCH_NEAREST_POINT,
	/**
	 * Query that returns all points that have exactly or more hits than the given
	 * hits number
	 */
	FETCH_POINTS_BY_HITS,
	/**
	 * Query that increases the hit counter of a Point. The Point that will have its
	 * counter increased, is identified by id
	 */
	INCREMENT_POINT_HITS;

	private static final Properties properties = new Properties();

	private static final String FILENAME = "queries.xml";

	static {
		try (InputStream stream = Query.class.getResourceAsStream(FILENAME)) {
			properties.loadFromXML(stream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String sql() {
		return properties.getProperty(name());
	}

}
