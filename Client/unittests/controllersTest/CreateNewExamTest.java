package controllersTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import common.CreateNewExamManagerIF;
import controllersLecturer.CreateNewExamController;
import entities.Question;
import entities.QuestionForExam;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class CreateNewExamTest {
	
    String code, lecNotes, studNotes, name, subject, duration;
    Label ActuallblError, ActuallblErrorCode, ActuallblErrorDuration, ActuallblScore, ActuallblErrorSelected, ActuallblErrorName, ActuallblErrorSubject;
    Label ExpectedlblError, ExpectedlblErrorCode, ExpectedlblErrorDuration, ExpectedlblScore, ExpectedlblErrorSelected, ExpectedlblErrorName, ExpectedlblErrorSubject;
    ArrayList<Object> qSelected;
    ArrayList<QuestionForExam> selectedItems;
    CreateNewExamController createNewExamController;
    StubCreateNewExamManager stubCreateNewExamManager;
    Question question1, question2, question3;
    HashMap<String,Object> affectedRowsHM, affectedRowsHM1;
    ArrayList<HashMap<String,Object>> resultSet2, resultSet1, resultSet;
    TableView<QuestionForExam> MockQuestionTable;
    ActionEvent MockActionEvent;
    
	private class StubCreateNewExamManager implements CreateNewExamManagerIF{

		@Override
		public String getSubject() {
			return subject;
		}

		@Override
		public String getCode() {
			return code;
		}

		@Override
		public String getDuration() {
			return duration;
		}

		@Override
		public String getLecNotes() {
			return lecNotes;
		}

		@Override
		public String getStudNotes() {
			return studNotes;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public void addAllSelectedToArray(ObservableList<QuestionForExam> selectedItems) {
			qSelected.addAll(selectedItems);
		}

		@Override
		public ArrayList<Object> getSelected() {
			return qSelected;
		}

		@Override
		public void initializeSelected() {
			qSelected = new ArrayList<>();
			
		}

		@Override
		public ArrayList<HashMap<String, Object>> insertExamToDB(String code, String duration, String lecNotes,
				String studNotes, String name, String subject) {
			return resultSet;
		}
		
		@Override
		public ArrayList<HashMap<String, Object>> insertQuestionsToDB(Integer examId) {
			return resultSet;
		}
		
		@Override
		public ArrayList<HashMap<String, Object>> updateExamBank(HashMap<String, Object> bank, Integer examId) {
			return resultSet;
		}

		@Override
		public ArrayList<HashMap<String, Object>> getExamBankQuery() {
			return resultSet;
		}
		
	}
	
	@BeforeEach
	public void setUp() throws Exception {
	    qSelected = new ArrayList<>();
	    affectedRowsHM = new HashMap<>();
	    affectedRowsHM1 = new HashMap<>();
	    resultSet2 = new ArrayList<>();
	    resultSet1 = new ArrayList<>();
	    resultSet = new ArrayList<>(); 
		stubCreateNewExamManager= new StubCreateNewExamManager();
		createNewExamController = new CreateNewExamController(stubCreateNewExamManager);
		MockQuestionTable = mock(TableView.class);
	    MockActionEvent = mock(ActionEvent.class);
		
		//set private field to mock
		Field privateTableView = CreateNewExamController.class.getDeclaredField("QuestionTable");
		privateTableView.setAccessible(true);
		privateTableView.set(createNewExamController, MockQuestionTable);
		
		selectedItems = new ArrayList<>();
		question1 = new Question(1, "What is the dot product of vectors u = (2, 3, 1) and v = (4, -1, 2)?", "1", 8, "linear function", "{'answer1':'11','answer2':'10','answer3':'9','answer4':'8'}", "no calculator allowed", "{'courses':[1]}");
		question2 = new Question(2, "Given the matrix A = [[1, 2, 3], [4, 5, 6], [7, 8, 9]], what is the determinant of A?", "1", 8, "linear function", "{'answer1':'0','answer2':'1','answer3':'-1','answer4':'2'}", "no calculator allowed", "{'courses':[1]}");
		question3 = new Question(3, "If matrix C has rank 2 and 3 columns, how many rows does C have?", "3", 8, "linear function", "{'answer1':'1','answer2':'2','answer3':'3','answer4':'4'}", "no calculator allowed", "{'courses':[1]}");
		//create resultSet after create succesfully
		affectedRowsHM.put("affectedRows", 1);
		resultSet2.add(affectedRowsHM);
		//create resultSet after create unsuccesfully
		affectedRowsHM1.put("affectedRows", 0);
		resultSet1.add(affectedRowsHM1);
	}
	
	@Test
	public void createExamSucsess() {
		//setup
	    code = "A123";
	    lecNotes = "notes";
	    studNotes = "notes";
	    name = "Algebra_TermA_2023";
	    subject = "Software Engineering";
	    duration = "120";
	    
	    selectedItems.add(new QuestionForExam(question1, "60"));
	    selectedItems.add(new QuestionForExam(question2, "40"));
	    resultSet = resultSet2;
	    ExpectedlblError = new Label("Exam uploaded successfully");
	    ExpectedlblErrorCode = new Label(" ");
	    ExpectedlblErrorDuration = new Label(" ");
	    ExpectedlblScore = new Label("100/100");
	    ExpectedlblErrorSelected = new Label(" ");
	    ExpectedlblErrorName = new Label(" ");
	    ExpectedlblErrorSubject = new Label(" ");

	    when(MockQuestionTable.getSelectionModel().getSelectedItems()).thenReturn((ObservableList<QuestionForExam>) selectedItems);
	    
	    createNewExamController.getSelected(MockActionEvent);
	    ActuallblScore = createNewExamController.getLblScore();
	    //check if score label is 100/100
	    assertEquals(ExpectedlblScore.getText(), ActuallblScore.getText());
	    ExpectedlblScore = new Label(" ");
	    createNewExamController.getFinish(MockActionEvent);
	    
	    ActuallblError = createNewExamController.getLblError();
	    ActuallblErrorCode = createNewExamController.getLblErrorCode();
	    ActuallblErrorDuration = createNewExamController.getLblErrorDuration();
	    ActuallblScore = createNewExamController.getLblScore();
	    ActuallblErrorSelected = createNewExamController.getLblErrorSelected();
	    ActuallblErrorName = createNewExamController.getLblErrorName();
	    ActuallblErrorSubject = createNewExamController.getLblErrorSubject();
	    
	    assertEquals(ExpectedlblError.getText(), ActuallblError.getText());
	    assertEquals(ExpectedlblErrorCode.getText(), ActuallblErrorCode.getText());
	    assertEquals(ExpectedlblErrorDuration.getText(), ActuallblErrorDuration.getText());
	    assertEquals(ExpectedlblScore.getText(), ActuallblScore.getText());
	    assertEquals(ExpectedlblErrorSelected.getText(), ActuallblErrorSelected.getText());
	    assertEquals(ExpectedlblErrorName.getText(), ActuallblErrorName.getText());
	    assertEquals(ExpectedlblErrorSubject.getText(), ActuallblErrorSubject.getText());
	}

//	@Test
//	void scoreTooHightTest() {
//
//	}
//	
//	@Test
//	void scoreTooLowTest() {
//	   
//	}
//
//	@Test
//	void allFildesEmptyTest() {
//	   
//	}
//
//	@Test
//	void wrongExamCodeTest() {
//	        
//	}
//
//	@Test
//	void questionScoreIsZeroTest() { 
//
//	}
//
//	@Test
//	void questionScoreIsNegativeTest() { 
//
//	}
//
//	@Test
//	void NegativeDurationTest() {
//	        
//	}
//	       
//	@Test
//	void durationNotNumberTest() {
//	        
//	}
//
//	@Test
//	void durationIsZeroTest() {
//	        
//	}
//
//	@Test
//	void someFiledsAreEmptyTest() {
//	        
//	}
	
}
