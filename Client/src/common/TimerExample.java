package common;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TimerExample extends Application{
	private double xOffset = 0; 
	private double yOffset = 0;

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/gui/TimerExample.fxml"));		
		Scene scene = new Scene(root);
		//scene.getStylesheets().add(getClass().getResource("/gui/ConnectClientCSS.css").toExternalForm());
		primaryStage.getIcons().add(new Image("/Images/CemsIcon32-Color.png"));
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setTitle("Timer");
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
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
