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
import timer.Clock;
import timer.TimeMode;
import timer.TimerController;

public class VirtualExamController extends AbstractController implements Initializable{
	private ArrayList<QuestionForVirtualExam> questions = new ArrayList<>();
	private QuestionForVirtualExam currQ =null;
	private Integer currIndex=0;
	private Clock clock;
	private TimeMode timeMode;
	private TimerController timerController;
	
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
    void getBackwardBtn(ActionEvent event) {
		btnForward.setDisable(false);
		currIndex--;
		currQ=questions.get(currIndex);
		loadQuestion(currQ,currIndex);
		if(currIndex==0) {
			btnBackward.setDisable(true);
		}
    }
	
	@FXML
    void getForwardBtn(ActionEvent event) {
		btnBackward.setDisable(false);
    	currIndex++;
		currQ=questions.get(currIndex);
		loadQuestion(currQ,currIndex);
    }
    @FXML
    void getExitBtn(ActionEvent event) {
    	//need to set
    }

    @FXML
    void getMinimizeBtn(ActionEvent event) {
    	Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

	void loadQuestionsAndTime(ArrayList<QuestionForVirtualExam> questions,Integer time) {
		this.questions=questions;
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
	}


	private void loadQuestion(QuestionForVirtualExam q, Integer currIndex) {
		if(currIndex==0) {
			btnBackward.setDisable(true);
		}
		if((currIndex+1)==questions.size()) {
			btnForward.setDisable(true);
		}
		txtQuestionNo.setText((currIndex+1)+"/"+questions.size());
		txtfieldQuestion.setText(q.getDetails());
		if(q.getNotes()!=null)
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