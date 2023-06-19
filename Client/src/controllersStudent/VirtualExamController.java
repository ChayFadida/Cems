package controllersStudent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import abstractControllers.AbstractController;
import client.ConnectionServer;
import entities.QuestionForVirtualExam;
import entities.Student;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import thirdPart.JsonHandler;
import timer.Clock;
import timer.TimeMode;
import timer.TimerController;
import timer.TimerHandler;

/**
 * Controller class for the student.
 * In this controller the student preform virtual exam.
 * Extends AbstractController and implements Initializable.
 * @author pisto
 *
 */
public class VirtualExamController extends AbstractController implements Initializable{
	private ArrayList<QuestionForVirtualExam> questions = new ArrayList<>();
	private ArrayList<HashMap<String,Object>> rs= new ArrayList<>();
	private QuestionForVirtualExam currQ =null;
	private Integer currIndex=0;
	private Clock clock;
	private TimeMode timeMode;
	private TimerController timerController;
	private String startTime,endTime;
	private Stage thisStage;
	
	@FXML
    private ToggleGroup answers;

    @FXML
    private Button btnBackward;

    @FXML
    private Button btnForward;

    @FXML
    private Button btnMinimize;

    @FXML
    private Label lblHour;

    @FXML
    private Label lblMin;
    
    @FXML
    private Label lblSec;
    @FXML
    private AnchorPane questionAP;

    @FXML
    private RadioButton radio1;

    @FXML
    private RadioButton radio2;

    @FXML
    private RadioButton radio3;

    @FXML
    private RadioButton radio4;

    @FXML
    private Text txtQuestionNo;
    
    @FXML
    private TextField examNotes;
    
    @FXML
    private TextField txtfieldAnswer1;

    @FXML
    private TextField txtfieldAnswer2;

    @FXML
    private TextField txtfieldAnswer3;

    @FXML
    private TextField txtfieldAnswer4;

    @FXML
    private TextField txtfieldNotes;

    @FXML
    private TextField txtfieldQuestion;
    
    @FXML
    private ProgressBar progressBar;
    
    @FXML
    private Label lblScore;
    
    @FXML
    private Button btnSubmit;
    
    /**
     * This method provide the option to go back to the preview question.
     * @param event Action event.
     */
	@FXML
    void getBackwardBtn(ActionEvent event) {
		currIndex--;
		currQ=questions.get(currIndex);
		loadQuestion(currQ,currIndex);
    }
	/**
	 * This method provide the option to go to the next question.
	 * @param event Action event.
	 */
	@FXML
    void getForwardBtn(ActionEvent event) {
    	currIndex++;
		currQ=questions.get(currIndex);
		loadQuestion(currQ,currIndex);
    }
	/**
	 * This method provide the option to minimze current window.
	 * @param event Action event.
	 */
    @FXML
    void getMinimizeBtn(ActionEvent event) {
    	Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    /**
     * This method loads the questions and the time for the viretual exam.
     * @param questions the question in the exam.
     * @param time the duration for the exam
     * @param rs The result set we got back from the server.
     * @param stage The FXML page we load.
     */
	void loadQuestionsAndTime(ArrayList<QuestionForVirtualExam> questions, Integer time, ArrayList<HashMap<String,Object>> rs,Stage stage) {
		this.questions=questions;
		this.rs=rs;
		thisStage = stage;
		timeMode= new TimeMode(time);
		timerController = new TimerController();
		Student student = (Student) ConnectionServer.getInstance().getUser();
		clock = new Clock(timerController,lblHour,lblMin,lblSec,progressBar,timeMode);
		student.setExamSession(clock);
		timerController.start(clock, timeMode,"Virtual",this);
		if(!questions.isEmpty()) {
			QuestionForVirtualExam q = questions.get(0);
			currQ=q;
			currIndex=0;
			loadQuestion(currQ,currIndex);
		}
		else {
			System.out.println("No questions in test");
			timerController.countdown.stop();
			thisStage.close();
			return;
		}
		String studNote = (String) rs.get(0).get("studentNote");
		if(studNote==null || studNote== "" || studNote== " ")
			examNotes.setText("No specific instruction or notes for this exam.");
		else
			examNotes.setText(studNote);
		startTime=TimerHandler.GetCurrentTimestamp();
		int inserted = TakeExamController.insertToExamresults((Integer) rs.get(0).get("examId"),ConnectionServer.user.getId(),"Virtual",startTime);
		if(inserted!=1) {
			System.out.println("Problem at inserting to examresults");
			timerController.countdown.stop();
			thisStage.close();
			return;
		}
	}
	/**
	 * This method loads the question every time the student go back and forward in his exam. 
	 * @param q the question to load
	 * @param currIndex index of the question.
	 */
	private void loadQuestion(QuestionForVirtualExam q, Integer currIndex) {
		if(currIndex==0) {
			btnBackward.setDisable(true);
		}
		else
			btnBackward.setDisable(false);
		if((currIndex+1)==questions.size()) {
			btnForward.setDisable(true);
		}
		else
			btnForward.setDisable(false);
		setRadioBtnSelection(q);
		txtQuestionNo.setText((currIndex+1)+"/"+questions.size());
		txtfieldQuestion.setText(q.getDetails());
		if(q.getNotes()!=" ")
			txtfieldNotes.setText(q.getNotes());
		else
			txtfieldNotes.setText("No specific notes from the lecturer.");
		HashMap<String,String> answersHM = q.getAnswersHM();
		txtfieldAnswer1.setText(answersHM.get("answer1"));
		txtfieldAnswer2.setText(answersHM.get("answer2"));
		txtfieldAnswer3.setText(answersHM.get("answer3"));
		txtfieldAnswer4.setText(answersHM.get("answer4"));
		lblScore.setText(q.getScore()+"");
	}
	/**
	 * This method is for making the student to be able to chose only one answer in the exam.
	 * @param q the question.
	 */
	private void setRadioBtnSelection(QuestionForVirtualExam q) {
		radio1.setSelected(false);
		radio2.setSelected(false);
		radio3.setSelected(false);
		radio4.setSelected(false);
		if(q.getSelection()==1)
			radio1.setSelected(true);
		if(q.getSelection()==2)
			radio2.setSelected(true);
		if(q.getSelection()==3)
			radio3.setSelected(true);
		if(q.getSelection()==4)
			radio4.setSelected(true);
	}
	/**
	 * getter for the question
	 * @return the question.
	 */
    public ArrayList<QuestionForVirtualExam> getQuestions() {
		return questions;
	}
    /**
     * getter for the result set from the DB.
     * @return the result set that came back from the DB.
     */
	public ArrayList<HashMap<String, Object>> getRs() {
		return rs;
	}
	/**
	 * Return the current stage.
	 * @returnthe current stage.
	 */
	public Stage getStage() {
		return thisStage;
	}
	/**
	 * This method is called when the submit button is clicked.
	 * It performs several actions to handle the submission of an exam.
	 *
	 * @param event the action event generated by clicking the submit button.
	 */
	@FXML
    void getSubmitBtn(ActionEvent event) {
		endTime= TimerHandler.GetCurrentTimestamp();
		ArrayList<Integer> selection = new ArrayList<>();
		int grade=0;
    	for(QuestionForVirtualExam q: questions) {
    		if(q.getSelection()==Integer.parseInt(q.getRightAnswer())) {
    			grade+=q.getScore();
    		}
    		selection.add(q.getSelection());
    	}
    	HashMap<String,ArrayList<Integer>> jsonHM = new HashMap<>();
    	jsonHM.put("answers", selection);
    	String jsonString = JsonHandler.convertHashMapToJson(jsonHM, String.class, ArrayList.class);
    	int updated = TakeExamController.updateExamresults((int)rs.get(0).get("examId"),ConnectionServer.user.getId(),endTime,"waiting for approve",grade,jsonString);
    	if(updated!=1) {
			System.out.println("Problem at updating examresults");
			timerController.countdown.stop();
			thisStage.close();
			return;
		}	
    	timerController.countdown.stop();
		thisStage.close();
		checkCheating(questions,(int)rs.get(0).get("examId"));
    	if(TakeExamController.checkInProgressStudents((int)rs.get(0).get("examId"))==0)
    		TakeExamController.lockExam((int)rs.get(0).get("examId"));
    }
	
	/**
	 * This method is used to check for cheating attempts by comparing a student's answers with other students' answers for the same exam.
	 * @param q ArrayList of QuestionForVirtualExam objects.
	 * @param examId Id of the exam to check 
	 */
	 public void checkCheating(ArrayList<QuestionForVirtualExam> q, int examId) {
			HashMap<String,ArrayList<String>> msg = new HashMap<>();
			ArrayList<String> user = new ArrayList<>();
			user.add("Student");
			msg.put("client", user);
			ArrayList<String> query = new ArrayList<>();
			query.add("getExamresultsOfOtherStudentsByExamId");
			msg.put("task",query);
			ArrayList<String> parameter = new ArrayList<>();
			parameter.add(examId+"");
			parameter.add(ConnectionServer.user.getId()+"");
			msg.put("param", parameter);
			sendMsgToServer(msg);
			ArrayList<HashMap<String,Object>> rs = ConnectionServer.rs;
			if(rs == null){
				System.out.println("Could not load data from server.");
				return;
			}
			if(rs.isEmpty()) {
				System.out.println("No data in server.");
				return;
			}
			for(HashMap<String,Object> row:rs) {
				String answers = (String) row.get("answersChosen");
				HashMap<String,ArrayList<Integer>> jsonHM = JsonHandler.convertJsonToHashMap(answers, String.class, ArrayList.class,Integer.class);
				ArrayList<Integer> otherStudentAnswers = jsonHM.get("answers");
				System.out.println(otherStudentAnswers.toString());
				int wrongAnswers=0;
				int matchingWrongAnswers=0;
				for(int i=0;i<q.size();i++){
					QuestionForVirtualExam question = q.get(i);
					if(question.getSelection()!=0 && Integer.parseInt(question.getRightAnswer())!=question.getSelection() ) {
						wrongAnswers+=1;
						if(question.getSelection()==otherStudentAnswers.get(i))
							matchingWrongAnswers+=1;
					}
				}
				if(wrongAnswers==matchingWrongAnswers){
					System.out.println("Cheat!");
					String email = getLecturerEmail(examId);
					openPopUp(email);
					break;
				}
			}
		}
	
	 /**
	  * This method is used to retrieve the email address of the lecturer associated with a specific exam.
	  *
	  * @param examId the ID of the exam for which to retrieve the lecturer's email address.
	  * @return the email address of the lecturer.
	  */
	private String getLecturerEmail(int examId) {
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> user = new ArrayList<>();
		user.add("Student");
		msg.put("client", user);
		ArrayList<String> query = new ArrayList<>();
		query.add("getLecturerEmailByExamId");
		msg.put("task",query);
		ArrayList<String> parameter = new ArrayList<>();
		parameter.add(examId+"");
		msg.put("param", parameter);
		sendMsgToServer(msg);
		ArrayList<HashMap<String,Object>> rs = ConnectionServer.rs;
		if(rs == null){
			System.out.println("Could not get data from server.");
			return null;
		}
		if(rs.isEmpty()) {
			return "";
		}
		return (String) rs.get(0).get("email");
	}
	
	/**
	 * This method loads the pop-up window that displays cheating suspicion.
	 * @param email lecturer eamil adress to display.
	 */
	private void openPopUp(String email) {
		try {
			FXMLLoader loader = new FXMLLoader();
			Parent root = loader.load(getClass().getResource("/guiStudent/SimulationPopUp.fxml").openStream());
			SimulationPopUpController simulationPopUpController = loader.getController();
			simulationPopUpController.viewEmail(email);
			simulationPopUpController.setLblMsg("Studnet id: "+ConnectionServer.user.getId()+" might cheated");
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * This method initialize the controller.	
	 * @param location  the location used to resolve relative paths for the root object, or null if the location is not known.
	 * @param resources the resources used to localize the root object, or null if the root object was not localized.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		radio1.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
		        if (isNowSelected && currQ!=null) { 
		            currQ.setSelection(1);
		        }
		    }
		});
		radio2.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
		        if (isNowSelected && currQ!=null) { 
		            currQ.setSelection(2);
		        }
		    }
		});
		radio3.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
		        if (isNowSelected && currQ!=null) { 
		            currQ.setSelection(3);
		        }
		    }
		});
		radio4.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
		        if (isNowSelected && currQ!=null) { 
		            currQ.setSelection(4);
		        }
		    }
		});
	}
}