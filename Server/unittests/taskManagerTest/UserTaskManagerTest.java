package taskManagerTest;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import DataBase.DBController;
import entities.Hod;
import entities.Lecturer;
import entities.Student;
import entities.Super;
import entities.User;
import taskManager.TaskHandler;
import taskManager.TaskHandlerFactory;

class UserTaskManagerTest {
	 private TaskHandler taskHandler;
//	 private HashMap<String, Object> sampleResult;
//	 private UserTaskManager userTaskManager;
	 private ArrayList<String> task , details;
	 private static HashMap<String,String> dbinfo;
	 private HashMap<String,ArrayList<String>> hm;
	 private static DBController dBController;
	 private User expectedUser;
	 private HashMap<String,Object> userhm;
	 
	
	
	@BeforeAll
	static void setUp() throws Exception {
		// connect to DB
		dBController = DBController.getInstance();
		dBController.setDbDriver();
		dbinfo = new HashMap<String,String>();
		dbinfo.put("ip", "localhost");
		dbinfo.put("password", "EyalMySql");
		dbinfo.put("username", "root");
		dbinfo.put("scheme", "sys");
		dBController.setDbInfo(dbinfo);
		dBController.connectToDb();
	}
	
	@BeforeEach
	void setUpEach() throws Exception {
	    TaskHandlerFactory.getInstance();
		taskHandler = TaskHandlerFactory.getTaskHandler().get("User");
		hm = new HashMap<String,ArrayList<String>>();
		task = new ArrayList<String>();
		details = new ArrayList<String>();
		hm.put("task", task);
		hm.put("details", details);

		// set all users to be logged off in db
		try {
		dBController.updateQueries("UPDATE users SET isLogged = 0;");
		} catch (Exception e) {	e.printStackTrace();}
	}

	// checking if getting a non existing user arraylist from the server..
    // input: task-login attempt , details - wrong username, random password.
    // expected: arraylist of hashmap of string and object of denied access to non existing user.
	@Test
	void executeUserCommand_loginAttempt_nonExistUser_FailLogin() {
	    //set-up - expected.
		ArrayList<HashMap<String,Object>> expected = new ArrayList<HashMap<String,Object>>();
		HashMap<String,Object> expectedhm = new HashMap<String,Object>();
		expectedhm.put("access","deny");
	    expectedhm.put("response", "not exist");
	    expected.add(expectedhm);
	    //set-up - client message.
	    task.add("loginAttempt");
	    details.add("password1"); 
	    details.add("mjordan1"); // this user doesnt exist in the DB.
	    //act
	    ArrayList<HashMap<String, Object>> serverResult = taskHandler.executeUserCommand(hm);
		//assertion
	    assertEquals(expected,serverResult);
	}
	// checking if able to make graph with empty nodes array.
    // input: result set with 0 nodes.
    // expected: success building a graph
	@Test
	void executeUserCommand_loginAttempt_WrongPassword_FailLogin() {
	    //set-up - expected.
		ArrayList<HashMap<String,Object>> expected = new ArrayList<HashMap<String,Object>>();
		HashMap<String,Object> expectedhm = new HashMap<String,Object>();
		expectedhm.put("access","deny");
		expectedhm.put("response", "wrong credentials");
		expected.add(expectedhm);
		//set-up - client message.
		task.add("loginAttempt");
		details.add("password0"); 
		details.add("mjordan"); // this user doesnt exist in the DB.
		//act
		ArrayList<HashMap<String, Object>> serverResult = taskHandler.executeUserCommand(hm);
		//assertion
		assertEquals(expected,serverResult);
	}	
	
	// checking if able to make graph with empty nodes array.
    // input: result set with 0 nodes.
    // expected: success building a graph
	@Test
	void executeUserCommand_loginAttempt_alreadyLoggedin_FailLogin() {
	    //set-up - expected.
		ArrayList<HashMap<String,Object>> expected = new ArrayList<HashMap<String,Object>>();
		HashMap<String,Object> expectedhm = new HashMap<String,Object>();
		expectedhm.put("access","deny");
		expectedhm.put("response", "logged in");
		expected.add(expectedhm);
		//set-up - client message.
		task.add("loginAttempt");
		details.add("password1"); 
		details.add("mjordan"); // this user doesnt exist in the DB.
		//act
		try {
			dBController.updateQueries("UPDATE users SET isLogged = 1 WHERE id = 1 ;");//updating mjordan's isLogged to 1 on the DB.
		} catch (SQLException e) {e.printStackTrace();}
		ArrayList<HashMap<String, Object>> serverResult = taskHandler.executeUserCommand(hm);
		//assertion
		assertEquals(expected,serverResult);
	}
	
	// checking if able to make graph with empty nodes array.
    // input: result set with 0 nodes.
    // expected: success building a graph
	@Test
	void executeUserCommand_loginAttempt_emptyUsername_FailLogin() {
	    //set-up - expected.
		ArrayList<HashMap<String,Object>> expected = new ArrayList<HashMap<String,Object>>();
		HashMap<String,Object> expectedhm = new HashMap<String,Object>();
		expectedhm.put("access","deny");
		expectedhm.put("response", "not exist");
		expected.add(expectedhm);
		//set-up - client message.
		task.add("loginAttempt");
		details.add("password1"); 
		details.add(""); // this user doesnt exist in the DB.
		//act
		ArrayList<HashMap<String, Object>> serverResult = taskHandler.executeUserCommand(hm);
		//assertion
		assertEquals(expected,serverResult);
	}
	// checking if able to make graph with empty nodes array.
    // input: result set with 0 nodes.
    // expected: success building a graph
	@Test
	void executeUserCommand_loginAttempt_emptyPassword_FailLogin() {
	    //set-up - expected.
		ArrayList<HashMap<String,Object>> expected = new ArrayList<HashMap<String,Object>>();
		HashMap<String,Object> expectedhm = new HashMap<String,Object>();
		expectedhm.put("access","deny");
		expectedhm.put("response", "wrong credentials");
		expected.add(expectedhm);
		//set-up - client message.
		task.add("loginAttempt");
		details.add(""); 
		details.add("mjordan"); // this user doesnt exist in the DB.
		//act
		ArrayList<HashMap<String, Object>> serverResult = taskHandler.executeUserCommand(hm);
		//assertion
		assertEquals(expected,serverResult);
	}
	// checking if able to make graph with empty nodes array.
    // input: result set with 0 nodes.
    // expected: success building a graph
	@Test
	void executeUserCommand_loginAttempt_emptyPasswordAndUsername_FailLogin() {
	    //set-up - expected.
		ArrayList<HashMap<String,Object>> expected = new ArrayList<HashMap<String,Object>>();
		HashMap<String,Object> expectedhm = new HashMap<String,Object>();
		expectedhm.put("access","deny");
		expectedhm.put("response", "not exist");
		expected.add(expectedhm);
		//set-up - client message.
		task.add("loginAttempt");
		details.add(""); 
		details.add(""); // this user doesnt exist in the DB.
		//act
		ArrayList<HashMap<String, Object>> serverResult = taskHandler.executeUserCommand(hm);
		//assertion
		assertEquals(expected,serverResult);
	}
	// checking if able to make graph with empty nodes array.
    // input: result set with 0 nodes.
    // expected: success building a graph
	@Test
	void executeUserCommand_loginAttempt_allKindsOfnullInputs_FailLogin() {
	    //set-up - expected.
		ArrayList<HashMap<String,Object>> expected = new ArrayList<HashMap<String,Object>>();
		HashMap<String,Object> expectedhm = new HashMap<String,Object>();
		expectedhm.put("access","deny");
		expectedhm.put("response", "not exist");
		expected.add(expectedhm);
		//set-up - client message.
		task.add("loginAttempt");
		details.add("password1"); 
		details.add(null); // this user doesnt exist in the DB.
		//act
		ArrayList<HashMap<String, Object>> serverResult = taskHandler.executeUserCommand(hm);
		//assertion
		assertEquals(expected,serverResult);
		
		expected.clear();//hash cleanup
		//2nd simmilar test. for password.
		//set-up - expected.
		expectedhm.put("access","deny");
		expectedhm.put("response", "not exist");
		expected.add(expectedhm);
		//set-up - client message.
		task.add("loginAttempt");
		details.add(null); 
		details.add("mjordan"); // this user doesnt exist in the DB.
		//act
		serverResult = taskHandler.executeUserCommand(hm);
		//assertion
		assertEquals(expected,serverResult);
		
		expected.clear();//hash cleanup
		//3rd simmilar test. for password.
		//set-up - expected.
		expectedhm.put("access","deny");
		expectedhm.put("response", "not exist");
		expected.add(expectedhm);
		//set-up - client message.
		task.add("loginAttempt");
		details.add(null); 
		details.add(null); // this user doesnt exist in the DB.
		//act
		serverResult = taskHandler.executeUserCommand(hm);
		//assertion
		assertEquals(expected,serverResult);
	}
	// checking if able to make graph with empty nodes array.
    // input: result set with 0 nodes.
    // expected: success building a graph
	@Test
	void executeUserCommand_loginAttempt_StudentLogin_Success() {
	    //set-up - expected.
		ArrayList<HashMap<String,Object>> expected = new ArrayList<HashMap<String,Object>>();
		HashMap<String,Object> expectedhm = new HashMap<String,Object>();
		userhm = new HashMap<String,Object>();
		userhm.put("id",21);
		userhm.put("firstName","Lionel");
		userhm.put("lastName","Messi");
		userhm.put("email","messi@example.com");
		userhm.put("position","Student");
		userhm.put("pass","password26");
		userhm.put("username","messi2");
		userhm.put("isLogged",true);
		expectedUser = new Student(userhm,1);
		expectedhm.put("access","approve");
		expectedhm.put("response",expectedUser);
		expected.add(expectedhm);
		//set-up - client message.
		task.add("loginAttempt");
		details.add("password26");
		details.add("messi2"); // this user doesnt exist in the DB.
		//act
		ArrayList<HashMap<String, Object>> serverResult = taskHandler.executeUserCommand(hm);
		//assertion
		assertEquals(expected,serverResult);
	}
	// checking if able to make graph with empty nodes array.
    // input: result set with 0 nodes.
    // expected: success building a graph
	@Test
	void executeUserCommand_loginAttempt_LecturerLogin_Success() {
	    //set-up - expected.
		ArrayList<HashMap<String,Object>> expected = new ArrayList<HashMap<String,Object>>();
		HashMap<String,Object> expectedhm = new HashMap<String,Object>();
		userhm = new HashMap<String,Object>();
		ArrayList<Integer> coursesId = new ArrayList<Integer>();
		HashMap<String,ArrayList<Integer>> coursesIdHM = new HashMap<String,ArrayList<Integer>>(); 
		userhm.put("id",1);
		userhm.put("firstName","Michael");
		userhm.put("lastName","Jordan");
		userhm.put("email","mjordan@example.com");
		userhm.put("position","Lecturer");
		userhm.put("pass","password1");
		userhm.put("username","mjordan");
		userhm.put("isLogged",true);
		coursesId.add(4);
		coursesId.add(5);
		coursesIdHM.put("courses", coursesId);
		expectedUser = new Lecturer(userhm,coursesIdHM,1);
		expectedhm.put("access","approve");
		expectedhm.put("response",expectedUser );
		expected.add(expectedhm);
		//set-up - client message.
		task.add("loginAttempt");
		details.add("password1");
		details.add("mjordan"); // this user doesnt exist in the DB.
		//act
		ArrayList<HashMap<String, Object>> serverResult = taskHandler.executeUserCommand(hm);
		//assertion
		assertEquals(expected,serverResult);
	}
	// checking if able to make graph with empty nodes array.
    // input: result set with 0 nodes.
    // expected: success building a graph
	@Test
	void executeUserCommand_loginAttempt_HodLogin_Success() {
	    //set-up - expected.
		ArrayList<HashMap<String,Object>> expected = new ArrayList<HashMap<String,Object>>();
		HashMap<String,Object> expectedhm = new HashMap<String,Object>();
		userhm = new HashMap<String,Object>();
		userhm.put("id",91);
		userhm.put("firstName","Conor");
		userhm.put("lastName","McGregor");
		userhm.put("email","mcgregor@example.com");
		userhm.put("position","HOD");
		userhm.put("pass","password99");
		userhm.put("username","mcgregor");
		userhm.put("isLogged",true);
		expectedUser = new Hod(userhm,1);
		expectedhm.put("access","approve");
		expectedhm.put("response",expectedUser);
		expected.add(expectedhm);
		//set-up - client message.
		task.add("loginAttempt");
		details.add("password99");
		details.add("mcgregor"); // this user doesnt exist in the DB.
		//act
		ArrayList<HashMap<String, Object>> serverResult = taskHandler.executeUserCommand(hm);
		//assertion
		assertEquals(expected,serverResult);
	}
	// checking if able to make graph with empty nodes array.
    // input: result set with 0 nodes.
    // expected: success building a graph
	@Test
	void executeUserCommand_loginAttempt_SuperLogin_Success() {
	    //set-up - expected.
		ArrayList<HashMap<String,Object>> expected = new ArrayList<HashMap<String,Object>>();
		HashMap<String,Object> expectedhm = new HashMap<String,Object>();
		userhm = new HashMap<String,Object>();
		ArrayList<Integer> coursesId = new ArrayList<Integer>();
		HashMap<String,ArrayList<Integer>> coursesIdHM = new HashMap<String,ArrayList<Integer>>(); 
		userhm.put("id",98);
		userhm.put("firstName","Chay");
		userhm.put("lastName","Fadid");
		userhm.put("email","chay.fadid@yehez.com");
		userhm.put("position","Super");
		userhm.put("pass","fadid");
		userhm.put("username","chay");
		userhm.put("isLogged",true);
		coursesId.add(7);
		coursesIdHM.put("courses", coursesId);
		expectedUser = new Super(userhm,coursesIdHM,3);
		expectedhm.put("access","approve");
		expectedhm.put("response",expectedUser);
		expected.add(expectedhm);
		//set-up - client message.
		task.add("loginAttempt");
		details.add("fadid");
		details.add("chay"); // this user doesnt exist in the DB.
		//act
		ArrayList<HashMap<String, Object>> serverResult = taskHandler.executeUserCommand(hm);
		//assertion
		assertEquals(expected,serverResult);
	}
	// checking if able to make graph with empty nodes array.
    // input: result set with 0 nodes.
    // expected: success building a graph
	@Test
	void executeUserCommand_loginAttempt_nullUser_Success() {
	    //set-up - expected.
		ArrayList<HashMap<String,Object>> expected = new ArrayList<HashMap<String,Object>>();
		HashMap<String,Object> expectedhm = new HashMap<String,Object>();
		expectedhm.put("access","deny");
		expectedhm.put("response",null);
		expected.add(expectedhm);
		//set-up - client message.
		task.add("loginAttempt");
		details.add("eyal123");
		details.add("eyal1"); // this user doesnt exist in the DB.
		//act
		ArrayList<HashMap<String, Object>> serverResult = taskHandler.executeUserCommand(hm);
		//assertion
		assertEquals(expected,serverResult);
	}
	
}
