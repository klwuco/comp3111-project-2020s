package comp3111.coursescraper;

import java.awt.event.ActionEvent;
import java.time.LocalTime;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType; 
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.CheckBox;


import java.util.Arrays;
import java.util.ArrayList;
import java.util.Random;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;
import java.util.Collections;


public class Controller {

	private String searchedUrl;

	private String searchedTerm;

    private String[] subjects;

    private String[] consoleText = initializeStringArray();

    private Scraper scraper = new Scraper();

	private List<Course> courses = new ArrayList<Course>();
    
    private List<FList> filteredList = new ArrayList<FList>();
    
    private List<FList> enrolledList = new ArrayList<FList>();
    
    private List<CheckBox> enrollBox = new ArrayList<CheckBox>();
    
    private List<Label> timeTableEntries = new ArrayList<Label>();

	private HashSet<Color> timetableColors = new HashSet<Color>();
  
    private LocalTime noon = LocalTime.parse("12:00:00");

    enum TabLabel {
        Main,
        BackEnd,
        Filter,
        List,
        Timetable,
        AllSubject,
        SFQ
    }

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab tabMain;

    @FXML
    private Tab tabStatistic;

    @FXML
    private Tab tabFilter;

    @FXML
    private Tab tabList;

    @FXML
    private Tab tabTimetable;

    @FXML
    private Tab tabAllSubject;

    @FXML
    private ProgressBar progressbar;

	@FXML
    private ComboBox<?> comboboxTimeSlot;

    @FXML
    private TextField textfieldTerm;

    @FXML
    private TextField textfieldSubject;

    @FXML
    private TextField textfieldURL;

    @FXML
    private TextField textfieldSfqUrl;

	@FXML
    private Button buttonSearch;

	@FXML
    private Button buttonSearchAll;

    @FXML
    private Button buttonSfqEnrollCourse;

    @FXML
    private Button buttonInstructorSfq;
        
    @FXML
    private Button buttonSelectAll;

	@FXML
    private TextArea textAreaConsole;
    
    @FXML
    private CheckBox checkboxAM;
    
    @FXML
    private CheckBox checkboxPM;
    
    @FXML
    private CheckBox checkboxMon;
    
    @FXML
    private CheckBox checkboxTue;
    
    @FXML
    private CheckBox checkboxWed;
    
    @FXML
    private CheckBox checkboxThu;
    
    @FXML
    private CheckBox checkboxFri;
    
    @FXML
    private CheckBox checkboxSat;
    
    @FXML
    private CheckBox checkboxCC;
    
    @FXML
    private CheckBox checkboxNoEx;
    
    @FXML
    private CheckBox checkboxWLoT;
    
    @FXML
    private TableView<FList> tableView;
    
    @FXML
    private TableColumn<FList,String> courseCode;
    
    @FXML
    private TableColumn<FList, String> section;
    
    @FXML
    private TableColumn<FList, String> courseName;
    
    @FXML
    private TableColumn<FList, String> instructor;
    
    @FXML
    private TableColumn<FList, CheckBox> enroll;
    
    
    
    /**
     * initialize the tableView
     */
    @FXML
    public void initialize() {
    	courseCode.setCellValueFactory(new PropertyValueFactory<FList,String>("courseCode"));
    	section.setCellValueFactory(new PropertyValueFactory<FList,String>("section"));
    	courseName.setCellValueFactory(new PropertyValueFactory<FList,String>("courseName"));
    	instructor.setCellValueFactory(new PropertyValueFactory<FList,String>("instructor"));
    	enroll.setCellValueFactory(new PropertyValueFactory<FList,CheckBox>("enroll"));
    	
    	//edit table column state, also done in ui.fxml
    	//so only enroll column is set editable
    	tableView.setEditable(false);
    	courseCode.setEditable(false);
    	section.setEditable(false);
    	courseName.setEditable(false);
    	instructor.setEditable(false);
    	
    
    	
    	tableView.setItems(getList());
    	
    	
    	
    }
    
    /**
     * //create data to fill in in initializing tableView
     * //Since the list is filled during runtime, nothing is required to be imported by observable list
     * //so import a blank sheet
     * @return ObservableList getList for initializing the tableView
     */
    public ObservableList<FList> getList(){
    	ObservableList<FList> startList= FXCollections.observableArrayList();
    	
    	
    	
    	
    	

    	
    	return startList;
    	
    }
    
    
    /**
	 * Obtain a subjects list when the searchAll button in All Subjects Search tab is clicked at the first time
	 * Search all courses information of all subjects in the subjects list when the searchAll button in All Subjects Search tab is clicked at the second time
	 */
    @FXML
    void allSubjectSearch() {
        new Thread(() -> {
			enableTabInput(false);
			// buttonSearchAll.setDisable(true);
            if(!subjectIsSearched()) {
				enableTabInput(true);
				// buttonSearchAll.setDisable(false);
				return;
			}
            progressbar.setProgress(0);
            final double increment = 1.0 / subjects.length;
            int counted_course = 0;
            courses = new Vector<Course>();
            for (String subject : subjects) {
                counted_course = searchCourse(subject);
                System.out.println(subject + " is done");
                Platform.runLater( () -> progressbar.setProgress(progressbar.getProgress() + increment) );
                try {Thread.sleep(100);} catch (Exception e) {}
            }
            progressbar.setProgress(1);

            String newline = "Total Number of Courses fetched : ";
            newline += Integer.toString(counted_course) + "\n";
            Platform.runLater(() -> {filter();}); //For doing list after searching,without filter
			enableTabInput(true);
			enableSFQInstructorButton();
			printTextInConsole(newline, TabLabel.AllSubject.ordinal());
        }).start();
    }
   
    /** 
     * Prints (unadjusted) SFQ score of all instructors on the console in GUI.
     * (task 6)
     */
    @FXML
    void findInstructorSfq() {
    	List<Instructor> instructors;
    	try {
    	instructors = scraper.scrapeSFQInstructor(textfieldSfqUrl.getText());
    	}catch(Exception e){
            Platform.runLater( () -> {
                final Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("404 NOT FOUND! \n Please Check the Base URL.");
                alert.showAndWait();
            });
            return;
    	}
    	String texts = "The (unadjusted) SFQ score for instructors:\n";
    	for(Instructor instructor: instructors) {
    		double score = instructor.getAverage();
    		texts += String.format("%s: %.2f\n", instructor.getName(), score);
    	}
    	consoleText[TabLabel.SFQ.ordinal()] = "";
    	printTextInConsole(texts, TabLabel.SFQ.ordinal());
    }
    
    /** 
     * Prints (unadjusted) SFQ score of enrolled courses on the console in GUI.
     * The button is enabled only after a search or all subject search is performed.
     * (task 6)
     */
    @FXML
    void findSfqEnrollCourse(){
    	try {
    		scraper.scrapeSFQ(textfieldSfqUrl.getText());
    	} catch(Exception e) {
            Platform.runLater( () -> {
                final Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("404 NOT FOUND! \n Please Check the Base URL.");
                alert.showAndWait();
            });
    	}
    	String texts = "The (unadjusted) SFQ score for your enrolled course(s):\n";
		HashSet<String> courseList = new HashSet<String>();
    	for(FList flist: enrolledList) {
    		String courseCode = flist.getCourseCode();
    		// If no duplicates
    		if(!courseList.contains(courseCode)) {
    			courseList.add(courseCode);
    			// Look up score
    			double score = scraper.SFQLookUp(flist.getCourseCode());
        		texts += String.format("%s: %.2f\n", flist.getCourseCode(), score);
    		}
    	}
    	consoleText[TabLabel.SFQ.ordinal()] = "";
    	printTextInConsole(texts, TabLabel.SFQ.ordinal());
    	
    }

	/**
	 * Obtain a subjects list and search all courses information of a subject when the search button in Main tab is clicked
	 */
    @FXML
    void search() {

        new Thread(() -> {
			enableTabInput(false);
            subjectIsSearched();
			courses = new Vector<Course>();
            if(subjects != null)
                searchCourse(textfieldSubject.getText());
			enableTabInput(true);
            Platform.runLater(() -> {filter();}); //For doing list after searching,without filter
            enableSFQInstructorButton();
        }).start();
    	
    }

	/**
	 * Change text message in the console when tab is Changed
	 */
    @FXML
    void onTabChange(){
        Platform.runLater( () -> 
            textAreaConsole.setText(consoleText[tabPane.getSelectionModel().getSelectedIndex()])
        );
    }
    
    /**
     * Task2
     * Imply the behavior of Selected-All and De-selected All button
     */
    @FXML
	void SelectAll(){
    	if(buttonSelectAll.getText().equals("Select All")) {
    		buttonSelectAll.setText("De-select All");
    		checkboxAM.setSelected(true);
    		checkboxPM.setSelected(true);
    		checkboxMon.setSelected(true);
    		checkboxTue.setSelected(true);
    		checkboxWed.setSelected(true);
    		checkboxThu.setSelected(true);
    		checkboxFri.setSelected(true);
    		checkboxSat.setSelected(true);
    		checkboxCC.setSelected(true);
    		checkboxNoEx.setSelected(true);
    		checkboxWLoT.setSelected(true);
    		filter();
    
    	}
    	
    	else if(buttonSelectAll.getText().equals("De-select All")) {
    		buttonSelectAll.setText("Select All");
    		checkboxAM.setSelected(false);
    		checkboxPM.setSelected(false);
    		checkboxMon.setSelected(false);
    		checkboxTue.setSelected(false);
    		checkboxWed.setSelected(false);
    		checkboxThu.setSelected(false);
    		checkboxFri.setSelected(false);
    		checkboxSat.setSelected(false);
    		checkboxCC.setSelected(false);
    		checkboxNoEx.setSelected(false);
    		checkboxWLoT.setSelected(false);
    		filter();
    	}
    	
	}
    
    /**
     * Task2,3
     * Handling the filter result, while updating the filteredList and tableView for task 3
     */
    @FXML
    void filter() {
    	//Clear the interface and the filteredList
    	// textAreaConsole.clear();
   
    	tableView.getItems().clear();
    	 	
    	filteredList.clear();
    	
    	//start the filtering
    	for (Course c : courses) {
   
    		//if the course is not CC and the cc box is checked, skip the course
    		if(checkboxCC.isSelected()) {
    			if(!c.getIsCC()) {
    				continue;
    			}
    		}
    		//if the NoEx is checked while the course have exclusions, skip the course
    		if(checkboxNoEx.isSelected()) {
    			if(!c.getExclusion().equals("null")) {
    				continue;
    			}
    		}
    		
    		//using different flag to filter different requirements
    		boolean isLoRT = true;
    		if(checkboxWLoT.isSelected()) {isLoRT=false;}
    		boolean isAM   = true;
    		if(checkboxAM.isSelected()) {isAM=false;}
    		boolean isPM   = true;
    		if(checkboxPM.isSelected()) {isPM=false;}
    		boolean isAMPM = true;
    		if(checkboxAM.isSelected() && checkboxPM.isSelected()) {isAMPM=false;}
    		boolean isMon  = true;
    		if(checkboxMon.isSelected()) {isMon=false;}
    		boolean isTue  = true;
    		if(checkboxTue.isSelected()) {isTue=false;}
    		boolean isWed  = true;
    		if(checkboxWed.isSelected()) {isWed=false;}
    		boolean isThu  = true;
    		if(checkboxThu.isSelected()) {isThu=false;}
    		boolean isFri  = true;
    		if(checkboxFri.isSelected()) {isFri=false;}
    		boolean isSat  = true;
    		if(checkboxSat.isSelected()) {isSat=false;}
    		
    		
    		for (Section section : c.getSection()) {
                if(section != null){
                	if(checkboxWLoT.isSelected() && !isLoRT) {
                		//Checking With Labs or Tutorial
                		if(section.getType()=="LAB"||section.getType()=="TUT") 
                			isLoRT = true;
                    }
                    for(Slot t : section.getSlot()) {
                    	if(t!=null) {
                    		//check AM case
                    		if(checkboxAM.isSelected() && !isAM) {
                    			if(noon.compareTo(t.getStart())>0 && noon.compareTo(t.getEnd())>0) 
                    				isAM=true;          			
                    		}
                    		//check PM case
                    		if(checkboxPM.isSelected() && !isPM) {
                    			if(noon.compareTo(t.getStart())<0 && noon.compareTo(t.getEnd())<0) 
                    				isPM=true;          			
                    		}
                    		//check AM & PM case
                    		if(checkboxAM.isSelected() && checkboxPM.isSelected() && !isAMPM) {
                    			if(isAM&&isPM || noon.compareTo(t.getStart())>0 && noon.compareTo(t.getEnd())<0)
                    				isAMPM=true;
                    				         			
                    		}
                    		//check Monday case
                    		if(checkboxMon.isSelected() && !isMon) {
                    			if(t.getDay()==0)
                    				isMon=true;
                    		}
                    		//check Tuesday case
                    		if(checkboxTue.isSelected() && !isTue) {
                    			if(t.getDay()==1)
                    				isTue=true;
                    		}
                    		//check Wednesday case 
                    		if(checkboxWed.isSelected() && !isWed) {
                    			if(t.getDay()==2)
                    				isWed=true;
                    		}
                    		//check Thursday case
                    		if(checkboxThu.isSelected() && !isThu) {
                    			if(t.getDay()==3)
                    				isThu=true;
                    		}
                    		//check Friday case
                    		if(checkboxFri.isSelected() && !isFri) {
                    			if(t.getDay()==4)
                    				isFri=true;
                    		}
                    		//check Saturday case
                    		if(checkboxSat.isSelected() && !isSat) {
                    			if(t.getDay()==5)
                    				isSat=true;
                    		}
                    			
                    		
                    	}	
                    }
                }
    		}
    		
    		
    	    //Using and logic to combine all of the requirements
    		if(!(isLoRT && isAM && isPM && isAMPM && isMon && isTue && isWed && isThu && isFri && isSat)) 
    			continue;
    		
    		
    		//print all the courses that pass through the requirements
    		//with all of their sections and slots
    		String newline = c.getTitle() + "\n";
    		
            
    		for (Section section : c.getSection()) {
                if(section != null){
                    int i = 0;
                    for(Slot t : section.getSlot())
                        if(t != null)
                        	
                            newline += section + " Slot " + i++ + ":" + t + "\n";
                    //update the filteredList for task3, and pulling the checkboxes to the controller
                    filteredList.add(new FList(c.getCourseCode(),section.getSectionCode(),c.getTitle(),section.getInstructor(),section));
                    enrollBox.add(filteredList.get(filteredList.size()-1).getEnroll());
                   
                }
    		}

			newline = textAreaConsole.getText() + "\n" + newline;

			printTextInConsole(newline, TabLabel.Filter.ordinal());
    		// textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
    		
    	}
        //start to fill in the tableView for task3
    	fillTable();
    }
    
    /**
     * Task 3 
     * fill in and update the tableView
     */
    private void fillTable() {
        //update the checkbox status for already enrolled course in tableView 	
    	//perform linear search for each courses in filteredList, and look through courses in enrolledList,
    	//if the course is already enrolled, tick the respective checkbox
    	for(int i = 0; i < filteredList.size(); ++i) {
    		if(!enrolledList.isEmpty()) {	    			
    			for(int j = 0; j < enrolledList.size();++j) {
    				if(filteredList.get(i).getCourseCode().contentEquals(enrolledList.get(j).getCourseCode()) && filteredList.get(i).getSection().contentEquals(enrolledList.get(j).getSection())){
    						filteredList.get(i).getEnroll().setSelected(true);
    				}
    			}  			
    		}
    	}
    	
    	//Start to add the filtered, updated course details to the tableView
    	if (!filteredList.isEmpty()) {
    		for(int i = 0; i < filteredList.size() ; ++i) {
    			tableView.getItems().add(filteredList.get(i));
    		}	
    	}
	    
    	//set up listeners for the response of all of the checkboxers,
    	//after any of the checkboxes is clicked, it will be directed to startEnroll()
    	for (int i = 0; i < enrollBox.size();++i) {
    		enrollBox.get(i).selectedProperty().addListener( (v, oldValue, newValue) -> startEnroll());
    	}
	
	
    }
    

    /**
     * Task3
     * Action performed when any of the chcekbox status is changed
     * Mainly update the enrolledList, which is useful for task 4
     * Also, print out the newest version of enrolled course list(enrolledList).
     */
    private void startEnroll() {
    	//clear the console
    	// textAreaConsole.clear();
    
    	//perform linear search for each course in the tableView, if the course is checked
    	//search through the enrolled list with Course code and section
    	//If the enrolled list have not added the course section, add the section to the enrolled list
    	for(int i = 0; i < filteredList.size(); ++i) {
    		if(filteredList.get(i).getEnroll().isSelected()) {
    			boolean isEnrolled = false;
    			if(!enrolledList.isEmpty()) {
    				for(int j = 0; j < enrolledList.size();++j) {
    					if(filteredList.get(i).getCourseCode().contentEquals(enrolledList.get(j).getCourseCode()) && filteredList.get(i).getSection().contentEquals(enrolledList.get(j).getSection())){
    						isEnrolled = true;
    					}
    				}
    				
    			}
    			if(!isEnrolled)
    				enrolledList.add(filteredList.get(i));
    		}
    		
    	}
    	//perform linear search for each course in the tableView, if the course is not checked
    	//search through the enrolled list with Course code and section
    	//If the enrolled list have been added the course section, remove the course section from the enrolled list
    	for(int i = 0; i < filteredList.size(); ++i) {
    		if(!filteredList.get(i).getEnroll().isSelected()) {
    			if(!enrolledList.isEmpty()) {
    				for(int j = 0; j < enrolledList.size();++j) {
    					if(filteredList.get(i).getCourseCode().contentEquals(enrolledList.get(j).getCourseCode()) && filteredList.get(i).getSection().contentEquals(enrolledList.get(j).getSection())){
    						enrolledList.remove(j);
    					}
    				}	
    			}	
    		}
    		
    	}
        renderTimeTable();
    	//print out the updated version of the enrolled course section list
    	String newline = "The following sections are enrolled:" + "\n";
    	for(int i = 0; i < enrolledList.size(); ++i) {
    		newline += enrolledList.get(i).getCourseCode() + " " + enrolledList.get(i).getSection() + " " + enrolledList.get(i).getCourseName() + enrolledList.get(i).getInstructor() + "\n";   		
    	}
    	
    	// textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);

		newline = textAreaConsole.getText() + "\n" + newline;

		printTextInConsole(newline, TabLabel.List.ordinal());

    	
    }

	/**
	 * Obtain the subject list if it is not searched before
	 * @return The boolean value indicates the subject list is searched before
	 */
    private Boolean subjectIsSearched() {
		String url = textfieldURL.getText();
		String term = textfieldTerm.getText();
        if ( subjects != null && term.equals(searchedTerm) && url.equals(searchedUrl) ) return true;
		subjects = scraper.scrapeSubject(url, term);
        if(subjects == null){
            Platform.runLater( () -> {
                final Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText(null);
                alert.setContentText("404 NOT FOUND! \n Please Check the Base URL and Term.");
                alert.showAndWait();
            });
            return false;
        }
        String newline = "Total Number of Categories/Code Prefix: " + Integer.toString(subjects.length) + "\n";
        printTextInConsole(newline, TabLabel.AllSubject.ordinal());
		searchedUrl = url;
		searchedTerm = term;
        
        return false;
        
    }

	/**
	 * Search all courses information of a subject
	 * @param subject the subject code of a subject which is needed to be search
	 * @return the number of scraped courses in this searching
	 */
    private int searchCourse(String subject){

    	List<Course> courseList = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(), subject);

        if(courseList == null) {
            Platform.runLater( () -> {
                final Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText(null);
                alert.setContentText("404 NOT FOUND! \n Please Check the Base URL, Term and Subject.");
                alert.showAndWait();
				
            });
            return 0;
        }

        int NUMBER_OF_SECTIONS = 0, NUMBER_OF_COURSES = 0;

        Vector<String> INSTRUCTOR_NAME = new Vector<String>();

        String newline = new String();

        courseList.forEach( c -> {
            c.getInstructor().forEach( instructor -> {
                if(!INSTRUCTOR_NAME.contains(instructor))
                    INSTRUCTOR_NAME.add(instructor);
            });
        } );

    	for (Course c : courseList) {
            String courseTitle = c.getTitle() + "\n";
    		String courseInfo = new String();
            Boolean counted = false;
            for(String instructor : c.getFilterInstructor())
                if(INSTRUCTOR_NAME.contains(instructor))
                    INSTRUCTOR_NAME.remove(instructor);
            Collections.sort(INSTRUCTOR_NAME);
    		for (Section section : c.getSection()) {
                if(section != null){
                    if(!counted && section.getType() != null){
                        counted = true;
                        courseTitle += courseInfo;
                        courseInfo = courseTitle;
                        ++NUMBER_OF_COURSES;
                        courses.add(c);
                    }
                    ++NUMBER_OF_SECTIONS;
                    int i = 0;

                    if (section.getNumSlots() == 0){
                        courseInfo += section + "\n";
                        break;
                    }

                    for(Slot t : section.getSlot())
                        if(t != null)
                            courseInfo += section + " Slot " + i++ + ":" + t + "\n";
                        else
                            break;
                }
                else 
                    break;
    		}
            if(counted){
                courseInfo += "\n";
                printTextInConsole(courseInfo, TabLabel.Main.ordinal());
            }
            try {Thread.sleep(100);} catch (Exception e) {}
    	}

        newline = "Total Number of difference sections : " + Integer.toString(NUMBER_OF_SECTIONS) + "\n";
        newline += "Total Number of Course : " + Integer.toString(NUMBER_OF_COURSES) + "\n";
        newline += "Instructors who has teaching assignment this term but does not need to teach at Tu 3:10pm : ";
        
        for(String instructor : INSTRUCTOR_NAME)
            newline += instructor + ", ";
        newline = newline.substring(0, newline.length() - 2);
        newline += "\n" + "\n";

        printTextInConsole(newline, TabLabel.BackEnd.ordinal());

        return courses.size();
    }
    
    /** 
     * Render the timetable corresponding to the enrolled sections.
     * 
     */
	private void renderTimeTable() {
		clearTimeTable();
    	for(FList flist: enrolledList)
    		renderSection(flist.getCourseCode(), flist.get_section());
    }
	
	/**
	 * Clears the timetable
	 */
	private void clearTimeTable() {
		AnchorPane ap = (AnchorPane)tabTimetable.getContent();
		for(Label label: timeTableEntries) {
			ap.getChildren().remove(label);
		}
		timeTableEntries.clear();
	}
    
	/**
	 * Render the section onto the timetable.
	 * @param courseCode The course code of the course
	 * @param section The section object to render
	 */
    private void renderSection(String courseCode, Section section) {
    	final float LABEL_WIDTH = 100.0f;
    	AnchorPane ap = (AnchorPane)tabTimetable.getContent();
    	String sectionName = section.getSectionCode();
    	String labelText = courseCode + '\n' + sectionName;
    	Slot slots[] = section.getSlot();
    	Color color = randomColor();
    	for(Slot slot: slots) {
    		if(slot == null)
    			break;
	    	Label label = new Label(labelText);
	    	label.setFont(new Font(10));
	    	label.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
	    	label.setLayoutX((slot.getDay() + 1) * LABEL_WIDTH);
	    	label.setLayoutY(timeToLabelYPos(slot.getStartHour(),slot.getStartMinute()));
	    	label.setMinWidth(LABEL_WIDTH);
	    	label.setMaxWidth(LABEL_WIDTH);
	    	int minute = slot.getDuration();
	    	float height = minute/3.0f;
	    	if(height < 20.0f) {
	    		label.setText(labelText.replace('\n', ' '));
	    	}
	    	label.setMinHeight(height);
	    	label.setMaxHeight(height);
	    	ap.getChildren().add(label);
	    	timeTableEntries.add(label);
    	}
    }
    
    /**
     * Returns a random (light) color
     * @return A random (light) color
     */
    private Color randomColor() {
    	final float alpha = 0.5f;
    	Random r = new Random();
    	Color c;
    	do {
        	float red = r.nextFloat() / 2 + 0.5f;
        	float green = r.nextFloat() / 2 + 0.5f;
        	float blue = r.nextFloat() / 2 + 0.5f;
        	c = new Color(red, green, blue, alpha);
    	} while (timetableColors.contains(c));
    	timetableColors.add(c);
    	return c;
    }

	/**
	 * Create and initialize a string array
	 * @return a initialize string array with empty string
	 */
    private String[] initializeStringArray(){
        String[] array = new String[TabLabel.values().length];
        Arrays.fill(array, "");
        return array;
    }

	/**
	 * Save the text message and print it in the console in a particular tab if the tab is showed currently
	 * @param newline the text message in the console in a tab
	 * @param index the index number represents a tab
	 */
    private void printTextInConsole(String newline, int index){
        final int MAX_LENGTH = 20000;
        if(consoleText[index].length() > MAX_LENGTH) {
            consoleText[index] = consoleText[index].substring(10000, consoleText[index].length());
            consoleText[index] = consoleText[index].substring(consoleText[index].indexOf("\n"), consoleText[index].length());
        }
        consoleText[index] += newline;
        if(tabPane.getSelectionModel().getSelectedIndex() == index)
            Platform.runLater( () -> textAreaConsole.setText(consoleText[index]) );
    }

    /**
     * Translates slot time to coordinates in timetable
     * @param hour The hour of time
     * @param minute The minute of time
     * @return The y coordinate corresponding to the position in the timetable
     */
    private float timeToLabelYPos(int hour, int minute) {
    	final float offset = 40.f;
    	final int classStartTime = 9;
    	float fractionHour = minute / 60.0f; // Turn minute to fractions of hours
    	float pos = (hour + fractionHour - classStartTime) * 20.0f + offset;
    	return pos;
    }

	private void enableTabInput(Boolean enable) {
		textfieldURL.setDisable(!enable);
		textfieldTerm.setDisable(!enable);
		textfieldSubject.setDisable(!enable);
    	buttonSearch.setDisable(!enable);
		buttonSearchAll.setDisable(!enable);
    }
    
    /**
     * Enables the button to look up instructor SFQ score
     */
    void enableSFQInstructorButton() {
    	Platform.runLater(()-> buttonSfqEnrollCourse.setDisable(false));
    }
}



