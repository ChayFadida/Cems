package controllersLecturer;

import abstractControllers.AbstractController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class ManageExamsController extends AbstractController {
	

    @FXML
    private Button AnalyizeExamButton;

    @FXML
    private Button ChangeDurationButton;

    @FXML
    private Button LockExamButton;

    @FXML
    private Button ViewExamButton;

    @FXML
    private TableView<?> examTable;
    
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

    @FXML
    void AnalyzeExam(ActionEvent event) {
       	//((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
    		Stage primaryStage = new Stage();
    		AnalyzerExamController analyzerExamController = new AnalyzerExamController();
    		//need to implement start method in AddNewQuestionController and then -->
    		analyzerExamController.start(primaryStage);
    }

    @FXML
    void ChangeDuration(ActionEvent event) {
    	//((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		ChangeDurationController changeDurationController = new ChangeDurationController();
		//need to implement start method in AddNewQuestionController and then -->
		changeDurationController.start(primaryStage);
    }

    @FXML
    void LockExam(ActionEvent event) {

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
