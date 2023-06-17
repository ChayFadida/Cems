package controllersClient;

import java.io.IOException;

import abstractControllers.AbstractController;
import abstractControllers.AbstractController.DragHandler;
import abstractControllers.AbstractController.PressHandler;
import client.ConnectionServer;
import controllersHod.HODmenuController;
import controllersLecturer.LecturerMenuController;
import entities.Super;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Controller class that gives users who are both lecturers and HOD's the option to chose their working profile 
 * and to navigate to the wanted menu
 */
public class ChooseProfileController extends AbstractController{
	public Super s;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnHOD;

    @FXML
    private Button btnLecturer;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnMinimize;


   /**
    * Constructor for the class.
    */
    public ChooseProfileController() throws IOException {
		super();
		s = (Super) ConnectionServer.getInstance().getUser();

	}
    
    /**
     * Handling the action event when the btnHOD button is pressed 
     * @param event Action event
     */
	@FXML
    void getHodBtn(ActionEvent event) {
    	System.out.println("HOD Login Successfuly.");
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		HODmenuController hodMenuController = new HODmenuController();	
		hodMenuController.start(new Stage());
    }

	/**
     * Handling the action event when the btnLecturer button is pressed 
     * @param event Action event
     */
    @FXML
    void getLecBtn(ActionEvent event) {
    	System.out.println("Lecturer Login Successfuly.");
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		LecturerMenuController lecturerMenuController = new LecturerMenuController();	
		lecturerMenuController.start(new Stage());
    }

    /**
     * Start and initialize the stage.
     * @param primaryStage Stage.
     */
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/guiClient/SuperChooseScreen.fxml"));
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
     * Handling the action event when the btnLogout button is pressed 
     * @param event Action event
     */
    @FXML
    void getLogoutBtn(ActionEvent event) {
    	//need to implement log out
    	System.exit(0);
    }
    
	/**
     * Handling the action event when the btnMinimize button is pressed 
     * @param event Action event
     */
    @FXML
    void getMinimizeBtn(ActionEvent event) {
    	Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    
	/**
     * Handling the action event when the btnExit button is pressed 
     * @param event Action event
     */
    @FXML
    void getExitBtn(ActionEvent event) {
    	AreYouSureController areYouSureController = new AreYouSureController();
    	areYouSureController.start(new Stage());
    }

}
