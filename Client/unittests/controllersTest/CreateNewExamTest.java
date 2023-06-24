package controllersTest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import common.CreateNewExamManagerIF;
import controllersLecturer.CreateNewExamController;
import entities.Question;
import entities.QuestionForExam;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.text.Text;


public class CreateNewExamTest {
	
    String code, lecNotes, studNotes, name, subject, duration;
    Text ActuallblError, ActuallblErrorCode, ActuallblErrorDuration, ActuallblScore, ActuallblErrorSelected, ActuallblErrorName, ActuallblErrorSubject;
    Text ExpectedlblError, ExpectedlblErrorCode, ExpectedlblErrorDuration, ExpectedlblScore, ExpectedlblErrorSelected, ExpectedlblErrorName, ExpectedlblErrorSubject;
    ArrayList<Object> qSelected;
    ArrayList<QuestionForExam> selectedItems;
    CreateNewExamController createNewExamController;
    StubCreateNewExamManager stubCreateNewExamManager;
    Question question1, question2, question3;
    HashMap<String,Object> affectedRowsHM, affectedRowsHM1;
    ArrayList<HashMap<String,Object>> resultSet2, resultSet1, resultSet;
    ObservableList<QuestionForExam> obsSelectedItems;
    ActionEvent MockActionEvent;
    
	private class StubCreateNewExamManager implements CreateNewExamManagerIF{

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

		@Override
		public ObservableList<QuestionForExam> getSelectedItemsFromTable() {
			return obsSelectedItems;
		}

		@Override
		public void setTxtSubject(String s) {
			subject=s;
			
		}

		@Override
		public String getTxtSubject() {
			return subject;
		}

		@Override
		public void setTxtName(String n) {
			name=n;
			
		}

		@Override
		public String getTxtName() {
			return name;
		}

		@Override
		public void setStudNotesText(String sNotes) {
			studNotes=sNotes;
		}

		@Override
		public String getStudNotesText() {
			return studNotes;
		}

		@Override
		public void setLecNotesTxt(String lNotes) {
			lecNotes =lNotes;
		}

		@Override
		public String getLecNotesTxt() {
			return lecNotes;
		}

		@Override
		public void setCodeTxt(String c) {
			code=c;
		}

		@Override
		public String getCodeTxt() {
			return code;
		}

		@Override
		public void setDurationTxt(String d) {
			duration=d;
		}

		@Override
		public String getDurationTxt() {
			return duration;
		}

		@Override
		public void loadQuestionsTable() {
			return;
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
	    MockActionEvent = mock(ActionEvent.class);
		
		
		selectedItems = new ArrayList<>();
		question1 = new Question(1, "What is the dot product of vectors u = (2, 3, 1) and v = (4, -1, 2)?", "1", 8, "linear function", "{'answer1':'11','answer2':'10','answer3':'9','answer4':'8'}", "no calculator allowed", "{'courses':[1]}");
		question2 = new Question(2, "Given the matrix A = [[1, 2, 3], [4, 5, 6], [7, 8, 9]], what is the determinant of A?", "1", 8, "linear function", "{'answer1':'0','answer2':'1','answer3':'-1','answer4':'2'}", "no calculator allowed", "{'courses':[1]}");
		question3 = new Question(3, "If matrix C has rank 2 and 3 columns, how many rows does C have?", "3", 8, "linear function", "{'answer1':'1','answer2':'2','answer3':'3','answer4':'4'}", "no calculator allowed", "{'courses':[1]}");
		//create resultSet after create succesfully
		affectedRowsHM.put("affectedRows", 1);
		affectedRowsHM.put("id", new BigInteger("1"));
		resultSet2.add(affectedRowsHM);
		//create resultSet after create unsuccesfully
		affectedRowsHM1.put("affectedRows", 0);
		resultSet1.add(affectedRowsHM1);
	}
	
	// checking if the creation of an exam is successfully created when all field are valid
    // input: code = "A123"
    //		lecNotes = "notes"
    //		studNotes = "notes"
    //		name = "Algebra_TermA_2023"
    //		subject = "Software Engineering"
    //		duration = "120"
	//		QuestionForExam(question1, "60")
	//		QuestionForExam(question2, "40")
    // expected: all labels are empty (= " ") besides:
	//			ExpectedlblError = "Exam uploaded successfully"
	//			ExpectedlblScore = "100/100"
	//			ExpectedlblScore = ""0/100""

	@Test
	public void createExam_AllFieldsAreValid_Success() {
		//setup
	    code = "A123";
	    lecNotes = "notes";
	    studNotes = "notes";
	    name = "Algebra_TermA_2023";
	    subject = "Software Engineering";
	    duration = "120";
	    
	    selectedItems.add(new QuestionForExam(question1, "60"));
	    selectedItems.add(new QuestionForExam(question2, "40"));
	    obsSelectedItems =FXCollections.observableArrayList(selectedItems);
	    
	    resultSet = resultSet2;
	    ExpectedlblError = new Text("Exam uploaded successfully");
	    ExpectedlblErrorCode = new Text(" ");
	    ExpectedlblErrorDuration = new Text(" ");
	    ExpectedlblScore = new Text("100/100");
	    ExpectedlblErrorSelected = new Text(" ");
	    ExpectedlblErrorName = new Text(" ");
	    ExpectedlblErrorSubject = new Text(" ");
	    

	    createNewExamController.getSelected(MockActionEvent);
	    ActuallblScore = createNewExamController.getLblScore();
	    
	  
	    assertEquals(ExpectedlblScore.getText(), ActuallblScore.getText());//check if score label is 100/100
	    ExpectedlblScore = new Text("0/100");
	
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
	
	// checking if the creation of an exam is successfully created when score is above 100
    // input: code = "A123"
    //		lecNotes = "notes"
    //		studNotes = "notes"
    //		name = "Algebra_TermA_2023"
    //		subject = "Software Engineering"
    //		duration = "120"
	//		QuestionForExam(question1, "60")
	//		QuestionForExam(question2, "50")
    // expected: all labels are empty (= " ") besides:
	//			ExpectedlblError = "Please fix all error and try again later."
	//			ExpectedlblScore = "110/100"
	//			ExpectedlblErrorSelected = "Total score is not '100', change scores and try again."
	//			ExpectedlblErrorSelected = "Total score must be '100'."
	@Test
	public void createExam_ScoreTooHigh() {
		//setup
	    code = "A123";
	    lecNotes = "notes";
	    studNotes = "notes";
	    name = "Algebra_TermA_2023";
	    subject = "Software Engineering";
	    duration = "120";
	    
	    selectedItems.add(new QuestionForExam(question1, "60"));
	    selectedItems.add(new QuestionForExam(question2, "50"));
	    obsSelectedItems =FXCollections.observableArrayList(selectedItems);
	    
	    resultSet = resultSet2;
	    ExpectedlblError = new Text("Please fix all error and try again later.");
	    ExpectedlblErrorCode = new Text(" ");
	    ExpectedlblErrorDuration = new Text(" ");
	    ExpectedlblScore = new Text("110/100");
	    ExpectedlblErrorSelected = new Text("Total score is not '100', change scores and try again.");
	    ExpectedlblErrorName = new Text(" ");
	    ExpectedlblErrorSubject = new Text(" ");
	    
	    
	    createNewExamController.getSelected(MockActionEvent);
	    ActuallblScore = createNewExamController.getLblScore();
	    ActuallblErrorSelected = createNewExamController.getLblErrorSelected();
	    //check if score label is 100/100
	    assertEquals(ExpectedlblScore.getText(), ActuallblScore.getText());
	    assertEquals(ExpectedlblErrorSelected.getText(), ActuallblErrorSelected.getText());
	    
	    createNewExamController.getFinish(MockActionEvent);
	    ExpectedlblErrorSelected = new Text("Total score must be '100'.");
	    
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
	
	// checking if the creation of an exam is successfully created when score is below 100
    // input: code = "A123"
    //		lecNotes = "notes"
    //		studNotes = "notes"
    //		name = "Algebra_TermA_2023"
    //		subject = "Software Engineering"
    //		duration = "120"
	//		QuestionForExam(question1, "20")
	//		QuestionForExam(question2, "50")
    // expected: all labels are empty (= " ") besides:
	//			ExpectedlblError = "Please fix all error and try again later."
	//			ExpectedlblScore = "70/100"
	//			ExpectedlblErrorSelected = "Total score is not '100', change scores and try again."
	//			ExpectedlblErrorSelected = "Total score must be '100'."
	@Test
	public void createExam_ScoreTooLow() {
		//setup
	    code = "A123";
	    lecNotes = "notes";
	    studNotes = "notes";
	    name = "Algebra_TermA_2023";
	    subject = "Software Engineering";
	    duration = "120";
	    
	    selectedItems.add(new QuestionForExam(question1, "20"));
	    selectedItems.add(new QuestionForExam(question2, "50"));
	    obsSelectedItems =FXCollections.observableArrayList(selectedItems);
	    
	    resultSet = resultSet2;
	    ExpectedlblError = new Text("Please fix all error and try again later.");
	    ExpectedlblErrorCode = new Text(" ");
	    ExpectedlblErrorDuration = new Text(" ");
	    ExpectedlblScore = new Text("70/100");
	    ExpectedlblErrorSelected = new Text("Total score is not '100', change scores and try again.");
	    ExpectedlblErrorName = new Text(" ");
	    ExpectedlblErrorSubject = new Text(" ");
	    
	    
	    createNewExamController.getSelected(MockActionEvent);
	    ActuallblScore = createNewExamController.getLblScore();
	    ActuallblErrorSelected = createNewExamController.getLblErrorSelected();
	    //check if score label is 100/100
	    assertEquals(ExpectedlblScore.getText(), ActuallblScore.getText());
	    assertEquals(ExpectedlblErrorSelected.getText(), ActuallblErrorSelected.getText());
	    
	    createNewExamController.getFinish(MockActionEvent);
	    ExpectedlblErrorSelected = new Text("Total score must be '100'.");
	    
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
	
	
	// checking if the creation of an exam is successfully created when all the field are empty
    // input: code = "
    //		lecNotes = ""
    //		studNotes = ""
    //		name = ""
    //		subject = ""
    //		duration = ""
	//		QuestionForExam(question1, "50")
	//		QuestionForExam(question2, "50")
    // expected: all labels are empty (= " ") besides:
	//			ExpectedlblError = "Please fix all error and try again later."
	//			ExpectedlblScore = "100/100"
	//			ExpectedlblErrorCode = "Code must be 4 digits and contains only letters and numbers."
	//			ExpectedlblErrorDuration = "Duration must contain only numbers above 0 (represents minutes)."
	//			ExpectedlblErrorName = "You must enter an exam name"
	//			ExpectedlblErrorSubject = "You must enter a subject"
	
	@Test
	public void createExam_allFieldesEmpty() {
		//setup
	    code = "";
	    lecNotes = "";
	    studNotes = "";
	    name = "";
	    subject = "";
	    duration = "";
	    
	    selectedItems.add(new QuestionForExam(question1, ""));
	    selectedItems.add(new QuestionForExam(question2, ""));
	    obsSelectedItems =FXCollections.observableArrayList(selectedItems);
	    
	    resultSet = resultSet2;
	    ExpectedlblError = new Text("Please fix all error and try again later.");
	    ExpectedlblErrorCode = new Text("Code must be 4 digits and contains only letters and numbers.");
	    ExpectedlblErrorDuration = new Text("Duration must contain only numbers above 0 (represents minutes).");
	    ExpectedlblScore = new Text("0/100");
	    ExpectedlblErrorSelected = new Text("One of the selected questions score is not a positive number, try again.");
	    ExpectedlblErrorName = new Text("You must enter an exam name");
	    ExpectedlblErrorSubject = new Text("You must enter a subject");
	    
	    
	    createNewExamController.getSelected(MockActionEvent);
	    ActuallblScore = createNewExamController.getLblScore();
	    ActuallblErrorSelected = createNewExamController.getLblErrorSelected();
	    //check if score label is 100/100
	    assertEquals(ExpectedlblScore.getText(), ActuallblScore.getText());
	    assertEquals(ExpectedlblErrorSelected.getText(), ActuallblErrorSelected.getText());
	    
	    createNewExamController.getFinish(MockActionEvent);
	    ExpectedlblErrorSelected = new Text("Please select questions first.");

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
	
	
	// checking if the creation of an exam is successfully created when exam code contains special letters
    // input: code = "A12!"
    //		lecNotes = "notes"
    //		studNotes = "notes"
    //		name = "Algebra_TermA_2023"
    //		subject = "Software Engineering"
    //		duration = "120"
	//		QuestionForExam(question1, "50")
	//		QuestionForExam(question2, "50")
    // expected: all labels are empty (= " ") besides:
	//			ExpectedlblError = "Please fix all error and try again later."
	//			ExpectedlblScore = "100/100"
	//			ExpectedlblErrorCode = "Code must be 4 digits and contains only letters and numbers."
	@Test
	public void createExam_ExamCodeContainsSpecialLetters() {
		//setup
	    code = "A12!";
	    lecNotes = "notes";
	    studNotes = "notes";
	    name = "Algebra_TermA_2023";
	    subject = "Software Engineering";
	    duration = "120";
	    
	    selectedItems.add(new QuestionForExam(question1, "50"));
	    selectedItems.add(new QuestionForExam(question2, "50"));
	    obsSelectedItems =FXCollections.observableArrayList(selectedItems);
	    
	    resultSet = resultSet2;
	    ExpectedlblError = new Text("Please fix all error and try again later.");
	    ExpectedlblErrorCode = new Text("Code must be 4 digits and contains only letters and numbers.");
	    ExpectedlblErrorDuration = new Text(" ");
	    ExpectedlblScore = new Text("100/100");
	    ExpectedlblErrorSelected = new Text(" ");
	    ExpectedlblErrorName = new Text(" ");
	    ExpectedlblErrorSubject = new Text(" ");
	    
	    
	    createNewExamController.getSelected(MockActionEvent);
	    ActuallblScore = createNewExamController.getLblScore();
	    ActuallblErrorSelected = createNewExamController.getLblErrorSelected();
	    //check if score label is 100/100
	    assertEquals(ExpectedlblScore.getText(), ActuallblScore.getText());
	    assertEquals(ExpectedlblErrorSelected.getText(), ActuallblErrorSelected.getText());
	    
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
	
	// checking if the creation of an exam is successfully created when exam code length is greater than 4
    // input: code = "A1231"
    //		lecNotes = "notes"
    //		studNotes = "notes"
    //		name = "Algebra_TermA_2023"
    //		subject = "Software Engineering"
    //		duration = "120"
	//		QuestionForExam(question1, "50")
	//		QuestionForExam(question2, "50")
    // expected: all labels are empty (= " ") besides:
	//			ExpectedlblError = "Please fix all error and try again later."
	//			ExpectedlblScore = "100/100"
	//			ExpectedlblErrorCode = "Code must be 4 digits and contains only letters and numbers."
	@Test
	public void createExam_ExamCodeLengthGreaterThan4() {
		//setup
	    code = "A1231";
	    lecNotes = "notes";
	    studNotes = "notes";
	    name = "Algebra_TermA_2023";
	    subject = "Software Engineering";
	    duration = "120";
	    
	    selectedItems.add(new QuestionForExam(question1, "50"));
	    selectedItems.add(new QuestionForExam(question2, "50"));
	    obsSelectedItems =FXCollections.observableArrayList(selectedItems);
	    
	    resultSet = resultSet2;
	    ExpectedlblError = new Text("Please fix all error and try again later.");
	    ExpectedlblErrorCode = new Text("Code must be 4 digits and contains only letters and numbers.");
	    ExpectedlblErrorDuration = new Text(" ");
	    ExpectedlblScore = new Text("100/100");
	    ExpectedlblErrorSelected = new Text(" ");
	    ExpectedlblErrorName = new Text(" ");
	    ExpectedlblErrorSubject = new Text(" ");
	    
	    
	    createNewExamController.getSelected(MockActionEvent);
	    ActuallblScore = createNewExamController.getLblScore();
	    ActuallblErrorSelected = createNewExamController.getLblErrorSelected();
	    //check if score label is 100/100
	    assertEquals(ExpectedlblScore.getText(), ActuallblScore.getText());
	    assertEquals(ExpectedlblErrorSelected.getText(), ActuallblErrorSelected.getText());
	    
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
	
	// checking if the creation of an exam is successfully created when exam code length is less than 4
    // input: code = "A12"
    //		lecNotes = "notes"
    //		studNotes = "notes"
    //		name = "Algebra_TermA_2023"
    //		subject = "Software Engineering"
    //		duration = "120"
	//		QuestionForExam(question1, "50")
	//		QuestionForExam(question2, "50")
    // expected: all labels are empty (= " ") besides:
	//			ExpectedlblError = "Please fix all error and try again later."
	//			ExpectedlblScore = "100/100"
	//			ExpectedlblErrorCode = "Code must be 4 digits and contains only letters and numbers."
	@Test
	public void createExam_ExamCodeLengthLessThan4() {
		//setup
	    code = "A12";
	    lecNotes = "notes";
	    studNotes = "notes";
	    name = "Algebra_TermA_2023";
	    subject = "Software Engineering";
	    duration = "120";
	    
	    selectedItems.add(new QuestionForExam(question1, "50"));
	    selectedItems.add(new QuestionForExam(question2, "50"));
	    obsSelectedItems =FXCollections.observableArrayList(selectedItems);
	    
	    resultSet = resultSet2;
	    ExpectedlblError = new Text("Please fix all error and try again later.");
	    ExpectedlblErrorCode = new Text("Code must be 4 digits and contains only letters and numbers.");
	    ExpectedlblErrorDuration = new Text(" ");
	    ExpectedlblScore = new Text("100/100");
	    ExpectedlblErrorSelected = new Text(" ");
	    ExpectedlblErrorName = new Text(" ");
	    ExpectedlblErrorSubject = new Text(" ");
	    
	    
	    createNewExamController.getSelected(MockActionEvent);
	    ActuallblScore = createNewExamController.getLblScore();
	    ActuallblErrorSelected = createNewExamController.getLblErrorSelected();
	    //check if score label is 100/100
	    assertEquals(ExpectedlblScore.getText(), ActuallblScore.getText());
	    assertEquals(ExpectedlblErrorSelected.getText(), ActuallblErrorSelected.getText());
	    
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
	
	// checking if the creation of an exam is successfully created when question score is 0
    // input: code = "A123"
    //		lecNotes = "notes"
    //		studNotes = "notes"
    //		name = "Algebra_TermA_2023"
    //		subject = "Software Engineering"
    //		duration = "120"
	//		QuestionForExam(question1, "0")
	//		QuestionForExam(question2, "0")
    // expected: all labels are empty (= " ") besides:
	//			ExpectedlblError = "Please fix all error and try again later."
	//			ExpectedlblScore = "0/100"
	//			ExpectedlblErrorSelected = "One of the selected questions score is set to '0', try again."
	//			ExpectedlblErrorSelected = "Please select questions first."
	@Test
	public void createExam_questionScoreIsZero() { 
		//setup
	    code = "A123";
	    lecNotes = "notes";
	    studNotes = "notes";
	    name = "Algebra_TermA_2023";
	    subject = "Software Engineering";
	    duration = "120";
	    
	    selectedItems.add(new QuestionForExam(question1, "0"));
	    selectedItems.add(new QuestionForExam(question2, "0"));
	    obsSelectedItems =FXCollections.observableArrayList(selectedItems);
	    
	    resultSet = resultSet2;
	    ExpectedlblError = new Text("Please fix all error and try again later.");
	    ExpectedlblErrorCode = new Text(" ");
	    ExpectedlblErrorDuration = new Text(" ");
	    ExpectedlblScore = new Text("0/100");
	    ExpectedlblErrorSelected = new Text("One of the selected questions score is set to '0', try again.");
	    ExpectedlblErrorName = new Text(" ");
	    ExpectedlblErrorSubject = new Text(" ");
	    
	    
	    createNewExamController.getSelected(MockActionEvent);
	    ActuallblScore = createNewExamController.getLblScore();
	    ActuallblErrorSelected = createNewExamController.getLblErrorSelected();
	    //check if score label is 100/100
	    assertEquals(ExpectedlblScore.getText(), ActuallblScore.getText());
	    assertEquals(ExpectedlblErrorSelected.getText(), ActuallblErrorSelected.getText());
	    
	    createNewExamController.getFinish(MockActionEvent);
	    ExpectedlblErrorSelected = new Text("Please select questions first.");
	    
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
	
	// checking if the creation of an exam is successfully created when question score is negative
    // input: code = "A123"
    //		lecNotes = "notes"
    //		studNotes = "notes"
    //		name = "Algebra_TermA_2023"
    //		subject = "Software Engineering"
    //		duration = "120"
	//		QuestionForExam(question1, "-10")
	//		QuestionForExam(question2, "50")
    // expected: all labels are empty (= " ") besides:
	//			ExpectedlblError = "Please fix all error and try again later."
	//			ExpectedlblScore = "0/100"
	//			ExpectedlblErrorSelected = "One of the selected questions score is set to '0', try again."
	//			ExpectedlblErrorSelected = "Please select questions first."
	@Test
	public void createExam_questionScoreIsNegative() { 
		//setup
	    code = "A123";
	    lecNotes = "notes";
	    studNotes = "notes";
	    name = "Algebra_TermA_2023";
	    subject = "Software Engineering";
	    duration = "120";
	    
	    selectedItems.add(new QuestionForExam(question1, "-10"));
	    selectedItems.add(new QuestionForExam(question2, "50"));
	    obsSelectedItems =FXCollections.observableArrayList(selectedItems);
	    
	    resultSet = resultSet2;
	    ExpectedlblError = new Text("Please fix all error and try again later.");
	    ExpectedlblErrorCode = new Text(" ");
	    ExpectedlblErrorDuration = new Text(" ");
	    ExpectedlblScore = new Text("0/100");
	    ExpectedlblErrorSelected = new Text("One of the selected questions score is not a positive number, try again.");
	    ExpectedlblErrorName = new Text(" ");
	    ExpectedlblErrorSubject = new Text(" ");
	    
	    
	    createNewExamController.getSelected(MockActionEvent);
	    ActuallblScore = createNewExamController.getLblScore();
	    ActuallblErrorSelected = createNewExamController.getLblErrorSelected();
	    //check if score label is 100/100
	    assertEquals(ExpectedlblScore.getText(), ActuallblScore.getText());
	    assertEquals(ExpectedlblErrorSelected.getText(), ActuallblErrorSelected.getText());
	    
	    createNewExamController.getFinish(MockActionEvent);
	    ExpectedlblErrorSelected = new Text("Please select questions first.");
	    
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
	
	// checking if the creation of an exam is successfully created when duration value is negative
    // input: code = "A123"
    //		lecNotes = "notes"
    //		studNotes = "notes"
    //		name = "Algebra_TermA_2023"
    //		subject = "Software Engineering"
    //		duration = "-120"
	//		QuestionForExam(question1, "50")
	//		QuestionForExam(question2, "50")
    // expected: all labels are empty (= " ") besides:
	//			ExpectedlblError = "Please fix all error and try again later."
	//			ExpectedlblScore = "100/100"
	//			ExpectedlblErrorDuration = "Duration must contain only numbers above 0 (represents minutes)."
	@Test
	public void createExam_NegativeDuration() {
		//setup
	    code = "A123";
	    lecNotes = "notes";
	    studNotes = "notes";
	    name = "Algebra_TermA_2023";
	    subject = "Software Engineering";
	    duration = "-120";
	    
	    selectedItems.add(new QuestionForExam(question1, "50"));
	    selectedItems.add(new QuestionForExam(question2, "50"));
	    obsSelectedItems =FXCollections.observableArrayList(selectedItems);
	    
	    resultSet = resultSet2;
	    ExpectedlblError = new Text("Please fix all error and try again later.");
	    ExpectedlblErrorCode = new Text(" ");
	    ExpectedlblErrorDuration = new Text("Duration must contain only numbers above 0 (represents minutes).");
	    ExpectedlblScore = new Text("100/100");
	    ExpectedlblErrorSelected = new Text(" ");
	    ExpectedlblErrorName = new Text(" ");
	    ExpectedlblErrorSubject = new Text(" ");
	    
	    
	    createNewExamController.getSelected(MockActionEvent);
	    ActuallblScore = createNewExamController.getLblScore();
	    ActuallblErrorSelected = createNewExamController.getLblErrorSelected();
	    //check if score label is 100/100
	    assertEquals(ExpectedlblScore.getText(), ActuallblScore.getText());
	    assertEquals(ExpectedlblErrorSelected.getText(), ActuallblErrorSelected.getText());
	    
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
    
	// checking if the creation of an exam is successfully created when duration value is not a number
    // input: code = "A123"
    //		lecNotes = "notes"
    //		studNotes = "notes"
    //		name = "Algebra_TermA_2023"
    //		subject = "Software Engineering"
    //		duration = "!!"
	//		QuestionForExam(question1, "50")
	//		QuestionForExam(question2, "50")
    // expected: all labels are empty (= " ") besides:
	//			ExpectedlblError = "Please fix all error and try again later."
	//			ExpectedlblScore = "100/100"
	//			ExpectedlblErrorDuration = "Duration must contain only numbers above 0 (represents minutes)."
	@Test
	void createExam_DurationIsNotNumber() {
		//setup
	    code = "A123";
	    lecNotes = "notes";
	    studNotes = "notes";
	    name = "Algebra_TermA_2023";
	    subject = "Software Engineering";
	    duration = "!!";
	    
	    selectedItems.add(new QuestionForExam(question1, "50"));
	    selectedItems.add(new QuestionForExam(question2, "50"));
	    obsSelectedItems =FXCollections.observableArrayList(selectedItems);
	    
	    resultSet = resultSet2;
	    ExpectedlblError = new Text("Please fix all error and try again later.");
	    ExpectedlblErrorCode = new Text(" ");
	    ExpectedlblErrorDuration = new Text("Duration must contain only numbers above 0 (represents minutes).");
	    ExpectedlblScore = new Text("100/100");
	    ExpectedlblErrorSelected = new Text(" ");
	    ExpectedlblErrorName = new Text(" ");
	    ExpectedlblErrorSubject = new Text(" ");
	    
	    
	    createNewExamController.getSelected(MockActionEvent);
	    ActuallblScore = createNewExamController.getLblScore();
	    ActuallblErrorSelected = createNewExamController.getLblErrorSelected();
	    //check if score label is 100/100
	    assertEquals(ExpectedlblScore.getText(), ActuallblScore.getText());
	    assertEquals(ExpectedlblErrorSelected.getText(), ActuallblErrorSelected.getText());
	    
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
	
	// checking if the creation of an exam is successfully created when duration value is 0
    // input: code = "A123"
    //		lecNotes = "notes"
    //		studNotes = "notes"
    //		name = "Algebra_TermA_2023"
    //		subject = "Software Engineering"
    //		duration = "0"
	//		QuestionForExam(question1, "50")
	//		QuestionForExam(question2, "50")
    // expected: all labels are empty (= " ") besides:
	//			ExpectedlblError = "Please fix all error and try again later."
	//			ExpectedlblScore = "100/100"
	//			ExpectedlblErrorDuration = "Duration must contain only numbers above 0 (represents minutes)."
	@Test
	void createExam_DurationIsZero() {
		//setup
	    code = "A123";
	    lecNotes = "notes";
	    studNotes = "notes";
	    name = "Algebra_TermA_2023";
	    subject = "Software Engineering";
	    duration = "0";
	    
	    selectedItems.add(new QuestionForExam(question1, "50"));
	    selectedItems.add(new QuestionForExam(question2, "50"));
	    obsSelectedItems =FXCollections.observableArrayList(selectedItems);
	    
	    resultSet = resultSet2;
	    ExpectedlblError = new Text("Please fix all error and try again later.");
	    ExpectedlblErrorCode = new Text(" ");
	    ExpectedlblErrorDuration = new Text("Duration must contain only numbers above 0 (represents minutes).");
	    ExpectedlblScore = new Text("100/100");
	    ExpectedlblErrorSelected = new Text(" ");
	    ExpectedlblErrorName = new Text(" ");
	    ExpectedlblErrorSubject = new Text(" ");
	    
	    
	    createNewExamController.getSelected(MockActionEvent);
	    ActuallblScore = createNewExamController.getLblScore();
	    ActuallblErrorSelected = createNewExamController.getLblErrorSelected();
	    //check if score label is 100/100
	    assertEquals(ExpectedlblScore.getText(), ActuallblScore.getText());
	    assertEquals(ExpectedlblErrorSelected.getText(), ActuallblErrorSelected.getText());
	    
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
	
	// checking if the creation of an exam is successfully created when the notes fields (lecture and student notes) are empty
    // input: code = "A123"
    //		lecNotes = ""
    //		studNotes = ""
    //		name = "Algebra_TermA_2023"
    //		subject = "Software Engineering"
    //		duration = "120"
	//		QuestionForExam(question1, "60")
	//		QuestionForExam(question2, "40")
    // expected: all labels are empty (= " ") besides:
	//			ExpectedlblError = "Exam uploaded successfully"
	//			ExpectedlblScore = "100/100"
	//			ExpectedlblScore = "0/100"
	@Test
	void createExam_NotesFieledsAreEmpty_Success() {
		//setup
	    code = "A123";
	    lecNotes = "";
	    studNotes = "";
	    name = "Algebra_TermA_2023";
	    subject = "Software Engineering";
	    duration = "120";
	    
	    selectedItems.add(new QuestionForExam(question1, "60"));
	    selectedItems.add(new QuestionForExam(question2, "40"));
	    obsSelectedItems =FXCollections.observableArrayList(selectedItems);
	    
	    resultSet = resultSet2;
	    ExpectedlblError = new Text("Exam uploaded successfully");
	    ExpectedlblErrorCode = new Text(" ");
	    ExpectedlblErrorDuration = new Text(" ");
	    ExpectedlblScore = new Text("100/100");
	    ExpectedlblErrorSelected = new Text(" ");
	    ExpectedlblErrorName = new Text(" ");
	    ExpectedlblErrorSubject = new Text(" ");
	    
	    
	    createNewExamController.getSelected(MockActionEvent);
	    ActuallblScore = createNewExamController.getLblScore();
	    //check if score label is 100/100
	    assertEquals(ExpectedlblScore.getText(), ActuallblScore.getText());
	    ExpectedlblScore = new Text("0/100");
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
	
	// checking if the creation of an exam is successfully created when question score is not a number
    // input: code = "A123"
    //		lecNotes = "notes"
    //		studNotes = "notes"
    //		name = "Algebra_TermA_2023"
    //		subject = "Software Engineering"
    //		duration = "120"
	//		QuestionForExam(question1, "!!")
	//		QuestionForExam(question2, "50")
    // expected: all labels are empty (= " ") besides:
	//			ExpectedlblError = "Please fix all error and try again later."
	//			ExpectedlblScore = "0/100"
	//			ExpectedlblErrorSelected = "One of the selected questions score is not a positive number, try again."
	//			ExpectedlblErrorSelected = "Please select questions first."
	@Test
	public void createExam_questionScoreIsNotNumber() { 
		//setup
	    code = "A123";
	    lecNotes = "notes";
	    studNotes = "notes";
	    name = "Algebra_TermA_2023";
	    subject = "Software Engineering";
	    duration = "120";
	    
	    selectedItems.add(new QuestionForExam(question1, "!!"));
	    selectedItems.add(new QuestionForExam(question2, "50"));
	    obsSelectedItems =FXCollections.observableArrayList(selectedItems);
	    
	    resultSet = resultSet2;
	    ExpectedlblError = new Text("Please fix all error and try again later.");
	    ExpectedlblErrorCode = new Text(" ");
	    ExpectedlblErrorDuration = new Text(" ");
	    ExpectedlblScore = new Text("0/100");
	    ExpectedlblErrorSelected = new Text("One of the selected questions score is not a positive number, try again.");
	    ExpectedlblErrorName = new Text(" ");
	    ExpectedlblErrorSubject = new Text(" ");
	    
	    
	    createNewExamController.getSelected(MockActionEvent);
	    ActuallblScore = createNewExamController.getLblScore();
	    ActuallblErrorSelected = createNewExamController.getLblErrorSelected();
	    //check if score label is 100/100
	    assertEquals(ExpectedlblScore.getText(), ActuallblScore.getText());
	    assertEquals(ExpectedlblErrorSelected.getText(), ActuallblErrorSelected.getText());
	    
	    createNewExamController.getFinish(MockActionEvent);
	    ExpectedlblErrorSelected = new Text("Please select questions first.");
	    
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
	
	// checking if the creation of an exam is successfully created when none of the questions were selected
    // input: code = "A123"
    //		lecNotes = "notes"
    //		studNotes = "notes"
    //		name = "Algebra_TermA_2023"
    //		subject = "Software Engineering"
    //		duration = "120"
    // expected: all labels are empty (= " ") besides:
	//			ExpectedlblError = "Please fix all error and try again later."
	//			ExpectedlblScore = "0/100"
	//			ExpectedlblErrorSelected = "No questions were selected."
	//			ExpectedlblErrorSelected = "Please select questions first."
	@Test
	public void createExam_NoQuestionsSelected() { 
		//setup
	    code = "A123";
	    lecNotes = "notes";
	    studNotes = "notes";
	    name = "Algebra_TermA_2023";
	    subject = "Software Engineering";
	    duration = "120";
	    
	    obsSelectedItems =FXCollections.observableArrayList(selectedItems);
	    
	    resultSet = resultSet2;
	    ExpectedlblError = new Text("Please fix all error and try again later.");
	    ExpectedlblErrorCode = new Text(" ");
	    ExpectedlblErrorDuration = new Text(" ");
	    ExpectedlblScore = new Text("0/100");
	    ExpectedlblErrorSelected = new Text("No questions were selected.");
	    ExpectedlblErrorName = new Text(" ");
	    ExpectedlblErrorSubject = new Text(" ");
	    
	    
	    createNewExamController.getSelected(MockActionEvent);
	    ActuallblScore = createNewExamController.getLblScore();
	    ActuallblErrorSelected = createNewExamController.getLblErrorSelected();
	    //check if score label is 100/100
	    assertEquals(ExpectedlblScore.getText(), ActuallblScore.getText());
	    assertEquals(ExpectedlblErrorSelected.getText(), ActuallblErrorSelected.getText());
	    
	    createNewExamController.getFinish(MockActionEvent);
	    ExpectedlblErrorSelected = new Text("Please select questions first.");
	    
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
	
	// checking if the creation of an exam is successfully created when the button "Add Questions" has not been pressed
    // input: code = "A123"
    //		lecNotes = "notes"
    //		studNotes = "notes"
    //		name = "Algebra_TermA_2023"
    //		subject = "Software Engineering"
    //		duration = "120"
    // expected: all labels are empty (= " ") besides:
	//			ExpectedlblError = "Please fix all error and try again later."
	//			ExpectedlblScore = null
	//			ExpectedlblErrorSelected = "Please select questions first."
	@Test
	public void createExam_getFinishWithoutPressingAddQuestions() { 
		//setup
	    code = "A123";
	    lecNotes = "notes";
	    studNotes = "notes";
	    name = "Algebra_TermA_2023";
	    subject = "Software Engineering";
	    duration = "120";
	    
	    obsSelectedItems =FXCollections.observableArrayList(selectedItems);
	    
	    resultSet = resultSet2;
	    ExpectedlblError = new Text("Please fix all error and try again later.");
	    ExpectedlblErrorCode = new Text(" ");
	    ExpectedlblErrorDuration = new Text(" ");
	    //getSelected never been pressed and the label never initialized
	    ExpectedlblScore = null;
	    ExpectedlblErrorSelected = new Text("Please select questions first.");
	    ExpectedlblErrorName = new Text(" ");
	    ExpectedlblErrorSubject = new Text(" ");
	    
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
	    assertEquals(ExpectedlblScore, ActuallblScore);
	    assertEquals(ExpectedlblErrorSelected.getText(), ActuallblErrorSelected.getText());
	    assertEquals(ExpectedlblErrorName.getText(), ActuallblErrorName.getText());
	    assertEquals(ExpectedlblErrorSubject.getText(), ActuallblErrorSubject.getText());
	}
	
	// checking if the creation of an exam is successfully created when all the field are empty
    // input: code = "
    //		lecNotes = ""
    //		studNotes = ""
    //		name = ""
    //		subject = ""
    //		duration = ""
	//		QuestionForExam(question1, "50")
	//		QuestionForExam(question2, "50")
    // expected: all labels are empty (= " ") besides:
	//			ExpectedlblError = "Please fix all error and try again later."
	//			ExpectedlblScore = "100/100"
	//			ExpectedlblErrorCode = "Code must be 4 digits and contains only letters and numbers."
	//			ExpectedlblErrorDuration = "Duration must contain only numbers above 0 (represents minutes)."
	//			ExpectedlblErrorName = "You must enter an exam name"
	//			ExpectedlblErrorSubject = "You must enter a subject"
	
	@Test
	public void createExam_SelectQuestionANDallFieldesNULL() {
		//setup
	    code = null;
	    lecNotes = null;
	    studNotes = null;
	    name = null;
	    subject = null;
	    duration = null;
	    
	    selectedItems.add(new QuestionForExam(question1, null));
	    selectedItems.add(new QuestionForExam(question2, null));
	    obsSelectedItems =FXCollections.observableArrayList(selectedItems);
	    
	    resultSet = resultSet2;
	    ExpectedlblError = new Text("Please fix all error and try again later.");
	    ExpectedlblErrorCode = new Text("Code must be 4 digits and contains only letters and numbers.");
	    ExpectedlblErrorDuration = new Text("Duration must contain only numbers above 0 (represents minutes).");
	    ExpectedlblScore = new Text("0/100");
	    ExpectedlblErrorSelected = new Text("One of the selected questions score is not a positive number, try again.");
	    ExpectedlblErrorName = new Text("You must enter an exam name");
	    ExpectedlblErrorSubject = new Text("You must enter a subject");
	    
	    
	    createNewExamController.getSelected(MockActionEvent);
	    ActuallblScore = createNewExamController.getLblScore();
	    ActuallblErrorSelected = createNewExamController.getLblErrorSelected();
	    //check if score label is 100/100
	    assertEquals(ExpectedlblScore.getText(), ActuallblScore.getText());
	    assertEquals(ExpectedlblErrorSelected.getText(), ActuallblErrorSelected.getText());
	    
	    createNewExamController.getFinish(MockActionEvent);
	    ExpectedlblErrorSelected = new Text("Please select questions first.");

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
}
