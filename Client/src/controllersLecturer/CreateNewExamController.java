package controllersLecturer;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import abstractControllers.AbstractController;
import client.ConnectionServer;
import entities.Course;
import entities.Question;
import entities.QuestionForExam;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

public class CreateNewExamController extends AbstractController implements Initializable{
	private ArrayList<Course> courses;
	private ArrayList<QuestionForExam> qArr;
	private ArrayList<QuestionForExam> qSelected=new ArrayList<>();
	private int sum;
    @FXML
    private ComboBox<Course> CourseComboBox;
    
    @FXML
    private TextField DurauinTxt;

    @FXML
    private Button FinishCreateNewExamButton;

    @FXML
    private TableView<QuestionForExam> QuestionTable;

    @FXML
    private TableColumn<QuestionForExam, Integer> clmID;

    @FXML
    private TableColumn<QuestionForExam, String> clmQuestion;

    @FXML
    private TableColumn<QuestionForExam, String> clmSubject;
    
    @FXML
    private TableColumn<QuestionForExam, TextField> clmScore;

    @FXML
    private TextField codetXT;

    @FXML
    private TextField lecNotesTxt;

    @FXML
    private TextField studNotesText;
    
    @FXML
    private Button closeButton;

    @FXML
    private Button minimizeButton;
    
    @FXML
    private Button btnSelected;

    @FXML
    private Label lblError;

    @FXML
    private Label lblErrorCode;

    @FXML
    private Label lblErrorDuration;
    
    @FXML
    private Label lblScore;
    
    @FXML
    private Label lblErrorSelected;
    
    @FXML
    void getFinish(ActionEvent event) {
    	lblError.setText(" ");
    	lblErrorCode.setText(" ");
    	lblErrorDuration.setText(" ");
    	lblErrorSelected.setText(" ");
    	String code = codetXT.getText();
    	String duration = DurauinTxt.getText();
    	String lecNotes = lecNotesTxt.getText();
    	String studNotes = studNotesText.getText();
    	boolean flag=false;
    	Integer durationMins;
    	if(qArr.isEmpty()) {
    		lblError.setText("You must select atleast one question");
    		flag=true;
    	}
    	if(code==null || duration==null) {
    		lblError.setText("You must define both code and duration");
    		flag=true;
    	}
    	if(code.length()!=4 || !code.matches("^[a-zA-Z0-9]+$")) {
    		lblErrorCode.setText("Code must be 4 digits and contains only letters and numbers.");
    		flag=true;
    	}
    	if(!duration.matches("\\d+")) {
    		lblErrorDuration.setText("Duration must contain only numbers (represents minutes).");
    		flag=true;
    	}
    	if(qSelected.isEmpty()) {
    		if(sum==0)
    			lblErrorSelected.setText("Please select questions first.");
    		else
    			lblErrorSelected.setText("Total score must be '100'.");
    		flag=true;
    	}
    	if(flag) {
    		lblError.setText("Please fix all error and try again later.");
    		return;
    	}
    	durationMins = Integer.parseInt(duration);
    	//implement enter exam to DB
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setCoursesComboBox();
	}

	private void setCoursesComboBox() {
		//implement query to import lecturer courses
//		try {
//			ConnectionServer.getInstance();
//			ArrayList<HashMap<String, Object>> rs = ConnectionServer.rs;
//			if(rs==null) {
//				System.out.println("RS is null");
//				return;
//			}
//			courses=new ArrayList<>();
//			for (int i = 0; i < rs.size(); i++) {
//			    HashMap<String, Object> element = rs.get(i);
//			    courses.add(new Course((Integer)element.get("courseID"), (String)element.get("courseName")/*,(Integer)element.get("departmentId")*/));
//			    CourseComboBox.getItems().add(courses.get(i));
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	    CourseComboBox.getItems().add(new Course(1,"one",1));
	    CourseComboBox.getItems().add(new Course(2,"two",2));
	    CourseComboBox.setOnAction(new EventHandler<ActionEvent>() {
	         public void handle(ActionEvent ae) {
	            loadQuestions(CourseComboBox.getSelectionModel().getSelectedItem());
	         }
	      });
	    

	}
	private void loadQuestions(Course selectedItem) {
		// query to get questions from course
		qArr=new ArrayList<>();
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> arr = new ArrayList<>();
		arr.add("Lecturer");
		msg.put("client", arr);
		ArrayList<String> arr1 = new ArrayList<>();
		arr1.add("getAllQuestions");
		msg.put("task",arr1);
		super.sendMsgToServer(msg);
		ArrayList<HashMap<String,Object>> rs = ConnectionServer.rs;
		if(rs == null) {
			System.out.println("rs is null");
		}
		for (int i = 0; i < rs.size(); i++) {
		    HashMap<String, Object> element = rs.get(i);
		    Question q = new Question((Integer)element.get("questionId"), (String)element.get("details"),
		    		(String)element.get("rightAnswer"), (Integer)element.get("questionBank"), 
		    		(String)element.get("subject"), (String)element.get("answers"),(String)element.get("notes"));
		    QuestionForExam questionForExam = new QuestionForExam(q,"0");
		    qArr.add(questionForExam);
		}
		loadTable();
	}

    private void loadTable() {
    	ObservableList<QuestionForExam> list = FXCollections.observableArrayList(qArr);
		PropertyValueFactory<QuestionForExam, Integer> pvfId = new PropertyValueFactory<>("questionID");
		PropertyValueFactory<QuestionForExam, String> pvfQuestion = new PropertyValueFactory<>("details");
		PropertyValueFactory<QuestionForExam, String> pvfSubject = new PropertyValueFactory<>("subject");
		PropertyValueFactory<QuestionForExam, TextField> pvfScore = new PropertyValueFactory<>("score");		
		clmID.setCellValueFactory(pvfId);
		clmSubject.setCellValueFactory(pvfSubject);
		clmQuestion.setCellValueFactory(pvfQuestion);
		clmScore.setCellValueFactory(pvfScore);
		QuestionTable.setItems(list);
		QuestionTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}

	@FXML
    void getSelected(ActionEvent event) {
    	qSelected = new ArrayList<>();
    	sum=0;
    	lblErrorSelected.setText(" ");
    	lblScore.setText("0/100");
    	qSelected.addAll(QuestionTable.getSelectionModel().getSelectedItems());
    	for(QuestionForExam q: qSelected) {
    		String scoreStr = q.getScore().getText();
    		if(!scoreStr.matches("\\d+")) {
    			lblErrorSelected.setText("One of the selected questions score is not number, try again.");
    			qSelected = new ArrayList<>();
    			return;
    		}
    		int score = Integer.parseInt(q.getScore().getText());
    		if(score==0) {
    			lblErrorSelected.setText("One of the selected questions score is set to '0', try again.");
    			qSelected = new ArrayList<>();
    			return;
    		}
    		sum = sum + score;
    	}
    	lblScore.setText(sum+"/100");
    	if(sum!=100) {
    		lblErrorSelected.setText("Total score is not '100', change scores and try again.");
    		qSelected = new ArrayList<>();
    		return;
    	}
    }

	}
	


//	questionForExam.getSelection().cacheProperty().addListener( (observable, oldValue, newValue)->{
//    	if(oldValue==false && newValue==true) {
//    		qSelected.add(questionForExam);
//    	}
//    	else if(oldValue==true && newValue==false) {
//    		qSelected.remove(questionForExam);
//    	}
//    	System.out.println(oldValue +" "+ newValue);
//    });
//	questionForExam.getScore().textProperty().addListener((observable, oldValue, newValue) -> {
//    	if(qSelected.contains(questionForExam)) {
//	        sum = sum + (Integer.parseInt(newValue) - Integer.parseInt(newValue));
//	        lblScore.setText(sum+"/100");
//	        System.out.println(sum);
//    	}
//    	System.out.println(oldValue +" "+ newValue);
//    	
//    });

