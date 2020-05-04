package comp3111.coursescraper;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.HashSet;
import java.util.List;

import comp3111.coursescraper.Scraper;
import comp3111.coursescraper.Course;

public class InstructorSFQScraperTest {
	
	private String baseurl = getClass().getResource("/sfq.html").toString();
	private Scraper scraper = new Scraper();
	private final double delta=1e-5d;
	
	private Instructor findInstructor(List<Instructor> list, String name) {
		for(Instructor instructor: list) {
			if(instructor.getName().equals(name))
				return instructor;
		}
		return null;
	}
	
	@Test
	public void testSFQInstructorAppearOnce() throws Exception {
		List<Instructor> instructors = scraper.scrapeSFQInstructor(baseurl);
		HashSet<String> instructorSet = new HashSet<String>();
		for(Instructor instructor: instructors) {
			instructorSet.add(instructor.getName());
		}
		assertEquals(instructors.size(), instructorSet.size());
	}

	@Test
	public void testSFQInstructorNormal() throws Exception {
		List<Instructor> instructors = scraper.scrapeSFQInstructor(baseurl);
		final String name = "WU, Angela";
		Instructor instructor = findInstructor(instructors, name);
		double score = instructor.getAverage();
		assertEquals(score, 93.8d, delta);
	}
	
	@Test
	public void testSFQInstructorTakeAverage() throws Exception {
		List<Instructor> instructors = scraper.scrapeSFQInstructor(baseurl);
		final String name = "FUNG, Pascale Ngan";
		Instructor instructor = findInstructor(instructors, name);
		double score = instructor.getAverage();
		final double expected = (81.3d + 40.0d) / 2.;
		assertEquals(score, expected, delta);
	}
	
	@Test
	public void testSFQInstructorInvalidInstructor() throws Exception {
		List<Instructor> instructors = scraper.scrapeSFQInstructor(baseurl);
		final String name = "NOT, An Instructor";
		Instructor instructor = findInstructor(instructors, name);
		assertNull(instructor);
	}

}
