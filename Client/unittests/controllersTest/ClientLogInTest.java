package controllersTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.sun.jdi.connect.spi.Connection;

import client.ConnectionServer;
import common.ILoginManager;
import controllersClient.ChooseProfileController;
import controllersClient.LogInController;
import controllersHod.HODmenuController;
import controllersLecturer.LecturerMenuController;
import controllersStudent.StudentMenuController;
import entities.Hod;
import entities.Lecturer;
import entities.Student;
import entities.Super;
import entities.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;


class ClientLogInTest {
	String permission;
	Text actualErrorLabel;
	String userName,password;
	Text expectedErrorLabel;
	boolean actualLoggedIn, expectedLoggedIn;
	LogInController logInController;
	StubLogInManager stubLogInManager;
	ArrayList<User> users;
	ConnectionServer connectionServerMock;
	ArrayList<HashMap<String,Object>> rs;
	Lecturer lecturer;
	Hod hod;
	Student student;
	Super sup;
	Stage stageMock;
	ActionEvent eventMock;
	LecturerMenuController lecturerMenuMock;
	HODmenuController hodMenuMock;
	StudentMenuController studentMenuMock;
	ChooseProfileController superMenuMock;
	
	private class StubLogInManager implements ILoginManager{
		@Override
		public ArrayList<HashMap<String,Object>> getUserFromDB(String userName, String password){
			return rs;
		}
		@Override
		public String getUserName() {
			return userName;
		}

		@Override
		public String getPassword() {
			return password;
		}
		@Override
		public Stage getStage() {
			return stageMock;
		}
		@Override
		public void initializeCoursesLogin() {
			return;
		}
		@Override
		public void setUser(User user) {
			connectionServerMock.setUser(user);
		}
		@Override
		public void hideWindow(ActionEvent event) {
			return;
		}
		@Override
		public LecturerMenuController LecturerMenuController() {
			return lecturerMenuMock;
		}
		@Override
		public StudentMenuController StudentMenuController() {
			return studentMenuMock;
		}
		@Override
		public HODmenuController HODmenuController() {
			return hodMenuMock;
		}
		@Override
		public ChooseProfileController ChooseProfileController() {
			return superMenuMock;
		}

	}	
	
	@BeforeEach
	void setUp() throws Exception {
		stubLogInManager = new StubLogInManager();
		logInController = new LogInController(stubLogInManager);
		
		password = "pass";
		userName = "user";

		HashMap<String, Object> user1Map = new HashMap<>();
		user1Map.put("id", 1);
		user1Map.put("firstName", "John");
		user1Map.put("lastName", "Doe");
		user1Map.put("email", "johndoe@example.com");
		user1Map.put("position", "Lecturer");
		user1Map.put("pass", "password1");
		user1Map.put("username", "username1");
		user1Map.put("isLogged", true);
		lecturer = new Lecturer(user1Map,new HashMap<String,ArrayList<Integer>>(),1);

		HashMap<String, Object> user2Map = new HashMap<>();
		user2Map.put("id", 2);
		user2Map.put("firstName", "Jane");
		user2Map.put("lastName", "Smith");
		user2Map.put("email", "janesmith@example.com");
		user2Map.put("position", "HOD");
		user2Map.put("pass", "password2");
		user2Map.put("username", "username2");
		user2Map.put("isLogged", false);
		hod = new Hod(user2Map,1);

		HashMap<String, Object> user3Map = new HashMap<>();
		user3Map.put("id", 3);
		user3Map.put("firstName", "Michael");
		user3Map.put("lastName", "Johnson");
		user3Map.put("email", "michaeljohnson@example.com");
		user3Map.put("position", "Student");
		user3Map.put("pass", "password3");
		user3Map.put("username", "username3");
		user3Map.put("isLogged", true);
		student = new Student(user3Map,1);

		HashMap<String, Object> user4Map = new HashMap<>();
		user4Map.put("id", 4);
		user4Map.put("firstName", "Emily");
		user4Map.put("lastName", "Brown");
		user4Map.put("email", "emilybrown@example.com");
		user4Map.put("position", "Assistant");
		user4Map.put("pass", "password4");
		user4Map.put("username", "username4");
		user4Map.put("isLogged", false);
		sup = new Super(user4Map,new HashMap<String,ArrayList<Integer>>(),1);
		users = new ArrayList<>();
		users.add(lecturer);
		users.add(hod);
		users.add(student);
		users.add(sup);
		rs= new ArrayList<>();
		connectionServerMock = mock(ConnectionServer.class);
		stageMock = mock(Stage.class);
		eventMock = mock(ActionEvent.class);
		lecturerMenuMock = mock(LecturerMenuController.class);
		studentMenuMock = mock(StudentMenuController.class);
		hodMenuMock = mock(HODmenuController.class);
		superMenuMock = mock(ChooseProfileController.class);
	}

	@Test
	void logInUserSuccess() {
		doNothing().when(lecturerMenuMock).start(stageMock);
		doNothing().when(studentMenuMock).start(stageMock);
		doNothing().when(hodMenuMock).start(stageMock);
		doNothing().when(superMenuMock).start(stageMock);
		expectedErrorLabel = null;
		expectedLoggedIn = true;
		
		for (int i=0 ; i < users.size(); i++) {		
			//initialize user;
			HashMap<String,Object> row = new HashMap<>();
			row.put("access", "approve");
			row.put("response", users.get(i));
			rs.add(row);
			userName = users.get(i).getUsername();
			password = "password" + (i+1);
			when(connectionServerMock.getUser()).thenReturn(users.get(i));
			
			try {
				actualLoggedIn = logInController.getLoginBtn(eventMock);
			} catch (Exception e) {
				e.printStackTrace();
				fail();
			}
			actualErrorLabel = logInController.getLblError();
			//Assertions
			assertEquals(expectedLoggedIn, actualLoggedIn);
			assertEquals(expectedErrorLabel, actualErrorLabel);
		}
		
	}
	
	@Test
	void logInUserAlreadyLogedIn() {
		users.get(0).setIsLogged(true);
		HashMap<String,Object> row = new HashMap<>();
		row.put("access", "deny");
		row.put("response", "logged in");
		rs.add(row);
		userName = users.get(0).getUsername();
		password = "password1";
		try {
			actualLoggedIn = logInController.getLoginBtn(eventMock);
		} catch (Exception e) {
			fail();
		}
		
		actualErrorLabel = logInController.getLblError();
		expectedLoggedIn = false;
		expectedErrorLabel = new Text("This user is already logged in to the system.");		
		//Assertions
		assertEquals(expectedLoggedIn, actualLoggedIn);
		assertEquals(expectedErrorLabel.getText(), actualErrorLabel.getText());
	}
	
	@Test
	void logInUserName_NULL() {
		userName = null;
		password = "password1";
		try {
			actualLoggedIn = logInController.getLoginBtn(eventMock);
		} catch (Exception e) {
			fail();
		}
		
		actualErrorLabel = logInController.getLblError();
		expectedLoggedIn = false;
		expectedErrorLabel = new Text("One of the fields is empty, try again.");		
		//Assertions
		assertEquals(expectedLoggedIn, actualLoggedIn);
		assertEquals(expectedErrorLabel.getText(), actualErrorLabel.getText());
	}
	
	@Test
	void logInPassword_NULL() {
		userName = "username";
		password = null;
		try {
			actualLoggedIn = logInController.getLoginBtn(eventMock);
		} catch (Exception e) {
			fail();
		}
		
		actualErrorLabel = logInController.getLblError();
		expectedLoggedIn = false;
		expectedErrorLabel = new Text("One of the fields is empty, try again.");		
		//Assertions
		assertEquals(expectedLoggedIn, actualLoggedIn);
		assertEquals(expectedErrorLabel.getText(), actualErrorLabel.getText());
	}
	
	@Test
	void logInUserName_Empty() {
		userName = "";
		password = "password1";
		try {
			actualLoggedIn = logInController.getLoginBtn(eventMock);
		} catch (Exception e) {
			fail();
		}
		
		actualErrorLabel = logInController.getLblError();
		expectedLoggedIn = false;
		expectedErrorLabel = new Text("One of the fields is empty, try again.");		
		//Assertions
		assertEquals(expectedLoggedIn, actualLoggedIn);
		assertEquals(expectedErrorLabel.getText(), actualErrorLabel.getText());
	}
	
	@Test
	void logInPassword_Empty() {
		userName = "username";
		password = "";
		try {
			actualLoggedIn = logInController.getLoginBtn(eventMock);
		} catch (Exception e) {
			fail();
		}
		
		actualErrorLabel = logInController.getLblError();
		expectedLoggedIn = false;
		expectedErrorLabel = new Text("One of the fields is empty, try again.");		
		//Assertions
		assertEquals(expectedLoggedIn, actualLoggedIn);
		assertEquals(expectedErrorLabel.getText(), actualErrorLabel.getText());
	}
	
	@Test
	void logInUserNotFound() {
		HashMap<String,Object> row = new HashMap<>();
		row.put("access", "deny");
		row.put("response", "not exist");
		rs.add(row);
		userName = users.get(0).getUsername() +"1";
		password = "password1";
		try {
			actualLoggedIn = logInController.getLoginBtn(eventMock);
		} catch (Exception e) {
			fail();
		}
		
		actualErrorLabel = logInController.getLblError();
		expectedLoggedIn = false;
		expectedErrorLabel = new Text("User does not exist in the system, try again.");		
		//Assertions
		assertEquals(expectedLoggedIn, actualLoggedIn);
		assertEquals(expectedErrorLabel.getText(), actualErrorLabel.getText());
	}
	
	@Test
	void logInWrongPassword() {
		HashMap<String,Object> row = new HashMap<>();
		row.put("access", "deny");
		row.put("response", "wrong credentials");
		rs.add(row);
		userName = users.get(0).getUsername();
		password = "password";
		try {
			actualLoggedIn = logInController.getLoginBtn(eventMock);
		} catch (Exception e) {
			fail();
		}
		
		actualErrorLabel = logInController.getLblError();
		expectedLoggedIn = false;
		expectedErrorLabel = new Text("Wrong password, try again.");		
		//Assertions
		assertEquals(expectedLoggedIn, actualLoggedIn);
		assertEquals(expectedErrorLabel.getText(), actualErrorLabel.getText());
	}
	
	@Test
	void logInUserEmptyFields() {
		userName = "";
		password = "";
		try {
			actualLoggedIn = logInController.getLoginBtn(eventMock);
		} catch (Exception e) {
			fail();
		}
		
		actualErrorLabel = logInController.getLblError();
		expectedLoggedIn = false;
		expectedErrorLabel = new Text("One of the fields is empty, try again.");		
		//Assertions
		assertEquals(expectedLoggedIn, actualLoggedIn);
		assertEquals(expectedErrorLabel.getText(), actualErrorLabel.getText());
	}
}

