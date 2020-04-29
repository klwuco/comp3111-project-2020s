package comp3111.coursescraper;

import java.awt.event.ActionEvent;
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

import java.util.Arrays;
import java.util.Random;
import java.util.List;
import java.util.Vector;
import java.util.Collections;

public class Controller {

    private String[] subjects;

    private String[] consoleText = initializeStringArray();

    private Scraper scraper = new Scraper();

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

    @FXML
    void allSubjectSearch() {
        new Thread(() -> {
            if(!subjectIsSearched()) return;
            progressbar.setProgress(0);
            final double increment = 1.0 / subjects.length;
            int counted_course = 0;
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
            
        }).start();
    }

    @FXML
    void findInstructorSfq() {
    	buttonInstructorSfq.setDisable(true);
    }

    @FXML
    void findSfqEnrollCourse() {

    }

    @FXML
    void search() {

        new Thread(() -> {
            subjectIsSearched();
            if(subjects != null)
                searchCourse(textfieldSubject.getText());
        }).start();

    	//Add a random block on Saturday
    	AnchorPane ap = (AnchorPane)tabTimetable.getContent();
    	Label randomLabel = new Label("COMP1022\nL1");
    	Random r = new Random();
    	double start = (r.nextInt(10) + 1) * 20 + 40;

    	randomLabel.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
    	randomLabel.setLayoutX(600.0);
    	randomLabel.setLayoutY(start);
    	randomLabel.setMinWidth(100.0);
    	randomLabel.setMaxWidth(100.0);
    	randomLabel.setMinHeight(60);
    	randomLabel.setMaxHeight(60);
    
    	ap.getChildren().addAll(randomLabel);
    	 	
    	
    }

    @FXML
    void onTabChange(){
        Platform.runLater( () -> 
            textAreaConsole.setText(consoleText[tabPane.getSelectionModel().getSelectedIndex()])
        );
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
        
    	List<Course> courses = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(), subject);

        if(courses == null) {
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

        courses.forEach( c -> {
            c.getInstructor().forEach( instructor -> {
                if(!INSTRUCTOR_NAME.contains(instructor))
                    INSTRUCTOR_NAME.add(instructor);
            });
        } );

    	for (Course c : courses) {
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

}
