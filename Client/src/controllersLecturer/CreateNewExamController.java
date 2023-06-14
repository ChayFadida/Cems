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
import entities.Lecturer;
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
import thirdPart.JsonHandler;
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
    private TextField txtName;
    
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
    private Label lblErrorName;
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
    	String name = txtName.getText();
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
    	if(lecNotes==null)
    		lecNotes=" ";
    	if(studNotes==null)
    		studNotes=" ";
    	if(name==null)
    		name=" ";
    	createExam(code,duration,lecNotes,studNotes,name);
    	//implement enter exam to DB
    }
    
	private void createExam(String code, String duration, String lecNotes, String studNotes,String name) {
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> arr = new ArrayList<>();
		HashMap<String, Object> bank=getExamBank();
		arr.add("Lecturer");
		msg.put("client", arr);
		ArrayList<String> arr1 = new ArrayList<>();
		arr1.add("insertExam");
		msg.put("task",arr1);
		ArrayList<String> arr2 = new ArrayList<>();
		arr2.add(CourseComboBox.getSelectionModel().getSelectedItem().getCourseId()+"");
		arr2.add(getDepartmentName());
		arr2.add(duration);
		arr2.add(lecNotes);
		arr2.add(studNotes);
		arr2.add(ConnectionServer.user.getId()+"");
		arr2.add(code);
		arr2.add((getLecturerExamCount()+1)+"");
		arr2.add((Integer)bank.get("bankId")+"");
		arr2.add(name);
		msg.put("param", arr2);
		super.sendMsgToServer(msg);
		ArrayList<HashMap<String,Object>> rs = ConnectionServer.rs;
		if(rs == null) {
			System.out.println("RS is null");
		}
		long lastId=((long) rs.get(0).get("keys"));
		Long lId = lastId;
		Integer examId = lId.intValue();
		addToExamBank(bank,examId);
		addQuestionsAndScore(examId);
	}
	

	private void addQuestionsAndScore(Integer examId) {
		HashMap<String,ArrayList<Integer>> qIdsHm = new HashMap<>();
		HashMap<String,ArrayList<Integer>> qScoresHm = new HashMap<>();
		ArrayList<Integer> qIds= new ArrayList<>();
		ArrayList<Integer> qScores= new ArrayList<>();
		for(QuestionForExam q: qSelected) {
			qIds.add(q.getQuestionID());
			qScores.add(Integer.parseInt(q.getScore().getText()));
		}
		qIdsHm.put("questions", qIds);
		qScoresHm.put("scores", qScores);
		String qIdsStr= JsonHandler.convertHashMapToJson(qIdsHm, String.class, ArrayList.class);
		String qScoresStr= JsonHandler.convertHashMapToJson(qScoresHm, String.class, ArrayList.class);
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> arr = new ArrayList<>();
		arr.add("Lecturer");
		msg.put("client", arr);
		ArrayList<String> arr1 = new ArrayList<>();
		arr1.add("insertQuestionsForExam");
		msg.put("task",arr1);
		ArrayList<String> arr2 = new ArrayList<>();
		arr2.add(examId+"");
		arr2.add(qIdsStr);
		arr2.add(qScoresStr);
		msg.put("param", arr2);
		super.sendMsgToServer(msg);
		ArrayList<HashMap<String,Object>> rs = ConnectionServer.rs;
		if(rs == null){
			System.out.println("RS is null");
		}
		if((int)rs.get(0).get("affectedRows")==1) {
			resetAll();
			lblError.setText("Exam uploaded successfully");
		}
		else {
			lblError.setText("Problam at exam upload");
		}
	}

	private String getDepartmentName() {
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> arr = new ArrayList<>();
		arr.add("Lecturer");
		msg.put("client", arr);
		ArrayList<String> arr1 = new ArrayList<>();
		arr1.add("getDepartmentNameById");
		msg.put("task",arr1);
		ArrayList<String> arr2 = new ArrayList<>();
		arr2.add(((Lecturer)ConnectionServer.user).getDepartmentId()+"");
		msg.put("param", arr2);
		super.sendMsgToServer(msg);
		ArrayList<HashMap<String,Object>> rs = ConnectionServer.rs;
		if(rs == null){
			System.out.println("RS is null");
		}
		return (String) rs.get(0).get("name");
	}

	private void addToExamBank(HashMap<String, Object> bank, Integer examId) {
		String exams = (String) bank.get("exams");
		HashMap<String,ArrayList<Integer>> jsonHM= JsonHandler.convertJsonToHashMap(exams, String.class, ArrayList.class,Integer.class);
		ArrayList<Integer> examsInBank = jsonHM.get("exams");
		examsInBank.add(examId);
		jsonHM.put("exams",examsInBank);
		String jsonString = JsonHandler.convertHashMapToJson(jsonHM, String.class, ArrayList.class);
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> arr = new ArrayList<>();
		arr.add("Lecturer");
		msg.put("client", arr);
		ArrayList<String> arr1 = new ArrayList<>();
		arr1.add("updateExamBankById");
		msg.put("task",arr1);
		ArrayList<String> arr2 = new ArrayList<>();
		arr2.add(bank.get("bankId")+"");
		arr2.add(jsonString);
		msg.put("param", arr2);
		super.sendMsgToServer(msg);
		ArrayList<HashMap<String,Object>> rs = ConnectionServer.rs;
		if(rs == null){
			System.out.println("RS is null");
		}
		if((int)rs.get(0).get("affectedRows")==1) {
			resetAll();
			lblError.setText("Exam uploaded successfully");
		}
		else {
			lblError.setText("Problam at exam upload");
		}
		
	}

	private void resetAll() {
		lblError.setText(" ");
		lblErrorCode.setText(" ");
		lblErrorDuration.setText(" ");
		lblErrorSelected.setText(" ");
		codetXT.setText("");
		DurauinTxt.setText("");
		lecNotesTxt.setText("");
		studNotesText.setText("");
		lblScore.setText("0/100");
		loadQuestions(CourseComboBox.getSelectionModel().getSelectedItem());
	}

	private HashMap<String, Object> getExamBank() {
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> arr = new ArrayList<>();
		arr.add("Lecturer");
		msg.put("client", arr);
		ArrayList<String> arr1 = new ArrayList<>();
		arr1.add("getExamBankByLecId");
		msg.put("task",arr1);
		ArrayList<String> arr2 = new ArrayList<>();
		arr2.add(ConnectionServer.user.getId()+"");
		msg.put("param", arr2);
		super.sendMsgToServer(msg);
		ArrayList<HashMap<String,Object>> rs = ConnectionServer.rs;
		if(rs == null) {
			System.out.println("RS is null");
		}
		if(rs.get(0)==null) {
			System.out.println("Empty table from Sql");
		}
		return rs.get(0);
	}

	//need to check lecturer's exam count in order to put in exam number
	private Integer getLecturerExamCount() {
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> arr = new ArrayList<>();
		arr.add("Lecturer");
		msg.put("client", arr);
		ArrayList<String> arr1 = new ArrayList<>();
		arr1.add("getExamCountByLecId");
		msg.put("task",arr1);
		ArrayList<String> arr2 = new ArrayList<>();
		arr2.add(ConnectionServer.user.getId()+"");
		msg.put("param", arr2);
		super.sendMsgToServer(msg);
		ArrayList<HashMap<String,Object>> rs = ConnectionServer.rs;
		if(rs == null) {
			System.out.println("RS is null");
		}
		return ((Long) rs.get(0).get("count")).intValue();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setCoursesComboBox();
	}

	private void setCoursesComboBox() {
		//implement query to import lecturer courses
		try {
			ConnectionServer.getInstance();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Lecturer lecturer = (Lecturer) ConnectionServer.user;
		ArrayList<Integer> coursesId = (ArrayList<Integer>) lecturer.getCoursesIdHM().get("courses");
		for(Integer id: coursesId) {
			HashMap<String,ArrayList<String>> msg = new HashMap<>();
			ArrayList<String> arr = new ArrayList<>();
			arr.add("Lecturer");
			msg.put("client", arr);
			ArrayList<String> arr1 = new ArrayList<>();
			arr1.add("getCoursesByCourseId");
			msg.put("task",arr1);
			ArrayList<String> arr2 = new ArrayList<>();
			arr2.add(id+"");
			msg.put("param", arr2);
			super.sendMsgToServer(msg);
			ArrayList<HashMap<String, Object>> rs = ConnectionServer.rs;
			if(rs==null) {
				System.out.println("RS is null");
				return;
			}
			courses=new ArrayList<>();
			HashMap<String, Object> element = rs.get(0);
			Course course= new Course((Integer)element.get("courseID"), (String)element.get("courseName"),(Integer)element.get("departmentId"));
		    courses.add(course);
		    CourseComboBox.getItems().add(course);
		}
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
		arr1.add("getQuestionsByIdAndCourse");
		msg.put("task",arr1);
		ArrayList<String> arr2 = new ArrayList<>();
		arr2.add(ConnectionServer.user.getId()+"");
		arr2.add(selectedItem.getCourseId()+"");
		msg.put("param", arr2);
		super.sendMsgToServer(msg);
		ArrayList<HashMap<String,Object>> rs = ConnectionServer.rs;
		if(rs == null) {
			System.out.println("RS is null");
		}
		for (int i = 0; i < rs.size(); i++) {
		    HashMap<String, Object> element = rs.get(i);
		    Question q = new Question((Integer)element.get("questionId"), (String)element.get("details"),
		    		(String)element.get("rightAnswer"), (Integer)element.get("questionBank"), 
		    		(String)element.get("subject"), (String)element.get("answers"),(String)element.get("notes"),(String)element.get("courses"));
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

