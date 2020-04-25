package comp3111.coursescraper;

import java.awt.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.control.CheckBox;

import java.util.Random;
import java.util.List;
public class Controller {

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
    
    
    
    private Scraper scraper = new Scraper();
    
    @FXML
    void allSubjectSearch() {
    	
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
    	List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),textfieldSubject.getText());
    	for (Course c : v) {
    		String newline = c.getTitle() + "\n";
    		for (int i = 0; i < c.getNumSlots(); i++) {
    			Slot t = c.getSlot(i);
    			newline += "Slot " + i + ":" + t + "\n";
    		}
    		textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
    	}
    	
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
    		return;
    	}
    	
    	if(buttonSelectAll.getText().equals("De-select All")) {
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
    		return;
    	}
    	
	}

}

	


