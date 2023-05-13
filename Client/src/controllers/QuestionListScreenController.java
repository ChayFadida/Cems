package controllers;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

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
import javafx.stage.Stage;
import logic.Question;

public class QuestionListScreenController implements Initializable{
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
    
    //ObservableList<Question> list;
    
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
		try {
			this.loadQuestions(ConnectionServer.records);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initTableView(qArr);
	}
	
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

	public void loadQuestions(ArrayList<ArrayList<Object>> records) throws Exception {
		if(records == null) {
			System.out.println("rs is nulllllll");
		}
		for(int i=0;i<records.size();i++) {
			qArr.add(new Question((Integer)records.get(i).get(0), (String)records.get(i).get(1), (String)records.get(i).get(2), (String)records.get(i).get(3),(Integer)records.get(i).get(4)));
		}
	}

}