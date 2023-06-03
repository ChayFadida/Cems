package controllersLecturer;

import java.io.IOException;

import abstractControllers.AbstractController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MyQuestionBankController extends AbstractController{

	private LecturerMenuController lecturerMenuController;
	
    @FXML
    private Button AddNewQuestionButton;

    @FXML
    private Button DeleteQuestionButton;

    @FXML
    private Button EditQuestionButton;

    @FXML
    void AddNewQuestion(ActionEvent event) {
    	//((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		AddNewQuestionController addNewQuestionController = new AddNewQuestionController();
		//need to implement start method in AddNewQuestionController and then -->
		addNewQuestionController.start(primaryStage);
    }

    @FXML
    void EditQuestion(MouseEvent event) {
    	//((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		EditQuestionController editQuestionController = new EditQuestionController();
		//need to implement start method in EditQuestionController and then -->
		//editQuestionController.start(primaryStage);
    }
    
    





}

