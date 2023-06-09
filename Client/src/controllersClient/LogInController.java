package controllersClient;
import java.util.ArrayList;
import java.util.HashMap;

import abstractControllers.AbstractController;
import client.ConnectionServer;
import controllersHod.HODmenuController;
import controllersLecturer.LecturerMenuController;
import controllersStudent.StudentMenuController;
import entities.Hod;
import entities.Lecturer;
import entities.Student;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
    
    @FXML
    private Text lblError;

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
					//also add entity class student and send it to student menu controller. so we have info of entity there.
					System.out.println("Student Login Successfuly.");
					//to be implemented.
					((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
					StudentMenuController studentMenuController = new StudentMenuController();	
					studentMenuController.start(primaryStage);
					break;
					
				case("HOD"):
					//also add entity class HOD and send it to HOD menu controller. so we have info of entity there.
					System.out.println("HOD Login Successfuly.");
					//to be implemented.
					((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
					HODmenuController hodMenuController = new HODmenuController();	
					hodMenuController.start(primaryStage);
					break;
				case "logged in":
					System.out.println("User is already logged in");
					//add text to error label
					break;
				case "not exist":
					System.out.println("User does not exist");
					//add text to error label
					break;
				default: 
		    		System.out.println("no such user");
		    		break;
			}
			
		}catch (Exception e) {
			e.printStackTrace();
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
		super.sendMsgToServer(msg);
		//ConnectionServer.getInstance().handleMessageFromClientUI(msg);
		if(!ConnectionServer.rs.isEmpty()) {
			HashMap<String,Object> rsHM = ConnectionServer.rs.get(0);
			switch ((String)rsHM.get("access")){
				case "approve":
					User user = (User)rsHM.get("response");
					if(user instanceof Lecturer)
						return "Lecturer";
					else if (user instanceof Hod)
						return "HOD";
					else if (user instanceof Student)
						return "Student";
					return "Super";
				case "deny":
					return (String) rsHM.get("response");
			}
//			ConnectionServer.setUser((User) ConnectionServer.rs.get(0).get("response"));
//			if(ConnectionServer.getUser()==null)
//				return "Already Logged in";
//			return (String)ConnectionServer.rs.get(0).get("position");
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
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.getIcons().add(new Image("/Images/CemsIcon32-Color.png"));
			primaryStage.setScene(scene);
			primaryStage.show();
	        super.setPrimaryStage(primaryStage);
	        PressHandler<MouseEvent> press = new PressHandler<>();
	        DragHandler<MouseEvent> drag = new DragHandler<>();
	        root.setOnMousePressed(press);
	        root.setOnMouseDragged(drag);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
