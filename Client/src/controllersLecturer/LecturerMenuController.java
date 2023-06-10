package controllersLecturer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import abstractControllers.AbstractController;
import client.ConnectionServer;
import controllersClient.AreYouSureController;
import controllersClient.ChooseProfileController;
import entities.Lecturer;
import entities.Super;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
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

public class LecturerMenuController extends AbstractController implements Initializable{
	private MyQuestionBankController myQuestionBankController=null;
	private MyExamBankController myExamBankController=null;
	private CreateNewExamController createNewExamController=null;
	private ManageExamsController manageExamsController=null;
	private CheckResultController checkResultController=null;
	private Lecturer lecturer=null ;
	private Super s;
	
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

    @FXML
    private Text lblHello;
    public LecturerMenuController() {
    	try {
    		User user = ConnectionServer.getInstance().getUser();
    		if(user instanceof Super) {
    			this.s = ((Super) user);
    			this.lecturer = s.getLecturer();
    		}
    		else {
    			this.lecturer= (Lecturer) ConnectionServer.getInstance().getUser();
    			this.s=null;
    		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

//	public LecturerMenuController(Super s) {
//		this.s= s;
//		this.lecturer=s.getLecturer();
//	}

	public void start(Stage primaryStage) {
	    try {
	        BorderPane root =  (BorderPane)FXMLLoader.load(getClass().getResource("/guiLecturer/LecturerMenu.fxml"));
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
    	if(s!=null) {
    		System.out.println("Super Login Successfuly.");
			((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
			ChooseProfileController chooseProfileController = new ChooseProfileController();	
			chooseProfileController.start(new Stage());
    	}
    	else {
    		System.exit(0);
    	}
    	
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lblHello.setText("Hello, "+lecturer.getFirstName()+ "!");
	}
    

 
}
