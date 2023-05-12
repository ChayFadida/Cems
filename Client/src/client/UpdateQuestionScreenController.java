package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpdateQuestionScreenController {

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnBack;

    @FXML
    private Label lblInsertNewInfo;

    @FXML
    private Label lblQuestionNo;

    @FXML
    private Label lblQuestionText;

    @FXML
    private TextField txtQuestionNo;

    @FXML
    private TextField txtQuestionText;
    
    private String getQuestionNo() {
		return txtQuestionNo.getText();
	}
    private String getQuestionText() {
		return txtQuestionText.getText();
	}
    
    //to be implemented with a server up . 
    public void getUpdateBtn(ActionEvent event) throws Exception {
//		String str = new String();
//		str = "save " + this.getQuestionNo()  + " " + this.getQuestionText();
//		ClientUI.chat.accept(str);
//allow the above once we have server connection and Cliet UI.
	}
//    public void initialize(Object question) {
//    	
//    }
   
    public void getBackBtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		QuestionListScreenController questionListScreenController = new QuestionListScreenController();	
		questionListScreenController.start(primaryStage);
	}
    
	public void start(Stage primaryStage) throws Exception {	
		Parent root = FXMLLoader.load(getClass().getResource("/gui/UpdateQuestionScreen.fxml"));
		Scene scene = new Scene(root);
		//scene.getStylesheets().add(getClass().getResource("/gui/AcademicFrame.css").toExternalForm());
		primaryStage.setTitle("Question Update");
		primaryStage.setScene(scene);
		primaryStage.show();	 	   
	}

}
