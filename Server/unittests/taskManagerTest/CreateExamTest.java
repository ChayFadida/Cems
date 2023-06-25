package taskManagerTest;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import DataBase.DBController;
import taskManager.TaskHandler;
import taskManager.TaskHandlerFactory;
import thirdPart.JsonHandler;
import entities.Question;
import java.math.BigInteger;

class CreateExamTest {

	 private TaskHandler taskHandler;
	 private ArrayList<Object> task;
	 private ArrayList<Object> param;
	 private static HashMap<String,String> dbinfo;
	 private HashMap<String,ArrayList<Object>> hm;
	 private static DBController dBController;
	 private ArrayList<Object> parameter;
	 private static ArrayList<Object> questions = new ArrayList<>();
	 private static Question question1 = new Question(1, "What is the capital of France?", "Paris", 1, "Geography", "{'A': 'Paris', 'B': 'London', 'C': 'Berlin', 'D': 'Rome'}", "Some notes for question 1", "Course A, Course B");
	 private static Question question2 = new Question(2, "Who painted the Mona Lisa?", "Leonardo da Vinci", 2, "Art", "{'A': 'Pablo Picasso', 'B': 'Leonardo da Vinci', 'C': 'Vincent van Gogh', 'D': 'Michelangelo'}", "Some notes for question 2", "Course C");
	 private static Question question3 = new Question(3, "What is the largest planet in our solar system?", "Jupiter", 1, "Astronomy", "{'A': 'Mars', 'B': 'Saturn', 'C': 'Jupiter', 'D': 'Neptune'}", "Some notes for question 3", "Course A, Course D");
	 private static Question question4 = new Question(4, "Who wrote the play 'Romeo and Juliet'?", "William Shakespeare", 3, "Literature", "{'A': 'William Shakespeare', 'B': 'Jane Austen', 'C': 'Charles Dickens', 'D': 'F. Scott Fitzgerald'}", "Some notes for question 4", "Course E");
	 private static Question question5 = new Question(5, "What is the chemical symbol for gold?", "Au", 2, "Chemistry", "{'A': 'Fe', 'B': 'Au', 'C': 'Ag', 'D': 'Cu'}", "Some notes for question 5", "Course F");
	 private static Question question6 = new Question(6, "Which country won the FIFA World Cup in 2018?", "France", 1, "Sports", "{'A': 'Germany', 'B': 'Brazil', 'C': 'France', 'D': 'Argentina'}", "Some notes for question 6", "Course G");
	 private static Question question7 = new Question(7, "What is the square root of 64?", "8", 3, "Mathematics", "{'A': '4', 'B': '6', 'C': '8', 'D': '10'}", "Some notes for question 7", "Course H");
	 private static Question question8 = new Question(8, "Who discovered the theory of relativity?", "Albert Einstein", 2, "Physics", "{'A': 'Isaac Newton', 'B': 'Marie Curie', 'C': 'Albert Einstein', 'D': 'Nikola Tesla'}", "Some notes for question 8", "Course I");
	 private static Question question9 = new Question(9, "What is the largest ocean in the world?", "Pacific Ocean", 1, "Geography", "{'A': 'Atlantic Ocean', 'B': 'Indian Ocean', 'C': 'Arctic Ocean', 'D': 'Pacific Ocean'}", "Some notes for question 9", "Course A, Course J");
	 private static Question question10 = new Question(10, "Who painted the Sistine Chapel ceiling?", "Michelangelo", 3, "Art", "{'A': 'Leonardo da Vinci', 'B': 'Pablo Picasso', 'C': 'Vincent van Gogh', 'D': 'Michelangelo'}", "Some notes for question 10", "Course K");
	 private static ArrayList<Integer> questionsId = new ArrayList<>();
	 private static ArrayList<Integer> questionsScore = new ArrayList<>();
	 
	@BeforeAll
	static void setUp() throws Exception {
		// connect to DB
		dBController = DBController.getInstance();
		dBController.setDbDriver();
		dbinfo = new HashMap<String,String>();
		
		// set db info
		dbinfo.put("ip", "localhost");
		dbinfo.put("password", "EyalMySql"); // insert your own my sql password
		dbinfo.put("username", "root");// insert your own my sql username
		dbinfo.put("scheme", "sys");
		dBController.setDbInfo(dbinfo);
		dBController.connectToDb();
		
		// create question array
		questions.add(question1);
		questions.add(question2);
		questions.add(question3);
		questions.add(question4);
		questions.add(question5);
		questions.add(question6);
		questions.add(question7);
		questions.add(question8);
		questions.add(question9);
		questions.add(question10);
		
		// create question id array
		questionsId.add(1);
		questionsId.add(2);
		questionsId.add(3);
		questionsId.add(4);
		questionsId.add(5);
		questionsId.add(6);
		questionsId.add(7);
		questionsId.add(8);
		
		// create question score array
		questionsScore.add(30);
		questionsScore.add(25);
		questionsScore.add(5);
		questionsScore.add(10);
		questionsScore.add(10);
		questionsScore.add(10);
		questionsScore.add(5);
		questionsScore.add(5);


	}
	
	@BeforeEach
	void setUpEach() throws Exception {
		
		// set correct task handler and objects for call to server side
	    TaskHandlerFactory.getInstance();
		taskHandler = TaskHandlerFactory.getTaskHandler().get("Lecturer");
		hm = new HashMap<String,ArrayList<Object>>();
		task = new ArrayList<Object>();
		parameter = new ArrayList<>();

		hm.put("task", task);
		hm.put("param", param);
		hm.put("questions", questions);
		// set all users to be logged off in db
		try {
		dBController.updateQueries("UPDATE users SET isLogged = 0;");
		} catch (Exception e) {	e.printStackTrace();}
	}
	
	// checking creation of new valid exam is working.
    // input: courseId, subject, duration, lecNotes, studNotes, userId, code.
    // expected: creation of new exam and upload new row that representing exam to db.
	@Test
	void insertExam_insertExamToDbSuccess() {
		// set param to new exam
		ArrayList<HashMap<String,Object>> expected = new ArrayList<HashMap<String,Object>>();
		HashMap<String,Object> expectedhm = new HashMap<String,Object>();
        String courseId = "5";
        String subject = "test subject";
        String duration = "180";
        String lecNotes = "test lec note";
        String studNotes = "test stud note";
        String userId = "1";
        String code = "tst1";
        String examCount = "5";
        String bankId = "1";
        String name = "exam Test Name";

        // set expected exam values
		expectedhm.put("examName", name);
		expectedhm.put("courseId", Integer.valueOf(courseId));
		expectedhm.put("subject", subject);
		expectedhm.put("duration", Integer.valueOf(duration));
		expectedhm.put("lecturerNote", lecNotes);
		expectedhm.put("studentNote", studNotes);
		expectedhm.put("composerId", Integer.valueOf(userId));
		expectedhm.put("code", code);
		expectedhm.put("examNum", examCount);
		expectedhm.put("bankId", Integer.valueOf(bankId));
		expectedhm.put("isLocked", 0);
	
		//set-up - client message.
	    task.add("insertExam");	    
		parameter.add(courseId);
		parameter.add(subject);
		parameter.add(duration);
		parameter.add(lecNotes);
		parameter.add(studNotes);
		parameter.add(bankId);
		parameter.add(code);
		parameter.add(examCount);
		parameter.add(userId);
		parameter.add(name);
		hm.put("param", parameter);
		hm.put("questions", questions);
		expected.add(expectedhm);
		
		// get new id of the new exam
		BigInteger newExamId = (BigInteger) taskHandler.executeUserCommand(hm).get(0).get("id");
		task.clear();
		ArrayList<Object> newExamIdArr = new ArrayList<>();
		newExamIdArr.add(newExamId.toString());
		hm.put("param", newExamIdArr);
		task.add("getExamsById");
		
		// get the new row that we just insert
		ArrayList<HashMap<String, Object>> serverResult = taskHandler.executeUserCommand(hm);
		serverResult.get(0).remove("examId");
		serverResult.get(0).remove("examFile");
		// assetion
		assertEquals(serverResult, expected);
		
		//deleting exam from db
		task.clear();
		task.add("deleteExam");
		parameter.clear();
		parameter.add(newExamId);
		taskHandler.executeUserCommand(hm);
	}
	
	
	// checking creation of exam with null string parameter for all parameter interativly.
    // input: courseId, subject, duration, lecNotes, studNotes, userId, code.
    // expected: exception thrown for each iteration.
	@Test
	void insertExam_nullExamParameter_fail() {
        String courseId = "5";
        String subject = "test subject";
        String duration = "180";
        String lecNotes = "test lec note";
        String studNotes = "test stud note";
        String userId = "1";
        String code = "tst1";
        String examCount = "5";
        String bankId = "1";
        String name = "exam Test Name";
        
		//set-up - client message.
	    task.add("insertExam");	    
		parameter.add(courseId);
		parameter.add(subject);
		parameter.add(duration);
		parameter.add(lecNotes);
		parameter.add(studNotes);
		parameter.add(bankId);
		parameter.add(code);
		parameter.add(examCount);
		parameter.add(userId);
		parameter.add(name);
		hm.put("param", parameter);
		hm.put("questions", questions);
		
		// iterate over all parameters and in each iteration initialize one param to null
		for (int i = 0 ; i < 10 ; i++) {
			Object Obj = parameter.get(i);
			parameter.remove(i);
			parameter.add(i, null);
			try {
				// act
				taskHandler.executeUserCommand(hm).get(0).get("id");
				fail("did not throw exception");
			} catch(Exception e){
				//assertion
				assertTrue(true);
				parameter.add(i, Obj);
			}
		}
	}
	
	// checking creation of exam with null array of questions.
    // input: courseId, subject, duration, lecNotes, studNotes, userId, code, null question array.
    // expected: exception thrown.
	@Test
	void insertExam_nullQuestionArray_fail() {
        String courseId = "5";
        String subject = "test subject";
        String duration = "180";
        String lecNotes = "test lec note";
        String studNotes = "test stud note";
        String userId = "1";
        String code = "tst1";
        String examCount = "5";
        String bankId = "1";
        String name = "exam Test Name";
        
		//set-up - client message.
	    task.add("insertExam");	    
		parameter.add(courseId);
		parameter.add(subject);
		parameter.add(duration);
		parameter.add(lecNotes);
		parameter.add(studNotes);
		parameter.add(bankId);
		parameter.add(code);
		parameter.add(examCount);
		parameter.add(userId);
		parameter.add(name);
		hm.put("param", parameter);
		hm.put("questions", null);
		// act
		try {
			taskHandler.executeUserCommand(hm).get(0).get("id");
			fail("did not throw exception");
		} catch(Exception e){
			//assertion
			assertTrue(true);
		}
	}
	
	// checking creation of exam with empty string parameter for all parameter interativly.
    // input: courseId, subject, duration, lecNotes, studNotes, userId, code.
    // expected: exception thrown for each iteration.
	@Test
	void insertExam_emptyStringParamToCommandEachTime_fail() {
        String courseId = "5";
        String subject = "test subject";
        String duration = "180";
        String lecNotes = "test lec note";
        String studNotes = "test stud note";
        String userId = "1";
        String code = "tst1";
        String examCount = "5";
        String bankId = "1";
        String name = "exam Test Name";
        
		//set-up - client message.
	    task.add("insertExam");	    
		parameter.add(courseId);
		parameter.add(subject);
		parameter.add(duration);
		parameter.add(lecNotes);
		parameter.add(studNotes);
		parameter.add(bankId);
		parameter.add(code);
		parameter.add(examCount);
		parameter.add(userId);
		parameter.add(name);
		hm.put("param", parameter);
		hm.put("questions", questions);
		
		// act iterate over all parameters and in each iteration initialize one param to empty string
		for (int i = 0 ; i < 10 ; i++) {
			Object Obj = parameter.get(i);
			parameter.remove(i);
			parameter.add(i, "");
			try {
				taskHandler.executeUserCommand(hm).get(0).get("id");
				fail("did not throw exception");
			} catch(Exception e){
				// assertion
				assertTrue(true);
				parameter.add(i, Obj);
			}
		}
	}
	
	// checking add questions and score successfully to questions in exam table.
    // input: courseId, subject, duration, lecNotes, studNotes, userId, code.
    // expected: affected rows = 1.
	@Test
	void addQuestionsAndScore_validInputForUser_success() {
		// set up new exam
		ArrayList<HashMap<String, Object>> expected = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> expectedhm = new HashMap<String, Object>();
		expectedhm.put("affectedRows", 1);
		expected.add(expectedhm);
        String courseId = "5";
        String subject = "test subject";
        String duration = "180";
        String lecNotes = "test lec note";
        String studNotes = "test stud note";
        String userId = "1";
        String code = "tst1";
        String examCount = "5";
        String bankId = "1";
        String name = "exam Test Name";
	    task.add("insertExam");	    
		parameter.add(courseId);
		parameter.add(subject);
		parameter.add(duration);
		parameter.add(lecNotes);
		parameter.add(studNotes);
		parameter.add(bankId);
		parameter.add(code);
		parameter.add(examCount);
		parameter.add(userId);
		parameter.add(name);
		hm.put("param", parameter);
		hm.put("questions", questions);
		
		// create new exam in db
		BigInteger newExamId = (BigInteger) taskHandler.executeUserCommand(hm).get(0).get("id");
		task.clear();
		parameter.clear();
		questions.clear();
		
		// initiallize new server call to add question and score that related to this exam
		HashMap<String,ArrayList<Integer>> questionsIdHm = new HashMap<>();
		HashMap<String,ArrayList<Integer>> qScoresHm = new HashMap<>();
		questionsIdHm.put("questions", questionsId);
		qScoresHm.put("scores", questionsScore);
		String qIdsStr = JsonHandler.convertHashMapToJson(questionsIdHm, String.class, ArrayList.class);
		String qScoresStr = JsonHandler.convertHashMapToJson(qScoresHm, String.class, ArrayList.class);

		parameter.add(newExamId);
		parameter.add(qIdsStr);
		parameter.add(qScoresStr);
	    task.add("insertQuestionsForExam");	    
		hm.put("param", parameter);
		hm.put("task", task);
		//act
		ArrayList<HashMap<String, Object>> serverResult = taskHandler.executeUserCommand(hm);
		// assertion
		assertEquals(serverResult, expected);
		
		//deleting exam from db
		task.clear();
		task.add("deleteExam");
		parameter.clear();
		parameter.add(newExamId);
		taskHandler.executeUserCommand(hm);
	}
	
	// checking add questions with both null as params to the server side.
    // input: courseId, subject, duration, lecNotes, studNotes, userId, code.
    // expected: affected rows = 0.
	@Test
	void addQuestionsAndScore_nullQuestionIdArrayAndQuesionScoreArray_success() {
		// set up new exam
		ArrayList<HashMap<String, Object>> expected = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> expectedhm = new HashMap<String, Object>();
		expectedhm.put("affectedRows", 1);
		expected.add(expectedhm);
        String courseId = "5";
        String subject = "test subject";
        String duration = "180";
        String lecNotes = "test lec note";
        String studNotes = "test stud note";
        String userId = "1";
        String code = "tst1";
        String examCount = "5";
        String bankId = "1";
        String name = "exam Test Name";
	    task.add("insertExam");	    
		parameter.add(courseId);
		parameter.add(subject);
		parameter.add(duration);
		parameter.add(lecNotes);
		parameter.add(studNotes);
		parameter.add(bankId);
		parameter.add(code);
		parameter.add(examCount);
		parameter.add(userId);
		parameter.add(name);
		hm.put("param", parameter);
		hm.put("questions", questions);
		
		// create new exam in db
		BigInteger newExamId = (BigInteger) taskHandler.executeUserCommand(hm).get(0).get("id");
		task.clear();
		parameter.clear();
		questions.clear();
		
		// initiallize new server call to add question and score that related to this exam with two null params
		parameter.add(newExamId);
		parameter.add(null);
		parameter.add(null);
	    task.add("insertQuestionsForExam");	    
		hm.put("param", parameter);
		hm.put("task", task);
		//act
		ArrayList<HashMap<String, Object>> serverResult = taskHandler.executeUserCommand(hm);
		
		//assertion
		assertEquals(null, serverResult);
			
		//deleting exam from db
		task.clear();
		task.add("deleteExam");
		parameter.clear();
		parameter.add(newExamId);
		taskHandler.executeUserCommand(hm);
	}
	
	// checking add questions with one null param to the server side the null param is questionId.
    // input: courseId, subject, duration, lecNotes, studNotes, userId, code.
    // expected: return null from server.
	@Test
	void addQuestionsAndScore_nullQuestionOnlyIdArray_fail() {
		// set up new exam
        String courseId = "5";
        String subject = "test subject";
        String duration = "180";
        String lecNotes = "test lec note";
        String studNotes = "test stud note";
        String userId = "1";
        String code = "tst1";
        String examCount = "5";
        String bankId = "1";
        String name = "exam Test Name";
	    task.add("insertExam");	    
		parameter.add(courseId);
		parameter.add(subject);
		parameter.add(duration);
		parameter.add(lecNotes);
		parameter.add(studNotes);
		parameter.add(bankId);
		parameter.add(code);
		parameter.add(examCount);
		parameter.add(userId);
		parameter.add(name);
		hm.put("param", parameter);
		hm.put("questions", questions);
		// create new exam in db
		BigInteger newExamId = (BigInteger) taskHandler.executeUserCommand(hm).get(0).get("id");
		task.clear();
		parameter.clear();
		questions.clear();
		
		// initiallize new server call to add question and score that related to this exam with one null params
		HashMap<String,ArrayList<Integer>> qScoresHm = new HashMap<>();
		qScoresHm.put("scores", questionsScore);
		String qScoresStr = JsonHandler.convertHashMapToJson(qScoresHm, String.class, ArrayList.class);
		parameter.add(newExamId);
		parameter.add(null);
		parameter.add(qScoresStr);
	    task.add("insertQuestionsForExam");	    
		hm.put("param", parameter);
		hm.put("task", task);
		// act
		ArrayList<HashMap<String, Object>> serverResult = taskHandler.executeUserCommand(hm);
		// assertion
		assertEquals(null, serverResult);
			
		//deleting exam from db
		task.clear();
		task.add("deleteExam");
		parameter.clear();
		parameter.add(newExamId);
		taskHandler.executeUserCommand(hm);
	}
	
	// checking add questions with one null param to the server side the null param is question scores.
    // input: courseId, subject, duration, lecNotes, studNotes, userId, code.
    // expected: return null from server.
	@Test
	void addQuestionsAndScore_nullQuesionScoreArray_fail() {
		
		// set up new exam
        String courseId = "5";
        String subject = "test subject";
        String duration = "180";
        String lecNotes = "test lec note";
        String studNotes = "test stud note";
        String userId = "1";
        String code = "tst1";
        String examCount = "5";
        String bankId = "1";
        String name = "exam Test Name";
	    task.add("insertExam");	    
		parameter.add(courseId);
		parameter.add(subject);
		parameter.add(duration);
		parameter.add(lecNotes);
		parameter.add(studNotes);
		parameter.add(bankId);
		parameter.add(code);
		parameter.add(examCount);
		parameter.add(userId);
		parameter.add(name);
		hm.put("param", parameter);
		hm.put("questions", questions);
		
		// create new exam in db
		BigInteger newExamId = (BigInteger) taskHandler.executeUserCommand(hm).get(0).get("id");
		task.clear();
		parameter.clear();
		questions.clear();
		
		// initiallize new server call to add question and score that related to this exam with one null params
		HashMap<String,ArrayList<Integer>> questionsIdHm = new HashMap<>();
		questionsIdHm.put("questions", questionsId);
		String qIdsStr = JsonHandler.convertHashMapToJson(questionsIdHm, String.class, ArrayList.class);
		parameter.add(newExamId);
		parameter.add(qIdsStr);
		parameter.add(null);
	    task.add("insertQuestionsForExam");	    
		hm.put("param", parameter);
		hm.put("task", task);
		// act
		ArrayList<HashMap<String, Object>> serverResult = taskHandler.executeUserCommand(hm);
		// assertion
		assertEquals(null, serverResult);
			
		//deleting exam from db
		task.clear();
		task.add("deleteExam");
		parameter.clear();
		parameter.add(newExamId);
		taskHandler.executeUserCommand(hm);
	}
}

