/**
 * Controller for the lecturer menu.
 * From here the lecturer can navigate threw different tasks.
 * Extends AbstractController and implements initializable.
 */
package controllersLecturer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import abstractControllers.AbstractController;
import client.ConnectionServer;
import controllersClient.AreYouSureController;
import controllersClient.ChooseProfileController;
import controllersClient.LogInController;
import entities.Lecturer;
import entities.Super;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
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
	private Super LecturerAndHOD;
	
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
    
    /**
     * Constructor for the LecturerMenuController class.
     * Initialize both lecturer and LecturerAndHOD users
     */
    public LecturerMenuController() {
    	User user = ConnectionServer.getInstance().getUser();
		if(user instanceof Super) {
			this.LecturerAndHOD = ((Super) user);
			this.lecturer = LecturerAndHOD.getLecturer();
		}
		else {
			this.lecturer= (Lecturer) ConnectionServer.getInstance().getUser();
			this.LecturerAndHOD=null;
		}
    }

    /**
     * Starts the lecturer menu gui.
     * @param primaryStage gui primary stage.
     */
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

	/**
	 * By pressing the exit button the program will load AreYouSureController, so the user can decide if exit and log out.
	 * @param event Action event
	 */
    @FXML
    void getExitBtn(ActionEvent event) {
    	AreYouSureController areYouSureController = new AreYouSureController();
    	areYouSureController.start(new Stage());
    }
    
    /**
     * By pressing minimze button the current window will minimze.
     * @param event Action Event
     */
    @FXML
    void getMinimizeBtn(ActionEvent event) {
    	Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    
    /**
     * By pressing check result button the program will load the relevant page
     * @param event MouseEvent
     */
    @FXML
    void CheckResult(MouseEvent event) {
    	loadPage("CheckResult");
    	if(checkResultController==null)
    		checkResultController= new CheckResultController();
    }

    /**
     * By pressing create new exam button the program will load the relevant page
     * @param event MouseEvent
     */
    @FXML
    void CreateNewExam(MouseEvent event) {
    	loadPage("CreateNewExam");
    	if(createNewExamController==null)
    		createNewExamController= new CreateNewExamController();
    }
    
    /**
     * By pressing log out button the program will shut down and log out the user from the DB 
     * @param event MouseEvent
     * @throws IOException
     */
    @FXML
    void LogOut(MouseEvent event) throws IOException {
    	if(LecturerAndHOD!=null) {
			((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
			ChooseProfileController chooseProfileController = new ChooseProfileController();	
			chooseProfileController.start(new Stage());
    	}
    	else {
    		try {
				boolean res = super.logoutRequest(lecturer);
				int id = lecturer.getId();
				if (res) {
					lecturer=null;
					LecturerAndHOD=null;
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
    }

    /**
     * By pressing manage exams button the program will load the relevant page
     * @param event MouseEvent
     * @throws IOException
     */
    @FXML
    void ManageExams(MouseEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/guiLecturer/ManageExams.fxml").openStream());
		manageExamsController = loader.getController();
		manageExamsController.showTable();
        bp.setCenter(root);
    }
    
    /**
     * By pressing my exam bank button the program will load the relevant page
     * @param event MouseEvent
     */
    @FXML
    void MyExambank(MouseEvent event) {
    	loadPage("MyExambank");
    	if(myExamBankController==null)
    		myExamBankController = new MyExamBankController();
    }
    
    /**
     * By pressing my question bank button the program will load the relevant page
     * @param event MouseEvent
     */
    @FXML
    void MyQuestionbank(MouseEvent event) {
    	loadPage("MyQuestionbank");
    	if(myQuestionBankController==null)
    		myQuestionBankController = new MyQuestionBankController();
    }
    
    /**
     * Initialize effects to the buttons.
     */
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
    
    /**
     * Sets effects to the buttons
     * @param event
     */
    private void applyButtonPressEffect(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setEffect(buttonPressEffect);
    }
    
    /**
     * Remove effects from the buttons
     * @param event
     */
    private void removeButtonPressEffect(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setEffect(null);
    }
    
    /**
     * Load specific FXML page.
     * @param page the bname of the page we want to reload.
     */
    public void loadPage(String page) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/guiLecturer/"+page + ".fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bp.setCenter(root);
    }
    
    /**
     * Initialize the controller.
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lblHello.setText("Hello, "+lecturer.getFirstName()+ "!");
	}
    

 
}
