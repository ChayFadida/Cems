package controllersHod;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
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
public class HODmenuController extends Application  {
	private double xOffset = 0; 
	private double yOffset = 0;
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
    
    private final Glow buttonPressEffect = new Glow(0.5);
    /// in order to start without login dependency 
    // after Login, remove @override (start should stay), remove main
    @Override
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
    public static void main(String[] args) {
		launch(args);
	}
    ///END ITAMAR COMMANDS
    @FXML
    void LogOut(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void ViewExamBank(MouseEvent event) {
    	loadPage("ViewExamBank");
    }

    @FXML
    void ViewQuestionBank(MouseEvent event) {
    	loadPage("ViewQuestionBank");
    }

    @FXML
    void ViewRequest(MouseEvent event) {
    	loadPage("ViewRequest");
    }

    @FXML
    void ViewStatistics(MouseEvent event) {
    	loadPage("ViewStatistics");
    }
    
    @FXML
    void ViewAllStudents(MouseEvent event) {
    	loadPage("ViewAllStudents");
    }
    
    @FXML
    void ViewAllLecturers(MouseEvent event) {
    	loadPage("ViewAllLecturers");
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
}

