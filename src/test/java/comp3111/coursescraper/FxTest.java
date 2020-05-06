/**
 * 
 * You might want to uncomment the following code to learn testFX. Sorry, no tutorial session on this.
 * 
 */
package comp3111.coursescraper;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit.ApplicationTest;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
	
	@Test
	public void testButtonSelectAll() {
		clickOn("#tabMain");
		clickOn("#buttonSearch");
		sleep(10000);
		clickOn("#tabFilter");
		clickOn("#buttonSelectAll");
		Button b = (Button)s.lookup("#buttonSelectAll");
		CheckBox am = (CheckBox)s.lookup("#checkboxAM");
		CheckBox pm = (CheckBox)s.lookup("#checkboxPM");
		CheckBox mon = (CheckBox)s.lookup("#checkboxMon");
		CheckBox tue = (CheckBox)s.lookup("#checkboxTue");
		CheckBox wed = (CheckBox)s.lookup("#checkboxWed");
		CheckBox thu = (CheckBox)s.lookup("#checkboxThu");
		CheckBox fri = (CheckBox)s.lookup("#checkboxFri");
		CheckBox sat = (CheckBox)s.lookup("#checkboxSat");
		CheckBox cc = (CheckBox)s.lookup("#checkboxCC");
		CheckBox noEx = (CheckBox)s.lookup("#checkboxNoEx");
		CheckBox lt = (CheckBox)s.lookup("#checkboxWLoT");
		sleep(1000);
		assertEquals(b.getText(),"De-select All");
		assertTrue(am.isSelected());
		assertTrue(pm.isSelected());
		assertTrue(mon.isSelected());
		assertTrue(tue.isSelected());
		assertTrue(wed.isSelected());
		assertTrue(thu.isSelected());
		assertTrue(fri.isSelected());
		assertTrue(sat.isSelected());
		assertTrue(cc.isSelected());
		assertTrue(noEx.isSelected());
		assertTrue(lt.isSelected());
	}
	
	@Test
	public void testButtonDeSelectAll() {
		clickOn("#tabMain");
		clickOn("#buttonSearch");
		sleep(10000);
		clickOn("#tabFilter");
		clickOn("#buttonSelectAll");
		clickOn("#buttonSelectAll");
		Button b = (Button)s.lookup("#buttonSelectAll");
		CheckBox am = (CheckBox)s.lookup("#checkboxAM");
		CheckBox pm = (CheckBox)s.lookup("#checkboxPM");
		CheckBox mon = (CheckBox)s.lookup("#checkboxMon");
		CheckBox tue = (CheckBox)s.lookup("#checkboxTue");
		CheckBox wed = (CheckBox)s.lookup("#checkboxWed");
		CheckBox thu = (CheckBox)s.lookup("#checkboxThu");
		CheckBox fri = (CheckBox)s.lookup("#checkboxFri");
		CheckBox sat = (CheckBox)s.lookup("#checkboxSat");
		CheckBox cc = (CheckBox)s.lookup("#checkboxCC");
		CheckBox noEx = (CheckBox)s.lookup("#checkboxNoEx");
		CheckBox lt = (CheckBox)s.lookup("#checkboxWLoT");
		sleep(1000);
		assertEquals(b.getText(),"Select All");
		assertTrue(!am.isSelected());
		assertTrue(!pm.isSelected());
		assertTrue(!mon.isSelected());
		assertTrue(!tue.isSelected());
		assertTrue(!wed.isSelected());
		assertTrue(!thu.isSelected());
		assertTrue(!fri.isSelected());
		assertTrue(!sat.isSelected());
		assertTrue(!cc.isSelected());
		assertTrue(!noEx.isSelected());
		assertTrue(!lt.isSelected());
	}
	
	@Test
	public void testCheckBoxAM() {
		clickOn("#tabMain");
		clickOn("#buttonSearch");
		sleep(10000);
		clickOn("#tabFilter");
		clickOn("#checkboxAM");
		CheckBox am = (CheckBox)s.lookup("#checkboxAM");
		sleep(1000);
		assertTrue(am.isSelected());
	}
	
	@Test
	public void testCheckBoxPM() {
		clickOn("#tabMain");
		clickOn("#buttonSearch");
		sleep(10000);
		clickOn("#tabFilter");
		clickOn("#checkboxPM");
		CheckBox pm = (CheckBox)s.lookup("#checkboxPM");
		sleep(1000);
		assertTrue(pm.isSelected());
	}
	
	@Test
	public void testCheckBoxAMPM() {
		clickOn("#tabMain");
		clickOn("#buttonSearch");
		sleep(10000);
		clickOn("#tabFilter");
		clickOn("#checkboxAM");
		clickOn("#checkboxPM");
		CheckBox am = (CheckBox)s.lookup("#checkboxAM");
		CheckBox pm = (CheckBox)s.lookup("#checkboxPM");
		sleep(1000);
		assertTrue(am.isSelected());
		assertTrue(pm.isSelected());
	}
	
	@Test
	public void testCheckBoxMon() {
		clickOn("#tabMain");
		clickOn("#buttonSearch");
		sleep(10000);
		clickOn("#tabFilter");
		clickOn("#checkboxMon");
		CheckBox mon = (CheckBox)s.lookup("#checkboxMon");
		sleep(1000);
		assertTrue(mon.isSelected());
	}
	
	@Test
	public void testCheckBoxTue() {
		clickOn("#tabMain");
		clickOn("#buttonSearch");
		sleep(10000);
		clickOn("#tabFilter");
		clickOn("#checkboxTue");
		CheckBox tue = (CheckBox)s.lookup("#checkboxTue");
		sleep(1000);
		assertTrue(tue.isSelected());
	}
	
	@Test
	public void testCheckBoxWed() {
		clickOn("#tabMain");
		clickOn("#buttonSearch");
		sleep(10000);
		clickOn("#tabFilter");
		clickOn("#checkboxWed");
		CheckBox wed = (CheckBox)s.lookup("#checkboxWed");
		sleep(1000);
		assertTrue(wed.isSelected());
	}
	
	@Test
	public void testCheckBoxThu() {
		clickOn("#tabMain");
		clickOn("#buttonSearch");
		sleep(10000);
		clickOn("#tabFilter");
		clickOn("#checkboxThu");
		CheckBox thu = (CheckBox)s.lookup("#checkboxThu");
		sleep(1000);
		assertTrue(thu.isSelected());
	}
	
	@Test
	public void testCheckBoxFri() {
		clickOn("#tabMain");
		clickOn("#buttonSearch");
		sleep(10000);
		clickOn("#tabFilter");
		clickOn("#checkboxFri");
		CheckBox fri = (CheckBox)s.lookup("#checkboxFri");
		sleep(1000);
		assertTrue(fri.isSelected());
	}
	
	@Test
	public void testCheckBoxSat() {
		clickOn("#tabMain");
		clickOn("#buttonSearch");
		sleep(10000);
		clickOn("#tabFilter");
		clickOn("#checkboxSat");
		CheckBox sat = (CheckBox)s.lookup("#checkboxSat");
		sleep(1000);
		assertTrue(sat.isSelected());
	}
	
	@Test
	public void testCheckBoxCC() {
		clickOn("#tabMain");
		clickOn("#buttonSearch");
		sleep(10000);
		clickOn("#tabFilter");
		clickOn("#checkboxCC");
		CheckBox cc = (CheckBox)s.lookup("#checkboxCC");
		sleep(1000);
		assertTrue(cc.isSelected());
	}
	
	@Test
	public void testCheckBoxEx() {
		clickOn("#tabMain");
		clickOn("#buttonSearch");
		sleep(10000);
		clickOn("#tabFilter");
		clickOn("#checkboxNoEx");
		CheckBox ex = (CheckBox)s.lookup("#checkboxNoEx");
		sleep(1000);
		assertTrue(ex.isSelected());
	}
	
	@Test
	public void testCheckBoxlt() {
		clickOn("#tabMain");
		clickOn("#buttonSearch");
		sleep(10000);
		clickOn("#tabFilter");
		clickOn("#checkboxWLoT");
		CheckBox lt = (CheckBox)s.lookup("#checkboxWLoT");
		sleep(1000);
		assertTrue(lt.isSelected());
	}
	
	@Test
	public void testCheckTableView() {
		clickOn("#tabList");
		
		TableView<FList>  tv = (TableView<FList>)s.lookup("#tableView");
		sleep(1000);
		assertTrue(!tv.isEditable());
	}
	
	@Ignore
	public void testExample() {
		List<Course> courses = scraper.scrape(baseurl, "1910", "COMP");
		assertTrue(courses.size()>0);
	}

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
