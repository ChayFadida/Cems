package controllers;

import java.util.ArrayList;
import java.util.HashMap;

import client.ConnectionServer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import logic.Question;

public class LecturerMenuScreenController {
    @FXML
    private Button btnDisplayQuestion;

    @FXML
    private Button btnLogout;

    @FXML
    private Label lblChooseOption;

    @FXML
    private Label lblHelloLecturer;

    
	public void getDisplayQuestionBtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		QuestionListScreenController questionListScreenController = new QuestionListScreenController();	
		questionListScreenController.start(primaryStage);
	}
    
	public void getLogoutBtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		ConnectClientScreenController connectClientScreenController = new ConnectClientScreenController();	
		connectClientScreenController.start(primaryStage);
	}
    
	public void start(Stage primaryStage) throws Exception {	
		Parent root = FXMLLoader.load(getClass().getResource("/gui/LecturerMenuScreen.fxml"));		
		Scene scene = new Scene(root);
		primaryStage.setTitle("Question List");
		primaryStage.setScene(scene);
		primaryStage.show();	 	   
	}
}
