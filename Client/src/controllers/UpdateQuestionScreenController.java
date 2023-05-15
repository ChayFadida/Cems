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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.Question;

public class UpdateQuestionScreenController extends AbstractController {
	private Question q;
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
    	String qNumber = getQuestionNo(),qText=getQuestionText();
    	HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> arr = new ArrayList<>();
		arr.add("Lecturer");
		msg.put("client", arr);
		ArrayList<String> arr1 = new ArrayList<>();
		arr1.add("updateQuestionById");
		msg.put("task",arr1);
		ArrayList<String> arr2 = new ArrayList<>();
		arr2.add(q.getId()+"");
		arr2.add(qNumber);
		arr2.add(qText);
		msg.put("param",arr2);
		sendMsgToServer(msg);
		try {
			int affectedRows = (Integer)(ConnectionServer.rs.get(0).get("affectedRows"));
			if(affectedRows==0) {
				System.out.println("No rows were updated");
				return;
			}
			System.out.println("Updated successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    public void getBackBtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		QuestionListScreenController questionListScreenController = new QuestionListScreenController();	
		questionListScreenController.start(primaryStage);
	}
    
//	public void start(Stage primaryStage) throws Exception {	
//		Parent root = FXMLLoader.load(getClass().getResource("/gui/UpdateQuestionScreen.fxml"));
//		Scene scene = new Scene(root);
//		//scene.getStylesheets().add(getClass().getResource("/gui/AcademicFrame.css").toExternalForm());
//		primaryStage.setTitle("Question Update");
//		primaryStage.setScene(scene);
//		primaryStage.show();	 	   
//	}
	public void loadQuestion(Question q1) {
		this.q=q1;
		this.txtQuestionNo.setText(q.getNumber()+"");
		this.txtQuestionText.setText(q.getQuestion());
	}
	

}
