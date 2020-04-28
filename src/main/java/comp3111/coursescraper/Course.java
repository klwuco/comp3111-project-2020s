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
	private boolean isCC;
	
	public Course() {
		sections = new Section[DEFAULT_MAX_SLOT];
		instructors = new Vector<String>();
		targetInstructors = new Vector<String>();
		for (int i = 0; i < DEFAULT_MAX_SLOT; i++) 
			sections[i] = null;
		numSections = 0;
	}

	public void addSection(Section s) {
		if (numSections >= DEFAULT_MAX_SLOT)
			return;
		sections[numSections++] = s;
	}
	public Section[] getSection() {
		return sections;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the exclusion
	 */
	public String getExclusion() {
		return exclusion;
	}

	/**
	 * @param exclusion the exclusion to set
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
	 * @param exclusion the exclusion to set
	 */
	public void setIsCC(boolean isCC) {
		this.isCC = isCC;
	}
	

}
