package controllersStudent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import abstractControllers.AbstractController;
import abstractControllers.AbstractController.DragHandler;
import abstractControllers.AbstractController.PressHandler;
import client.ConnectionServer;
import controllersClient.AreYouSureController;
import entities.Student;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

//remove Application after Login implementation
public class StudentMenuController extends AbstractController implements Initializable{
	private double xOffset = 0; 
	private double yOffset = 0;
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
    
    public StudentMenuController() {
    	try {
			student = (Student) ConnectionServer.getInstance().getUser();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    @FXML
    void getExitBtn(ActionEvent event) {
    	AreYouSureController areYouSureController = new AreYouSureController();
    	areYouSureController.start(new Stage());
    }

    @FXML
    void getMinimizeBtn(ActionEvent event) {
    	Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    
    private final Glow buttonPressEffect = new Glow(0.5);

   
	public void start(Stage primaryStage) {
	    try {
	        BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("/guiStudent/StudentMenu.fxml"));
	        Scene scene = new Scene(root);
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.getIcons().add(new Image("/Images/CemsIcon32-Color.png"));

	        // Set the scene to the primary stage
	        primaryStage.setScene(scene);
	        primaryStage.show();
	        //after login implementation:
	        //remove ->
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
	        //add instead:
//	        super.setPrimaryStage(primaryStage);
//	        PressHandler<MouseEvent> press = new PressHandler<>();
//	        DragHandler<MouseEvent> drag = new DragHandler<>();
//	        root.setOnMousePressed(press);
//	        root.setOnMouseDragged(drag);
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
	}

    @FXML
    void LogOut(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void MyExam(MouseEvent event) {
        loadPage("MyExam");
        if(myExamController==null)
        	myExamController = new MyExamController();
    }

    @FXML
    void TakeExam(MouseEvent event) {
        loadPage("TakeExam");
        if(takeExamController==null)
        	takeExamController = new TakeExamController();
    }

    @FXML
    public void initialize() {
        buttonPressEffect.setInput(LogOutButton.getEffect());
        buttonPressEffect.setInput(MyExamsButton.getEffect());
        buttonPressEffect.setInput(TTakeExamButton.getEffect());

        LogOutButton.setOnMousePressed(this::applyButtonPressEffect);
        LogOutButton.setOnMouseReleased(this::removeButtonPressEffect);

        MyExamsButton.setOnMousePressed(this::applyButtonPressEffect);
        MyExamsButton.setOnMouseReleased(this::removeButtonPressEffect);

        TTakeExamButton.setOnMousePressed(this::applyButtonPressEffect);
        TTakeExamButton.setOnMouseReleased(this::removeButtonPressEffect);
    }

    private void applyButtonPressEffect(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setEffect(buttonPressEffect);
    }

    private void removeButtonPressEffect(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setEffect(null);
    }
    

    private void loadPage(String page) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/guiStudent/"+ page + ".fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bp.setCenter(root);
    }
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lblHello.setText("Hello, "+student.getFirstName()+ "!");
		
	}

}
