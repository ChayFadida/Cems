package controllersTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import common.ILoginManager;
import controllersClient.LogInController;
import entities.User;
import javafx.event.ActionEvent;
import javafx.scene.text.Text;



class ClientLogInTest {
	String permission;
	String actualErrorLabel;
	String userName,password;
	String expectedErrorLabel;
	boolean actualLoggedIn, expectedLoggedIn;
	LogInController logInController;
	StubLogInManager stubLogInManager;
	ArrayList<String> permissions, users;
	User user;
	
	private class StubLogInManager implements ILoginManager{

		@Override
		public String isValidPermission(String userName, String password) throws Exception {
			user = 
			
			
			
			return permission;
		}

		@Override
		public String getUserName() {
			return userName;
		}

		@Override
		public String getPassword() {
			return password;
		}

	}	
	
	@BeforeEach
	void setUp() throws Exception {
		stubLogInManager = new StubLogInManager();
		logInController = new LogInController(stubLogInManager);
		password = "pass";
		userName = "user";
		permissions = new ArrayList<>() {{
			new String("Lecturer");
			new String("HOD");
			new String("Student");
			new String("Super");
		}};
		
		users = new ArrayList<>() {{
			new String("lecturer");
			new String("hod");
			new String("student");
			new String("super");
		}};
	}

	@Test
	void logInUserSuccess() {	
		expectedErrorLabel = null;
		expectedLoggedIn = true;
		
		for (int i=0 ; i < users.size(); i++) {			
			userName = users.get(i);
			this.permission = permissions.get(i);
			
			try {
				actualLoggedIn = logInController.getLoginBtn(new ActionEvent());
			} catch (Exception e) {
				fail();
			}
			
			actualErrorLabel = logInController.getLblError().getText();
			//Assertions
			assertEquals(expectedLoggedIn, actualLoggedIn);
			assertEquals(expectedErrorLabel, actualErrorLabel);
		}
	}
	
	@Test
	void logInUserAlreadyLogedIn() {
		permission = "logged in";
		
		try {
			actualLoggedIn = logInController.getLoginBtn(new ActionEvent());
		} catch (Exception e) {
			fail();
		}
		
		actualErrorLabel = logInController.getLblError().getText();
		
		expectedLoggedIn = false;
		expectedErrorLabel = "This user is already logged in to the system.";
		
		//Assertions
		assertEquals(expectedLoggedIn, actualLoggedIn);
		assertEquals(expectedErrorLabel, actualErrorLabel);
	}
	
	@Test
	void logInUserEmptyUserName() {
		permission = "not exist";
		
		try {
			actualLoggedIn = logInController.getLoginBtn(new ActionEvent());
		} catch (Exception e) {
			fail();
		}
		
		actualErrorLabel = logInController.getLblError().getText();
		
		expectedLoggedIn = false;
		expectedErrorLabel = "User does not exist in the system, try again.";
		
		//Assertions
		assertEquals(expectedLoggedIn, actualLoggedIn);
		assertEquals(expectedErrorLabel, actualErrorLabel);
	}
	}
	
	@Test
	void logInUserEmptyPassword() {
		fail("Not yet implemented");
	}
	
	@Test
	void logInUserNotFound() {
		fail("Not yet implemented");
	}
	
	@Test
	void logInWrongPassword() {
		fail("Not yet implemented");
	}
	
	@Test
	void logInUserEmptyFields() {
		fail("Not yet implemented");
	}

}
