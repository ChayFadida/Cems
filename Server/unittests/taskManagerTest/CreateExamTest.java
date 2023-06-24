package taskManagerTest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import DataBase.DBController;
import entities.User;
import taskManager.TaskHandler;
import taskManager.TaskHandlerFactory;
import thirdPart.ExamGenerator;
import entities.Question;

class CreateExamTest {

	 private TaskHandler taskHandler;
	 ArrayList<Object> task;
	 private ArrayList<Object> param;
	 private static HashMap<String,String> dbinfo;
	 private HashMap<String,ArrayList<Object>> hm;
	 private static DBController dBController;
	 private User expectedUser;
	 private HashMap<String,Object> userhm;
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
	 private byte[] fileBytes;
	 private Path tempDir;
	 // You can access the instances like question1, question2, etc.

	
	@BeforeAll
	static void setUp() throws Exception {
		// connect to DB
		dBController = DBController.getInstance();
		dBController.setDbDriver();
		dbinfo = new HashMap<String,String>();
		dbinfo.put("ip", "localhost");
		dbinfo.put("password", "FF8515150f");
		dbinfo.put("username", "root");
		dbinfo.put("scheme", "sys");
		dBController.setDbInfo(dbinfo);
		dBController.connectToDb();
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
	}
	
	@BeforeEach
	void setUpEach() throws Exception {
	    TaskHandlerFactory.getInstance();
		taskHandler = TaskHandlerFactory.getTaskHandler().get("User");
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
	
	void insertExam_insertManualExamToDbSuccess() {
		ArrayList<HashMap<String,Object>> expected = new ArrayList<HashMap<String,Object>>();
		HashMap<String,Object> expectedhm = new HashMap<String,Object>();
        ExamGenerator examGenerator = new ExamGenerator();
		try {
			tempDir = Files.createTempDirectory("my-temp-dir");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String dirPath = tempDir.toString();
        String filePath = dirPath + "/wordFile.doc";
        String courseId = "5";
        String subject = "test subject";
        String duration = "180";
        String lecNotes = "test lec note";
        String studNotes = "test stud note";
        String userId = "1";
        String code = "tst1";
        String examCount = "3";
        Integer bankId = 1;
        String name = "exam Test Name";
        byte[] expectedFileBytes; 
        
        examGenerator.generateExamDoc(questions, filePath, courseId, name, duration);
        try {
			fileBytes = Files.readAllBytes(Path.of(filePath));
		} catch (IOException e) {

		}
		
		expectedhm.put("examName", name);
		expectedhm.put("courseId", courseId);
		expectedhm.put("subject", subject);
		expectedhm.put("duration", duration);
		expectedhm.put("lecturerNote", lecNotes);
		expectedhm.put("studentNote", studNotes);
		expectedhm.put("composerId", userId);
		expectedhm.put("code", code);
		expectedhm.put("examNum", examCount);
		expectedhm.put("bankId", bankId);
		expectedhm.put("examFile", fileBytes);
		

		
		//set-up - client message.
	    task.add("insertExam");	    
		parameter.add("5");
		parameter.add(subject);
		parameter.add("180");
		parameter.add("test lec note");
		parameter.add("test stud note");
		parameter.add("1");
		parameter.add("tst1");
		parameter.add("5");
		parameter.add(1);
		parameter.add("exam Test Name");
		hm.put("questions", questions);
		ArrayList<HashMap<String, Object>> serverResult = taskHandler.executeUserCommand(hm);
		
		assertEquals(serverResult, expected);

	}
}
