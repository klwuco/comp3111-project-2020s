package comp3111.coursescraper;

public class Section {
    private static final int DEFAULT_MAX_SLOT = 20;

    private String sectionID;
    private String sectionCode;
    private String type;
    private String instructor;

    private Boolean enrolled;
    private Slot[] slots;
    private int numSlots;
	
    /**
	 * Instructor of the section class
	 * @param section the name of a section which is needed to be construct
	 */
	public Section(String section) {
        sectionID = section.split(" ")[1];
        sectionID = sectionID.substring(1, sectionID.length()-1);
        sectionCode = section.split(" ")[0];
        if(sectionCode.charAt(0) == 'L')
            type = sectionCode.charAt(1) == 'A'? "LEC": "LAB";      
        else
            type = sectionCode.charAt(0) == 'T'? "TUT": null;
		slots = new Slot[DEFAULT_MAX_SLOT];
		for (int i = 0; i < DEFAULT_MAX_SLOT; i++) slots[i] = null;
		numSlots = 0;
    }
    
    /**
     * Set the section ID to section
	 * @param sectionID the section ID of a section
	 */
    public void setSectionID(String sectionID){
        this.sectionID = sectionID;
    }

    /**
     * Get the sectionID of section
	 * @return the section ID of the section
	 */
    public String getSectionID(){
        return sectionID;
    }

    /**
     * Set the section code to section
	 * @param sectionCode the section code of a section
	 */
    public void setSectionCode(String sectionCode){
        this.sectionCode = sectionCode;
    }

    /**
     * Get the section code of section
	 * @return the section code of the section
	 */
    public String getSectionCode(){
        return sectionCode;
    }

    /**
     * Add a slot to section
	 * @param slot the slot which is needed to be added
	 */
	public void addSlot(Slot slot){
		if (numSlots >= DEFAULT_MAX_SLOT)
			return;
		slots[numSlots++] = slot.clone();
	}

    /**
     * Get all slots in section
	 * @return the array of slot of the section
	 */
    public Slot[] getSlot() {
        return slots;
    }
    
    /**
     * Get a particular slot in section
     * @param i the index of the slot in the section
	 * @return the slot in the section
	 */
    public Slot getSlot(int i){
        return slots[i];
    }

    /**
     * Get the number of slot in section
	 * @return the number of slot in the section
	 */
    public int getNumSlots() {
		return numSlots;
	}

    /**
     * Get the type of section
	 * @return the string of type of the section
	 */
    public String getType() {
        return type;
    }

    /**
     * Set the enrollment state to section
	 * @param enroll the boolean value indicates the enrollment state of a section
	 */
    public void setEnrollState(Boolean enroll){
        enrolled = enroll;
    }

    /**
     * Get the indicator of enrollment state
	 * @return the boolean value of enrollment state of the section
	 */
    public Boolean getEnrollState(){
        return enrolled;
    }

    /**
     * Get the instructor of section
	 * @return the name of instructor belongs to the section
	 */
    public String getInstructor() {
		return instructor;
	}

    /**
     * Set the instructor to section
	 * @param instructor the name of instructor belongs to the section
	 */
	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}

    /**
     * Get the printed information of section
	 * @return the string of section code and section ID
	 */
    @Override
    public String toString() {
		return sectionCode + " (" + sectionID + ")";
	}


}