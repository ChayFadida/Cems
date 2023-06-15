package controllersStudent;

import java.util.ArrayList;
import java.util.HashMap;

import abstractControllers.AbstractController;
import abstractControllers.AbstractController.DragHandler;
import abstractControllers.AbstractController.PressHandler;
import client.ConnectionServer;
import entities.Course;
import entities.Lecturer;
import entities.QuestionForExam;
import entities.QuestionForVirtualExam;
import entities.Student;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import thirdPart.JsonHandler;

public class TakeExamController extends AbstractController{
	private ArrayList<QuestionForVirtualExam> questions = new ArrayList<>();
	private ArrayList<HashMap<String,Object>> rs = new ArrayList<>();
	
	@FXML
	private AnchorPane apA;

	@FXML
	private AnchorPane apB;

    @FXML
    private Button btnBegin;

    @FXML
    private Button btnManual;

    @FXML
    private Button btnVirtual;

    @FXML
    private Label lblErrorBegin;

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtID;

    @FXML
    void getBeginExam(ActionEvent event) {
    	boolean  flagId = checkID(txtID.getText());
    	rs = checkCode(txtCode.getText());
    	int flagCode = (int)rs.get(0).get("examId");
    	if(flagId && (flagCode==-1)) {
    		lblErrorBegin.setText("Both ID and exam code are wrong, try again.");
    		return;
    	}
    	else if(flagId){
    		lblErrorBegin.setText("Wrong ID, try again");
    		return;
    	}
    	else if(flagCode==-1){
    		lblErrorBegin.setText("Wrong exam code, try again");
    		return;
    	}
    	int res = retrieveQuestionsForExam(flagCode);
    	if(res==1) {
	    	FadeTransition fadeA = new FadeTransition();  
	    	fadeA.setNode(apA);
	    	fadeA.setFromValue(0);
	    	fadeA.setToValue(1);
	    	apA.setDisable(false);
	    	FadeTransition fadeB = new FadeTransition();
	    	fadeB.setNode(apB);
	    	fadeB.setFromValue(1);
	    	fadeB.setToValue(0);
	    	apB.setDisable(true);
	    	fadeA.play();
	    	fadeB.play();
    	}

    }

	private int retrieveQuestionsForExam(int flagCode) {
		questions = new ArrayList<>();
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> arr = new ArrayList<>();
		arr.add("Student");
		msg.put("client", arr);
		ArrayList<String> arr1 = new ArrayList<>();
		arr1.add("getQuestionsAndScoresByExamId");
		msg.put("task",arr1);
		ArrayList<String> arr2 = new ArrayList<>();
		arr2.add(flagCode+"");
		msg.put("param", arr2);
		super.sendMsgToServer(msg);
		ArrayList<HashMap<String,Object>> rs = ConnectionServer.rs;
		if(rs == null){
			System.out.println("RS is null");
			return -1;
		}
		if(rs.isEmpty()) {
			System.out.println("Empty table from DB");
			return -1;
		}
		String questionsStr = (String) rs.get(0).get("questions");
		HashMap<String,ArrayList<Integer>> QjsonHM= JsonHandler.convertJsonToHashMap(questionsStr, String.class, ArrayList.class,Integer.class);
		ArrayList<Integer> questionsInExam = QjsonHM.get("questions");
		String scoresStr = (String) rs.get(0).get("scores");
		HashMap<String,ArrayList<Integer>> SjsonHM= JsonHandler.convertJsonToHashMap(scoresStr, String.class, ArrayList.class,Integer.class);
		ArrayList<Integer> scoresForQuestions = SjsonHM.get("scores");
		for(int i=0;i<questionsInExam.size();i++) {
			HashMap<String,ArrayList<String>> msg1 = new HashMap<>();
			ArrayList<String> Arr = new ArrayList<>();
			Arr.add("Student");
			msg1.put("client", Arr);
			ArrayList<String> Arr1 = new ArrayList<>();
			Arr1.add("getQuestionById");
			msg1.put("task",Arr1);
			ArrayList<String> Arr2 = new ArrayList<>();
			Arr2.add(questionsInExam.get(i)+"");
			msg1.put("param", Arr2);
			super.sendMsgToServer(msg1);
			ArrayList<HashMap<String, Object>> rs1 = ConnectionServer.rs;
			if(rs1==null) {
				System.out.println("RS is null");
				return -1;
			}
			if(rs.isEmpty()) {
				System.out.println("One of the exam's questions does not exist in DB");
				lblErrorBegin.setText("One of the exam's questions does not exist in DB, check it with you lecturer.");
				return -1;
			}
			QuestionForVirtualExam q = new QuestionForVirtualExam((Integer)rs1.get(0).get("questionId"),
					(String)rs1.get(0).get("details"),(String)rs1.get(0).get("rightAnswer"),(Integer)rs1.get(0).get("questionBankId"),
					(String)rs1.get(0).get("subject"),(String)rs1.get(0).get("answers"),(String)rs1.get(0).get("notes"),
					(String)rs1.get(0).get("courses"),scoresForQuestions.get(i));
			questions.add(q);
		}
		return 1;
		
	}

	@FXML
    void getManual(ActionEvent event) {
		//wait for chay
    }

    @FXML
    void getVirtual(ActionEvent event) {
    	try {
    		FXMLLoader loader = new FXMLLoader();
    		Parent root = loader.load(getClass().getResource("/guiStudent/VirtualExamScreen.fxml").openStream());
    		VirtualExamController virtualExamController = loader.getController();
    		virtualExamController.loadQuestionsAndTime(questions, (Integer)rs.get(0).get("duration"),rs);
    		Stage primaryStage = new Stage();
    		Scene scene = new Scene(root);
    		primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.getIcons().add(new Image("/Images/CemsIcon32-Color.png"));
			primaryStage.setScene(scene);
		    primaryStage.show();
		    super.setPrimaryStage(primaryStage);
	        PressHandler<MouseEvent> press = new PressHandler<>();
	        DragHandler<MouseEvent> drag = new DragHandler<>();
	        root.setOnMousePressed(press);
	        root.setOnMouseDragged(drag);
	        FadeTransition fadeC = new FadeTransition();
	    	fadeC.setNode(apB);
	    	fadeC.setFromValue(0);
	    	fadeC.setToValue(1);
	    	fadeC.play();
    		
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    private boolean checkID(String text) {
		return (ConnectionServer.user.getId()+"")==text;
  	}
    
	private ArrayList<HashMap<String,Object>> checkCode(String text) {
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> arr = new ArrayList<>();
		arr.add("Student");
		msg.put("client", arr);
		ArrayList<String> arr1 = new ArrayList<>();
		arr1.add("getExamByCode");
		msg.put("task",arr1);
		ArrayList<String> arr2 = new ArrayList<>();
		arr2.add(text);
		msg.put("param", arr2);
		super.sendMsgToServer(msg);
		ArrayList<HashMap<String,Object>> rs = ConnectionServer.rs;
		if(rs == null){
			System.out.println("RS is null");
			rs= new ArrayList<>();
			HashMap<String,Object> hm = new HashMap<>();
			hm.put("examId",-1);
			rs.add(hm);
			return rs;
		}
		if(rs.isEmpty()) {
			HashMap<String,Object> hm = new HashMap<>();
			hm.put("examId",-1);
			rs.add(hm);
			return rs;
		}
		return rs;
	}
	
    @FXML
    void getSubmitBtn(ActionEvent event) {

    }
}
