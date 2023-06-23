package controllersClient;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import abstractControllers.AbstractController;
import client.ConnectionServer;
import common.ILoginManager;
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
	ILoginManager iLoginManager;
	
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
		
	private class LoginManager implements ILoginManager{
		
		 public ArrayList<HashMap<String,Object>> getUserFromDB(String userName, String password){
		    	HashMap<String,ArrayList<String>> msg = new HashMap<>();
				ArrayList<String> userInfo = new ArrayList<>();
				userInfo.add("User");
				msg.put("client", userInfo);
				ArrayList<String> query = new ArrayList<>();
				query.add("loginAttempt");
				msg.put("task", query);
				ArrayList<String> parameter = new ArrayList<>();
				parameter.add(password);
				parameter.add(userName);
				msg.put("details", parameter);
				sendMsgToServer(msg);
				if(ConnectionServer.rs==null) {
					return new ArrayList<>();
				}
				return ConnectionServer.rs;
		    }

		@Override
		public String getUserName() {
			return UserNameTxt.getText();
		}

		@Override
		public String getPassword() {
			return PasswordTxt.getText();
		}

		@Override
		public Stage getStage() {
			return new Stage();
		}

		@Override
		public void initializeCoursesLogin() {
			initializeCourses();
		}

		@Override
		public void setUser(User user) {
			ConnectionServer.getInstance().setUser(user);
		}

		@Override
		public void hideWindow(ActionEvent event) {
			((Node) event.getSource()).getScene().getWindow().hide();
		}

		@Override
		public LecturerMenuController LecturerMenuController() {
			return new LecturerMenuController();
		}

		@Override
		public StudentMenuController StudentMenuController() {
			// TODO Auto-generated method stub
			return new StudentMenuController();
		}

		@Override
		public HODmenuController HODmenuController() {
			// TODO Auto-generated method stub
			return new HODmenuController();
		}

		@Override
		public ChooseProfileController ChooseProfileController() {
			// TODO Auto-generated method stub
			try {
				return new ChooseProfileController();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
	}
	    
	public LogInController(ILoginManager iLoginManager) {
		this.iLoginManager = iLoginManager;
	}
	
	public LogInController() {
		this.iLoginManager = new LoginManager();
	}

//    /**
//	 *password getter
//	 *@return String of the password
//	 * */
//    public String getPassword() {
//		return PasswordTxt.getText();
//	}
//    
//    /**
//	 *userName getter
//	 *@return String of the userName
//	 * */
//    public String getUserName() {
//		return UserNameTxt.getText();
//	}
    
    /**
	 *lblError getter
	 *@return String of the lblError
	 * */
	public Text getLblError() {
		return lblError;
	}
    
    /**
	 *this method implements the login button and logs in to the relevant user premission menu.
	 *@param event
	 * */
	public Boolean getLoginBtn(ActionEvent event) throws Exception {
		Stage primaryStage = iLoginManager.getStage();
		try {
			switch(isValidPermission(iLoginManager.getUserName(), iLoginManager.getPassword())) {
				case("Lecturer"):
					System.out.println("Lecturer Login Successfuly.");
					iLoginManager.hideWindow(event);
					LecturerMenuController lecturerMenuController = iLoginManager.LecturerMenuController();	
					lecturerMenuController.start(primaryStage);
					return true;
					
				case("Student"):
					System.out.println("Student Login Successfuly.");
					iLoginManager.hideWindow(event);					
					StudentMenuController studentMenuController = iLoginManager.StudentMenuController();	
					studentMenuController.start(primaryStage);
					return true;
					
				case("HOD"):
					System.out.println("HOD Login Successfuly.");
					iLoginManager.hideWindow(event);					
					HODmenuController hodMenuController = iLoginManager.HODmenuController();	
					hodMenuController.start(primaryStage);
					return true;
					
				case "Super":
					System.out.println("Super Login Successfuly.");
					iLoginManager.hideWindow(event);	 //hiding primary window
					ChooseProfileController chooseProfileController = iLoginManager.ChooseProfileController();	
					chooseProfileController.start(primaryStage);
					return true;
					
				case "logged in":
					System.out.println("User is already logged in");
					if(lblError==null)
						lblError=new Text();
					lblError.setText("This user is already logged in to the system.");
					return false;
					
				case "not exist":
					System.out.println("User does not exist");
					if(lblError==null)
						lblError=new Text();
					lblError.setText("User does not exist in the system, try again.");
					return false;
					
				case "wrong credentials":
					System.out.println("Wrong password, try again.");
					if(lblError==null)
						lblError=new Text();
					lblError.setText("Wrong password, try again.");
					return false;
				case "empty field": //fixed bug for null and empty string
					System.out.println("One of the fields is empty");
					if(lblError==null)
						lblError=new Text();
					lblError.setText("One of the fields is empty, try again.");
					return false;
				default: 
		    		System.out.println("no such user");
					return false;
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Wrong input, try again");
			return false;
		}	
	}
	

	/**
	 *this method implements the back button and moves back to the client connection screen.
	 *@param event
	 * */
	public void getBackBtn(ActionEvent event) throws Exception {	
		ConnectionServer.getInstance().quit();
		ConnectionServer.resetInstance();
		((Node) event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		ConnectClientScreenController connectClientScreenController = new ConnectClientScreenController();
		connectClientScreenController.start(primaryStage);
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
    
    public String isValidPermission(String userName, String password) throws Exception {
    	//fixed bug for null and empty string
    	if(userName==null || password==null ||userName=="" ||password=="") {
    		return "empty field";
    	}
    	ArrayList<HashMap<String,Object>> rs = iLoginManager.getUserFromDB(userName, password);
		if(!rs.isEmpty()) {
			HashMap<String,Object> rsHM = rs.get(0);
			switch ((String) rsHM.get("access")){
				case "approve":
					User user = (User) rsHM.get("response");
					iLoginManager.initializeCoursesLogin();
					if(user instanceof Lecturer) {
						iLoginManager.setUser((Lecturer) user);
						return "Lecturer";
					}
					else if (user instanceof Hod) {
						iLoginManager.setUser((Hod) user);
						return "HOD";
					}
					else if (user instanceof Student) {
						iLoginManager.setUser((Student) user);
						return "Student";
					}
					else {
						iLoginManager.setUser((Super) user);
						return "Super";
					}
					
				case "deny":
					return (String) rsHM.get("response");
			}
		}
		return "No Such User";
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
