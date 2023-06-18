package controllersLecturer;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import abstractControllers.AbstractController;
import client.ConnectionServer;
import entities.Course;
import entities.Exam;
import entities.Lecturer;
import entities.Question;
import entities.QuestionForExam;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import thirdPart.JsonHandler;
/**
 * Controller class for the lecturer.
 * In this controller the lecturer can edit the exam.
 *Extends AbsractController.
 */
public class EditExamController extends AbstractController{
	private ArrayList<QuestionForExam> qArr ;
	private Exam exam;
    private MyExamBankController myExamBankController;
    private HashMap<Integer, String> HmCourseIdName = new HashMap<>();
	private ArrayList<Object> qSelected=new ArrayList<>();
	private int sum;
	private ArrayList<Course> courses;

    /**
     * Sets myExamBankController instance for current controller.
     * @param myExamBankController instance.
     */
    public void SetMyExamBankController(MyExamBankController myExamBankController) {
		this.myExamBankController = myExamBankController;
	}
    
    @FXML
    private ComboBox<Course> CourseComboBox;

    @FXML
    private TextField DurationTxt;

    @FXML
    private TableView<QuestionForExam> QuestionTable;

    @FXML
    private Button btnSaveChanges;

    @FXML
    private Button btnSelected;

    @FXML
    private TableColumn<QuestionForExam, String> clmCourse;

    @FXML
    private TableColumn<QuestionForExam, String> clmQuestion;

    @FXML
    private TableColumn<QuestionForExam, TextField> clmScore;

    @FXML
    private TableColumn<QuestionForExam, String> clmSubject;

    @FXML
    private TextField codetXT;

    @FXML
    private Label lblError;

    @FXML
    private Label lblErrorCode;

    @FXML
    private Label lblErrorDuration;

    @FXML
    private Label lblErrorName;

    @FXML
    private Label lblErrorSelected;

    @FXML
    private Label lblScore;

    @FXML
    private TextField lecNotesTxt;

    @FXML
    private TextField studNotesTxt;

    @FXML
    private TextField txtName;
    
    /**
     * Close current window.
     * @param event Action event
     */
    @FXML
	void Close(ActionEvent event) {
	    Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
	    currentStage.close();
	}
	/**
	 * Minimize current window
	 * @param event Action event
	 */
	@FXML
	void Minimize(ActionEvent event) {
		Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
	    stage.setIconified(true);
	}
    
	/**
	 * Retrieves the selected items from a table view and performs validation on their scores.
	 * @param event Action event.
	 */
    @FXML
    void getSelected(ActionEvent event) {
       	qSelected = new ArrayList<>();
    	sum=0;
    	lblErrorSelected.setText(" ");
    	lblScore.setText("0/100");
    	qSelected.addAll(QuestionTable.getSelectionModel().getSelectedItems());
    	for(Object q: qSelected) {
    		String scoreStr = ((QuestionForExam) q).getScore().getText();
    		if(!scoreStr.matches("\\d+")) {
    			lblErrorSelected.setText("One of the selected questions score is not number, try again.");
    			qSelected = new ArrayList<>();
    			return;
    		}
    		int score = Integer.parseInt(((QuestionForExam) q).getScore().getText());
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
    	}
    }
    
    /**
     * Gets the changes the lecturer made.
     * @param event Action event.
     */
    @FXML
    void getSaveChanges(ActionEvent event) {
    	lblError.setText(" ");
    	lblErrorCode.setText(" ");
    	lblErrorDuration.setText(" ");
    	lblErrorSelected.setText(" ");
    	String code = codetXT.getText();
    	String duration = DurationTxt.getText();
    	String lecNotes = lecNotesTxt.getText();
    	String studNotes = studNotesTxt.getText();
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
    	updateExam(code,duration,lecNotes,studNotes,name);
    }
    
    /**
     * Update the exam with the new information.
     * @param code exam cide	
     * @param duration exam duration
     * @param lecNotes exam notes for lecturers
     * @param studNotes exam notes for student 
     * @param name exam name.
     */
	private void updateExam(String code, String duration, String lecNotes, String studNotes,String name) {
		HashMap<String,ArrayList<Object>> msg = new HashMap<>();
		ArrayList<Object> user = new ArrayList<>();
		HashMap<String, Object> bank=getExamBank();
		user.add("Lecturer");
		msg.put("client", user);
		ArrayList<Object> query = new ArrayList<>();
		query.add("updateExam");
		msg.put("task",query);
		ArrayList<Object> parameter = new ArrayList<>();
		HashMap<Object, Object> arr2Param = new HashMap<>();
		parameter.add(CourseComboBox.getSelectionModel().getSelectedItem().getCourseId()+"");
		parameter.add(getDepartmentName());
		parameter.add(duration);
		parameter.add(lecNotes);
		parameter.add(studNotes);
		parameter.add(ConnectionServer.user.getId()+"");
		parameter.add(code);
		parameter.add((getLecturerExamCount()+1)+"");
		parameter.add((Integer)bank.get("bankId")+"");
		parameter.add(name);
		parameter.add(exam.getExamId() + "");
		msg.put("questions", qSelected);
		msg.put("param", parameter);
		sendMsgToServer(msg);
		ArrayList<HashMap<String,Object>> rs = ConnectionServer.rs;
		if(rs == null) {
			System.out.println("RS is null");
		}
		updateQuestionsAndScore(exam.getExamId());
		((Stage)btnSelected.getScene().getWindow()).close(); //hiding primary window

	}
	
	/**
	 * Update relevant questions and their score.
	 * @param examId exam id.
	 */
	private void updateQuestionsAndScore(Integer examId) {
		HashMap<String,ArrayList<Integer>> qIdsHm = new HashMap<>();
		HashMap<String,ArrayList<Integer>> qScoresHm = new HashMap<>();
		ArrayList<Integer> qIds= new ArrayList<>();
		ArrayList<Integer> qScores= new ArrayList<>();
		for(Object q: qSelected) {
			qIds.add(((QuestionForExam) q).getQuestionID());
			qScores.add(Integer.parseInt(((QuestionForExam) q).getScore().getText()));
		}
		qIdsHm.put("questions", qIds);
		qScoresHm.put("scores", qScores);
		String qIdsStr= JsonHandler.convertHashMapToJson(qIdsHm, String.class, ArrayList.class);
		String qScoresStr= JsonHandler.convertHashMapToJson(qScoresHm, String.class, ArrayList.class);
		HashMap<String,ArrayList<Object>> msg = new HashMap<>();
		ArrayList<Object> user = new ArrayList<>();
		user.add("Lecturer");
		msg.put("client", user);
		ArrayList<Object> query = new ArrayList<>();
		query.add("updateQuestionInExamInDB");
		msg.put("task",query);
		ArrayList<Object> parameter = new ArrayList<>();
		parameter.add(examId+"");
		parameter.add(qIdsStr);
		parameter.add(qScoresStr);
		msg.put("param", parameter);
		super.sendMsgToServer(msg);
		ArrayList<HashMap<String,Object>> rs = ConnectionServer.rs;
		if(rs == null){
			System.out.println("Could not update question");
		}
		if((int)rs.get(0).get("affectedRows")==1) {
			lblError.setText("Exam uploaded successfully");
		}
		else {
			lblError.setText("Problam at exam upload");
		}
	}
	
	/**
	 * Counts how much students conduct the exam
	 * @return number of students conduct the exam
	 */
	private Integer getLecturerExamCount() {
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> user = new ArrayList<>();
		user.add("Lecturer");
		msg.put("client", user);
		ArrayList<String> query = new ArrayList<>();
		query.add("getExamCountByLecId");
		msg.put("task",query);
		ArrayList<String> parameter = new ArrayList<>();
		parameter.add(ConnectionServer.user.getId()+"");
		msg.put("param", parameter);
		super.sendMsgToServer(msg);
		ArrayList<HashMap<String,Object>> rs = ConnectionServer.rs;
		if(rs == null) {
			System.out.println("Could not load information.");
		}
		return ((Long) rs.get(0).get("count")).intValue();
	}
	
	/**
	 * Get the department name from the db.
	 * @return department neme.
	 */
	private String getDepartmentName() {
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> user = new ArrayList<>();
		user.add("Lecturer");
		msg.put("client", user);
		ArrayList<String> query = new ArrayList<>();
		query.add("getDepartmentNameById");
		msg.put("task",query);
		ArrayList<String> parameter = new ArrayList<>();
		parameter.add(((Lecturer)ConnectionServer.user).getDepartmentId()+"");
		msg.put("param", parameter);
		super.sendMsgToServer(msg);
		ArrayList<HashMap<String,Object>> rs = ConnectionServer.rs;
		if(rs == null){
			System.out.println("Could not get department name.");
		}
		return (String) rs.get(0).get("name");
	}
	
	/**
	 * Get the exam bank.
	 * @return the exam bank in an HashMap form 
	 */
	private HashMap<String, Object> getExamBank() {
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> user = new ArrayList<>();
		user.add("Lecturer");
		msg.put("client", user);
		ArrayList<String> query = new ArrayList<>();
		query.add("getExamBankByLecId");
		msg.put("task",query);
		ArrayList<String> parameter = new ArrayList<>();
		parameter.add(ConnectionServer.user.getId()+"");
		msg.put("param", parameter);
		super.sendMsgToServer(msg);
		ArrayList<HashMap<String,Object>> rs = ConnectionServer.rs;
		if(rs == null) {
			System.out.println("could not get the exam bank");
		}
		if(rs.get(0)==null) {
			System.out.println("Empty table from Sql");
		}
		return rs.get(0);
	}
    
	/**
	 * Filter for the lecturer to choose relevant course.
	 * @param event Action event
	 */
    @FXML
    void cmbCourseFilter(ActionEvent event) {
        Course selectedCourse = CourseComboBox.getSelectionModel().getSelectedItem();
        showTableWithFilters(selectedCourse);
    }
    
    /**
     * Display the table after choose course to filter.
     * @param selectedItem selected course.
     */
    void showTableWithFilters(Course selectedItem) {
		qArr=new ArrayList<>();
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> user = new ArrayList<>();
		user.add("Lecturer");
		msg.put("client", user);
		ArrayList<String> query = new ArrayList<>();
		query.add("getQuestionsByIdAndCourse");
		msg.put("task",query);
		ArrayList<String> parameter = new ArrayList<>();
		parameter.add(ConnectionServer.user.getId()+"");
		parameter.add(selectedItem.getCourseId()+"");
		msg.put("param", parameter);
		super.sendMsgToServer(msg);
		ArrayList<HashMap<String,Object>> rs = ConnectionServer.rs;
		if(rs == null) {
			System.out.println("Could not load information.");
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
    
    /**
     * Loads data into the table.
     */
    private void loadTable() {
    	ObservableList<QuestionForExam> list = FXCollections.observableArrayList(qArr);
		PropertyValueFactory<QuestionForExam, String> pvfQuestion = new PropertyValueFactory<>("details");
		PropertyValueFactory<QuestionForExam, String> pvfSubject = new PropertyValueFactory<>("subject");
		PropertyValueFactory<QuestionForExam, TextField> pvfScore = new PropertyValueFactory<>("score");		
		clmSubject.setCellValueFactory(pvfSubject);
		clmQuestion.setCellValueFactory(pvfQuestion);
		clmScore.setCellValueFactory(pvfScore);
		clmCourse.setCellValueFactory(cellData -> {
		    Question question = cellData.getValue();
		    String courseId = question.getCourses();
		    HashMap<String,ArrayList<String>> json = JsonHandler.convertJsonToHashMap(courseId, String.class, ArrayList.class, String.class);
		    ArrayList<String> courseIds = json.get("courses");
		    List<String> courseNames = new ArrayList<>();
		    // Retrieve the course name for each course ID
		    for (String id : courseIds) {
		        int courseIdInt = Integer.parseInt(id.trim());
		        String courseName = HmCourseIdName.get(courseIdInt);
		        if (courseName != null) {
		            courseNames.add(courseName);
		        }
		    }

		    // Join the course names into a single string
		    String joinedNames = String.join(", ", courseNames);

		    return new SimpleStringProperty(joinedNames);
		});

		QuestionTable.setItems(list);
		QuestionTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			
        //set the answers of exam
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> arr = new ArrayList<>();
		arr.add("Lecturer");
		msg.put("client", arr);
		ArrayList<String> arr1 = new ArrayList<>();
		arr1.add("getQuestionsInExam");
		msg.put("task",arr1);
		ArrayList<String> arr2 = new ArrayList<>();
		arr2.add(exam.getExamId() + "");
		msg.put("param", arr2);
		super.sendMsgToServer(msg);
		       
        // Get the list of selected question IDs for the exam
        List<Integer> questionsScore = (List<Integer>) JsonHandler.convertJsonToHashMap((String) ConnectionServer.rs.get(0).get("scores"), String.class, List.class, Integer.class).get("scores");
        List<Integer> selectedQuestionIds = (List<Integer>) JsonHandler.convertJsonToHashMap((String) ConnectionServer.rs.get(0).get("questions"), String.class, List.class, Integer.class).get("questions");
        // Iterate over the questions in the QuestionTable
        for (QuestionForExam question : QuestionTable.getItems()) {
            int questionId = question.getQuestionID();
            
            if (selectedQuestionIds.contains(questionId)) {
                QuestionTable.getSelectionModel().select(question);
                int questionIndex = selectedQuestionIds.indexOf(questionId);
                int score = questionsScore.get(questionIndex);
                
                question.getScore().setText(String.valueOf(score));
            }
        }
	}
	
    /**
     * Loads the specific exam
     * @param e the exam.
     */
    void LoadExam(Exam e) {
    	this.exam = e;
    	HmCourseIdName = myExamBankController.getHmCourseIdName();
    	codetXT.setText(exam.getCode());
    	DurationTxt.setText(exam.getDuration() + "");
    	txtName.setText(exam.getExamName());
        lecNotesTxt.setText(exam.getLecturerNote());
        studNotesTxt.setText(exam.getStudentNote());
        lblScore.setText("100/100");
        
        
    	ConnectionServer.getInstance();
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
		    HmCourseIdName.put((Integer)element.get("courseID"), (String)element.get("courseName"));
		    courses.add(course);
		    CourseComboBox.getItems().add(course);
	        if(exam.getCourseId().equals(course.getCourseId()))
	        	CourseComboBox.setValue(course);
		}
        ActionEvent ae = new ActionEvent();
        cmbCourseFilter(ae);
    }
    
    
}
