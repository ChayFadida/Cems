package controllersLecturer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import abstractControllers.AbstractController;
import client.ConnectionServer;
import entities.Exam;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.beans.property.SimpleStringProperty;

/**
 * Controller class for the lecturer.
 * Throw this controller the lecturer can preform actions on his exams.
 */
public class ManageExamsController extends AbstractController  {
	
	private ArrayList<Exam> eArr ;
	private HashMap<Integer, String> HmCourseIdName = new HashMap<>();

    @FXML
    private Button AnalyizeExamButton;

    @FXML
    private Button ChangeDurationButton;

    @FXML
    private Button LockExamButton;

    @FXML
    private Button ViewExamButton;
    
    @FXML
    private TableColumn<Exam, String> clmCourse;

    @FXML
    private TableColumn<Exam, String> clmExamName;

    @FXML
    private TableColumn<Exam, String> clmSubject;
    
    @FXML
    private TableColumn<Exam, Integer> clmDuration;

    @FXML
    private TableColumn<Exam, String> clmIsLocked;

    @FXML
    private TableView<Exam> examTable;
    
    @FXML
    private Button closeButton;

    @FXML
    private Button minimizeButton;
    
    /**
     * Minimize current window.
     * @param event Action event
     */
    @FXML
    void Minimize(ActionEvent event) {
    	Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    
    /**
     * By activate, the current window is closed.
     * @param event Action event
     */
    @FXML
    void Close(ActionEvent event) {
    	System.exit(0);
    }
    
    /**
     * sends message to the server.
     */
    void showTable() {
    	String LecturerId = ConnectionServer.user.getId() + "";
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> user = new ArrayList<>();
		user.add("Lecturer");
		msg.put("client", user);
		ArrayList<String> query = new ArrayList<>();
		query.add("getLecturerExams");
		msg.put("task", query);
		ArrayList<String> paramter = new ArrayList<>();
		paramter.add(LecturerId);
		msg.put("param", paramter);
		super.sendMsgToServer(msg);
		try {
			this.loadExams(ConnectionServer.rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		initTableView(eArr);
    }
    
    /**
     * Initialize the table with relevant exams.
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
        clmCourse.setCellValueFactory(cellData -> {
            Exam exam = cellData.getValue();
            Integer courseId = exam.getCourseId();
            String courseName = HmCourseIdName.get(courseId);
            return new SimpleStringProperty(courseName);
        });
        clmIsLocked.setCellValueFactory(cellData -> {
            Integer isLocked = cellData.getValue().isLocked();
            String lockedStatus = isLocked != 0 ? "Yes" : "No";
            return new SimpleStringProperty(lockedStatus);
        });
        examTable.setItems(list);
	}

    /**
     * Loading the exams from the server.
     * @param rs result set from the server.
     * @throws Exception if cant load.
     */
	public void loadExams(ArrayList<HashMap<String, Object>> rs) throws Exception {
		eArr= new ArrayList<>();
		for (int i = 0; i < rs.size(); i++) {
		    HashMap<String, Object> element = rs.get(i);
		    eArr.add(new Exam((Integer) element.get("examId"), (String) element.get("examName"), (Integer) element.get("courseId"),
		    		(String) element.get("subject"), (Integer)element.get("duration"), (String) element.get("lecturerNote"),
		    		(String) element.get("studentNote"), (Integer) element.get("composerId"),(String) element.get("code"),
		    		(String) element.get("examNum"), (Integer) element.get("bankId"),(Integer) element.get("isLocked")));
		    HmCourseIdName.put((Integer) element.get("courseId"), (String) element.get("courseName"));
		}
	}
    
    /**
     * Activate the AnalyzeExam FXML page.
     * @param event Action event
     * @throws IOException
     */
    @FXML
    void AnalyzeExam(ActionEvent event) throws IOException {
    	Stage primaryStage = new Stage();
    	AnalyzerExamController analyzerExamController;
		SelectionModel<Exam> selectionModel = examTable.getSelectionModel();
    	Exam selectedItem = selectionModel.getSelectedItem();
    	if(!(selectedItem == null)) {
    		FXMLLoader loader = new FXMLLoader();
    		Pane root = loader.load(getClass().getResource("/guiLecturer/AnalyzeExam.fxml").openStream());
    		analyzerExamController = loader.getController();
    		analyzerExamController.setExam(selectedItem);
    		analyzerExamController.LoadExamStats(selectedItem);
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
     * Activate ChangeDuration FXML page.
     * @param event Action event.
     * @throws IOException 
     */
    @FXML
    void ChangeDuration(ActionEvent event) throws IOException {
		Stage primaryStage = new Stage();
		ChangeDurationController changeDurationController;
		SelectionModel<Exam> selectionModel = examTable.getSelectionModel();
		Exam selectedExam = selectionModel.getSelectedItem();
    	if(!(selectedExam == null)) {
    		FXMLLoader loader = new FXMLLoader();
    		Pane root = loader.load(getClass().getResource("/guiLecturer/ChangeDuration.fxml").openStream());
    		changeDurationController = loader.getController();
    		changeDurationController.setExam(selectedExam);
    		changeDurationController.LoadExamOldDuration(selectedExam);
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
     * Activate the LockExam FXML page.
     * @param event action event.
     * @throws IOException
     */
    @FXML
    void LockExam(ActionEvent event) throws IOException {
		Stage primaryStage = new Stage();
		LockAreYouSureController lockAreYouSureController;
		SelectionModel<Exam> selectionModel = examTable.getSelectionModel();
    	Exam selectedExam = selectionModel.getSelectedItem();
    	if(!(selectedExam == null)) {
        	if(selectedExam.isLocked() == 1) {
        		return;
        	}
    		FXMLLoader loader = new FXMLLoader();
    		Pane root = loader.load(getClass().getResource("/guiLecturer/LockAreYouSure.fxml").openStream());
    		lockAreYouSureController = loader.getController();
    		lockAreYouSureController.setExam(selectedExam);
    		lockAreYouSureController.setManageExamsController(this);
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
     * Activate ViewAllExams FXML page.
     * @param event Action event.
     */
    @FXML
    void ViewAllExams(ActionEvent event) {
		Stage primaryStage = new Stage();
		ViewAllExamsController viewAllExamController = new ViewAllExamsController();
		viewAllExamController.start(primaryStage);
    }
}
