package controllersLecturer;

import java.io.IOException;

import abstractControllers.AbstractController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
	        //handle dragging the window
	        super.setPrimaryStage(primaryStage);
	        PressHandler<MouseEvent> press = new PressHandler<>();
	        DragHandler<MouseEvent> drag = new DragHandler<>();
	        root.setOnMousePressed(press);
	        root.setOnMouseDragged(drag);
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
	}
//    public static void main(String[] args) {
//		launch(args);
//	}
    ///END ITAMAR COMMANDS
    @FXML
    void getExitBtn(ActionEvent event) {
    	//temporary! need to implement log out + server connection shut down
    	Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    void getMinimizeBtn(ActionEvent event) {
    	Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    
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
    

 
}
