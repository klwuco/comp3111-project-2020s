package comp3111.coursescraper;

//This FList class is used for tableView
//the attributes are respective to the table columns 
//Course code, Section, Course Name, Instructor and Enroll respectively
//The enroll is in checkbox type.

import javafx.beans.property.SimpleStringProperty;

import javafx.scene.control.CheckBox;

public class FList {

	private SimpleStringProperty courseCode, section,courseName, instructor;
	
	CheckBox enroll;
	
	
	

	public FList(String courseCode, String section, String courseName,
			String instructor) {
		super();
		this.courseCode = new SimpleStringProperty(courseCode);
		this.section = new SimpleStringProperty(section);
		this.courseName = new SimpleStringProperty(courseName);
		this.instructor = new SimpleStringProperty(instructor);
		CheckBox enroll = new CheckBox();
		this.enroll = enroll;
		
	}
	
	public FList(FList _flist) {
		 _flist.courseCode = this.courseCode; 
		 _flist.section = this.section;
		 _flist.courseName = this.courseName;
		 _flist.instructor = this.instructor;
		 _flist.enroll = this.enroll;
		 
	}


	public String getCourseCode() {
		return courseCode.get();
	}

	public void setCourseCode(SimpleStringProperty courseCode) {
		this.courseCode = courseCode;
	}

	public String getSection() {
		return section.get();
	}

	public void setSection(SimpleStringProperty section) {
		this.section = section;
	}

	public String getCourseName() {
		return courseName.get();
	}

	public void setCourseName(SimpleStringProperty courseName) {
		this.courseName = courseName;
	}

	public String getInstructor() {
		return instructor.get();
	}

	public void setInstructor(SimpleStringProperty instructor) {
		this.instructor = instructor;
	}

	public CheckBox getEnroll() {
		return enroll;
	}

	public void setEnrollBox(CheckBox enroll) {
		this.enroll = enroll;
	}
	
}