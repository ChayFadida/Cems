package timer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import abstractControllers.AbstractController;
import abstractControllers.AbstractController.DragHandler;
import abstractControllers.AbstractController.PressHandler;
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

public class TimerController extends AbstractController{
	public  CountDown countdown;
    private Clock clock;
    private TimeMode timeMode;
    private VirtualExamController Vexam=null;
    private ManualExamController Mexam=null;
    private ArrayList<QuestionForVirtualExam> questions = new ArrayList<>();
    private String type;
    private ArrayList<HashMap<String,Object>> rs= new ArrayList<>();
    private Stage stage;
    
    
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
    
    public void toggleBtnClicked() {
        if (countdown.isRunning())
            stop();
        else
            activate();
    }

    private void stop() {
        countdown.stop();
    }

    private void activate() {
        if (countdown.isTimeUp())
            reset();
        start();
    }

    private void reset() {
        countdown.reset();
    }

    private void start() {
        countdown.start();
    }
    
    public void timeIsUp() {
    	String endTime= TimerHandler.GetCurrentTimestamp();
    	String jsonString="";
    	int grade=0;
    	if(type=="Virtual") {
    		questions = Vexam.getQuestions();
    		ArrayList<Integer> selection = new ArrayList<>();
        	for(QuestionForVirtualExam q: questions) {
        		if(q.getSelection()==Integer.parseInt(q.getRightAnswer())) {
        			grade+=q.getScore();
        		}
        		selection.add(q.getSelection());
        	}
        	HashMap<String,ArrayList<Integer>> jsonHM = new HashMap<>();
        	jsonHM.put("answers", selection);
        	jsonString = JsonHandler.convertHashMapToJson(jsonHM, String.class, ArrayList.class);
    	}
    	int updated = TakeExamController.updateExamresults((int)rs.get(0).get("examId"),ConnectionServer.user.getId(),endTime,"not finished",grade,jsonString);
    	if(updated!=1) {
			System.out.println("Problem at updating examresults");
			return;
		}	
    	this.countdown.stop();
		stage.close();
		if(type=="Virtual")
			checkCheating(questions,(int)rs.get(0).get("examId"));
		if(TakeExamController.checkInProgressStudents((int)rs.get(0).get("examId"))==0)
			TakeExamController.lockExam((int)rs.get(0).get("examId"));
    	//implement query for timeIsUp
    	//update the examresult row to status 'not finished'
    	//run in loop on questions array in order to find matches betweeen 'Selection' and 'rightAnswer'
    	//sum up the grade when the student when he got the correct answer
    	//check if there are any other student in 'inProgess' status on the same exam, if not -> LockExam!
    }
    
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
			int wrongAnswers=0;
			int matchingWrongAnswers=0;
			for(int i=0;i<q.size();i++) {
				QuestionForVirtualExam question = q.get(i);
				if(question.getSelection()!=0 && Integer.parseInt(question.getRightAnswer())!=question.getSelection() ) {
					wrongAnswers++;
					if(question.getSelection()==otherStudentAnswers.get(i))
						matchingWrongAnswers++;
				}
			}
			if(wrongAnswers==matchingWrongAnswers) {
				openPopUp();
				break;
			}
		}
	}

	private void openPopUp() {
		try {
			FXMLLoader loader = new FXMLLoader();
			Parent root = loader.load(getClass().getResource("/guiStudent/SimulationPopUp.fxml").openStream());
			SimulationPopUpController simulationPopUpController = loader.getController();
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
	
	public void blockExam() {
		this.countdown.stop();
		stage.close();
	}
}
