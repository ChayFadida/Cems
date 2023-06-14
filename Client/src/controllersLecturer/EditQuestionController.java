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
    
    public void setMyQuestionBankController(MyQuestionBankController myQuestionBankController) {
		this.myQuestionBankController = myQuestionBankController;
	}

    private String getRightAnswer() {
    	return cmbRightAnswer.getSelectionModel().getSelectedItem();
    }
    private String getSubject() {
    	return txtSubject.getText();
    }

    private String getAnswer1() {
    	return answer1Field.getText();
    }
    private String getAnswer2() {
    	return answer2Field.getText();
    }
    private String getAnswer3() {
    	return answer3Field.getText();
    }
    private String getAnswer4() {
    	return answer4Field.getText();
    }
    private String getQuestionField() {
    	return QuestionField.getText();
    }
    private String getNotesField() {
    	return NotesField.getText();
    }
    
    @FXML
    void Close(ActionEvent event) {
        Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    void Minimize(ActionEvent event) {
    	Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    
    @FXML
    void SaveQuestionChanges(ActionEvent event) {		
    	HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> arr = new ArrayList<>();
		arr.add("Lecturer");
		msg.put("client", arr);
		ArrayList<String> arr1 = new ArrayList<>();
		arr1.add("updateQuestion");
		msg.put("task",arr1);
		
		ArrayList<String> arr2 = new ArrayList<>();
		HashMap<String,String> HmQuestions = new HashMap<>(); //create json of questions
		HmQuestions.put("answer1", getAnswer1());
		HmQuestions.put("answer2", getAnswer2());
		HmQuestions.put("answer3", getAnswer3());
		HmQuestions.put("answer4", getAnswer4());
		
		arr2.add(getQuestionField());
		arr2.add(JsonHandler.convertHashMapToJson(HmQuestions, String.class, String.class));
		arr2.add(getRightAnswer());
		arr2.add(getSubject());
		arr2.add(getNotesField());
		
		HashMap<String,ArrayList<Double>> HmCourses = new HashMap<>(); //create json of courses
		ArrayList<Double> doubleList = new ArrayList<>();
        for (String str : coursesSelected) {
            double value = Double.parseDouble(str);
            doubleList.add(value);
        }
		HmCourses.put("courses", doubleList);
		
		arr2.add(JsonHandler.convertHashMapToJson(HmCourses, String.class, ArrayList.class));
		arr2.add(question.getQuestionID() + "");
	
		msg.put("param", arr2);
		super.sendMsgToServer(msg);
		
    	((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
    	myQuestionBankController.showTable(event);
    	
    }
    
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
		ArrayList<String> arr = new ArrayList<>();
		arr.add("Lecturer");
		msg.put("client", arr);
		ArrayList<String> arr1 = new ArrayList<>();
		arr1.add("getCourses");
		msg.put("task",arr1);
		super.sendMsgToServer(msg);
		try {
			this.loadCourses(ConnectionServer.rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    private void loadCourses(ArrayList<HashMap<String, Object>> rs) {
		courses= new ArrayList<>();
		coursesMenuItems= new ArrayList<>();
		if(rs==null) {
			System.out.println("RS is null");
			return;
		}
		for (int i = 0; i < rs.size(); i++) {
		    HashMap<String, Object> element = rs.get(i);
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
