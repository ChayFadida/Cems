package controllersHod;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import abstractControllers.AbstractController;
import client.ConnectionServer;
import controllersClient.AreYouSureController;
import entities.Hod;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
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
import ocsf.server.AbstractServer;

//remove Application after Login implementation
public class HODmenuController extends AbstractController implements Initializable {
	private double xOffset = 0; 
	private double yOffset = 0;
	private HODviewAllStudentsController hODviewAllStudentsController;
	private HODviewAllLecturersTableController hODviewAllLecturersTableController;
	private HODviewExamBankController hODviewExamBankController;
	private HODviewRequestController hODviewRequestController;
	private HODviewStatisticsController hODviewStatisticsController;
	private HODviewQuestionBankController hODviewQuestionBankController;
	private Hod hod=null;
    @FXML
    private Button LogOutButton;

    @FXML
    private Button ViewAllLecturersButton;

    @FXML
    private Button ViewAllStudentsButton;

    @FXML
    private Button ViewExamBankButton;

    @FXML
    private Button ViewQuestionBankButton;

    @FXML
    private Button ViewRequestButton;

    @FXML
    private Button ViewStatisticsButton;

    @FXML
    private AnchorPane ap;

    @FXML
    private BorderPane bp;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnMinimize;

    @FXML
    private Text lblHello;
    
    public HODmenuController() {
    	try {
			hod = (Hod) ConnectionServer.getInstance().getUser();
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
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("/guiHod/HODmenu.fxml"));
			Scene scene = new Scene(root);
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.getIcons().add(new Image("/Images/CemsIcon32-Color.png"));
			scene.getStylesheets().add(getClass().getResource("/guiHod/HODmenuCSS.css").toExternalForm());
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

    @FXML
    void LogOut(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void ViewExamBank(MouseEvent event) {
    	loadPage("ViewExamBank");
    	if(hODviewExamBankController==null)
    		hODviewExamBankController = new HODviewExamBankController();
    }

    @FXML
    void ViewQuestionBank(MouseEvent event) {
    	loadPage("ViewQuestionBank");
    	if(hODviewQuestionBankController==null)
    		hODviewQuestionBankController = new HODviewQuestionBankController();
    }

    @FXML
    void ViewRequest(MouseEvent event) {
    	loadPage("ViewRequest");
    	if(hODviewRequestController==null)
    		hODviewRequestController = new HODviewRequestController();
    }

    @FXML
    void ViewStatistics(MouseEvent event) {
    	loadPage("ViewStatistics");
    	if(hODviewStatisticsController==null)
    		hODviewStatisticsController = new HODviewStatisticsController();
    }
    
    @FXML
    void ViewAllStudents(MouseEvent event) {
    	loadPage("ViewAllStudents");
    	if(hODviewAllStudentsController==null)
    		hODviewAllStudentsController= new HODviewAllStudentsController();
    }
    
    @FXML
    void ViewAllLecturers(MouseEvent event) {
    	loadPage("ViewAllLecturers");
    	if(hODviewAllLecturersTableController== null)
    		hODviewAllLecturersTableController = new HODviewAllLecturersTableController();
    	
    }
    
    @FXML
    public void initialize() {
        buttonPressEffect.setInput(LogOutButton.getEffect());
        buttonPressEffect.setInput(ViewExamBankButton.getEffect());
        buttonPressEffect.setInput(ViewQuestionBankButton.getEffect());
        buttonPressEffect.setInput(ViewRequestButton.getEffect());
        buttonPressEffect.setInput(ViewStatisticsButton.getEffect());
        buttonPressEffect.setInput(ViewAllLecturersButton.getEffect());
        buttonPressEffect.setInput(ViewAllStudentsButton.getEffect());

        LogOutButton.setOnMousePressed(this::applyButtonPressEffect);
        LogOutButton.setOnMouseReleased(this::removeButtonPressEffect);

        ViewExamBankButton.setOnMousePressed(this::applyButtonPressEffect);
        ViewExamBankButton.setOnMouseReleased(this::removeButtonPressEffect);

        ViewQuestionBankButton.setOnMousePressed(this::applyButtonPressEffect);
        ViewQuestionBankButton.setOnMouseReleased(this::removeButtonPressEffect);
        
        ViewRequestButton.setOnMousePressed(this::applyButtonPressEffect);
        ViewRequestButton.setOnMouseReleased(this::removeButtonPressEffect);
        
        ViewStatisticsButton.setOnMousePressed(this::applyButtonPressEffect);
        ViewStatisticsButton.setOnMouseReleased(this::removeButtonPressEffect);
        
        ViewAllLecturersButton.setOnMousePressed(this::applyButtonPressEffect);
        ViewAllLecturersButton.setOnMouseReleased(this::removeButtonPressEffect);
        
        ViewAllStudentsButton.setOnMousePressed(this::applyButtonPressEffect);
        ViewAllStudentsButton.setOnMouseReleased(this::removeButtonPressEffect);
        
        
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
            root = FXMLLoader.load(getClass().getResource("/guiHod/"+ page + ".fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bp.setCenter(root);
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lblHello.setText("Hello, "+hod.getFirstName()+ "!");
		
	}
}

