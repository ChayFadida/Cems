package controllers;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class studentmenueController extends Application{
	private double xOffset = 0; 
	private double yOffset = 0;
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

    private final Glow buttonPressEffect = new Glow(0.5);
    /// in order to start without login dependency 
    // after Login, remove @override (start should stay), remove main
    @Override	
	public void start(Stage primaryStage) {
	    try {
	        BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("/gui/StudentMenue.fxml"));
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
    public static void main(String[] args) {
		launch(args);
	}
    ///END ITAMAR COMMANDS

    @FXML
    void LogOut(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void MyExam(MouseEvent event) {
        loadPage("MyExam");
    }

    @FXML
    void TakeExam(MouseEvent event) {
        loadPage("TakeExam");
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
            root = FXMLLoader.load(getClass().getResource("/gui/"+ page + ".fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        bp.setCenter(root);
    }

}
