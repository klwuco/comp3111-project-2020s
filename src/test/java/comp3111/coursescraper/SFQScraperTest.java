package comp3111.coursescraper;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

import comp3111.coursescraper.Scraper;
import comp3111.coursescraper.Course;

public class SFQScraperTest {
	
	private String baseurl = getClass().getResource("/sfq.html").toString();
	private Scraper scraper = new Scraper();
	private final double delta=1e-5d;

	@Before
	public void setup() throws Exception{
		scraper.scrapeSFQ(baseurl);
	}
	
	@Test
	public void testSFQCourseNormal() {
		Course course = new Course();
		course.setTitle("BIEN 4000A");
		double score = scraper.SFQLookUp(course.getCourseCode());
		assertEquals(score, 58.3d, delta);
	}
	
	@Test
	public void testSFQCourseTakeAverage() {
		Course course = new Course();
		course.setTitle("COMP 1021");
		double score = scraper.SFQLookUp(course.getCourseCode());
		final double expected = (91.5d + 88.5d + 91.0d) / 3.;
		assertEquals(score, expected, delta);
	}
	
	@Test
	public void testSFQCourseInvalidCourse() {
		Course course = new Course();
		course.setTitle("TEST 9999");
		double score = scraper.SFQLookUp(course.getCourseCode());
		assertEquals(score, Double.NaN, delta);
	}

}
