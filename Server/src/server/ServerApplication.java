package server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ServerApplication extends Application {
	private String fxml = "ServerConnectionInfoScreen.fxml";
	private String css = "ServerPort.css";
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/gui/ServerConnectionInfoScreen.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Server");
		primaryStage.setScene(scene);
		primaryStage.show();	
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
