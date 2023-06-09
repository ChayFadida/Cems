package controllersLecturer;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
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


public class AddNewQuestionController extends AbstractController implements Initializable{
	List<String> coursesSelected;
	ArrayList<Course> courses;
    ArrayList<CheckMenuItem> coursesMenuItems;
    

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
    void getAddQuestion(ActionEvent event) {
    	lblCourses.setText(" ");
    	lblError.setText(" ");
    	coursesSelected= new ArrayList<>();
    	if(getRightAnswer()==null || getAnswer1()==null || getAnswer2()==null || getAnswer3()==null|| getAnswer4()==null 
    			|| getQuestionField()==null || getNotesField()==null || getSubject()==null) {
    		lblError.setText("One of the fields is empty, try again.");
    	}
    	
    	else{
    		HashMap<String,ArrayList<String>> msg = new HashMap<>();
    		ArrayList<String> arr = new ArrayList<>();
    		arr.add("Lecturer");
    		msg.put("client", arr);
    		ArrayList<String> arr1 = new ArrayList<>();
    		arr1.add("addNewQuestion");
    		msg.put("task",arr1);
    		
    		ArrayList<String> arr2 = new ArrayList<>();
    		HashMap<String,String> HmQuestions = new HashMap<>(); //create json of questions
    		HmQuestions.put("answer1", getAnswer1());
    		HmQuestions.put("answer2", getAnswer2());
    		HmQuestions.put("answer3", getAnswer3());
    		HmQuestions.put("answer4", getAnswer4());
    		
    		arr2.add(getQuestionField());
    		arr2.add(HmQuestions.toString());
    		arr2.add(getRightAnswer());
    		arr2.add(getSubject());
    		arr2.add(getNotesField());
    		arr2.add(coursesSelected.toString());
    	
    		msg.put("param", arr2);
    		super.sendMsgToServer(msg);
         	
    		
    	}
    	((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
    }
    
 
    public void start(Stage primaryStage) {
	    try {
	        Parent root =  FXMLLoader.load(getClass().getResource("/guiLecturer/AddNewQuestion.fxml"));
	        Scene scene = new Scene(root);
	        primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.getIcons().add(new Image("/Images/CemsIcon32-Color.png"));
	        // Set the scene to the primary stage
	        primaryStage.setScene(scene);
	        primaryStage.show();
	        super.setPrimaryStage(primaryStage);
	        PressHandler<MouseEvent> press = new PressHandler<>();
	        DragHandler<MouseEvent> drag = new DragHandler<>();
	        root.setOnMousePressed(press);
	        root.setOnMouseDragged(drag);
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cmbRightAnswer.getItems().addAll("1","2","3","4");
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
		    courses.add(new Course((Integer)element.get("courseID"), (String)element.get("courseName")));
		    CheckMenuItem checkMenuItem = new CheckMenuItem(courses.get(i).getCourseName());
		    coursesMenuItems.add(checkMenuItem);
		}
		CoursesMenu.getItems().addAll(coursesMenuItems);
		CoursesMenu.getItems().stream().forEach((MenuItem menuItem) -> menuItem.setOnAction(ev -> {
    		final List<String> selected = CoursesMenu.getItems().stream()
    	            .filter(item -> CheckMenuItem.class.isInstance(item) && CheckMenuItem.class.cast(item).isSelected())
    	            .map(MenuItem::getText)
    	            .collect(Collectors.toList());
    		if(selected.toString()=="[]")
    			lblCourses.setText(" ");
    		else
    			lblCourses.setText(selected.toString());
    		coursesSelected=selected;
    	}));
		
	}


}