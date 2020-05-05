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
	
	//For task 4 only, sorry for being lazy
	Section _section;
	
	/** 
	 * Parameterized constructor of FList
	 * @param courseCode the Course Code column attribute
	 * @param section the Section column attribute
	 * @param courseName the Course Name attribute
	 * @param instructor the Instructor attribute
	 * @param _section the respective section object,created for task 4
	 */
	public FList(String courseCode, String section, String courseName,
			String instructor, Section _section) {
		super();
		this.courseCode = new SimpleStringProperty(courseCode);
		this.section = new SimpleStringProperty(section);
		this.courseName = new SimpleStringProperty(courseName);
		this.instructor = new SimpleStringProperty(instructor);
		CheckBox enroll = new CheckBox();
		this.enroll = enroll;
		this._section = _section;
		
		
		
	}
	
	/**
	 * Copy constructor of FList
	 * @param _flist  FList object to be copied
	 */
	public FList(FList _flist) {
		 _flist.courseCode = this.courseCode; 
		 _flist.section = this.section;
		 _flist.courseName = this.courseName;
		 _flist.instructor = this.instructor;
		 _flist.enroll = this.enroll;
		 _flist._section = this._section;
	}


    /**
     * courseCode getter
     * @return the respective courseCode String
     */
	public String getCourseCode() {
		return courseCode.get();
	}
    
	/**
	 * courseCode setter
	 * @param courseCode Course Code String
	 */
	public void setCourseCode(SimpleStringProperty courseCode) {
		this.courseCode = courseCode;
	}

	/**
	 * Section getter
	 * @return the respective section String
	 */
	public String getSection() {
		return section.get();
	}

	/**
	 * Section getter
	 * @param section Section String
	 */
	public void setSection(SimpleStringProperty section) {
		this.section = section;
	}

	/**
	 * courseName getter
	 * @return the respective Course Name String
	 */
	public String getCourseName() {
		return courseName.get();
	}
    /**
     * courseName setter
     * @param courseName Course Name String
     */
	public void setCourseName(SimpleStringProperty courseName) {
		this.courseName = courseName;
	}
    /**
     * instructor getter
     * @return the respective Instructor Name String
     */
	public String getInstructor() {
		return instructor.get();
	}
	/**
	 * instructor setter
	 * @param instructor Instructor Name String
	 */
	public void setInstructor(SimpleStringProperty instructor) {
		this.instructor = instructor;
	}
	/**
	 * Enroll getter
	 * @return the enroll checkbox
	 */
	public CheckBox getEnroll() {
		return enroll;
	}

	/**
	 * Enroll setter
	 * @param enroll the enroll checkbox
	 */
	public void setEnrollBox(CheckBox enroll) {
		this.enroll = enroll;
	}
	/**
	 * section getter
	 * @return section object
	 */
	public Section get_section() {
		return _section;
	}
	/**
	 * section setter
	 * @param _section the section to be passed
	 */
	public void set_section(Section _section) {
		this._section = _section;
	}
}