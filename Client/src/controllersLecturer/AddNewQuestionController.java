package controllersLecturer;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import abstractControllers.AbstractController;
import client.ConnectionServer;
import entities.Course;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import thirdPart.JsonHandler;
import java.util.LinkedHashMap;

/**
 * Controller class for the Lecturer.
 * In this controller the lecturer can add new questions its own question bank.
 * Extends AbstractController implements Initializable
 *
 */
public class AddNewQuestionController extends AbstractController implements Initializable{
	List<String> coursesSelected = new ArrayList<>();
	HashMap<Integer,String> courses;
    ArrayList<CheckMenuItem> coursesMenuItems;
    private MyQuestionBankController myQuestionBankController;
    
	@FXML
    private MenuButton CoursesMenu;
    
	@FXML
    private Button AddQuestionToBankButton;

    @FXML
    private Button CloseBtn;

    @FXML
    private Button MinimizeBtn;

    @FXML
    private TextField NotesField;

    @FXML
    private TextField QuestionField;

    @FXML
    private TextField answer1Field;

    @FXML
    private TextField answer2Field;

    @FXML
    private TextField answer3Field;

    @FXML
    private TextField answer4Field;

    @FXML
    private ComboBox<String> cmbRightAnswer;

    @FXML
    private Label lblError;
    
    @FXML
    private Label lblCourses;
  
    @FXML
    private TextField txtSubject;
    

    /**
     * Setts instance of the MyQuestionBankController.
     * @param myQuestionBankController instance of the MyQuestionBankController.
     */
    public void setMyQuestionBankController(MyQuestionBankController myQuestionBankController) {
		this.myQuestionBankController = myQuestionBankController;
	}
    
    /**
     * Saves the right answer of the question.
     * @return right Answer.
     */
    private String getRightAnswer() {
    	return cmbRightAnswer.getSelectionModel().getSelectedItem();
    }
    
    /**
     * Get the new question subject from the text box.
     * @return subject
     */
    private String getSubject() {
    	return txtSubject.getText();
    }
    
    /**
     * Get the new question first answer from the text box.
     * @return first answer.
     */
    private String getAnswer1() {
    	return answer1Field.getText();
    }
    
    /**
     * Get the new question second answer from the text box.
     * @return second answer.
     */
    private String getAnswer2() {
    	return answer2Field.getText();
    }
    
    /**
     * Get the new question third answer from the text box.
     * @return third answer.
     */
    private String getAnswer3() {
    	return answer3Field.getText();
    }
    
    /**
     * Get the new question fourth answer from the text box.
     * @return fourth answer.
     */
    private String getAnswer4() {
    	return answer4Field.getText();
    }
    
    /**
     * Get the new question from the text box.
     * @return question.
     */
    private String getQuestionField() {
    	return QuestionField.getText();
    }
    
    /**
     * Get the new question notes from the text box.
     * @return notes.
     */
    private String getNotesField() {
    	return NotesField.getText();
    }
    
    /**
     * By pressing the close button, the program will close cuurent window.
     * @param event Action event
     */
    @FXML
    void Close(ActionEvent event) {
        Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
    
    /**
    * By pressing the minimize button, the program will minimze cuurent window.
     * @param event Action event
     */
    @FXML
    void Minimize(ActionEvent event) {
    	Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    
    /**
     * Return BankId by Lecturer Id
     */
    public Integer getBankId(){
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> arr = new ArrayList<>();
		arr.add("Lecturer");
		msg.put("client", arr);
		ArrayList<String> arr1 = new ArrayList<>();
		arr1.add("getExamBankByLecId");
		msg.put("task",arr1);
		ArrayList<String> arr2 = new ArrayList<>();
		arr2.add(ConnectionServer.user.getId() + "");
		msg.put("param", arr2);
		super.sendMsgToServer(msg);
		Integer id = (Integer) ConnectionServer.rs.get(0).get("bankId");
		return id;
    }

  /**
     * By pressing add question button, send message to the server and add to the data base the relevant question.
     * @param event Action event
     */
    @FXML
    void getAddQuestion(ActionEvent event) {
    	lblCourses.setText(" ");
    	lblError.setText(" ");
    	if(getRightAnswer()==null || getAnswer1()==null || getAnswer2()==null || getAnswer3()==null|| getAnswer4()==null
    			|| getQuestionField()==null || getNotesField()==null || getSubject()==null || coursesSelected.isEmpty()) {
    		lblError.setText("One of the fields is empty, try again.");
    	}
    	

    	else{
    		HashMap<String,ArrayList<String>> msg = new HashMap<>();
    		ArrayList<String> user = new ArrayList<>();
    		user.add("Lecturer");
    		msg.put("client", user);
    		ArrayList<String> query = new ArrayList<>();
    		query.add("addNewQuestion");
    		msg.put("task",query);
    		

    		ArrayList<String> parameter = new ArrayList<>();
    		HashMap<String,String> HmQuestions = new HashMap<>(); //create json of questions
    		LinkedHashMap<String,String> HmQuestions = new LinkedHashMap<>(); //create json of questions
    		HmQuestions.put("answer1", getAnswer1());
    		HmQuestions.put("answer2", getAnswer2());
    		HmQuestions.put("answer3", getAnswer3());
    		HmQuestions.put("answer4", getAnswer4());
    		
    		arr2.add(getQuestionField());
    		arr2.add(JsonHandler.convertHashMapToJson(HmQuestions, String.class, String.class));
    		arr2.add(getRightAnswer());
    		arr2.add(getBankId() + "");
    		arr2.add(getSubject());
    		arr2.add(getNotesField());
    		
    		HashMap<String,ArrayList<Integer>> HmCourses = new HashMap<>(); //create json of courses
    		ArrayList<Integer> doubleList = new ArrayList<>();
            for (String str : coursesSelected) {
                doubleList.add(Integer.parseInt(str));
            }
    		HmCourses.put("courses", doubleList);
    		
    		parameter.add(JsonHandler.convertHashMapToJson(HmCourses, String.class, ArrayList.class));
    	
    		msg.put("param", parameter);
    		super.sendMsgToServer(msg);
    		ArrayList<HashMap<String,Object>> questionResultSet = ConnectionServer.rs;
    		if(questionResultSet == null) {
    			System.out.println("Could not get question");
    		}
    		long lastId=((long) questionResultSet.get(0).get("keys"));
    		Long lId = lastId;
    		Integer questionId = lId.intValue();
    		addToQB(questionId);
    		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
    		myQuestionBankController.CourseFilter(event);
    	}
    }
    
    /**
     * Add the question by its id to the question bank.
     * @param id Question id.
     */
    private void addToQB(Integer id) {
    	HashMap<String,ArrayList<String>> msg = new HashMap<>();
    	HashMap<String,Object> bank = getQuestionBank(ConnectionServer.user.getId());
    	String questions = (String) bank.get("questions");
    	HashMap<String,ArrayList<Integer>> jsonHM= JsonHandler.convertJsonToHashMap(questions, String.class, ArrayList.class,Integer.class);
		ArrayList<Integer> questionsInBank = jsonHM.get("questions");
		questionsInBank.add(id);
		jsonHM.put("questions", questionsInBank);
		String jsonString = JsonHandler.convertHashMapToJson(jsonHM, String.class, ArrayList.class);
		ArrayList<String> user = new ArrayList<>();
		user.add("Lecturer");
		msg.put("client", user);
		ArrayList<String> query = new ArrayList<>();
		query.add("updateQuestionBank");
		msg.put("task",query);
		ArrayList<String> parameter = new ArrayList<>();
		parameter.add(bank.get("bankID")+"");
		parameter.add(jsonString);
		msg.put("param",parameter);
		super.sendMsgToServer(msg);
	}
    
    /**
     * Retrive the question bank for a specific user
     * @param id user id
     * @return hash map of the question.
     */
    private HashMap<String, Object> getQuestionBank(int id) {
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> user = new ArrayList<>();
		user.add("Lecturer");
		msg.put("client", user);
		ArrayList<String> query = new ArrayList<>();
		query.add("getQuestionBank");
		msg.put("task",query);
		ArrayList<String> parameter = new ArrayList<>();
		parameter.add(ConnectionServer.user.getId()+"");
		msg.put("param",parameter);
		super.sendMsgToServer(msg);
		ArrayList<HashMap<String,Object>> rs = ConnectionServer.rs;
		if(rs == null) {
			System.out.println("Could not get data.");
		}
		if(rs.get(0)==null) {
			System.out.println("Empty table from Sql");
		}
		return rs.get(0);
	}

    /**
     * Initialize the controller
     */
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		cmbRightAnswer.getItems().addAll("1","2","3","4");
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> user = new ArrayList<>();
		user.add("Lecturer");
		msg.put("client", user);
		ArrayList<String> query = new ArrayList<>();
		query.add("getCoursesIdByLecturerId");
		msg.put("task",query);
		ArrayList<String> parameter = new ArrayList<>();
		parameter.add(ConnectionServer.user.getId() + "");
		msg.put("param",parameter);
		super.sendMsgToServer(msg);
		
		HashMap<String,ArrayList<String>> msg1 = new HashMap<>();
		ArrayList<String> user2 = new ArrayList<>();
		user2.add("Lecturer");
		msg1.put("client", user2);
		ArrayList<String> query2 = new ArrayList<>();
		query2.add("getCoursesNameById");
		msg1.put("task",query2);
		
		ArrayList<String> parameter2 = new ArrayList<>();
		@SuppressWarnings("unchecked")
		ArrayList<String> crsId = (ArrayList<String>) JsonHandler.convertJsonToHashMap((String)ConnectionServer.rs.get(0).get("courseId"), String.class, ArrayList.class, String.class).get("courses");
		parameter2.add(crsId.size() + "");
		parameter2.addAll(crsId);
		msg1.put("param",parameter2);
		super.sendMsgToServer(msg1);
		
		try {
			this.loadCourses(ConnectionServer.rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    /**
     * Loads relevent courses from the DB.
     * @param courseResultSet the relevant courses of the lecturer.
     */
	private void loadCourses(ArrayList<HashMap<String, Object>> courseResultSet) {
		courses= new HashMap<>();
		coursesMenuItems= new ArrayList<>();
		if(courseResultSet==null) {
			System.out.println("Could not load courses");
			return;
		}
		for (int i = 0; i < courseResultSet.size(); i++) {
		    HashMap<String, Object> element = courseResultSet.get(i);
		    courses.put((Integer)element.get("courseID"), (String)element.get("courseName"));
		    CheckMenuItem checkMenuItem = new CheckMenuItem((String)element.get("courseName"));
		    checkMenuItem.setId((Integer)element.get("courseID")+ "");
		    coursesMenuItems.add(checkMenuItem);
		}
		CoursesMenu.getItems().addAll(coursesMenuItems);
		coursesMenuItems.forEach(menuItem -> menuItem.setOnAction(event -> updateSelectedCourses()));
        updateSelectedCourses();
		
	}
	
	/**
	 * Update the list of the courses the lecturer choose.
	 */
	private void updateSelectedCourses() {
        List<String> selected = coursesMenuItems.stream()
                .filter(CheckMenuItem::isSelected)
                .map(MenuItem::getText)
                .collect(Collectors.toList());
        List<String> selectedId = coursesMenuItems.stream()
                .filter(CheckMenuItem::isSelected)
                .map(MenuItem::getId)
                .collect(Collectors.toList());
        if (selected.isEmpty()) {
            lblCourses.setText(" ");
        } else {
            lblCourses.setText(selected.toString());
        }
        coursesSelected = selectedId;
    }
}
	
	
