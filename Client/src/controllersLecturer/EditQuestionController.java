package controllersLecturer;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import abstractControllers.AbstractController;
import client.ConnectionServer;
import entities.Course;
import entities.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionModel;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import thirdPart.JsonHandler;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.LinkedHashMap;


/**
 * Controller class for the lecturer.
 * In this controller the lecturer can Edit the questions from its own question bank.
 */
public class EditQuestionController extends AbstractController {
	Question question;
	List<String> coursesSelected;
	ArrayList<Course> courses;
    ArrayList<CheckMenuItem> coursesMenuItems;
    HashMap<String, ArrayList<Double>> hm;
    private MyQuestionBankController myQuestionBankController;
	
	@FXML
    private Button CloseBtn;

    @FXML
    private MenuButton CoursesMenu;

    @FXML
    private Button MinimizeBtn;

    @FXML
    private TextField NotesField;

    @FXML
    private TextField QuestionField;

    @FXML
    private Button SaveChangesButton;

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
    private Label lblCourses;

    @FXML
    private Label lblError;

    @FXML
    private TextField txtSubject;
    
    /**
     * Create an instance of MyQuestionBankController
     * @param myQuestionBankController instance 
     */
    public void setMyQuestionBankController(MyQuestionBankController myQuestionBankController) {
		this.myQuestionBankController = myQuestionBankController;
	}
    
    /**
     * get the right answer from the relevant text field.
     * @return the right answer
     */
    private String getRightAnswer() {
    	return cmbRightAnswer.getSelectionModel().getSelectedItem();
    }
    /**
     * get the subject from the relevant text fields.
     * @return the subject
     */
    private String getSubject() {
    	return txtSubject.getText();
    }
    /**
     * get answer number one from the text field.
     * @return answer number one.
     */
    private String getAnswer1() {
    	return answer1Field.getText();
    }
    /**
     * get answer number two from the text field.
     * @return answer number two.
     */
    private String getAnswer2() {
    	return answer2Field.getText();
    }
    /**
     * get answer number three from the text field.
     * @return answer number three.
     */
    private String getAnswer3() {
    	return answer3Field.getText();
    }
    /**
     * get answer number four from the text field.
     * @return answer number four.
     */
    private String getAnswer4() {
    	return answer4Field.getText();
    }
    /**
     * get question id from the text field.
     * @return question id.
     */
    private String getQuestionField() {
    	return QuestionField.getText();
    }
    /**
     * get notes from the text field.
     * @return the notes.
     */
    private String getNotesField() {
    	return NotesField.getText();
    }
    /**
     * By activate, close the current window
     * @param event Action event.
     */
    @FXML
    void Close(ActionEvent event) {
        Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
    /**
     * By activate, minimize current window.
     * @param event Action event.
     */
    @FXML
    void Minimize(ActionEvent event) {
    	Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    /**
     * Return Bank Id by Lecturer Id
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
     * Saving the question that were changed.
     * @param event Action event
     */
    @FXML
    void SaveQuestionChanges(ActionEvent event) {		
    	HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> user = new ArrayList<>();
		user.add("Lecturer");
		msg.put("client", user);
		ArrayList<String> query = new ArrayList<>();
		query.add("updateQuestion");
		msg.put("task",query);
		

		ArrayList<String> parameter = new ArrayList<>();
		LinkedHashMap<String,String> HmQuestions = new LinkedHashMap<>(); //create json of questions

		HmQuestions.put("answer1", getAnswer1());
		HmQuestions.put("answer2", getAnswer2());
		HmQuestions.put("answer3", getAnswer3());
		HmQuestions.put("answer4", getAnswer4());

		parameter.add(getQuestionField());
		parameter.add(JsonHandler.convertHashMapToJson(HmQuestions, String.class, String.class));
		parameter.add(getRightAnswer());
		parameter.add(getBankId() + "");
		parameter.add(getSubject());
		parameter.add(getNotesField());
		
		HashMap<String,ArrayList<Integer>> HmCourses = new HashMap<>(); //create json of courses
		ArrayList<Integer> IntegerList = new ArrayList<>();
        for (String str : coursesSelected) {
            Integer value = Integer.parseInt(str);
            IntegerList.add(value);
        }
		HmCourses.put("courses", IntegerList);
		
		parameter.add(JsonHandler.convertHashMapToJson(HmCourses, String.class, ArrayList.class));
		parameter.add(question.getQuestionID() + "");
	
		msg.put("param", parameter);
		super.sendMsgToServer(msg);
		
    	((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
    	myQuestionBankController.CourseFilter(event);
    }
    
    /**
     * Loads questions into the text fields.
     * @param q the question 
     */
    void LoadQuestion(Question q) {
    	this.question = q;
    	QuestionField.setText(question.getDetails());
        NotesField.setText(question.getNotes());
        txtSubject.setText(question.getSubject());
        answer1Field.setText(question.getAnswersHM().get("answer1"));
        answer2Field.setText(question.getAnswersHM().get("answer2"));
        answer3Field.setText(question.getAnswersHM().get("answer3"));
        answer4Field.setText(question.getAnswersHM().get("answer4"));
        cmbRightAnswer.setValue(question.getRightAnswer());
        cmbRightAnswer.getItems().addAll("1","2","3","4");
        
        hm = JsonHandler.convertJsonToHashMap(question.getCourses(),String.class, ArrayList.class);
        
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
     * Loads relevent courses for the lecturer.
     * @param coursesResultSet courses from the DB.
     */
    private void loadCourses(ArrayList<HashMap<String, Object>> coursesResultSet) {
		courses= new ArrayList<>();
		coursesMenuItems= new ArrayList<>();
		if(coursesResultSet==null) {
			System.out.println("Could not load courses from db.");
			return;
		}
		for (int i = 0; i < coursesResultSet.size(); i++) {
		    HashMap<String, Object> element = coursesResultSet.get(i);
		    courses.add(new Course((Integer)element.get("courseID"), (String)element.get("courseName"), null));
		    CheckMenuItem checkMenuItem = new CheckMenuItem(courses.get(i).getCourseName());
		    checkMenuItem.setId((Integer)element.get("courseID")+ "");
		    coursesMenuItems.add(checkMenuItem);
		    Double id = Double.valueOf(checkMenuItem.getId());
		    if (hm.get("courses").contains(id)) {
		        checkMenuItem.setSelected(true);
		    }
		}
		CoursesMenu.getItems().addAll(coursesMenuItems);
		coursesMenuItems.forEach(menuItem -> menuItem.setOnAction(event -> updateSelectedCourses()));

        updateSelectedCourses();
    }	
	/**
	 * update the selected courses by the lecturer.
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
