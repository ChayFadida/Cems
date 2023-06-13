package controllersLecturer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import abstractControllers.AbstractController;
import abstractControllers.AbstractController.DragHandler;
import abstractControllers.AbstractController.PressHandler;
import client.ConnectionServer;
import controllers.UpdateQuestionScreenController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import thirdPart.JsonHandler;
import entities.Question;
public class MyQuestionBankController extends AbstractController{
	
	private ArrayList<Question> qArr ;
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
    private TextField TempIDbox;
    
    @FXML
    private TableView<Question> QuestionBankLecTable;
    
    @FXML
    private Button BtnTemp;
    
    @FXML
    private TableColumn<Question, String> clmCourse;

    @FXML
    private TableColumn<Question, String> clmDetails;

    @FXML
    private TableColumn<Question, Integer> clmID;

    @FXML
    private TableColumn<Question,String> clmSubject;
    
//    @FXML
//    void showTable(ActionEvent event) {
//    	String TempId = getid();
//		HashMap<String,ArrayList<String>> msg = new HashMap<>();
//		ArrayList<String> arr = new ArrayList<>();
//		arr.add("Lecturer");
//		msg.put("client", arr);
//		ArrayList<String> arr1 = new ArrayList<>();
//		arr1.add("getQustionBankById");
//		msg.put("task",arr1);
//		ArrayList<String> arr2 = new ArrayList<>();
//		arr2.add(TempId);
//		msg.put("param", arr2);
//		super.sendMsgToServer(msg);
//		try {
//			this.loadQuestions(ConnectionServer.rs);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		initTableView(qArr);
//    	
//    }
    
    
    private void initTableView(ArrayList<Question> arr) {
    	ObservableList<Question> list = FXCollections.observableArrayList(arr);
		PropertyValueFactory<Question, Integer> pvfId = new PropertyValueFactory<>("questionID");
		PropertyValueFactory<Question, String> pvfDetails = new PropertyValueFactory<>("details");
		PropertyValueFactory<Question, String> pvfCourses = new PropertyValueFactory<>("courses");
		PropertyValueFactory<Question, String> pvfSubject = new PropertyValueFactory<>("subject");
		clmID.setCellValueFactory(pvfId);
		clmDetails.setCellValueFactory(pvfDetails);
		clmCourse.setCellValueFactory(pvfCourses);
		clmSubject.setCellValueFactory(pvfSubject);
		QuestionBankLecTable.setItems(list);
		
	}



	public void loadQuestions(ArrayList<HashMap<String, Object>> rs) throws Exception {
		qArr= new ArrayList<>();
		if(rs == null) {
			System.out.println("rs is null");
			return;
		}
		for (int i = 0; i < rs.size(); i++) {
		    HashMap<String, Object> element = rs.get(i);
		    qArr.add(new Question((Integer)element.get("questionId"), (String)element.get("details"),
		    (String)element.get("rightAnswer"), (Integer)element.get("questionBank"),
		    (String)element.get("subject"),(String)element.get("answers"),(String)element.get("notes"),(String)element.get("courses")));
		}
	}

	private String getid() {
    	return TempIDbox.getText();
    }
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

    void AddNewQuestion(ActionEvent event) throws IOException {	
		Stage primaryStage = new Stage();
		AddNewQuestionController addNewQuestionController;
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/guiLecturer/AddNewQuestion.fxml").openStream());
		addNewQuestionController = loader.getController();
		addNewQuestionController.setMyQuestionBankController(this);
		try {
	        Scene scene = new Scene(root);
	        scene.getStylesheets().add("/gui/GenericStyleSheet.css");
	        primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.getIcons().add(new Image("/Images/CemsIcon32-Color.png"));
	        // Set the scene to the primary stage
	        primaryStage.setScene(scene);
	        primaryStage.show();
	        super.setPrimaryStage(primaryStage);
	        PressHandler<MouseEvent> press = new PressHandler<>();
	        DragHandler<MouseEvent> drag = new DragHandler<>();
	        root.setOnMousePressed(press);
	        root.setOnMouseDragged(drag);
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
		
    }
    @FXML
    void EditQuestion(ActionEvent event) throws IOException {
    	//((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		EditQuestionController editQuestionController;
		SelectionModel<Question> selectionModel = QuestionBankLecTable.getSelectionModel();
    	Question selectedItem = selectionModel.getSelectedItem();
    	if(!(selectedItem == null)) {
    		FXMLLoader loader = new FXMLLoader();
    		Pane root = loader.load(getClass().getResource("/guiLecturer/EditQuestion.fxml").openStream());
    		editQuestionController = loader.getController();
    		editQuestionController.setMyQuestionBankController(this);
    		editQuestionController.LoadQuestion(selectedItem);
    		try {
    	        Scene scene = new Scene(root);
    	        scene.getStylesheets().add("/gui/GenericStyleSheet.css");
    	        primaryStage.initStyle(StageStyle.UNDECORATED);
    			primaryStage.getIcons().add(new Image("/Images/CemsIcon32-Color.png"));
    	        // Set the scene to the primary stage
    	        primaryStage.setScene(scene);
    	        primaryStage.show();
    	        super.setPrimaryStage(primaryStage);
    	        PressHandler<MouseEvent> press = new PressHandler<>();
    	        DragHandler<MouseEvent> drag = new DragHandler<>();
    	        root.setOnMousePressed(press);
    	        root.setOnMouseDragged(drag);
    	    } catch(Exception e) {
    	        e.printStackTrace();
    	    }
    	}
    		
    }
    
    @FXML
    void DeleteQuestion(ActionEvent event) {
    	SelectionModel<Question> selectionModel = QuestionBankLecTable.getSelectionModel();
    	Question selectedItem = selectionModel.getSelectedItem();
    	HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> arr = new ArrayList<>();
		arr.add("Lecturer");
		msg.put("client", arr);
		ArrayList<String> arr1 = new ArrayList<>();
		arr1.add("deleteQuestion");
		msg.put("task",arr1);
		ArrayList<String> arr2 = new ArrayList<>();
		arr2.add(selectedItem.getQuestionID() + "");
		msg.put("param",arr2);
		super.sendMsgToServer(msg);
		showTable(event);
    }

}

