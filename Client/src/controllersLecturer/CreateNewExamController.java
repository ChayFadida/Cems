package controllersLecturer;
import java.math.BigInteger;


import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import abstractControllers.AbstractController;
import client.ConnectionServer;
import common.CreateNewExamManagerIF;
import entities.Course;
import entities.Lecturer;
import entities.Question;
import entities.QuestionForExam;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import thirdPart.JsonHandler;

/**
 * 
 * Controller class for the lecturer.
 * In this controller the lecturer can create new exam and add it to the exam bank.
 */
public class CreateNewExamController extends AbstractController implements Initializable{
	CreateNewExamManagerIF createNewExamManager;
	private ArrayList<Course> courses;
	private ArrayList<QuestionForExam> qArr;
	private ArrayList<Object> qSelected=new ArrayList<>();
	private HashMap<Integer, String> HmCourseIdName = new HashMap<>();
	private boolean created_success;
	
	public CreateNewExamController() {
		super();
		createNewExamManager = new CreateNewExamManager();
	}

	public CreateNewExamController(CreateNewExamManagerIF createNewExamManager) {
		super();
		this.createNewExamManager = createNewExamManager;
	}

	private class CreateNewExamManager implements CreateNewExamManagerIF{
		
		public String getSubject() {
			return txtSubject.getText();
		}
		public String getCode() {
			return codetXT.getText();
		}
		public String getDuration() {
			return DurauinTxt.getText();
		}
		public String getLecNotes() {
			return lecNotesTxt.getText();
		}
		public String getStudNotes() {
			return studNotesText.getText();
		}
		public String getName() {
			return txtName.getText();
		}
		public void addAllSelectedToArray(ObservableList<QuestionForExam> selectedItems) {
			qSelected.addAll(selectedItems);
		}
		public ArrayList<Object> getSelected(){
			return qSelected;
		}
		public void initializeSelected() {
			qSelected = new ArrayList<>();
		}
		
		public ArrayList<HashMap<String, Object>> insertExamToDB(String code, String duration, String lecNotes, String studNotes, String name, String subject){
			HashMap<String,ArrayList<Object>> msg = new HashMap<>();
			ArrayList<Object> user = new ArrayList<>();
			HashMap<String, Object> bank=getExamBank();
			user.add("Lecturer");
			msg.put("client", user);
			ArrayList<Object> query = new ArrayList<>();
			query.add("insertExam");
			msg.put("task", query);
			ArrayList<Object> parameter = new ArrayList<>();
			parameter.add(CourseComboBox.getSelectionModel().getSelectedItem().getCourseId()+"");
			parameter.add(subject);
			parameter.add(duration);
			parameter.add(lecNotes);
			parameter.add(studNotes);
			parameter.add(ConnectionServer.user.getId()+"");
			parameter.add(code);
			parameter.add((getLecturerExamCount() + 1) + "");
			parameter.add((Integer) bank.get("bankId")+"");
			parameter.add(name);
			msg.put("questions", qSelected);
			msg.put("param", parameter);
			sendMsgToServer(msg);
			return ConnectionServer.rs;
		}
		
		public ArrayList<HashMap<String, Object>> insertQuestionsToDB(Integer examId){
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
			query.add("insertQuestionsForExam");
			msg.put("task", query);
			ArrayList<Object> parameter = new ArrayList<>();
			parameter.add(examId + "");
			parameter.add(qIdsStr);
			parameter.add(qScoresStr);
			msg.put("param", parameter);
			sendMsgToServer(msg);
			return ConnectionServer.rs;
		}
		
		public ArrayList<HashMap<String, Object>> updateExamBank(HashMap<String, Object> bank, Integer examId){
			String exams = (String) bank.get("exams");
			HashMap<String,ArrayList<Integer>> jsonHM= JsonHandler.convertJsonToHashMap(exams, String.class, ArrayList.class, Integer.class);
			ArrayList<Integer> examsInBank = jsonHM.get("exams");
			examsInBank.add(examId);
			jsonHM.put("exams", examsInBank);
			String jsonString = JsonHandler.convertHashMapToJson(jsonHM, String.class, ArrayList.class);
			HashMap<String,ArrayList<String>> msg = new HashMap<>();
			ArrayList<String> user = new ArrayList<>();
			user.add("Lecturer");
			msg.put("client", user);
			ArrayList<String> query = new ArrayList<>();
			query.add("updateExamBankById");
			msg.put("task", query);
			ArrayList<String> parameter = new ArrayList<>();
			parameter.add(bank.get("bankId") + "");
			parameter.add(jsonString);
			msg.put("param", parameter);
			sendMsgToServer(msg);
			return ConnectionServer.rs;
		}
		
		public ArrayList<HashMap<String, Object>> getExamBankQuery() {
			HashMap<String,ArrayList<String>> msg = new HashMap<>();
			ArrayList<String> user = new ArrayList<>();
			user.add("Lecturer");
			msg.put("client", user);
			ArrayList<String> query = new ArrayList<>();
			query.add("getExamBankByLecId");
			msg.put("task", query);
			ArrayList<String> parameter = new ArrayList<>();
			parameter.add(ConnectionServer.user.getId()+"");
			msg.put("param", parameter);
			sendMsgToServer(msg);
			return ConnectionServer.rs;
		}
	}

	
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
    private TableColumn<QuestionForExam, String> clmCourse;

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
    private TextField txtSubject;
    
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
    private Label lblErrorSubject;
    
	 /**
     * Activate necessary methodes and send relevant message to the DB.
     * @param code exam code
     * @param duration exam duration 
     * @param lecNotes exam lecturer notes
     * @param studNotes exam student notes
     * @param name exam name 
     * @param subject exam subject
     */
	public void createExam(String code, String duration, String lecNotes, String studNotes, String name, String subject) {
		HashMap<String, Object> bank=getExamBank();
		ArrayList<HashMap<String,Object>> rs = createNewExamManager.insertExamToDB(code, duration, lecNotes, studNotes, name, subject);
		if(rs == null) {
			System.out.println("Could not load data from DB.");
			return;
		}
		BigInteger lastId = (BigInteger) rs.get(0).get("id");
		Integer examId = lastId.intValue();
		addToExamBank(bank,examId);
		addQuestionsAndScore(examId);
	}
	
    /**
     * By activate, the lecturer finish to create the exam.
     * Checking that the lecturer insert all the neccery data.
     * @param event Action event
     */
    @FXML
    public void getFinish(ActionEvent event) {
    	if(lblError==null)
    		lblError= new Label();
    	lblError.setText(" ");
    	if(lblErrorCode==null)
    		lblErrorCode= new Label();
    	lblErrorCode.setText(" ");
    	if(lblErrorDuration==null)
    		lblErrorDuration= new Label();
    	lblErrorDuration.setText(" ");
    	if(lblErrorSelected==null)
    		lblErrorSelected= new Label();
    	lblErrorSelected.setText(" ");
    	if(lblErrorSubject==null)
    		lblErrorSubject= new Label();
    	lblErrorSubject.setText(" ");
    	String subject = createNewExamManager.getSubject();
    	String code = createNewExamManager.getCode();
    	String duration = createNewExamManager.getDuration();
    	String lecNotes = createNewExamManager.getLecNotes();
    	String studNotes = createNewExamManager.getStudNotes();
    	String name = createNewExamManager.getName();
    	boolean flag= false;
    	
    	if(name.equals("")) {
			lblErrorName.setText("You must enter an exam name");
    		flag=true;
    	}
    	if(qArr == null || qArr.isEmpty()) {
    		lblError.setText("You must select atleast one question");
    		flag=true;
    	}
    	if(subject.equals("")) {
    		lblErrorSubject.setText("You must enter a subject");
    		flag=true;
    	}
    	if(code.length() != 4 || !code.matches("^[a-zA-Z0-9]+$")) {
    		lblErrorCode.setText("Code must be 4 digits and contains only letters and numbers.");
    		flag=true;
    	}
    	//code refactor
    	if(!duration.matches("\\d+") || duration != "0") {
    		lblErrorDuration.setText("Duration must contain only numbers above 0 (represents minutes).");
    		flag=true;
    	}

    	if(createNewExamManager.getSelected().isEmpty()) {
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
    	if(lecNotes == null)
    		lecNotes = " ";
    	if(studNotes == null)
    		studNotes = " ";
    	createExam(code,duration,lecNotes,studNotes,name,subject);
    	if(created_success) {
			lblError.setText("Exam uploaded successfully");
		}
		else {
			lblError.setText("Problem at exam upload");
		}
    }
    
   

	
	public Label getLblError() {
		return lblError;
	}

	public void setLblError(Label lblError) {
		this.lblError = lblError;
	}

	public Label getLblErrorCode() {
		return lblErrorCode;
	}

	public void setLblErrorCode(Label lblErrorCode) {
		this.lblErrorCode = lblErrorCode;
	}

	public Label getLblErrorDuration() {
		return lblErrorDuration;
	}

	public void setLblErrorDuration(Label lblErrorDuration) {
		this.lblErrorDuration = lblErrorDuration;
	}

	public Label getLblScore() {
		return lblScore;
	}

	public void setLblScore(Label lblScore) {
		this.lblScore = lblScore;
	}

	public Label getLblErrorSelected() {
		return lblErrorSelected;
	}

	public void setLblErrorSelected(Label lblErrorSelected) {
		this.lblErrorSelected = lblErrorSelected;
	}

	public Label getLblErrorName() {
		return lblErrorName;
	}

	public void setLblErrorName(Label lblErrorName) {
		this.lblErrorName = lblErrorName;
	}

	public Label getLblErrorSubject() {
		return lblErrorSubject;
	}

	public void setLblErrorSubject(Label lblErrorSubject) {
		this.lblErrorSubject = lblErrorSubject;
	}

	/**
	 * Add question and score to the lecturer exam.
	 * @param examId exam id
	 */
	private void addQuestionsAndScore(Integer examId) {
		ArrayList<HashMap<String,Object>> rs = createNewExamManager.insertQuestionsToDB(examId);
		if(rs == null){
			System.out.println("Could not get data from server.");
			created_success = false;
		}
		if((int)rs.get(0).get("affectedRows")==1) {
			resetAll();
		}
		else {
			created_success = false;
		}
	}
	
	/**
	 * After creating new exam, Add it to the lecturer exam bank.
	 * @param bank lecturer exam bank.
	 * @param examId lecturer exam id.
	 */
	private void addToExamBank(HashMap<String, Object> bank, Integer examId) {
		ArrayList<HashMap<String,Object>> rs = createNewExamManager.updateExamBank(bank, examId);
		if(rs == null){
			System.out.println("Could not load data from server.");
		}
		if((int) rs.get(0).get("affectedRows")==1) {
			resetAll();
		}
		else {
			created_success = false;
		}
	}
	
	/**
	 * Reset and initialize the text fields.
	 */
	private void resetAll() {
		lblError.setText(" ");
		lblErrorName.setText(" ");
		lblErrorCode.setText(" ");
		lblErrorDuration.setText(" ");
		lblErrorSelected.setText(" ");
		txtSubject.setText("");
		txtName.setText("");
		codetXT.setText("");
		DurauinTxt.setText("");
		lecNotesTxt.setText("");
		studNotesText.setText("");
		lblScore.setText("0/100");
		loadQuestions(CourseComboBox.getSelectionModel().getSelectedItem());
	}
	
	/**
	 * Get the exam bank.
	 * @return the exam bank in an hash map form 
	 */
	private HashMap<String, Object> getExamBank() {
		ArrayList<HashMap<String,Object>> rs = createNewExamManager.getExamBankQuery();
		if(rs == null) {
			System.out.println("Could not get data from DB.");
		}
		if(rs.get(0)==null) {
			System.out.println("Empty table from Sql");
		}
		return rs.get(0);
	}

	/**
	 * Get how many students did the exam.
	 * @return number of students did the exam.
	 */
	private Integer getLecturerExamCount() {
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> user = new ArrayList<>();
		user.add("Lecturer");
		msg.put("client", user);
		ArrayList<String> query = new ArrayList<>();
		query.add("getExamCountByLecId");
		msg.put("task", query);
		ArrayList<String> parameter = new ArrayList<>();
		parameter.add(ConnectionServer.user.getId()+"");
		msg.put("param", parameter);
		super.sendMsgToServer(msg);
		ArrayList<HashMap<String,Object>> rs = ConnectionServer.rs;
		if(rs == null) {
			System.out.println("Could not load data from server.");
		}
		return ((Long) rs.get(0).get("count")).intValue();
	}
	
	/**
	 * Initialize course filter.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setCoursesComboBox();
	}
	
	/**
	 * Sets course filter with relevant courses.
	 */
	private void setCoursesComboBox() {
		ConnectionServer.getInstance();
		Lecturer lecturer = (Lecturer) ConnectionServer.user;
		ArrayList<Integer> coursesId = (ArrayList<Integer>) lecturer.getCoursesIdHM().get("courses");
		for(Integer id : coursesId) {
			HashMap<String,ArrayList<String>> msg = new HashMap<>();
			ArrayList<String> user = new ArrayList<>();
			user.add("Lecturer");
			msg.put("client", user);
			ArrayList<String> query = new ArrayList<>();
			query.add("getCoursesByCourseId");
			msg.put("task", query);
			ArrayList<String> parameter = new ArrayList<>();
			parameter.add(id + "");
			msg.put("param", parameter);
			super.sendMsgToServer(msg);
			ArrayList<HashMap<String, Object>> rs = ConnectionServer.rs;
			if(rs == null) {
				System.out.println("Could not load data from server.");
				return;
			}
			courses=new ArrayList<>();
			HashMap<String, Object> element = rs.get(0);
			Course course= new Course((Integer)element.get("courseID"), (String)element.get("courseName"), (Integer)element.get("departmentId"));
		    HmCourseIdName.put((Integer)element.get("courseID"), (String)element.get("courseName"));
		    courses.add(course);
		    CourseComboBox.getItems().add(course);
		}
	    CourseComboBox.setOnAction(new EventHandler<ActionEvent>() {
	         public void handle(ActionEvent ae) {
	            loadQuestions(CourseComboBox.getSelectionModel().getSelectedItem());
	         }
	    });
	}
	
	/**
	 * Loads the question accourding to the course the lecturer choose.
	 * @param selectedItem the selected courses by the lecturer.
	 */
	private void loadQuestions(Course selectedItem) {
		qArr=new ArrayList<>();
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> user = new ArrayList<>();
		user.add("Lecturer");
		msg.put("client", user);
		ArrayList<String> query = new ArrayList<>();
		query.add("getQuestionsByIdAndCourse");
		msg.put("task", query);
		ArrayList<String> parameter = new ArrayList<>();
		parameter.add(ConnectionServer.user.getId() + "");
		parameter.add(selectedItem.getCourseId()+"");
		msg.put("param", parameter);
		super.sendMsgToServer(msg);
		ArrayList<HashMap<String,Object>> rs = ConnectionServer.rs;
		if(rs == null) {
			System.out.println("Could not load data from the server.");
		}
		for (int i = 0 ; i < rs.size() ; i++) {
		    HashMap<String, Object> element = rs.get(i);
		    Question q = new Question((Integer) element.get("questionId"), (String) element.get("details"),
		    		(String) element.get("rightAnswer"), (Integer) element.get("questionBank"), 
		    		(String) element.get("subject"), (String) element.get("answers"),(String) element.get("notes"), (String) element.get("courses"));
		    QuestionForExam questionForExam = new QuestionForExam(q,"0");
		    qArr.add(questionForExam);
		}
		loadTable();
	}
	
	/**
	 * Loading the table with the relevant questions.
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
		    for (String id : courseIds) {
		        int courseIdInt = Integer.parseInt(id.trim());
		        String courseName = HmCourseIdName.get(courseIdInt);
		        if (courseName != null) {
		            courseNames.add(courseName);
		        }
		    }
		    String joinedNames = String.join(", ", courseNames);
		    return new SimpleStringProperty(joinedNames);
		});
		QuestionTable.setItems(list);
		QuestionTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}

    /**
     * Get the selected questions and update the score lable accourding to the question score.
     * @param event
     */
    @FXML
    public void getSelected(ActionEvent event) {
    	createNewExamManager.initializeSelected();
    	sum = 0;
    	if(lblErrorSelected==null)
    		lblErrorSelected= new Label();
    	lblErrorSelected.setText(" ");
    	if(lblScore==null)
    		lblScore= new Label();
    	lblScore.setText("0/100");
    	createNewExamManager.addAllSelectedToArray(QuestionTable.getSelectionModel().getSelectedItems());
    	//qSelected.addAll(QuestionTable.getSelectionModel().getSelectedItems());
    	for(Object q: createNewExamManager.getSelected()) {
    		String scoreStr = ((QuestionForExam) q).getScore().getText();
    		if(!scoreStr.matches("\\d+")) {
    			lblErrorSelected.setText("One of the selected questions score is not number, try again.");
    			createNewExamManager.initializeSelected();
    			return;
    		}
    		int score = Integer.parseInt(((QuestionForExam) q).getScore().getText());
    		if(score == 0) {
    			lblErrorSelected.setText("One of the selected questions score is set to '0', try again.");
    			createNewExamManager.initializeSelected();
    			return;
    		}
    		sum = sum + score;
    	}
    	lblScore.setText(sum+"/100");
    	if(sum!=100) {
    		lblErrorSelected.setText("Total score is not '100', change scores and try again.");
    		createNewExamManager.initializeSelected();
    	}
    }
}