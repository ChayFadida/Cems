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


public class ManageExamsController extends AbstractController {
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
    
    @FXML
    void Minimize(ActionEvent event) {
    	Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    
    @FXML
    void Close(ActionEvent event) {
    	System.exit(0);
    }
    
    void showTable() {
    	String LecturerId = ConnectionServer.user.getId() + "";
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> arr = new ArrayList<>();
		arr.add("Lecturer");
		msg.put("client", arr);
		ArrayList<String> arr1 = new ArrayList<>();
		arr1.add("getLecturerExams");
		msg.put("task",arr1);
		ArrayList<String> arr2 = new ArrayList<>();
		arr2.add(LecturerId);
		msg.put("param", arr2);
		super.sendMsgToServer(msg);
		try {
			this.loadExams(ConnectionServer.rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		initTableView(eArr);
    	
    }
    
    
    private void initTableView(ArrayList<Exam> arr) {
        ObservableList<Exam> list = FXCollections.observableArrayList(arr);
        
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
        
        // Set the cell value factory for the Is Locked column
        clmIsLocked.setCellValueFactory(cellData -> {
            Boolean isLocked = cellData.getValue().isLocked();
            String lockedStatus = isLocked ? "Yes" : "No";
            return new SimpleStringProperty(lockedStatus);
        });

        examTable.setItems(list);		
	}


	public void loadExams(ArrayList<HashMap<String, Object>> rs) throws Exception {
		eArr= new ArrayList<>();
		for (int i = 0; i < rs.size(); i++) {
		    HashMap<String, Object> element = rs.get(i);
		    eArr.add(new Exam((Integer)element.get("examId"),(String)element.get("examName"),(Integer)element.get("courseId"), (String)element.get("subject"),(Integer)element.get("duration"), (String)element.get("lecturerNote"), (String)element.get("studentNote"), (Integer)element.get("composerId"),(String)element.get("code"),(String)element.get("examNum"), (Integer)element.get("bankId"),(Boolean)element.get("isLocked")));
		    HmCourseIdName.put((Integer)element.get("courseId"), (String)element.get("courseName"));
		}
	}
    
    
    @FXML
    void AnalyzeExam(ActionEvent event) {
       	//((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
    		Stage primaryStage = new Stage();
    		AnalyzerExamController analyzerExamController = new AnalyzerExamController();
    		//need to implement start method in AddNewQuestionController and then -->
    		analyzerExamController.start(primaryStage);
    }

    @FXML
    void ChangeDuration(ActionEvent event) throws IOException {
		Stage primaryStage = new Stage();
		ChangeDurationController ChangeDurationController;
		SelectionModel<Exam> selectionModel = examTable.getSelectionModel();
    	Exam selectedExam = selectionModel.getSelectedItem();
    	if(!(selectedExam == null)) {
    		FXMLLoader loader = new FXMLLoader();
    		Pane root = loader.load(getClass().getResource("/guiLecturer/ChangeDuration.fxml").openStream());
    		ChangeDurationController = loader.getController();
    		ChangeDurationController.setExam(selectedExam);
    		ChangeDurationController.LoadExamOldDuration(selectedExam);
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
    void LockExam(ActionEvent event) throws IOException {
		Stage primaryStage = new Stage();
		LockAreYouSureController lockAreYouSureController;
		SelectionModel<Exam> selectionModel = examTable.getSelectionModel();
    	Exam selectedExam = selectionModel.getSelectedItem();
    	if(!(selectedExam == null)) {
    		FXMLLoader loader = new FXMLLoader();
    		Pane root = loader.load(getClass().getResource("/guiLecturer/LockAreYouSure.fxml").openStream());
    		lockAreYouSureController = loader.getController();
    		lockAreYouSureController.setExam(selectedExam);
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
    void ViewExam(ActionEvent event) {
    	//((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		ViewExamController viewExamController = new ViewExamController();
		//need to implement start method in AddNewQuestionController and then -->
		viewExamController.start(primaryStage);

    }

}
