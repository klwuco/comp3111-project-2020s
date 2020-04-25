package comp3111.coursescraper;



public class Section {
   private static final int DEFAULT_MAX_SLOT = 20;

   private String sectionID;
   private String sectionCode;
   private Boolean enrolled;
   private Slot[] slots;
   private int numSlots;
	
	public Section(String section) {
		slots = new Slot[DEFAULT_MAX_SLOT];
		for (int i = 0; i < DEFAULT_MAX_SLOT; i++) slots[i] = null;
		numSlots = 0;
	}

	public void addSlot(Slot slot){
		if (numSlots >= DEFAULT_MAX_SLOT)
			return;
		slots[numSlots++] = slot.clone();
	}

}