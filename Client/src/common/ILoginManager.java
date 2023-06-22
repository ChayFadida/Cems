package common;

import javafx.event.ActionEvent;
import javafx.scene.text.Text;

/**
 * This Interface is to be able to use dependency injection in the testing for login
 * controller class (this interface remove the dependency of the ui elements and the server 
 * when use with stub class)
 */
public interface ILoginManager {
	public String isValidPermission(String userName, String password) throws Exception;
	public String getUserName();
	public String getPassword();
}
