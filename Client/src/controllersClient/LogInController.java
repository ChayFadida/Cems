package controllersClient;
import java.io.IOException;
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
import entities.Super;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
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
    
    @FXML
    private Button btnExit;

    @FXML
    private Button btnMinimize;

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
					System.out.println("Lecturer Login Successfuly.");
					((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
					LecturerMenuController lecturerMenuController = new LecturerMenuController();	
					lecturerMenuController.start(primaryStage);
					break;
				case("Student"):
					System.out.println("Student Login Successfuly.");
					((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
					StudentMenuController studentMenuController = new StudentMenuController();	
					studentMenuController.start(primaryStage);
					break;
					
				case("HOD"):
					System.out.println("HOD Login Successfuly.");
					((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
					HODmenuController hodMenuController = new HODmenuController();	
					hodMenuController.start(primaryStage);
					break;
				case "Super":
					System.out.println("Super Login Successfuly.");
					((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
					ChooseProfileController chooseProfileController = new ChooseProfileController();	
					chooseProfileController.start(primaryStage);
					break;
				case "logged in":
					System.out.println("User is already logged in");
					lblError.setText("This user is already logged in to the system.");
					break;
				case "not exist":
					System.out.println("User does not exist");
					lblError.setText("User does not exist in the system, try again.");
					break;
				case "wrong credentials":
					System.out.println("User does not exist");
					lblError.setText("Wrong password, try again.");
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
			ConnectionServer.getInstance().quit();
			ConnectionServer.resetInstance();
			((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
			Stage primaryStage = new Stage();
			ConnectClientScreenController connectClientScreenController = new ConnectClientScreenController();
			connectClientScreenController.start(primaryStage);
	}
	
	
	/**
	 *this method checks if the client has a valid permission listed in the DB.
	 *@param event
	 * */
	public String isValidPermission(String userName , String password) throws Exception {
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> userInfo = new ArrayList<>();
		userInfo.add("User");
		msg.put("client", userInfo);
		ArrayList<String> query = new ArrayList<>();
		query.add("loginAttempt");
		msg.put("task",query);
		ArrayList<String> parameter = new ArrayList<>();
		parameter.add(password);
		parameter.add(userName);
		msg.put("details",parameter);
		super.sendMsgToServer(msg);
		if(!ConnectionServer.rs.isEmpty()) {
			HashMap<String,Object> rsHM = ConnectionServer.rs.get(0);
			switch ((String)rsHM.get("access")){
				case "approve":
					User user = (User)rsHM.get("response");
					initializeCourses();
					if(user instanceof Lecturer) {
						ConnectionServer.getInstance().setUser((Lecturer)user);
						return "Lecturer";
					}
					else if (user instanceof Hod) {
						ConnectionServer.getInstance().setUser((Hod)user);
						return "HOD";
					}
					else if (user instanceof Student) {
						ConnectionServer.getInstance().setUser((Student)user);
						return "Student";
					}
					else {
						ConnectionServer.getInstance().setUser((Super)user);
						return "Super";
					}
				case "deny":
					return (String) rsHM.get("response");
			}
		}
		return "No Such User";
	}
    
	/**
	 *this method launch the screen
	 *@param Stage primaryStage
	 * */
    public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/guiClient/Login.fxml"));
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
    /**
	 *this method exits the buttons the screen
	 *@param ActionEvent event
	 * */
    @FXML
    void getExitBtn(ActionEvent event) {
    	ConnectionServer.getInstance().quit();
		System.out.println("exit Academic Tool");
		System.exit(0);
    }
    /**
	 *this method minimizes the buttons the screen
	 *@param ActionEvent event
	 * */
    @FXML
    void getMinimizeBtn(ActionEvent event) {
    	Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

}
