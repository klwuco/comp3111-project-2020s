package comp3111.coursescraper;

import java.awt.event.ActionEvent;
import java.time.LocalTime;

import javafx.application.Platform;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType; 
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
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

    private String[] subjects;

    private List<Course> courses = new ArrayList<Course>();

    private String[] consoleText = initializeStringArray();

    private Scraper scraper = new Scraper();
  
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
    private TextField textfieldTerm;

    @FXML
    private TextField textfieldSubject;

    @FXML
    private Button buttonSearch;

    @FXML
    private TextField textfieldURL;

    @FXML
    private Tab tabStatistic;

    @FXML
    private ComboBox<?> comboboxTimeSlot;

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
    private TextField textfieldSfqUrl;

    @FXML
    private Button buttonSfqEnrollCourse;

    @FXML
    private Button buttonInstructorSfq;

    @FXML
    private TextArea textAreaConsole;
    
    private HashSet<Color> timetableColors = new HashSet<Color>();
    
	
    
    @FXML
    private Button buttonSelectAll;
    
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
    void allSubjectSearch() {
        new Thread(() -> {
            if(!subjectIsSearched()) return;
            progressbar.setProgress(0);
            final double increment = 1.0 / subjects.length;
            int counted_course = 0;
            courses = new Vector<Course>();
            for (String subject : subjects) {
                counted_course += searchCourse(subject);
                System.out.println(subject + " is done");
                Platform.runLater( () -> progressbar.setProgress(progressbar.getProgress() + increment) );
                try {Thread.sleep(100);} catch (Exception e) {}
            }
            progressbar.setProgress(1);

            String newline = "Total Number of Courses fetched : ";
            newline += Integer.toString(counted_course) + "\n";
            printTextInConsole(newline, TabLabel.AllSubject.ordinal());
            // Temp Logic to update timetable when search is performed
            Platform.runLater(() -> {renderTimeTable();});
            enableSFQInstructorButton();
        }).start();
    }
   

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
    	// While the enrolled function is not complete, use this
    	List<Course> enrolled = getSearchCourse();
    	String texts = "The (unadjusted) SFQ score for your enrolled course(s):\n";
    	for(Course course: enrolled) {
    		double score = scraper.SFQLookUp(course);
    		texts += String.format("%s: %.2f\n", course.getCourseCode(), score);
    	}
    	consoleText[TabLabel.SFQ.ordinal()] = "";
    	printTextInConsole(texts, TabLabel.SFQ.ordinal());
    	
    }

    @FXML
    void search() {

        new Thread(() -> {
            subjectIsSearched();
            if(subjects != null)
                searchCourse(textfieldSubject.getText());
            // Temp Logic to update timetable when search is performed
            Platform.runLater(() -> {renderTimeTable();});
            enableSFQInstructorButton();
        }).start();
    	
    }

    @FXML
    void onTabChange(){
        Platform.runLater( () -> 
            textAreaConsole.setText(consoleText[tabPane.getSelectionModel().getSelectedIndex()])
        );
    }
    
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
    
    @FXML
    void filter() {
    	textAreaConsole.clear();
    	for (Course c : courses) {
   
    		//String newline = c.getTitle() + "\n";
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
                		if(section.getType()=="LAB"||section.getType()=="TUT") 
                			isLoRT = true;
                    }
                    for(Slot t : section.getSlot()) {
                    	if(t!=null) {
                    		if(checkboxAM.isSelected() && !isAM) {
                    			if(noon.compareTo(t.getStart())>0 && noon.compareTo(t.getEnd())>0) 
                    				isAM=true;          			
                    		}
                    		if(checkboxPM.isSelected() && !isPM) {
                    			if(noon.compareTo(t.getStart())<0 && noon.compareTo(t.getEnd())<0) 
                    				isPM=true;          			
                    		}
                    		if(checkboxAM.isSelected() && checkboxPM.isSelected() && !isAMPM) {
                    			if(isAM&&isPM || noon.compareTo(t.getStart())>0 && noon.compareTo(t.getEnd())<0)
                    				isAMPM=true;
                    				         			
                    		}
                    		if(checkboxMon.isSelected() && !isMon) {
                    			if(t.getDay()==0)
                    				isMon=true;
                    		}
                    		if(checkboxTue.isSelected() && !isTue) {
                    			if(t.getDay()==1)
                    				isTue=true;
                    		}
                    		if(checkboxWed.isSelected() && !isWed) {
                    			if(t.getDay()==2)
                    				isWed=true;
                    		}
                    		if(checkboxThu.isSelected() && !isThu) {
                    			if(t.getDay()==3)
                    				isThu=true;
                    		}
                    		if(checkboxFri.isSelected() && !isFri) {
                    			if(t.getDay()==4)
                    				isFri=true;
                    		}
                    		if(checkboxSat.isSelected() && !isSat) {
                    			if(t.getDay()==5)
                    				isSat=true;
                    		}
                    		
                    		
                    		
                    		
                    	}
                    	
                    }
                	
                	
                         
                    
                }
    		}
    		
    		
    	
    		if(!(isLoRT && isAM && isPM && isAMPM && isMon && isTue && isWed && isThu && isFri && isSat)) 
    			continue;
    		
    		String newline = c.getTitle() + "\n";
            
    		for (Section section : c.getSection()) {
                if(section != null){
                    int i = 0;
                    for(Slot t : section.getSlot())
                        if(t != null)
                            newline += section + " Slot " + i++ + ":" + t + "\n";
                }
    		}
    		textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
    		
    	}
    	
    }

    private Boolean subjectIsSearched() {
        if (subjects != null) return true;
        
        subjects = scraper.scrapeSubject(textfieldURL.getText(), textfieldTerm.getText());
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
        
        return false;
        
    }

    private int searchCourse(String subject){
        
    	List<Course> courseList = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(), subject);

        if(courseList == null) {
            Platform.runLater( () -> {
                final Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText(subject); // testing
                // alert.setHeaderText(null);
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
    
	private void renderTimeTable() {
		// While the enrolled function is not complete, use this
    	List<Course> enrolled = getSearchCourse();
    	for(Course course: enrolled) {
    		renderSession(course.getCourseCode(), course.getSection()[0]);
    	}
    }
    
    private void renderSession(String course_code, Section section) {
    	final float LABEL_WIDTH = 100.0f;
    	AnchorPane ap = (AnchorPane)tabTimetable.getContent();
    	String sectionName = section.getSectionCode();
    	String labelText = course_code + '\n' + sectionName;
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
	    	float height = minute/2.0f;
	    	label.setMinHeight(height);
	    	label.setMaxHeight(height);
	    	ap.getChildren().add(label);
    	}
    }
    
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

  
    private String[] initializeStringArray(){
        String[] array = new String[TabLabel.values().length];
        Arrays.fill(array, "");
        return array;
    }

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
    private float timeToLabelYPos(int hour, int minute) {
    	final float offset = 40.f;
    	final int classStartTime = 9;
    	float fractionHour = minute / 60.0f; // Turn minute to fractions of hours
    	float pos = (hour + fractionHour - classStartTime) * 20.0f + offset;
    	return pos;
    }
    
    void enableSFQInstructorButton() {
    	Platform.runLater(()-> buttonSfqEnrollCourse.setDisable(false));
    }
    
    private List<Course> getSearchCourse(){
    	// Simulate select course
    	List<Course> enrolled = new ArrayList<Course>();
    	int count = 0;
    	while(true) {
    		Course course = courses.get(count);
    		for(Section section: course.getSection()) {
    			if(section == null)
    				break;
    			enrolled.add(course);
    			break;
    		}
			if(++count >= 5) 
				break;
    	}
    	return enrolled;
    }
}

	


