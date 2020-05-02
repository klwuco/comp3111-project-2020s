package comp3111.coursescraper;



public class Section {
    private static final int DEFAULT_MAX_SLOT = 20;

    private String sectionID;
    private String sectionCode;
    private String type; 

    private Boolean enrolled;
    private Slot[] slots;
    private int numSlots;
    private String instructor;
	
	
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
    
    public void setSectionID(String sectionID){
        this.sectionID = sectionID;
    }

    public String getSectionID(){
        return sectionID;
    }

    public void setSectionCode(String sectionCode){
        this.sectionCode = sectionCode;
    }

    public String getSectionCode(){
        return sectionCode;
    }

	public void addSlot(Slot slot){
		if (numSlots >= DEFAULT_MAX_SLOT)
			return;
		slots[numSlots++] = slot.clone();
	}

    public Slot[] getSlot() {
        return slots;
    }
    
    public Slot getSlot(int i){
        return slots[i];
    }

    public int getNumSlots() {
		return numSlots;
	}

    public String getType() {
        return type;
    }

    public void setEnrollState(Boolean enroll){
        enrolled = enroll;
    }

    public Boolean getEnrollState(){
        return enrolled;
    }

    @Override
    public String toString() {
		return sectionCode + " (" + sectionID + ")";
	}
    //get the instructor in sections
    public String getInstructor() {
		return instructor;
	}
//set the instructor to sections
	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}


}