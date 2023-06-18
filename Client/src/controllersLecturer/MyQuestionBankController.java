package controllersLecturer;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import abstractControllers.AbstractController;
import abstractControllers.AbstractController.DragHandler;
import abstractControllers.AbstractController.PressHandler;
import client.ConnectionServer;
import controllers.UpdateQuestionScreenController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import thirdPart.JsonHandler;
import entities.Course;
import entities.Exam;
import entities.Lecturer;
import entities.Question;
public class MyQuestionBankController extends AbstractController implements Initializable{
	
	private ArrayList<Question> qArr ;
	private LecturerMenuController lecturerMenuController;
	private Lecturer lecturer;
	private HashMap<Integer, String> HmCourseIdName = new HashMap<>();
	
    @FXML
    private Button AddNewQuestionButton;

    @FXML
    private Button DeleteQuestionButton;

    @FXML
    private Button EditQuestionButton;
    
    @FXML
    private Button closeButton;

    @FXML
    private Button minimizeButton;
    
    @FXML
    private ComboBox<String> CourseComboBox;
    
    @FXML
    private TableView<Question> QuestionBankLecTable;
    
    @FXML
    private TableColumn<Question, String> clmCourse;

    @FXML
    private TableColumn<Question, String> clmDetails;

    @FXML
    private TableColumn<Question,String> clmSubject;
    
    @FXML
    void CourseFilter(ActionEvent event) {
        String selectedCourse = CourseComboBox.getSelectionModel().getSelectedItem();
        if (selectedCourse.equals("All Courses")) {
        	showTable();
        } else {
            showTableWithFilters(selectedCourse);
        }

	}
    
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
    
    void showTableWithFilters(String selectedCourse) {
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> arr = new ArrayList<>();
		arr.add("Lecturer");
		msg.put("client", arr);
		ArrayList<String> arr1 = new ArrayList<>();
		arr1.add("getQuestionsByIdByCourse");
		msg.put("task",arr1);
		ArrayList<String> arr2 = new ArrayList<>();
		arr2.add(getBankId()+"");
		ArrayList<Integer> crsIds = new ArrayList<>();
		for(Integer id: HmCourseIdName.keySet()) {
			if(HmCourseIdName.get(id).equals(selectedCourse)) {
				crsIds.add(id);
			}
		}
		for(Integer id: crsIds) {
			arr2.add(id + "");
		}
		msg.put("param", arr2);
		super.sendMsgToServer(msg);
		try {
			this.loadQuestions(ConnectionServer.rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		initTableView(qArr);
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
    	ObservableList<Question> list = FXCollections.observableArrayList(arr);
		PropertyValueFactory<Question, String> pvfDetails = new PropertyValueFactory<>("details");
		PropertyValueFactory<Question, String> pvfSubject = new PropertyValueFactory<>("subject");
		clmDetails.setCellValueFactory(pvfDetails);
		clmSubject.setCellValueFactory(pvfSubject);
		
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
		
		QuestionBankLecTable.setItems(list);
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
    void Minimize(ActionEvent event) {
    	Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    
    @FXML
    void Close(ActionEvent event) {
    	System.exit(0);
    }

    @FXML

    void AddNewQuestion(ActionEvent event) throws IOException {	
		Stage primaryStage = new Stage();
		AddNewQuestionController addNewQuestionController;
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/guiLecturer/AddNewQuestion.fxml").openStream());
		addNewQuestionController = loader.getController();
		addNewQuestionController.setMyQuestionBankController(this);
		try {
	        Scene scene = new Scene(root);
	        scene.getStylesheets().add("/gui/GenericStyleSheet.css");
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
    @FXML
    void EditQuestion(ActionEvent event) throws IOException {
		Stage primaryStage = new Stage();
		EditQuestionController editQuestionController;
		SelectionModel<Question> selectionModel = QuestionBankLecTable.getSelectionModel();
    	Question selectedItem = selectionModel.getSelectedItem();
    	if(!(selectedItem == null)) {
    		FXMLLoader loader = new FXMLLoader();
    		Pane root = loader.load(getClass().getResource("/guiLecturer/EditQuestion.fxml").openStream());
    		editQuestionController = loader.getController();
    		editQuestionController.setMyQuestionBankController(this);
    		editQuestionController.LoadQuestion(selectedItem);
    		try {
    	        Scene scene = new Scene(root);
    	        scene.getStylesheets().add("/gui/GenericStyleSheet.css");
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
    		
    }
    
    @FXML
    void DeleteQuestion(ActionEvent event) {
    	SelectionModel<Question> selectionModel = QuestionBankLecTable.getSelectionModel();
    	Question selectedItem = selectionModel.getSelectedItem();
    	HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> arr = new ArrayList<>();
		arr.add("Lecturer");
		msg.put("client", arr);
		ArrayList<String> arr1 = new ArrayList<>();
		arr1.add("deleteQuestion");
		msg.put("task",arr1);
		ArrayList<String> arr2 = new ArrayList<>();
		arr2.add(selectedItem.getQuestionID() + "");
		msg.put("param",arr2);
		super.sendMsgToServer(msg);;
		deleteFromQB(selectedItem.getQuestionID());
		CourseFilter(event);
    }

	private void deleteFromQB(Integer id) {
    	HashMap<String,ArrayList<String>> msg = new HashMap<>();
    	HashMap<String,Object> bank = getQuestionBank(ConnectionServer.user.getId());
    	String questions = (String) bank.get("questions");
    	HashMap<String,ArrayList<Integer>> jsonHM= JsonHandler.convertJsonToHashMap(questions, String.class, ArrayList.class,Integer.class);
		ArrayList<Integer> questionsInBank = jsonHM.get("questions");
		if(questionsInBank.contains(id)) {
			questionsInBank.remove(id);
			jsonHM.put("questions",questionsInBank);
		}
		else {
			System.out.println("Problem at removing question from bank");
			return;
		}
		
		String jsonString = JsonHandler.convertHashMapToJson(jsonHM, String.class, ArrayList.class);
		ArrayList<String> arr = new ArrayList<>();
		arr.add("Lecturer");
		msg.put("client", arr);
		ArrayList<String> arr1 = new ArrayList<>();
		arr1.add("updateQuestionBank");
		msg.put("task",arr1);
		ArrayList<String> arr2 = new ArrayList<>();
		arr2.add(bank.get("bankID")+"");
		arr2.add(jsonString);
		msg.put("param",arr2);
		super.sendMsgToServer(msg);
	}
	
	private HashMap<String, Object> getQuestionBank(int id) {
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> arr = new ArrayList<>();
		arr.add("Lecturer");
		msg.put("client", arr);
		ArrayList<String> arr1 = new ArrayList<>();
		arr1.add("getQuestionBank");
		msg.put("task",arr1);
		ArrayList<String> arr2 = new ArrayList<>();
		arr2.add(ConnectionServer.user.getId()+"");
		msg.put("param",arr2);
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
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//those queries return the courses names of the lecturer
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> arr = new ArrayList<>();
		arr.add("Lecturer");
		msg.put("client", arr);
		ArrayList<String> arr1 = new ArrayList<>();
		arr1.add("getCoursesIdByLecturerId");
		msg.put("task",arr1);
		ArrayList<String> arr2 = new ArrayList<>();
		arr2.add(ConnectionServer.user.getId() + "");
		msg.put("param",arr2);
		super.sendMsgToServer(msg);
		
		HashMap<String,ArrayList<String>> msg1 = new HashMap<>();
		ArrayList<String> arr3 = new ArrayList<>();
		arr3.add("Lecturer");
		msg1.put("client", arr3);
		ArrayList<String> arr4 = new ArrayList<>();
		arr4.add("getCoursesNameById");
		msg1.put("task",arr4);
		
		ArrayList<String> arr5 = new ArrayList<>();
		@SuppressWarnings("unchecked")
		ArrayList<String> crsId = (ArrayList<String>) JsonHandler.convertJsonToHashMap((String)ConnectionServer.rs.get(0).get("courseId"), String.class, ArrayList.class, String.class).get("courses");
		arr5.add(crsId.size() + "");
		arr5.addAll(crsId);
		msg1.put("param",arr5);
		super.sendMsgToServer(msg1);
		
		//initiate the HM of courses
		ArrayList<HashMap<String, Object>> rs = ConnectionServer.rs;
		
		if(rs==null) {
			System.out.println("RS is null");
			return;
		}
		for (int i = 0; i < rs.size(); i++) {
		    HashMap<String, Object> element = rs.get(i);
		    HmCourseIdName.put((Integer)element.get("courseID"), (String)element.get("courseName"));
		}
		
		//sets the courseFilter
		CourseComboBox.getItems().add(0, "All Courses");
		ObservableList<String> courseItems = CourseComboBox.getItems();
		courseItems.addAll(HmCourseIdName.values());
		CourseComboBox.getSelectionModel().selectFirst();
		showTable();
	}

}