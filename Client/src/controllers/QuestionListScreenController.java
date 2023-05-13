package controllers;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import client.ConnectionServer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import logic.Question;

public class QuestionListScreenController implements Initializable{
	private ArrayList<Question> qArr = new ArrayList<>();
    @FXML
    private ListView<String> ViewListQuestions;

    @FXML
    private Button btnBack;
    
    @FXML
    private Button btnContinue;

    @FXML
    private Label lblQuestionList;
    
    
	public void getBackBtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		LecturerMenuScreenController lecturerMenuScreenController = new LecturerMenuScreenController();	
		lecturerMenuScreenController.start(primaryStage);
	}
	public void getContinueBtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		UpdateQuestionScreenController updateQuestionScreenController = new UpdateQuestionScreenController();	
		updateQuestionScreenController.start(primaryStage);
	}
    
	public void start(Stage primaryStage) throws Exception {	
		Parent root = FXMLLoader.load(getClass().getResource("/gui/QuestionListScreen.fxml"));
				
		Scene scene = new Scene(root);
		//scene.getStylesheets().add(getClass().getResource("/gui/AcademicFrame.css").toExternalForm());
		primaryStage.setTitle("Question List");
		primaryStage.setScene(scene);
		primaryStage.show();	 	   
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		HashMap<String,ArrayList<String>> msg = new HashMap();
		ArrayList<String> arr = new ArrayList<>();
		arr.add("Lecturer");
		msg.put("client", arr);
		ArrayList<String> arr1 = new ArrayList<>();
		arr1.add("getAllQuestions");
		msg.put("task",arr1);
		AbstractController controller = new AbstractController();
		controller.sendMsgToServer(msg);
//		try {
//			this.loadQuestions(ConnectionServer.rs);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		//initListView();
		//AbstractController controller = new AbstractController();
		//controller.sendMsgToServer("hellooo worllddd");
	}
	
	private void initListView() {
		if (qArr.isEmpty()) {
			ViewListQuestions.getItems().add("no questions to display");
		}
		else {
			for(Question q: qArr) {
				ViewListQuestions.getItems().add(q.getQuestion());
			}
		}
	}
	
	public void loadQuestions(ResultSet rs) throws Exception {
		if(!(rs.next())) {
			System.out.println("no rs in load");
		
		}
		else {
			do {
				qArr.add(new Question(rs.getInt("id"), rs.getString("course"), rs.getString("lecturer"), rs.getString("question")));
			} while (rs.next());
		}
	}

}