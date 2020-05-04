package comp3111.coursescraper;

import java.util.Vector;

public class Course {
	private static final int DEFAULT_MAX_SLOT = 20;
	
	private String title; 
	private String description;
	private String exclusion;
	private Vector<String> instructors;
	private Vector<String> targetInstructors;
	private Section [] sections;
	private int numSections;
	private boolean isCC;    //isCC is added with respective functions.
	
	/**
	 * Instructor of the course class
	 */
	public Course() {
		sections = new Section[DEFAULT_MAX_SLOT];
		instructors = new Vector<String>();
		targetInstructors = new Vector<String>();
		for (int i = 0; i < DEFAULT_MAX_SLOT; i++) 
			sections[i] = null;
		numSections = 0;
	}

	/**
	 * Add a section title to course
	 * @param s the section needed to be added
	 */
	public void addSection(Section s) {
		if (numSections >= DEFAULT_MAX_SLOT)
			return;
		sections[numSections++] = s;
	}

	/**
	 * Get all sections in course
	 * @return the array of section in the course
	 */
	public Section[] getSection() {
		return sections;
	}

	/**
	 * Get the course title to course
	 * @return the title of the course
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set the course title to course
	 * @param title the title of the course
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Get the course code of course
	 * @return the course code of the course
	 */
	public String getCourseCode() {
		String[] c = title.split(" ");
		return c[0] + c[1];
	}

	/**
	 * Get the description of course
	 * @return the description of the course
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set description to set 
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Get the exclusion of course
	 * @return the exclusion
	 */
	public String getExclusion() {
		return exclusion;
	}

	/**
	 * Set the exclusion to course
	 * @param exclusion the exclusion of the course
	 */
	public void setExclusion(String exclusion) {
		this.exclusion = exclusion;
	}

	public void addInstructor(String instructor) {
		if(!instructors.contains(instructor))
			instructors.add(instructor);

	}

	public Vector<String> getInstructor() {
		return instructors;
	}

	public void addFilterInstructor(String instructor) {
		if(!targetInstructors.contains(instructor))
			targetInstructors.add(instructor);

	}

	public Vector<String> getFilterInstructor() {
		return targetInstructors;
	}

	/**
     * Get the number of sections in courses
	 * @return the number of sections in courses
	 */
	public int getNumSections(){
		return numSections;
	}
	
	/**
	 * @return is it CC
	 */
	public boolean getIsCC() {
		return isCC;
	}
	
	/**
	 * @param isCC set is common core
	 */
	public void setIsCC(boolean isCC) {
		this.isCC = isCC;
	}
	


}
