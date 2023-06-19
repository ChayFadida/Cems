package timer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import abstractControllers.AbstractController;
import client.ConnectionServer;
import controllersStudent.TakeExamController;
import controllersStudent.VirtualExamController;
import controllersStudent.ManualExamController;
import controllersStudent.SimulationPopUpController;
import entities.QuestionForVirtualExam;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import thirdPart.JsonHandler;
/**
 * Controller class for the Timer.
 * In this controller the timer  we manage the ui of a clock 
 */
public class TimerController extends AbstractController {
	public  CountDown countdown;
    @SuppressWarnings("unused")
	private Clock clock;
    @SuppressWarnings("unused")
	private TimeMode timeMode;
    private VirtualExamController Vexam=null;
    private ManualExamController Mexam=null;
    private ArrayList<QuestionForVirtualExam> questions = new ArrayList<>();
    private String type;
    private ArrayList<HashMap<String,Object>> rs= new ArrayList<>();
    private Stage stage;
   
    /**
	 * method for starting a clock timer on a given mode and type for a given exam.
	 * @param Clock clock,TimeMode timeMode, String type, AbstractController exam.
	 */ 
    public void start(Clock clock,TimeMode timeMode, String type, AbstractController exam) {
    	this.clock=clock;
    	this.timeMode=timeMode;
    	this.type=type;
    	if(type=="Virtual") {
    		this.Vexam=(VirtualExamController) exam;
    		this.rs=((VirtualExamController) exam).getRs();
    		stage = Vexam.getStage();
    	}
    	else {
    		this.Mexam=(ManualExamController) exam;
    		this.rs=((ManualExamController) exam).getRs();
    		stage = Mexam.getStage();
    	}
    	countdown = new CountDown(timeMode,clock);
    	activate();
    }
    /**
	 * method for handling the click on the toggle button of a timer, works as a togle,
	 * when clicked when working -> will stop. .
	 * when clicked when not working -> start working.
	 * @param non.
	 */ 
    public void toggleBtnClicked() {
        if (countdown.isRunning())
            stop();
        else
            activate();
    }
    /**
	 * method for stopping a Countdown of a timer.
	 * @param non.
	 */ 
    private void stop() {
        countdown.stop();
    }
    /**
	 * method for activating  current paused/finished but existing Countdown of a timer.
	 * @param non.
	 */ 
    private void activate() {
        if (countdown.isTimeUp())
            reset();
        start();
    }
    /**
	 * method for resetting  current existing Countdown of a timer.
	 * @param non.
	 */ 
    private void reset() {
        countdown.reset();
    }
    /**
	 * method for starting  current existing Countdown of a timer.
	 * @param non.
	 */ 
    private void start() {
        countdown.start();
    }
    /**
	 * method for managing the entire timer while having an exam.
	 * implement query for timeIsUp
     *update the examresult row to status 'not finished'
     *run in loop on questions array in order to find matches betweeen 'Selection' and 'rightAnswer'
     *sum up the grade when the student when he got the correct answer
     *check if there are any other student in 'inProgess' status on the same exam, if not -> LockExam!
	 * @param non.
	 */ 
    public void timeIsUp() {
    	String endTime = TimerHandler.GetCurrentTimestamp();
    	String jsonString = "";
    	int grade = 0;
    	if(type == "Virtual") {
    		questions = Vexam.getQuestions();
    		ArrayList<Integer> selection = new ArrayList<>();
        	for(QuestionForVirtualExam q: questions) {
        		if(q.getSelection() == Integer.parseInt(q.getRightAnswer())) {
        			grade += q.getScore();
        		}
        		selection.add(q.getSelection());
        	}
        	HashMap<String,ArrayList<Integer>> jsonHM = new HashMap<>();
        	jsonHM.put("answers", selection);
        	jsonString = JsonHandler.convertHashMapToJson(jsonHM, String.class, ArrayList.class);
    	}
    	int updated = TakeExamController.updateExamresults((int) rs.get(0).get("examId"), ConnectionServer.user.getId(), endTime,"not finished", grade, jsonString);
    	if(updated != 1) {
			System.out.println("Problem at updating examresults");
			return;
		}	
    	this.countdown.stop();
		stage.close();
		if(type=="Virtual")
			checkCheating(questions,(int)rs.get(0).get("examId"));
		if(TakeExamController.checkInProgressStudents((int)rs.get(0).get("examId"))==0)
			TakeExamController.lockExam((int)rs.get(0).get("examId"));
    }
    /**
	 * method for checking if students have been cheating during the exam
	 * by searching for matching wrong answers in results of exams.
	 * @param non.
	 */ 
    public void checkCheating(ArrayList<QuestionForVirtualExam> q, int examId) {
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> arr = new ArrayList<>();
		arr.add("Student");
		msg.put("client", arr);
		ArrayList<String> arr1 = new ArrayList<>();
		arr1.add("getExamresultsOfOtherStudentsByExamId");
		msg.put("task",arr1);
		ArrayList<String> arr2 = new ArrayList<>();
		arr2.add(examId+"");
		arr2.add(ConnectionServer.user.getId()+"");
		msg.put("param", arr2);
		sendMsgToServer(msg);
		ArrayList<HashMap<String,Object>> rs = ConnectionServer.rs;
		if(rs == null){
			System.out.println("RS is null");
			return;
		}
		if(rs.isEmpty()) {
			return;
		}
		for(HashMap<String,Object> row:rs) {
			String answers = (String) row.get("answersChosen");
			HashMap<String,ArrayList<Integer>> jsonHM = JsonHandler.convertJsonToHashMap(answers, String.class, ArrayList.class);
			ArrayList<Integer> otherStudentAnswers = jsonHM.get("answers");
			int wrongAnswers = 0;
			int matchingWrongAnswers = 0;
			for(int i = 0 ; i<q.size() ; i++) {
				QuestionForVirtualExam question = q.get(i);
				if(question.getSelection()!=0 && Integer.parseInt(question.getRightAnswer()) != question.getSelection() ) {
					wrongAnswers++;
					if(question.getSelection() == otherStudentAnswers.get(i))
						matchingWrongAnswers++;
				}
			}
			if(wrongAnswers == matchingWrongAnswers) {
				openPopUp();
				break;
			}
		}
	}
    /**
	 * method for popping up a window to simulate the findings of cheating students.
	 * @param non.
	 */ 
	private void openPopUp() {
		try {
			FXMLLoader loader = new FXMLLoader();
			Parent root = loader.load(getClass().getResource("/guiStudent/SimulationPopUp.fxml").openStream());
			SimulationPopUpController simulationPopUpController = loader.getController();
			simulationPopUpController.setLblMsg("Studnet id: " + ConnectionServer.user.getId() + " might cheated");
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
	 * method for blocking current existing Countdown of a timer of an exam
	 * in order to block the exam.
	 * @param non.
	 */ 
	public void blockExam() {
		this.countdown.stop();
		stage.close();
	}

}
