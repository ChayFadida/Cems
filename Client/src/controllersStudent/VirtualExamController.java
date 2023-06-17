package controllersStudent;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import abstractControllers.AbstractController;
import entities.QuestionForVirtualExam;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import timer.TimeMode;
import timer.TimerController;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;    
import timer.TimeMode;
import timer.TimerController;
import timer.Clock;

public class VirtualExamController extends AbstractController implements Initializable{
	private ArrayList<QuestionForVirtualExam> questions = new ArrayList<>();
	private ArrayList<HashMap<String,Object>> rs= new ArrayList<>();
	private QuestionForVirtualExam currQ =null;
	private Integer currIndex=0;
	private Clock clock;
	private TimeMode timeMode;
	private TimerController timerController;
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");  
	private LocalDateTime start,end; 
	private String startTime,endTime;
	
	@FXML
    private ToggleGroup answers;

    @FXML
    private Button btnBackward;

    @FXML
    private Button btnExit;

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
    
	@FXML
    void getBackwardBtn(ActionEvent event) {
		currIndex--;
		currQ=questions.get(currIndex);
		loadQuestion(currQ,currIndex);
    }
	
	@FXML
    void getForwardBtn(ActionEvent event) {
    	currIndex++;
		currQ=questions.get(currIndex);
		loadQuestion(currQ,currIndex);
    }
    @FXML
    void getExitBtn(ActionEvent event) {
    	//need to be set
    }

    @FXML
    void getMinimizeBtn(ActionEvent event) {
    	Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

	void loadQuestionsAndTime(ArrayList<QuestionForVirtualExam> questions, Integer time, ArrayList<HashMap<String,Object>> rs) {
		this.questions=questions;
		this.rs=rs;
		timeMode= new TimeMode(time);
		timerController = new TimerController();
		clock = new Clock(timerController,lblHour,lblMin,lblSec,progressBar,timeMode);
		timerController.start(clock, timeMode);
		if(!questions.isEmpty()) {
			QuestionForVirtualExam q = questions.get(0);
			currQ=q;
			currIndex=0;
			loadQuestion(currQ,currIndex);
		}
		else {
			System.out.println("No questions in test");
			timerController.countdown.stop();
			//need to close test window
			return;
		}
		start=LocalDateTime.now();
		startTime=dtf.format(start);
		//need to insert row to DB with the current Start Time(in 'startTime') details and status 'inProgress'
	}


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
	
    @FXML
    void getSubmitBtn(ActionEvent event) {
    	end=LocalDateTime.now();
    	endTime=dtf.format(end);
    	//update the row in examresuls to status 'done', and update 'endTime'
    	//run in loop on questions array in order to find matches betweeen 'Selection' and 'rightAnswer'
    	//sum up the grade when the student when he got the correct answer
    	//check if there are any other student in 'inProgess' status on the same exam, if not -> LockExam!
    	//close window

    }
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