package controllersLecturer;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import abstractControllers.AbstractController;
import entities.Exam;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import thirdPart.JsonHandler;
import client.ConnectionServer;
/**
 * Controller class for the lecturer.
 * In this class the lecturer can watch her exam bank.
 */
public class MyExamBankController extends AbstractController implements Initializable{
	private ArrayList<Exam> eArr ;
	private HashMap<Integer, String> HmCourseIdName = new HashMap<>();
	
	/**
	 * Get the course id and name.
	 * @return Hash map of the course id and its name.
	 */
    public HashMap<Integer, String> getHmCourseIdName() {
    	return HmCourseIdName;
		
	}
    /**
     * get course filter.
     * @return course comboBox
     */
    public ComboBox<String> getcombo() {
    	return CourseComboBox;
		
	}

	@FXML
    private ComboBox<String> CourseComboBox;

    @FXML
    private Button DeleteExamButton;

    @FXML
    private Button EditExamButton;

    @FXML
    private TableView<Exam> tblExams;
    
    @FXML
    private TableColumn<Exam, String> clmCourse;

    @FXML
    private TableColumn<Exam, Integer> clmDuration;

    @FXML
    private TableColumn<Exam, String> clmExamName;

    @FXML
    private TableColumn<Exam, String> clmSubject;
    
    @FXML
    private Button closeButton;

    @FXML
    private Button minimizeButton;
    
    /**
     * Minimze current window.
     * @param event Action event.
     */     
    @FXML
    void Minimize(ActionEvent event) {
    	Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    /**
     * close current window.
     * @param event Action event.
     */
    @FXML
    void Close(ActionEvent event) {
    	System.exit(0);
    }
    /**
     * acvitate course filter for the lecturer
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
     * Delete the exam from the lecturer exam bank.
     * @param event Action event.
     */
    @FXML
    void DeleteExam(ActionEvent event) {
    	SelectionModel<Exam> selectionModel = tblExams.getSelectionModel();
    	Exam selectedItem = selectionModel.getSelectedItem();
    	HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> user = new ArrayList<>();
		user.add("Lecturer");
		msg.put("client", user);
		ArrayList<String> query = new ArrayList<>();
		query.add("deleteExam");
		msg.put("task",query);
		ArrayList<String> parameter = new ArrayList<>();
		parameter.add(selectedItem.getExamId() + "");
		msg.put("param",parameter);
		super.sendMsgToServer(msg);;
		deleteFromEB(selectedItem.getExamId());
		showTable();
    }
    /**
     * update the table after lecturer choose filter.
     * @param selectedCourse the courses the lecturer choose.
     */
    void showTableWithFilters(String selectedCourse) {
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> user = new ArrayList<>();
		user.add("Lecturer");
		msg.put("client", user);
		ArrayList<String> query = new ArrayList<>();
		query.add("getLecturerExamsByCourse");
		msg.put("task",query);
		ArrayList<String> parameter = new ArrayList<>();
		parameter.add(ConnectionServer.user.getId()+"");
		parameter.add(selectedCourse);
		msg.put("param", parameter);
		super.sendMsgToServer(msg);
		try {
			this.loadExams(ConnectionServer.rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		initTableView(eArr);
    }
    
    /**
     * Delete the exam from the DB
     * @param id exam id.
     */
	private void deleteFromEB(Integer id) {
    	HashMap<String,ArrayList<String>> msg = new HashMap<>();
    	HashMap<String,Object> bank = getExamBank(ConnectionServer.user.getId());
    	String exams = (String) bank.get("exams");
    	HashMap<String,ArrayList<Integer>> jsonHM= JsonHandler.convertJsonToHashMap(exams, String.class, ArrayList.class,Integer.class);
		ArrayList<Integer> examsInBank = jsonHM.get("exams");
		if(examsInBank.contains(id)) {
			examsInBank.remove(id);
			jsonHM.put("exams",examsInBank);
		}
		else {
			System.out.println("Problem at removing exam from bank");
			return;
		}
		
		String jsonString = JsonHandler.convertHashMapToJson(jsonHM, String.class, ArrayList.class);
		ArrayList<String> user = new ArrayList<>();
		user.add("Lecturer");
		msg.put("client", user);
		ArrayList<String> query = new ArrayList<>();
		query.add("updateExamBankById");
		msg.put("task",query);
		ArrayList<String> parameter = new ArrayList<>();
		parameter.add(bank.get("bankId")+ "");
		parameter.add(jsonString);
		msg.put("param",parameter);
		super.sendMsgToServer(msg);
	}
	
	/**
	 * Get the exam bank by insert user id.
	 * @param id user id 
	 * @return hashMap of the exam bank.
	 */
	private HashMap<String, Object> getExamBank(int id) {
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> user = new ArrayList<>();
		user.add("Lecturer");
		msg.put("client", user);
		ArrayList<String> query = new ArrayList<>();
		query.add("getExamBankByLecId");
		msg.put("task",query);
		ArrayList<String> parameter = new ArrayList<>();
		parameter.add(ConnectionServer.user.getId()+"");
		msg.put("param",parameter);
		super.sendMsgToServer(msg);
		ArrayList<HashMap<String,Object>> rs = ConnectionServer.rs;
		if(rs == null) {
			System.out.println("Could not get info from the DB.");
		}
		if(rs.get(0)==null) {
			System.out.println("Empty table from Sql");
		}
		return rs.get(0);
	}
	/**
	 * send message to server and get relevant information for the table.
	 */
    void showTable() {
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> user = new ArrayList<>();
		user.add("Lecturer");
		msg.put("client", user);
		ArrayList<String> query = new ArrayList<>();
		query.add("getLecturerExams");
		msg.put("task",query);
		ArrayList<String> parameter = new ArrayList<>();
		parameter.add(ConnectionServer.user.getId()+"");
		msg.put("param", parameter);
		super.sendMsgToServer(msg);
		try {
			this.loadExams(ConnectionServer.rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		initTableView(eArr);
    	
    }
    /**
     * loads the relevant information for the exam.
     * @param rs result set from the server.
     * @throws Exception
     */
	public void loadExams(ArrayList<HashMap<String, Object>> rs) throws Exception {
		eArr= new ArrayList<>();
		if(rs == null) {
			System.out.println("rs is null");
			return;
		}
		for (int i = 0; i < rs.size(); i++) {
		    HashMap<String, Object> element = rs.get(i);
		    eArr.add(new Exam((Integer)element.get("examId"), (String)element.get("examName"), (Integer)element.get("courseId"),
                              (String)element.get("subject"), (Integer)element.get("duration"), (String)element.get("lecturerNote"),
                              (String)element.get("studentNote"), (Integer)element.get("composerId"), (String)element.get("code"),
                              (String)element.get("examNum"), (Integer)element.get("bankId"), (Integer)element.get("isLocked")));
		    HmCourseIdName.put((Integer)element.get("courseId"), (String)element.get("courseName"));
		}
		
	}
	/**
	 * Initialize the table columns with relrvant information.
	 * @param ExamArr ArrayList of exams.
	 */
    private void initTableView(ArrayList<Exam> ExamArr) {
    	ObservableList<Exam> list = FXCollections.observableArrayList(ExamArr);
		PropertyValueFactory<Exam, String> pvfExamName = new PropertyValueFactory<>("examName");
		PropertyValueFactory<Exam, String> pvfSubject = new PropertyValueFactory<>("subject");
		PropertyValueFactory<Exam, Integer> pvfDuration = new PropertyValueFactory<>("duration");
		clmExamName.setCellValueFactory(pvfExamName);
		clmSubject.setCellValueFactory(pvfSubject);
		clmDuration.setCellValueFactory(pvfDuration);
		
        // Set the cell value factory for the course column
        clmCourse.setCellValueFactory(cellData -> {
            Exam exam = cellData.getValue();
            Integer courseId = exam.getCourseId();
            // Retrieve the course name based on the course ID
            String courseName = HmCourseIdName.get(courseId);

            return new SimpleStringProperty(courseName);
        });
		
		tblExams.setItems(list);
		
	}
    /**
     * Initialize the controller.
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized. 
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		showTable();
		CourseComboBox.getItems().add(0, "All Courses");
		ObservableList<String> courseItems = CourseComboBox.getItems();
		courseItems.addAll(HmCourseIdName.values());
		CourseComboBox.getSelectionModel().selectFirst();
	}
	/**
	 * when activate, runs the EditExam FXML page
	 * @param event Action event.
	 * @throws IOException
	 */
    @FXML
    void EditExam(ActionEvent event) throws IOException {
		Stage primaryStage = new Stage();
		EditExamController editExamController;
		SelectionModel<Exam> selectionModel = tblExams.getSelectionModel();
    	Exam selectedItem = selectionModel.getSelectedItem();
    	if(!(selectedItem == null)) {
    		FXMLLoader loader = new FXMLLoader();
    		Pane root = loader.load(getClass().getResource("/guiLecturer/EditExam.fxml").openStream());
    		editExamController = loader.getController();
    		editExamController.SetMyExamBankController(this);
    		editExamController.LoadExam(selectedItem);
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
}