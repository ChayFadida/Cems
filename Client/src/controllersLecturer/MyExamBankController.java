package controllersLecturer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class MyExamBankController {

    @FXML
    private ComboBox<?> CourseComboBox;

    @FXML
    private Button DeleteExamButton;

    @FXML
    private Button EditExamButton;

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
    void DeleteExam(ActionEvent event) {

    }

    @FXML
    void EditExam(ActionEvent event) {
       	//((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
    	Stage primaryStage = new Stage();
    	EditExamController editExamController = new EditExamController();
    	//need to implement start method in AddNewQuestionController and then -->
    	editExamController.start(primaryStage);
    }

}

    
   /* @FXML
    void DeleteExam(ActionEvent event) {
    	//needs implementation
    }*/

    /*@FXML
    void EditExam(ActionEvent event) {
    	//((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		EditExamController editExamController = new EditExamController();
		//need to implement start method in AddNewQuestionController and then -->
		editExamController.start(primaryStage);
    }*/
