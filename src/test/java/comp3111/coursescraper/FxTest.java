/**
 * 
 * You might want to uncomment the following code to learn testFX. Sorry, no tutorial session on this.
 * 
 */
package comp3111.coursescraper;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.Test;
import org.junit.Ignore;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit.ApplicationTest;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;


public class FxTest extends ApplicationTest {

	private Scene s;
	private String baseurl = "file:/" + (new File("src/main/resources").getAbsolutePath());
	Scraper scraper = new Scraper();
	
	@Override
	public void start(Stage stage) throws Exception {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/ui.fxml"));
   		VBox root = (VBox) loader.load();
   		Scene scene =  new Scene(root);
   		stage.setScene(scene);
   		stage.setTitle("Course Scraper");
   		stage.show();
   		s = scene;
	}

	@Ignore
	public void testExample() {
		List<Course> courses = scraper.scrape(baseurl, "1910", "COMP");
		assertTrue(courses.size()>0);
	}
//	@Test
//	public void testButton() {
//		clickOn("#tabSfq");
//		clickOn("#buttonInstructorSfq");
//		Button b = (Button)s.lookup("#buttonInstructorSfq");
//		sleep(1000);
//		assertTrue(b.isDisabled());
//	}
}
