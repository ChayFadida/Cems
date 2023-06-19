package controllersStudent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import abstractControllers.AbstractController;
import client.ConnectionServer;
import entities.QuestionForVirtualExam;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import thirdPart.JsonHandler;

/**
 * Controller class for the lecturer.
 * This controller is the screen for the student where he choose how to preform an exam, manual or virtual.
 */
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
    
    /**
     * This method is activated when clicked the Begin exam button.
     * Preform checks based on the id that was insert by the student.
     * @param event Action event after clicking Begin Exam.
     */
    @FXML
    void getBeginExam(ActionEvent event) {
    	lblErrorBegin.setText(" ");
    	boolean flagId = checkID(txtID.getText());
    	rs = checkCode(txtCode.getText());
    	int flagCode = (int)rs.get(0).get("examId");
    	if(flagId && (flagCode == -1)) {
    		lblErrorBegin.setText("Both ID and exam code are wrong, try again.");
    		return;
    	}
    	else if(flagId){
    		lblErrorBegin.setText("Wrong ID, try again");
    		return;
    	}
    	else if(flagCode == -1){
    		lblErrorBegin.setText("Wrong exam code, try again");
    		return;
    	}
    	int isLocked = checkIfExamLocked(flagCode);
    	if(isLocked == 0) {
    		lblErrorBegin.setText("This exam is locked.");
    		return;
    	}
    	int tookExam = checkIfAlreadyStarted(flagCode,txtID.getText());
    	if(tookExam == 0) {
    		lblErrorBegin.setText("You already took this exam before, try other code.");
    		return;
    	}
    	int res = retrieveQuestionsForExam(flagCode);
    	if(tookExam ==1 && res == 1 && isLocked == 1) {
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
    	else {
    		System.out.println("One of the queries returned null");
    		return;
    	}
    }
    
    /**
     * This method checks with the server if a specific exam is locked.
     * @param examId the exam id to check.
     * @return 0 if the exam is locked, 1 if the exam isnt locked, -1 if there was an error while checking.
     */
	private int checkIfExamLocked(int examId) {
		questions = new ArrayList<>();
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> user = new ArrayList<>();
		user.add("Student");
		msg.put("client", user);
		ArrayList<String> query = new ArrayList<>();
		query.add("checkIsLockedByExamId");
		msg.put("task", query);
		ArrayList<String> parameter = new ArrayList<>();
		parameter.add(examId + "");
		msg.put("param", parameter);
		super.sendMsgToServer(msg);
		ArrayList<HashMap<String,Object>> rs = ConnectionServer.rs;
		if(rs == null){
			System.out.println("Could not load data.");
			return -1;
		}
		if(rs.isEmpty()) {
			return 1; //implies the exam is not locked
		}
		System.out.println("Exam locked");
		return 0; //implies the exam is locked
	}
	
	/**
	 * This method checks with the server if an exam as already begun.
	 * checks by the exam id and student id.
	 * @param examId the exam id 
	 * @param studentId the student id
	 * @return 0 if the exam as already begun and done, 1 if the student didnt preform the exam yes, -1 if an error ocurred.
	 */
	private int checkIfAlreadyStarted(int examId, String studentId) {
		questions = new ArrayList<>();
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> user = new ArrayList<>();
		user.add("Student");
		msg.put("client", user);
		ArrayList<String> query = new ArrayList<>();
		query.add("getExamresultByExamAndUserId");
		msg.put("task", query);
		ArrayList<String> parameter = new ArrayList<>();
		parameter.add(examId+"");
		parameter.add(studentId);
		msg.put("param", parameter);
		super.sendMsgToServer(msg);
		ArrayList<HashMap<String,Object>> rs = ConnectionServer.rs;
		if(rs == null){
			System.out.println("Could not load data.");
			return -1;
		}
		if(rs.isEmpty()) {
			return 1; //implies the student did not started this test before
		}
		System.out.println("Trying to start exam that alreasy been done");
		return 0;
	}
	
	/**
	 * Retrieves the questions for the specified exam from the server.
	 *
	 * @param flagCode  the code of the exam to retrieve questions for.
	 * @return 1 if the questions were successfully retrieved, -1 if an error occurred while retrieving the questions.
	 */
	private int retrieveQuestionsForExam(int flagCode) {
		questions = new ArrayList<>();
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> user = new ArrayList<>();
		user.add("Student");
		msg.put("client", user);
		ArrayList<String> query = new ArrayList<>();
		query.add("getQuestionsAndScoresByExamId");
		msg.put("task", query);
		ArrayList<String> parameter = new ArrayList<>();
		parameter.add(flagCode + "");
		msg.put("param", parameter);
		super.sendMsgToServer(msg);
		ArrayList<HashMap<String,Object>> rs = ConnectionServer.rs;
		if(rs == null){
			System.out.println("could not load data from server.");
			return -1;
		}
		if(rs.isEmpty()) {
			System.out.println("Empty table from DB");
			return -1;
		}
		String questionsStr = (String) rs.get(0).get("questions");
		HashMap<String,ArrayList<Integer>> QjsonHM = JsonHandler.convertJsonToHashMap(questionsStr, String.class, ArrayList.class, Integer.class);
		ArrayList<Integer> questionsInExam = QjsonHM.get("questions");
		String scoresStr = (String) rs.get(0).get("scores");
		HashMap<String,ArrayList<Integer>> SjsonHM = JsonHandler.convertJsonToHashMap(scoresStr, String.class, ArrayList.class, Integer.class);
		ArrayList<Integer> scoresForQuestions = SjsonHM.get("scores");
		for(int i = 0 ; i<questionsInExam.size() ; i++) {
			HashMap<String,ArrayList<String>> msg1 = new HashMap<>();
			ArrayList<String> user1 = new ArrayList<>();
			user1.add("Student");
			msg1.put("client", user1);
			ArrayList<String> query1 = new ArrayList<>();
			query1.add("getQuestionById");
			msg1.put("task", query1);
			ArrayList<String> parameter1 = new ArrayList<>();
			parameter1.add(questionsInExam.get(i)+"");
			msg1.put("param", parameter1);
			super.sendMsgToServer(msg1);
			ArrayList<HashMap<String, Object>> rs1 = ConnectionServer.rs;
			if(rs1 == null) {
				System.out.println("could not load data from server.");
				return -1;
			}
			if(rs1.isEmpty()) {
				System.out.println("One of the exam's questions does not exist in DB");
				lblErrorBegin.setText("One of the exam's questions does not exist in DB, check it with you lecturer.");
				return -1;
			}
			QuestionForVirtualExam q = new QuestionForVirtualExam((Integer)rs1.get(0).get("questionId"),
					(String) rs1.get(0).get("details"), (String) rs1.get(0).get("rightAnswer"), (Integer) rs1.get(0).get("questionBankId"),
					(String) rs1.get(0).get("subject"), (String) rs1.get(0).get("answers"), (String) rs1.get(0).get("notes"),
					(String) rs1.get(0).get("courses"), scoresForQuestions.get(i));
			questions.add(q);
		}
		return 1;
	}
	
	/**
	 * This method opens the menual exam FXML page.
	 * @param event Action event to activate the screen.
	 */
	@FXML
    void getManual(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader();
		try {
			Parent root = loader.load(getClass().getResource("/guiStudent/ManualExamScreen.fxml").openStream());
			ManualExamController manualExamController = loader.getController();
			Stage primaryStage = new Stage();
			manualExamController.setExamInfo(rs,primaryStage);
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
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	/**
	 * This method opens the virtual exam FXML page.
	 * @param event Action event to activate the screen.
	 */
    @FXML
    void getVirtual(ActionEvent event) {
    	try {
    		FXMLLoader loader = new FXMLLoader();
    		Parent root = loader.load(getClass().getResource("/guiStudent/VirtualExamScreen.fxml").openStream());
    		VirtualExamController virtualExamController = loader.getController();
    		Stage primaryStage = new Stage();
    		virtualExamController.loadQuestionsAndTime(questions, (Integer) rs.get(0).get("duration"),rs,primaryStage);
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
    
    /**
     * Checks if the id given is the same as the user id.
     * @param text the id provided.
     * @return true if there is a match, flase otherwise.
     */
    private boolean checkID(String text) {
		return (ConnectionServer.user.getId() + "") == text;
  	}
    
    /**
     * This method checks if the exam code provided by the student is matching to the code from the DB. 
     * @param text the exam code
     * @return ArrayList with the exam information if the code is matching or 
     * returns an ArrayList with single HashMap with key value -1 in the examId. 
     */
	private ArrayList<HashMap<String,Object>> checkCode(String text) {
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> user = new ArrayList<>();
		user.add("Student");
		msg.put("client", user);
		ArrayList<String> query = new ArrayList<>();
		query.add("getExamByCode");
		msg.put("task", query);
		ArrayList<String> parameter = new ArrayList<>();
		parameter.add(text);
		msg.put("param", parameter);
		super.sendMsgToServer(msg);
		ArrayList<HashMap<String,Object>> rs = ConnectionServer.rs;
		if(rs == null){
			System.out.println("could not load data from the server.");
			rs = new ArrayList<>();
			HashMap<String,Object> hm = new HashMap<>();
			hm.put("examId", -1);
			rs.add(hm);
			return rs;
		}
		if(rs.isEmpty()) {
			HashMap<String,Object> hm = new HashMap<>();
			hm.put("examId", -1);
			rs.add(hm);
			return rs;
		}
		return rs;
	}
	
	/**
	 * Inserts a new record into the exam results table with the provided information.
	 *
	 * @param examId     the ID of the exam.
	 * @param studentId  the ID of the student.
	 * @param type       the type of the exam result.
	 * @param startTime  the start time of the exam.
	 * @return 1 if the record was inserted successfully, 0 if the record was not inserted, or -1 if there was an error.
	 */
	public static int insertToExamresults(Integer examId, Integer studentId, String type,String startTime) {
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> user = new ArrayList<>();
		user.add("Student");
		msg.put("client", user);
		ArrayList<String> query = new ArrayList<>();
		query.add("insertToExamresults");
		msg.put("task", query);
		ArrayList<String> parameter = new ArrayList<>();
		parameter.add(examId + "");
		parameter.add(studentId + "");
		parameter.add(type);
		parameter.add(startTime);
		parameter.add("inProgress");
		msg.put("param", parameter);
		sendMsgToServer(msg);
		ArrayList<HashMap<String,Object>> rs = ConnectionServer.rs;
		if(rs == null){
			System.out.println("Could not load data from server.");
			return -1;
		}
		if(rs.isEmpty()) {
			System.out.println("Empty table from DB");
			return -1; //implies the exam is not locked
		}
		return (int)rs.get(0).get("affectedRows") == 1 ? 1 : 0 ; //return 1 if rows was inserted
	}
	
	/**
	 * Updates the exam results for a specific exam and student with the provided information.
	 *
	 * @param examId        the ID of the exam.
	 * @param studentId     the ID of the student.
	 * @param endTime       the end time of the exam.
	 * @param status        the status of the exam result.
	 * @param grade         the grade of the exam.
	 * @param answerChosen  the chosen answer for the exam.
	 * @return 1 if the exam results were updated successfully, 0 if the exam results were not updated, or -1 if there was an error.
	 */
	public static int updateExamresults(int examId, int studentId, String endTime, String status, int grade, String answerChosen) {
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> user = new ArrayList<>();
		user.add("Student");
		msg.put("client", user);
		ArrayList<String> query = new ArrayList<>();
		query.add("updateExamresults");
		msg.put("task", query);
		ArrayList<String> parameter = new ArrayList<>();
		parameter.add(examId+"");
		parameter.add(studentId+"");
		parameter.add(endTime);
		parameter.add(status);
		parameter.add(grade+"");
		parameter.add(answerChosen);
		msg.put("param", parameter);
		sendMsgToServer(msg);
		ArrayList<HashMap<String,Object>> rs = ConnectionServer.rs;
		if(rs == null){
			System.out.println("Could not load data from the server.");
			return -1;
		}
		if(rs.isEmpty()) {
			System.out.println("Empty table from DB");
			return -1; //implies the exam is not locked
		}
		return (int)rs.get(0).get("affectedRows") == 1 ? 1 : 0 ; //return 1 if rows was inserted
	}
	
	/**
	 * Retrieves the number of students who are currently in progress for a specific exam.
	 *
	 * @param examId The identifier of the exam for which the in-progress students need to be checked.
	 * @return The count of students who are in progress for the specified exam. Returns -1 if there was an error retrieving the data or if no data was available.
	 */
	public static long checkInProgressStudents(int examId) {
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> user = new ArrayList<>();
		user.add("Student");
		msg.put("client", user);
		ArrayList<String> query = new ArrayList<>();
		query.add("checkCountInProgressByExamId");
		msg.put("task", query);
		ArrayList<String> parameter = new ArrayList<>();
		parameter.add(examId + "");
		msg.put("param", parameter);
		sendMsgToServer(msg);
		ArrayList<HashMap<String,Object>> rs = ConnectionServer.rs;
		if(rs == null){
			System.out.println("Could not load data from the server.");
			return -1;
		}
		if(rs.isEmpty()) {
			System.out.println("No data from the server");
			return -1;
		}
		return (long) rs.get(0).get("count");
	}
	
	/**
	 * Locks an exam by the given examId.
	 * This function prevents any further changes or submissions to the exam.
	 *
	 * @param examId The identifier of the exam to be locked.
	 */
	public static void lockExam(int examId) {
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> user = new ArrayList<>();
		user.add("Student");
		msg.put("client", user);
		ArrayList<String> query = new ArrayList<>();
		query.add("lockExamById");
		msg.put("task", query);
		ArrayList<String> parameter = new ArrayList<>();
		parameter.add(examId + "");
		msg.put("param", parameter);
		sendMsgToServer(msg);
		ArrayList<HashMap<String,Object>> rs = ConnectionServer.rs;
		if(rs == null){
			System.out.println("Could not load data from the server.");
			return;
		}
		if(rs.isEmpty()) {
			System.out.println("No data from server.");
			return;
		}
		if((int)rs.get(0).get("affectedRows") == 1) {
			System.out.println("No student in progress, Exam successfully locked");
		}
	}
    
	/**
	 * Displays the blocked page FXML.
	 * This method loads the BlockedPopupScreen.
	 */
    @SuppressWarnings("unused")
	public static void showBlockedPage() {
        try {
            FXMLLoader loader = new FXMLLoader(TakeExamController.class.getResource("/guiStudent/BlockedPopupScreen.fxml"));
            BlockedPopupController blockedPopupController = loader.getController();
    		Stage primaryStage = new Stage();
            Parent root = loader.load();
    		Scene scene = new Scene(root);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.getIcons().add(new Image("/Images/CemsIcon32-Color.png"));
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Displays the extend time page FXML.
     * This method loads the ExtendTimePopupScreen.
     */
    @SuppressWarnings("unused")
	public static void showExtendTimePage() {
        try {
            FXMLLoader loader = new FXMLLoader(TakeExamController.class.getResource("/guiStudent/ExtendTimePopupScreen.fxml"));
            ExtendTimePopupController extendTimePopupController = loader.getController();
    		Stage primaryStage = new Stage();
            Parent root = loader.load();
    		Scene scene = new Scene(root);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.getIcons().add(new Image("/Images/CemsIcon32-Color.png"));
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
