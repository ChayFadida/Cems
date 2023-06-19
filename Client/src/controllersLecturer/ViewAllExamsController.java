package controllersLecturer;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import abstractControllers.AbstractController;
import client.ConnectionServer;
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
/**
 * Controller class for lecturer.
 * In this controller the lecturer can view the queation bank.
 * extends AbstractController implements Initializable
 */
public class ViewAllExamsController extends AbstractController implements Initializable{
	
	private ArrayList<Exam> eArr ;
	private HashMap<Integer, String> HmCourseIdName = new HashMap<>();

    @FXML
    private Button CloseBtn;

    @FXML
    private Button MinimizeBtn;
    
	@FXML
	private ComboBox<String> CourseComboBox;
	
    @FXML
    private Button ViewExamButton;

    @FXML
    private TableColumn<Exam, String> clmCourse;

    @FXML
    private TableColumn<Exam, Integer> clmDuration;

    @FXML
    private TableColumn<Exam, String> clmExamName;

    @FXML
    private TableColumn<Exam, String> clmNotes;

    @FXML
    private TableColumn<Exam, String> clmSubject;

    @FXML
    private TableView<Exam> examTable;
    
    /**
     * Loads the current FXML page.
     * @param primaryStage 
     */
    public void start(Stage primaryStage) {
    	FXMLLoader loader = new FXMLLoader();
		Pane root;
		try {
			 root = loader.load(getClass().getResource("/guiLecturer/ViewAllExams.fxml").openStream());
			 Scene scene = new Scene(root);
		     scene.getStylesheets().add("/gui/GenericStyleSheet.css");
		     primaryStage.initStyle(StageStyle.UNDECORATED);
			 primaryStage.getIcons().add(new Image("/Images/CemsIcon32-Color.png"));
		     primaryStage.setScene(scene);
		     primaryStage.show();
		     super.setPrimaryStage(primaryStage);
		     PressHandler<MouseEvent> press = new PressHandler<>();
		     DragHandler<MouseEvent> drag = new DragHandler<>();
		     root.setOnMousePressed(press);
		     root.setOnMouseDragged(drag);
		} catch (IOException e) {
			e.printStackTrace();
		}	
    }
    
    /**
     * by activate, loads the ViewExam FXML page.
     * @param event Action event.
     */
    @FXML
    void ViewExam(ActionEvent event) {
    	Stage primaryStage = new Stage();
		ViewExamController viewExamController;
		SelectionModel<Exam> selectionModel = examTable.getSelectionModel();
		Exam selectedExam = selectionModel.getSelectedItem();
    	if(!(selectedExam == null)) {	
    		try {
    			FXMLLoader loader = new FXMLLoader();
        		Pane root = loader.load(getClass().getResource("/guiLecturer/ViewExam.fxml").openStream());
        		viewExamController = loader.getController();
        		viewExamController.setExam(selectedExam);
        		viewExamController.loadExam();	
    	        Scene scene = new Scene(root);
    	        scene.getStylesheets().add("/gui/GenericStyleSheet.css");
    	        primaryStage.initStyle(StageStyle.UNDECORATED);
    			primaryStage.getIcons().add(new Image("/Images/CemsIcon32-Color.png"));
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
     * Initializes the controller.
     * Sets up the course filter with available course names.
     *
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
	 * send message to the server to get relevant information for the table.
	 */
	private void showTable() {
		String LecturerId = ConnectionServer.user.getId() + "";
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> user = new ArrayList<>();
		user.add("Lecturer");
		msg.put("client", user);
		ArrayList<String> query = new ArrayList<>();
		query.add("getAllExams");
		msg.put("task", query);
		ArrayList<String> parameter = new ArrayList<>();
		parameter.add(LecturerId);
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
	 * Initialize the table column with the relevant information.
	 * @param arr Exam Arr
	 */
	private void initTableView(ArrayList<Exam> arr) {
		ObservableList<Exam> list = FXCollections.observableArrayList(arr);
	     
        PropertyValueFactory<Exam, String> pvfExamName = new PropertyValueFactory<>("examName");
        PropertyValueFactory<Exam, String> pvfSubject = new PropertyValueFactory<>("subject");
        PropertyValueFactory<Exam, Integer> pvfDuration = new PropertyValueFactory<>("duration");
        PropertyValueFactory<Exam, String> pvfNotes = new PropertyValueFactory<>("lecturerNote");

        clmExamName.setCellValueFactory(pvfExamName);
        clmSubject.setCellValueFactory(pvfSubject);
        clmDuration.setCellValueFactory(pvfDuration);
        clmNotes.setCellValueFactory(pvfNotes);

        // Set the cell value factory for the course column
        clmCourse.setCellValueFactory(cellData -> {
            Exam exam = cellData.getValue();
            Integer courseId = exam.getCourseId();

            // Retrieve the course name based on the course ID
            String courseName = HmCourseIdName.get(courseId);

            return new SimpleStringProperty(courseName);
        });
        examTable.setItems(list);
	}

	/**
	 * loads exam information from the DB.
	 * @param rs
	 */
	private void loadExams(ArrayList<HashMap<String, Object>> rs) {
		eArr= new ArrayList<>();
		for (int i = 0; i < rs.size(); i++) {
		    HashMap<String, Object> element = rs.get(i);
		    eArr.add(new Exam((Integer) element.get("examId"), (String) element.get("examName"), (Integer) element.get("courseId"),
		    		(String) element.get("subject"), (Integer) element.get("duration"), (String) element.get("lecturerNote"), (String) element.get("studentNote"),
		    		(Integer) element.get("composerId"),(String) element.get("code"),(String) element.get("examNum"), (Integer) element.get("bankId"), 
		    		(Integer) element.get("isLocked")));
		    HmCourseIdName.put((Integer) element.get("courseId"), (String) element.get("courseName"));
		}
	}

	/**
	 * sets hash map of course id and name.
	 * @param hmCourseIdName
	 */
	public void setHmCourseIdName(HashMap<Integer, String> hmCourseIdName) {
		HmCourseIdName = hmCourseIdName;
	}
	
	/**
	 * 
	 * @param event
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
	 * sends message to the server to get relevant information.
	 * @param selectedCourse 
	 */
	void showTableWithFilters(String selectedCourse) {
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> user = new ArrayList<>();
		user.add("Lecturer");
		msg.put("client", user);
		ArrayList<String> query = new ArrayList<>();
		query.add("getLecturerExamsByCourse");
		msg.put("task", query);
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
	 * close current window 
	 * @param event Action event.
	 */
    @FXML
    void Close(ActionEvent event) {
        Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
    
    /**
     * minimze current window
     * @param event Action event.
     */
    @FXML
    void Minimize(ActionEvent event) {
    	Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
}
