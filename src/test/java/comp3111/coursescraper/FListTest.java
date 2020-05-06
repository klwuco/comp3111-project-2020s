package comp3111.coursescraper;

import static org.junit.Assert.*;

import org.junit.Test;

import javafx.beans.property.SimpleStringProperty;

import javafx.scene.control.CheckBox;

public class FListTest {
	
	

	@Test
	public void testSetCourseName() {
		FList i = new FList();
		i.setCourseName(new SimpleStringProperty("Software Engineering (4 units)"));
		assertEquals(i.getCourseName(), "Software Engineering (4 units)");
	}
	
	@Test
	public void testSetSection() {
		FList i = new FList();
		i.setSection(new SimpleStringProperty("LA1"));
		assertEquals(i.getSection(), "LA1");
	}
	
	@Test
	public void testSetCourseCode() {
		FList i = new FList();
		i.setCourseCode(new SimpleStringProperty("Comp3111"));
		assertEquals(i.getCourseCode(), "Comp3111");
	}
	

	

}
