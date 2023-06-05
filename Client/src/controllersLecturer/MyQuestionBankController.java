package controllersLecturer;

import java.io.IOException;

import abstractControllers.AbstractController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MyQuestionBankController extends AbstractController{
	


	private LecturerMenuController lecturerMenuController;
	
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
    void Minimize(ActionEvent event) {
    	Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    
    @FXML
    void Close(ActionEvent event) {
    	System.exit(0);
    }

    @FXML
    void AddNewQuestion(ActionEvent event) {
    	//((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		AddNewQuestionController addNewQuestionController = new AddNewQuestionController();
		//need to implement start method in AddNewQuestionController and then -->
		addNewQuestionController.start(primaryStage);
    }
    
   /* @FXML
    void AddNewQuestion(ActionEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close(); // Close the current window

        try {
            Stage primaryStage = new Stage();
            AddNewQuestionController addNewQuestionController = new AddNewQuestionController();
            addNewQuestionController.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    @FXML
    void EditQuestion(MouseEvent event) {
    	//((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		EditQuestionController editQuestionController = new EditQuestionController();
		//need to implement start method in EditQuestionController and then -->
		editQuestionController.start(primaryStage);
    }

}


