package controllersLecturer;

import java.util.ArrayList;
import java.util.HashMap;

import abstractControllers.AbstractController;
import client.ConnectionServer;
import entities.Exam;
import entities.QuestionForExam;
import entities.QuestionForVirtualExam;
import entities.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import thirdPart.JsonHandler;


public class ViewExamController extends AbstractController {
    private ArrayList<HashMap<String, Object>> rs = new ArrayList<>();
    private ArrayList<QuestionForExam> questions = new ArrayList<>();
    Exam exam;

    private QuestionForExam currQ = null;
    private Integer currIndex = 0;

    @FXML
    private Button btnBackward;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnForward;

    @FXML
    private Button btnMinimize;

    @FXML
    private Label lblScore;

    @FXML
    private AnchorPane questionAP;

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
    private TextField txtfieldCorrectAnswer;

    @FXML
    private TextField txtfieldNotes;

    @FXML
    private TextField txtfieldQuestion;

    @FXML
    void getBackwardBtn(ActionEvent event) {
        currIndex--;
        currQ = questions.get(currIndex);
        loadQuestion(currQ, currIndex);
    }

    @FXML
    void getForwardBtn(ActionEvent event) {
        currIndex++;
        currQ = questions.get(currIndex);
        loadQuestion(currQ, currIndex);
    }

    @FXML
    void getExitBtn(ActionEvent event) {
        Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    void getMinimizeBtn(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    public void setExam(Exam exam) {
        this.exam = exam;
        
    }

    public void setQuestion(ArrayList<QuestionForExam> questions) {
        this.questions = questions;
    }

    public void loadExam() {
    	int flagCode = exam.getExamId();
    	String studNote = exam.getStudentNote();
		if(studNote==null || studNote== "" || studNote== " ")
			examNotes.setText("No specific instruction or notes for this exam.");
		else
			examNotes.setText(studNote);
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
			return;
		}
		if(rs.isEmpty()) {
			System.out.println("Empty table from DB");
			return;
		}
		String questionsStr = (String) rs.get(0).get("questions");
		HashMap<String,ArrayList<Integer>> QjsonHM= JsonHandler.convertJsonToHashMap(questionsStr, String.class, ArrayList.class,Integer.class);
		ArrayList<Integer> questionsInExam = QjsonHM.get("questions");
		String scoresStr = (String) rs.get(0).get("scores");
		HashMap<String,ArrayList<Integer>> SjsonHM= JsonHandler.convertJsonToHashMap(scoresStr, String.class, ArrayList.class,Integer.class);
		ArrayList<Integer> scoresForQuestions = SjsonHM.get("scores");
        for (int i = 0; i < questionsInExam.size(); i++) {
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
            if (rs1 == null) {
                System.out.println("RS is null");
                return;
            }
            if (rs1.isEmpty()) {
                System.out.println("One of the exam's questions does not exist in DB");
                return;              
            }
            Question qu = new Question((Integer)rs1.get(0).get("questionId"),
					(String)rs1.get(0).get("details"),(String)rs1.get(0).get("rightAnswer"),(Integer)rs1.get(0).get("questionBankId"),
					(String)rs1.get(0).get("subject"),(String)rs1.get(0).get("answers"),(String)rs1.get(0).get("notes"),
					(String)rs1.get(0).get("courses"));
            QuestionForExam q = new QuestionForExam(qu, scoresForQuestions.get(i)+"");
            questions.add(q);
        }
        if (!questions.isEmpty()) {
            currQ = questions.get(0);
            loadQuestion(currQ, 0);
        }
    }

    private void loadQuestion(QuestionForExam q, Integer currIndex) {
        if (currIndex == 0) {
            btnBackward.setDisable(true);
        } else {
            btnBackward.setDisable(false);
        }
        if ((currIndex + 1) == questions.size()) {
            btnForward.setDisable(true);
        } else {
            btnForward.setDisable(false);
        }
        txtQuestionNo.setText((currIndex + 1) + "/" + questions.size());
        txtfieldQuestion.setText(q.getDetails());
        if (!q.getNotes().equals(" ")) {
            txtfieldNotes.setText(q.getNotes());
        } else {
            txtfieldNotes.setText("No specific notes from the lecturer.");
        }
        HashMap<String, String> answersHM = q.getAnswersHM();
        txtfieldAnswer1.setText(answersHM.get("answer1"));
        txtfieldAnswer2.setText(answersHM.get("answer2"));
        txtfieldAnswer3.setText(answersHM.get("answer3"));
        txtfieldAnswer4.setText(answersHM.get("answer4"));
        txtfieldCorrectAnswer.setText(q.getRightAnswer());
        lblScore.setText(q.getScore().getText());
    }
}
	
	
		
	

	
	

