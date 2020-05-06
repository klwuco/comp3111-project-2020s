/**
 * 
 * You might want to uncomment the following code to learn testFX. Sorry, no tutorial session on this.
 * 
 */
package comp3111.coursescraper;

import static org.junit.Assert.*;

import org.junit.Test;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit.ApplicationTest;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;


public class FxTest extends ApplicationTest {

	private Scene s;
	
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
		clickOn("#tabFilter");
		clickOn("#checkboxAM");
		CheckBox am = (CheckBox)s.lookup("#checkboxAM");
		sleep(1000);
		assertTrue(am.isSelected());
	}
	
	@Test
	public void testCheckBoxPM() {
		clickOn("#tabFilter");
		clickOn("#checkboxPM");
		CheckBox pm = (CheckBox)s.lookup("#checkboxPM");
		sleep(1000);
		assertTrue(pm.isSelected());
	}
	
	@Test
	public void testCheckBoxAMPM() {
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
		clickOn("#tabFilter");
		clickOn("#checkboxMon");
		CheckBox mon = (CheckBox)s.lookup("#checkboxMon");
		sleep(1000);
		assertTrue(mon.isSelected());
	}
	
	@Test
	public void testCheckBoxTue() {
		clickOn("#tabFilter");
		clickOn("#checkboxTue");
		CheckBox tue = (CheckBox)s.lookup("#checkboxTue");
		sleep(1000);
		assertTrue(tue.isSelected());
	}
	
	@Test
	public void testCheckBoxWed() {
		clickOn("#tabFilter");
		clickOn("#checkboxWed");
		CheckBox wed = (CheckBox)s.lookup("#checkboxWed");
		sleep(1000);
		assertTrue(wed.isSelected());
	}
	
	@Test
	public void testCheckBoxThu() {
		clickOn("#tabFilter");
		clickOn("#checkboxThu");
		CheckBox thu = (CheckBox)s.lookup("#checkboxThu");
		sleep(1000);
		assertTrue(thu.isSelected());
	}
	
	@Test
	public void testCheckBoxFri() {
		clickOn("#tabFilter");
		clickOn("#checkboxFri");
		CheckBox fri = (CheckBox)s.lookup("#checkboxFri");
		sleep(1000);
		assertTrue(fri.isSelected());
	}
	
	@Test
	public void testCheckBoxSat() {
		clickOn("#tabFilter");
		clickOn("#checkboxSat");
		CheckBox sat = (CheckBox)s.lookup("#checkboxSat");
		sleep(1000);
		assertTrue(sat.isSelected());
	}
	
	@Test
	public void testCheckBoxCC() {
		clickOn("#tabFilter");
		clickOn("#checkboxCC");
		CheckBox cc = (CheckBox)s.lookup("#checkboxCC");
		sleep(1000);
		assertTrue(cc.isSelected());
	}
	
	@Test
	public void testCheckBoxEx() {
		clickOn("#tabFilter");
		clickOn("#checkboxNoEx");
		CheckBox ex = (CheckBox)s.lookup("#checkboxNoEx");
		sleep(1000);
		assertTrue(ex.isSelected());
	}
	
	@Test
	public void testCheckBoxlt() {
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
	

	


	
	
	
	
	
	
	
	
	
}
