package controllersLecturer;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import abstractControllers.AbstractController;
import client.ConnectionServer;
import controllersClient.ConnectClientScreenController;
import controllersClient.LogInController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

//remove Application after Login implementation
public class LecturerMenuController extends AbstractController{
	private double xOffset = 0; 
	private double yOffset = 0;
	private MyQuestionBankController myQuestionBankController=null;
	private MyExamBankController myExamBankController=null;
	private CreateNewExamController createNewExamController=null;
	private ManageExamsController manageExamsController=null;
	private CheckResultController checkResultController=null;
	
	private final Glow buttonPressEffect = new Glow(0.5);
    @FXML
    private Button CheckResultButton;

    @FXML
    private Button CreateNewExamButton;

    @FXML
    private Button LogOutButton;

    @FXML
    private Button ManageExamsButton;

    @FXML
    private Button MyExambankButton;

    @FXML
    private Button MyQuestionbankButton;
    
    @FXML
    private Button closeButton;

    @FXML
    private Button minimizeButton;

    @FXML
    private AnchorPane ap;

    @FXML
    private BorderPane bp;
    
    
    /// in order to start without login dependency 
    // after Login, remove @override (start should stay), remove main
    public void start(Stage primaryStage) {
	    try {
	        BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("/guiLecturer/LecturerMenu.fxml"));
	        Scene scene = new Scene(root);
	        primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.getIcons().add(new Image("/Images/CemsIcon32-Color.png"));
	        // Set the scene to the primary stage
	        primaryStage.setScene(scene);
	        primaryStage.show();
	        root.setOnMousePressed((EventHandler<? super MouseEvent>) new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	                xOffset = event.getSceneX();
	                yOffset = event.getSceneY();
	            }
	        });
	        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	            	primaryStage.setX(event.getScreenX() - xOffset);
	            	primaryStage.setY(event.getScreenY() - yOffset);
	            }
	        });
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
	}
//    public static void main(String[] args) {
//		launch(args);
//	}
    ///END ITAMAR COMMANDS

    @FXML
    void CheckResult(MouseEvent event) {
    	loadPage("CheckResult");
    	if(checkResultController==null)
    		checkResultController= new CheckResultController();
    }

    @FXML
    void CreateNewExam(MouseEvent event) {
    	loadPage("CreateNewExam");
    	if(createNewExamController==null)
    		createNewExamController= new CreateNewExamController();
    }
    @FXML
    void getLogoutBtn(ActionEvent event) {
    	((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		LogInController logInController = new LogInController();
		logInController.start(primaryStage);
		//add functionality to turn IsLogged flag off.
    }
    
    // to be implemented later (need to change loggedIn flag to 0 and exit the system)
    @FXML
    void LogOut(MouseEvent event) {
    	System.exit(0);
    }

    @FXML
    void ManageExams(MouseEvent event) {
    	loadPage("ManageExams");
    	if(manageExamsController==null)
    		manageExamsController = new ManageExamsController();
    }

    @FXML
    void MyExambank(MouseEvent event) {
    	loadPage("MyExambank");
    	if(myExamBankController==null)
    		myExamBankController = new MyExamBankController();
    }

    @FXML
    void MyQuestionbank(MouseEvent event) {
    	loadPage("MyQuestionbank");
    	if(myQuestionBankController==null)
    		myQuestionBankController = new MyQuestionBankController();
    }
    
    @FXML
    public void initialize() {
        buttonPressEffect.setInput(LogOutButton.getEffect());
        buttonPressEffect.setInput(CheckResultButton.getEffect());
        buttonPressEffect.setInput(CreateNewExamButton.getEffect());
        buttonPressEffect.setInput(ManageExamsButton.getEffect());
        buttonPressEffect.setInput(MyExambankButton.getEffect());
        buttonPressEffect.setInput(MyQuestionbankButton.getEffect());

        LogOutButton.setOnMousePressed(this::applyButtonPressEffect);
        LogOutButton.setOnMouseReleased(this::removeButtonPressEffect);

        CheckResultButton.setOnMousePressed(this::applyButtonPressEffect);
        CheckResultButton.setOnMouseReleased(this::removeButtonPressEffect);

        CreateNewExamButton.setOnMousePressed(this::applyButtonPressEffect);
        CreateNewExamButton.setOnMouseReleased(this::removeButtonPressEffect);
        
        ManageExamsButton.setOnMousePressed(this::applyButtonPressEffect);
        ManageExamsButton.setOnMouseReleased(this::removeButtonPressEffect);
        
        MyExambankButton.setOnMousePressed(this::applyButtonPressEffect);
        MyExambankButton.setOnMouseReleased(this::removeButtonPressEffect);
        
        MyQuestionbankButton.setOnMousePressed(this::applyButtonPressEffect);
        MyQuestionbankButton.setOnMouseReleased(this::removeButtonPressEffect);
    }

    private void applyButtonPressEffect(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setEffect(buttonPressEffect);
    }

    private void removeButtonPressEffect(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setEffect(null);
    }
    
    
    public void loadPage(String page) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/guiLecturer/"+page + ".fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bp.setCenter(root);
    }
    //want to add ENTITY lecturer here to we can initiate it with the class so we 
    //have details to identify the current user of the system cuz now we cannot logg out
    
  //validation func of details and premmisions.- to be implemented here.
//  	public String isLoggedFlagOff(String userName , String password) throws Exception {
//  		HashMap<String,ArrayList<String>> msg = new HashMap<>();
//  		ArrayList<String> arr = new ArrayList<>();
//  		arr.add("User");
//  		msg.put("client", arr);
//  		ArrayList<String> arr1 = new ArrayList<>();
//  		arr1.add("logOutAttempt");
//  		msg.put("task",arr1);
//  		ArrayList<String> arr2 = new ArrayList<>();
//  		arr2.add((String)this.);
//  		arr2.add(userName);
//  		msg.put("details",arr2);
//  		sendMsgToServer(msg);
//  		//ConnectionServer.getInstance().handleMessageFromClientUI(msg);
//  		if(!ConnectionServer.rs.isEmpty()) {
//  			//need to flag his connected in DB
//  			return (String)ConnectionServer.rs.get(0).get("position");
//  		}
//  		return "No Such User";
//  	}
 
}
