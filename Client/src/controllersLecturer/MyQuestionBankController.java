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
/**
 * Controller class for lecturer.
 * In this controller the lecturer can watch the question bank, add new question, delete question and delete question.
 */
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
    
    /**
     * Course filter for the lecturer to display questions from specific course.
     * @param event Action event.
     */
    @FXML
    void CourseFilter(ActionEvent event) {
        String selectedCourse = CourseComboBox.getSelectionModel().getSelectedItem();
        if (selectedCourse.equals("All Courses")) {
        	showTable();
        } else {
            showTableWithFilters(selectedCourse);
        }

	}
    
    /**
     * Return bank Id by Lecturer Id
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
     * send message to server to get relevant information for the table after filter apply.
     * @param selectedCourse The courses the lecturer chose.
     */
    void showTableWithFilters(String selectedCourse) {
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> user = new ArrayList<>();
		user.add("Lecturer");
		msg.put("client", user);
		ArrayList<String> query = new ArrayList<>();
		query.add("getQuestionsByIdByCourse");
		msg.put("task",query);
		ArrayList<String> parameter = new ArrayList<>();
		parameter.add(getBankId()+"");
		ArrayList<Integer> crsIds = new ArrayList<>();
		for(Integer id: HmCourseIdName.keySet()) {
			if(HmCourseIdName.get(id).equals(selectedCourse)) {
				crsIds.add(id);
			}
		}
		for(Integer id: crsIds) {
			parameter.add(id + "");
		}
		msg.put("param", parameter);
		super.sendMsgToServer(msg);
		try {
			this.loadQuestions(ConnectionServer.rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		initTableView(qArr);
    }
   
    /**
     * send message to the server in order to get relevant information. 
     */
    void showTable() {
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> user = new ArrayList<>();
		user.add("Lecturer");
		msg.put("client", user);
		ArrayList<String> query = new ArrayList<>();
		query.add("getQuestionsById");
		msg.put("task",query);
		ArrayList<String> parameter = new ArrayList<>();
		parameter.add(ConnectionServer.user.getId()+"");
		msg.put("param", parameter);
		super.sendMsgToServer(msg);
		try {
			this.loadQuestions(ConnectionServer.rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		initTableView(qArr);
    	
    }
    
    /**
     * Initialize the table with relevant information into the column.
     * @param arr Array list of Questions.
     */
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
		    ArrayList<String> courseIds = json.get("courses");
		    List<String> courseNames = new ArrayList<>();
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


    /**
     * Get all the relevant information for the question.
     * @param rs result set from the DataBase.
     * @throws Exception
     */
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
	
	/**
	 * minimze current window.
	 * @param event
	 */
    @FXML
    void Minimize(ActionEvent event) {
    	Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    
    /**
     * close current window.
     * @param event
     */
    @FXML
    void Close(ActionEvent event) {
    	System.exit(0);
    }
    /**
     * Loads the AddNewQuestion FXML page.
     * @param event Action event.
     * @throws IOException
     */
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
    /**
     * loads the EditQuestion FXML page.
     * @param event Action event.
     * @throws IOException
     */
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
    /**
     * Sends message to the server in order to delete question.
     * @param event
     */
    @FXML
    void DeleteQuestion(ActionEvent event) {
    	SelectionModel<Question> selectionModel = QuestionBankLecTable.getSelectionModel();
    	Question selectedItem = selectionModel.getSelectedItem();
    	HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> user = new ArrayList<>();
		user.add("Lecturer");
		msg.put("client", user);
		ArrayList<String> query = new ArrayList<>();
		query.add("deleteQuestion");
		msg.put("task",query);
		ArrayList<String> parameter = new ArrayList<>();
		parameter.add(selectedItem.getQuestionID() + "");
		msg.put("param",parameter);
		super.sendMsgToServer(msg);;
		deleteFromQB(selectedItem.getQuestionID());
		CourseFilter(event);
    }
    /**
     * Deleter the question ftom the DB.
     * @param id question id.
     */
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
	 * Get the question bank from the server.
	 * @param id bank id
	 * @return hash map of question bank
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
			System.out.println("Could not load data from the server.");
		}
		if(rs.get(0)==null) {
			System.out.println("Empty table from Sql");
		}
		return rs.get(0);
	}
	
	/**
	 * Initializes the controller.
	 * Retrieves the courses names of the lecturer and sets up the course filter.
	 *
	 * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
	 * @param resources The resources used to localize the root object, or null if the root object was not localized.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//those queries return the courses names of the lecturer
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
		ArrayList<String> user1 = new ArrayList<>();
		user1.add("Lecturer");
		msg1.put("client", user1);
		ArrayList<String> query1 = new ArrayList<>();
		query1.add("getCoursesNameById");
		msg1.put("task",query1);
		
		ArrayList<String> parameter1 = new ArrayList<>();
		@SuppressWarnings("unchecked")
		ArrayList<String> crsId = (ArrayList<String>) JsonHandler.convertJsonToHashMap((String)ConnectionServer.rs.get(0).get("courseId"), String.class, ArrayList.class, String.class).get("courses");
		parameter1.add(crsId.size() + "");
		parameter1.addAll(crsId);
		msg1.put("param",parameter1);
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