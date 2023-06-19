package controllersStudent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import abstractControllers.AbstractController;
import abstractControllers.AbstractController.DragHandler;
import abstractControllers.AbstractController.PressHandler;
import client.ConnectionServer;
import controllersClient.AreYouSureController;
import controllersClient.LogInController;
import entities.Student;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Controller class for the student.
 * This is the main window with the menu of operations for the student.
 */
public class StudentMenuController extends AbstractController implements Initializable{
	private MyExamController myExamController;
	private TakeExamController takeExamController;
	private Student student=null;
    @FXML
    private Button LogOutButton;

    @FXML
    private Button MyExamsButton;

    @FXML
    private Button TTakeExamButton;

    @FXML
    private AnchorPane ap;

    @FXML
    private BorderPane bp;

    @FXML
    private Button exitBtn;

    @FXML
    private Button minimizeBtn;
    
    @FXML
    private Text lblHello;
    
    @FXML
    private Button btnChay;
    /**
     * Gets the id of the user who is logged to the system.
     */
    public StudentMenuController() {
    	student = (Student) ConnectionServer.getInstance().getUser();
    }
    /**
     * By activate it closed the current window.
     * @param event Action event triggerd when clicked exit button.
     */
    @FXML
    void getExitBtn(ActionEvent event) {
    	AreYouSureController areYouSureController = new AreYouSureController();
    	areYouSureController.start(new Stage());
    }
    /**
     * By activate it minimze the current window.
     * @param event Action event triggerd when clicked minimize button.
     */
    @FXML
    void getMinimizeBtn(ActionEvent event) {
    	Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    
    private final Glow buttonPressEffect = new Glow(0.5);

   /**
    * This method loads the student menu FXML page.
    * @param primaryStage the stage to load.
    */
	public void start(Stage primaryStage) {
	    try {
	        BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("/guiStudent/StudentMenu.fxml"));
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
	 * By activate this method, the user is log out from the program.
	 * @param event Mouse event triggered when pressing log out button.
	 */
    @FXML
    void LogOut(MouseEvent event) {
    	try {
			boolean res = super.logoutRequest(student);
			int id = student.getId();
			if (res) {
				student=null;
				((Stage) ((Node)event.getSource()).getScene().getWindow()).close(); //hiding primary window
				LogInController logInController = new LogInController();	
				logInController.start(new Stage());
				System.out.println("User id: "+id + " Logout successfully");
			}
			else {
				System.out.println("Problem at logout, requester id is different in rs->aborting");
				ConnectionServer.getInstance().quit();
				System.out.println("exit Academic Tool");
				
			}
		} catch (IOException e) {
			System.out.println("Problem at quit connection server");
		}catch (Exception e) {
			System.out.println("Exception at invoking logout");
		}
    }
    /**
     * This method loads the MyExam fxml page.
     * @param event Mouse event triggerd when click my exam button.
     */
    @FXML
    void MyExam(MouseEvent event) {
        loadPage("MyExam");
        if(myExamController==null)
        	myExamController = new MyExamController();
    }
    /**
     * This method loads the TakeExam fxml page.
     * @param event Mouse event triggerd when click TakeExam button.
     */
    @FXML
    void TakeExam(MouseEvent event) {
        loadPage("TakeExam");
        if(takeExamController==null)
        	takeExamController = new TakeExamController();
    }

    /**
     * this method is for load a specific fxml page.
     * @param page the page we want to load.
     */
    private void loadPage(String page) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/guiStudent/"+ page + ".fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bp.setCenter(root);
    }
    /**
     * Sets the name of the user who is logged in in the menu screen.
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lblHello.setText("Hello, "+student.getFirstName()+ "!");
		
	}


}
