package client;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
	private ArrayList<Question> qArr;
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
		initListView();
	}
	
	private void initListView() {
		for(Question q: qArr) {
			ViewListQuestions.getItems().add(q.getQuestion());
		}
	}
	
	public void loadQuestions(ArrayList<Question> qArr) {
		this.qArr = qArr;
	}

}