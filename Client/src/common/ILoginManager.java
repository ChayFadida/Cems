package common;

import java.util.ArrayList;
import java.util.HashMap;

import abstractControllers.AbstractController;
import controllersClient.ChooseProfileController;
import controllersHod.HODmenuController;
import controllersLecturer.LecturerMenuController;
import controllersStudent.StudentMenuController;
import entities.Student;
import entities.User;
import javafx.event.ActionEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This Interface is to be able to use dependency injection in the testing for login
 * controller class (this interface remove the dependency of the ui elements and the server 
 * when use with stub class)
 */
public interface ILoginManager {
	public ArrayList<HashMap<String,Object>> getUserFromDB(String userName, String password);
	public String getUserName();
	public String getPassword();
	public Stage getStage();
	public void initializeCoursesLogin();
	public void setUser(User user);
	public void hideWindow(ActionEvent event);
	public LecturerMenuController LecturerMenuController();
	public StudentMenuController StudentMenuController();
	public HODmenuController HODmenuController();
	public ChooseProfileController ChooseProfileController();
}
