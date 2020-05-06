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
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
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
	@Test
	public void testAvoidConflictDuringSearching() {
		Button search = (Button)s.lookup("#buttonSearch");
		Button searchAll = (Button)s.lookup("#buttonSearchAll");
		TextField url = (TextField)s.lookup("#textfieldURL");
		TextField term = (TextField)s.lookup("#textfieldTerm");
		TextField subject = (TextField)s.lookup("#textfieldSubject");
		term.setText("1940");
		clickOn(search);
		sleep(100);
		Boolean result = search.isDisabled() && searchAll.isDisabled() && url.isDisabled() && term.isDisabled() && subject.isDisabled() ; 
		assertTrue(result);
	}

	@Test
	public void testAvoidConflictDuringAllSearching() {
		Button search = (Button)s.lookup("#buttonSearch");
		Button searchAll = (Button)s.lookup("#buttonSearchAll");
		TextField url = (TextField)s.lookup("#textfieldURL");
		TextField term = (TextField)s.lookup("#textfieldTerm");
		TextField subject = (TextField)s.lookup("#textfieldSubject");
		term.setText("1930");
		clickOn("#tabAllSubject");
		clickOn(searchAll);
		sleep(100);
		Boolean result = search.isDisabled() && searchAll.isDisabled() && url.isDisabled() && term.isDisabled() && subject.isDisabled() ; 
		assertTrue(result);
	}

	@Test
	public void testConsoleTextNormal() {
		Button search = (Button)s.lookup("#buttonSearch");
		TextField term = (TextField)s.lookup("#textfieldTerm");
		TextArea consoleText = (TextArea)s.lookup("#textAreaConsole");
		String textInMain, textInBackEnd, textInAllSubjectSearch;
		Boolean result;
		term.setText("1940");
		clickOn(search);
		while(true){
			textInMain = consoleText.getText();
			if(!textInMain.equals("")){
				clickOn("#tabStatistic");
				textInBackEnd = consoleText.getText();
				result = !textInMain.equals(textInBackEnd);
				clickOn("#tabAllSubject");
				textInAllSubjectSearch = consoleText.getText();
				result = result && !textInMain.equals(textInAllSubjectSearch) && !textInBackEnd.equals(textInAllSubjectSearch);
				assertTrue(result);
				return;
			}
		} 
	}

}
