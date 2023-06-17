package controllersLecturer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import abstractControllers.AbstractController;
import client.ConnectionServer;
import entities.Course;
import entities.Exam;
import entities.Question;
import entities.QuestionForExam;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
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

public class EditExamController extends AbstractController{
	private ArrayList<Question> qArr ;
	private Exam exam;
    private MyExamBankController myExamBankController;
    private HashMap<Integer, String> HmCourseIdName = new HashMap<>();
    
    public void SetMyExamBankController(MyExamBankController myExamBankController) {
		this.myExamBankController = myExamBankController;
	}
    
    @FXML
    private ComboBox<String> CourseComboBox;

    @FXML
    private TextField DurationTxt;

    @FXML
    private TableView<Question> QuestionTable;

    @FXML
    private Button btnSaveChanges;

    @FXML
    private Button btnSelected;

    @FXML
    private TableColumn<Question, String> clmCourse;

    @FXML
    private TableColumn<Question, String> clmQuestion;

    @FXML
    private TableColumn<Question, Integer> clmScore;

    @FXML
    private TableColumn<Question, String> clmSubject;

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
    
    @FXML
    void cmbCourseFilter(ActionEvent event) {
        String selectedCourse = CourseComboBox.getSelectionModel().getSelectedItem();
        if (selectedCourse.equals("All Courses")) {
        	showTable();
        } else {
            showTableWithFilters(selectedCourse);
        }
    }
    
    void showTable() {
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> arr = new ArrayList<>();
		arr.add("Lecturer");
		msg.put("client", arr);
		ArrayList<String> arr1 = new ArrayList<>();
		arr1.add("getQuestionsById");
		msg.put("task",arr1);
		ArrayList<String> arr2 = new ArrayList<>();
		arr2.add(ConnectionServer.user.getId()+"");
		msg.put("param", arr2);
		super.sendMsgToServer(msg);
		try {
			this.loadQuestions(ConnectionServer.rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		initTableView(qArr);
    	
    }
    
    private void initTableView(ArrayList<Question> arr) {
    	ObservableList<Question> list = FXCollections.observableArrayList(qArr);
		PropertyValueFactory<QuestionForExam, String> pvfQuestion = new PropertyValueFactory<>("details");
		PropertyValueFactory<QuestionForExam, String> pvfSubject = new PropertyValueFactory<>("subject");
		PropertyValueFactory<QuestionForExam, TextField> pvfScore = new PropertyValueFactory<>("score");		
		
		clmCourse.setCellValueFactory(cellData -> {
		    Question question = cellData.getValue();
		    String courseId = question.getCourses();
		    HashMap<String,ArrayList<String>> json = JsonHandler.convertJsonToHashMap(courseId, String.class, ArrayList.class, String.class);
		    // Split the course IDs into an array
		    ArrayList<String> courseIds = json.get("courses");

		    // Create a list to store the course names
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
	}
    
	public void loadQuestions(ArrayList<HashMap<String, Object>> rs) throws Exception {
		qArr= new ArrayList<>();
		if(rs == null) {
			System.out.println("rs is null");
			return;
		}
		for (int i = 0; i < rs.size(); i++) {
		    HashMap<String, Object> element = rs.get(i);
		    qArr.add(new Question((Integer)element.get("questionId"), (String)element.get("details"), (String)element.get("rightAnswer"),
                              (Integer)element.get("questionBank"), (String)element.get("subject"), (String)element.get("answers"),
                              (String)element.get("notes"), (String)element.get("courses")));
		}
	}

    @FXML
    void getSaveChanges(ActionEvent event) {

    }

    @FXML
    void getSelected(ActionEvent event) {

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
    
	void LoadExam(Exam e) {
    	this.exam = e;
    	HmCourseIdName = myExamBankController.getHmCourseIdName();
    	codetXT.setText(exam.getCode());
    	DurationTxt.setText(exam.getDuration() + "");
    	txtName.setText(exam.getExamName());
        lecNotesTxt.setText(exam.getLecturerNote());
        studNotesTxt.setText(exam.getStudentNote());
		CourseComboBox.getItems().add(0, "All Courses");
		ObservableList<String> courseItems = CourseComboBox.getItems();
		courseItems.addAll(HmCourseIdName.values());
		CourseComboBox.setValue(myExamBankController.getcombo().getSelectionModel().getSelectedItem());
        ActionEvent event = new ActionEvent();
        cmbCourseFilter(event);
    }

}
