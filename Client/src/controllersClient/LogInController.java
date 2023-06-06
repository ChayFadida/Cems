package controllersClient;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import abstractControllers.AbstractController;
import client.ConnectionServer;
import controllersLecturer.LecturerMenuController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LogInController extends AbstractController{
    @FXML
    private Button LoginButton;

    @FXML
    private TextField PasswordTxt;

    @FXML
    private TextField UserNameTxt;

    @FXML
    private AnchorPane ap;

    @FXML
    private Button backButton;

    /**
	 *password getter
	 *@return String of the password
	 * */
    private String getPassword() {
		return PasswordTxt.getText();
	}
    /**
	 *userName getter
	 *@return String of the userName
	 * */
    private String getUserName() {
		return UserNameTxt.getText();
	}
    /**
	 *this method implements the login button and logs in to the relevant user premission menu.
	 *@param event
	 * */
	public void getLoginBtn(ActionEvent event) throws Exception {
		Stage primaryStage = new Stage();
		try {
			switch(isValidPermission(getUserName(),getPassword())) {
				case("Lecturer"):
					//also add entity class lecturer and send it to lecturer menu controller. so we have info of entity there.
					System.out.println("Lecturer Login Successfuly.");
					((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
					LecturerMenuController lecturerMenuController = new LecturerMenuController();	
					lecturerMenuController.start(primaryStage);
					break;
				case("Student"):
					System.out.println("Student Login Successfuly.");
					//to be implemented.
//					((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
//					StudentMenuScreenController studentMenuScreenController = new StudentMenuScreenController();	
//					studentMenuScreenController.start(primaryStage);
					
				case("HOD"):
					System.out.println("HOD Login Successfuly.");
					//to be implemented.
//					((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
//					HodMenuScreenController hodMenuScreenController = new HodMenuScreenController();	
//					hodMenuScreenController.start(primaryStage);
					
				default: 
		    		System.out.println("no such user");
			}
			
		}catch (Exception e) {
			System.out.println("Wrong input, try again");
		}	
	}
	/**
	 *this method implements the back button and moves back to the client connection screen.
	 *@param event
	 * */
	public void getBackBtn(ActionEvent event) throws Exception {			
			((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
			Stage primaryStage = new Stage();
			ConnectClientScreenController connectClientScreenController = new ConnectClientScreenController();
			connectClientScreenController.start(primaryStage);
	}
	
	
	
	//validation func of details and premmisions.- to be implemented here.
	public String isValidPermission(String userName , String password) throws Exception {
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> arr = new ArrayList<>();
		arr.add("User");
		msg.put("client", arr);
		ArrayList<String> arr1 = new ArrayList<>();
		arr1.add("loginAttempt");
		msg.put("task",arr1);
		ArrayList<String> arr2 = new ArrayList<>();
		arr2.add(password);
		arr2.add(userName);
		msg.put("details",arr2);
		sendMsgToServer(msg);
		//ConnectionServer.getInstance().handleMessageFromClientUI(msg);
		if(!ConnectionServer.rs.isEmpty()) {
			//need to flag his connected in DB
			return (String)ConnectionServer.rs.get(0).get("position");
		}
		return "No Such User";
	}
    
	/**
	 *this method launch the screen
	 *@param Stage primaryStage
	 * */
    public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/guiClient/LogIn.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/guiClient/LogIn.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
