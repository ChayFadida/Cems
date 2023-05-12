package server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ServerApplication extends Application {
	private String folder = "gui";
	private String fxml = "ServerConnectionInfoScreen.fxml";
	
	/**
	 * Start the Cems application from server side
	 *@param Stage ?? 
	 * */
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/" + folder + "/" + fxml));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Server");
		primaryStage.setScene(scene);
		primaryStage.show();	
	}
	
	/**
	 * main program of the application server side
	 * */
	public static void main(String[] args) {
		launch(args);
	}
}
