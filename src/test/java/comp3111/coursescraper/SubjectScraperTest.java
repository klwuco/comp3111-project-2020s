package comp3111.coursescraper;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import java.util.List;
import java.util.Vector;

import comp3111.coursescraper.Scraper;

public class SubjectScraperTest {
	
	private String url, term;
	private String[] subjects;
	private Scraper scraper = new Scraper();

	
	@Test
	public void testSearchSubjectCountNormal() throws Exception {
		final int expected = 75;
		int result;
		url = "https://w5.ab.ust.hk/wcq/cgi-bin/";
		term = "1910";
		subjects = scraper.scrapeSubject(url,term);
		while(true){
			if(subjects!=null){
				result = subjects.length;
				assertEquals(result, expected);
				return;
			}
		}
	}
	
	@Test
	public void testSearchSubjectInvalidTerm() throws Exception {
		url = "https://w5.ab.ust.hk/wcq/cgi-bin/";
		term = "1810";
		subjects = scraper.scrapeSubject(url,term);
		assertNull(subjects);
	}

	@Test
	public void testSearchSubjectInvalidUrl() throws Exception {
		url = "https://testing.ust.hk/";
		term = "1930";
		subjects = scraper.scrapeSubject(url,term);
		assertNull(subjects);
	}

}
