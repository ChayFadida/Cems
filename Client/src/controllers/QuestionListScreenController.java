package controllers;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import abstractControllers.AbstractController;
import client.ConnectionServer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.Question;

public class QuestionListScreenController extends AbstractController implements Initializable{
	private ArrayList<Question> qArr = new ArrayList<Question>();
	public Question q;
	@FXML
    private TableView<Question> tblQuestions;
	@FXML
    private TableColumn<Question, String> clmCourse;

    @FXML
    private TableColumn<Question, Integer> clmId;

    @FXML
    private TableColumn<Question, String> clmLecturer;

    @FXML
    private TableColumn<Question, Integer> clmNumber;

    @FXML
    private TableColumn<Question, String> clmQuestion;
    @FXML
    private Button btnBack;
    
    @FXML
    private Button btnContinue;

    @FXML
    private Label lblQuestionList;
    
    /**
	 *this method launch the previous screen
	 *@param event
	 * */
	public void getBackBtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		LecturerMenuScreenController lecturerMenuScreenController = new LecturerMenuScreenController();	
		lecturerMenuScreenController.start(primaryStage);
	}
	
	/**
	 *this method implements the continue button and continue to the next stage with the selected question
	 *@param event
	 * */
	public void getContinueBtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/gui/UpdateQuestionScreen.fxml").openStream());
		UpdateQuestionScreenController updateQuestionScreenController = loader.getController();
		q= tblQuestions.getSelectionModel().getSelectedItem();
		updateQuestionScreenController.loadQuestion(q);
		Scene scene = new Scene(root);	
		primaryStage.setTitle("Update Question");
		primaryStage.setScene(scene);		
		primaryStage.show();
	}
    
	/**
	 *this method launch the screen
	 *@param Stage primaryStage
	 * */
	public void start(Stage primaryStage) throws Exception {	
		Parent root = FXMLLoader.load(getClass().getResource("/gui/QuestionListScreen.fxml"));
				
		Scene scene = new Scene(root);
		primaryStage.setTitle("Question List");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	/**
	 *this method initialize the table view
	 *@param URL location, ResourceBundle resources
	 * */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> arr = new ArrayList<>();
		arr.add("Lecturer");
		msg.put("client", arr);
		ArrayList<String> arr1 = new ArrayList<>();
		arr1.add("getAllQuestions");
		msg.put("task",arr1);
		sendMsgToServer(msg);
		try {
			this.loadQuestions(ConnectionServer.rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		initTableView(qArr);
	}
	
	/**
	 *this method initialize the table view
	 *@param ArrayList of Question
	 * */
	private void initTableView(ArrayList<Question> arr) {
		ObservableList<Question> list = FXCollections.observableArrayList(arr);
		PropertyValueFactory<Question, Integer> pvfId = new PropertyValueFactory<>("id");
		PropertyValueFactory<Question, String> pvfCourse = new PropertyValueFactory<>("course");
		PropertyValueFactory<Question, String> pvfLecturer = new PropertyValueFactory<>("lecturer");
		PropertyValueFactory<Question, String> pvfQuestion = new PropertyValueFactory<>("question");
		PropertyValueFactory<Question, Integer> pvfNumber = new PropertyValueFactory<>("number");
		clmCourse.setCellValueFactory(pvfCourse);
		clmId.setCellValueFactory(pvfId);
		clmLecturer.setCellValueFactory(pvfLecturer);
		clmQuestion.setCellValueFactory(pvfQuestion);
		clmNumber.setCellValueFactory(pvfNumber);
		tblQuestions.setItems(list);
	}
	
	/**
	 *this method load questions
	 *@param ArrayList<HashMap<String, Object>> rs
	 * */
	public void loadQuestions(ArrayList<HashMap<String, Object>> rs) throws Exception {
		if(rs == null) {
			System.out.println("rs is null");
		}
		for (int i = 0; i < rs.size(); i++) {
		    HashMap<String, Object> element = rs.get(i);
		    qArr.add(new Question((int)element.get("id"), (String)element.get("course"), (String)element.get("lecturer"), (String)element.get("question"), (Integer)element.get("question_number")));
		}
	}

}