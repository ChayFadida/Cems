package client;

import controllersClient.ConnectClientScreenController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class ClientApplication extends Application {


	/**
	 *this method launch the first screen of the client
	 *@param primaryStage
	 * */
	public void start(Stage primaryStage) throws Exception {
		ConnectClientScreenController connectClientScreenController = new ConnectClientScreenController();
		connectClientScreenController.start(primaryStage);
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
