package server;

import java.util.Vector;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import taskManager.TaskHandlerFactory;

public class ServerApplication extends Application {
	ServerController serverController;
	public static Vector<TaskHandlerFactory>  taskHandlerFactory= new Vector<>();
	
	/**
	 * Start the Cems application from server side
	 *@param Stage ?? 
	 * */
	@Override
	public void start(Stage primaryStage) throws Exception {
		serverController = new ServerController();
		serverController.start(primaryStage);
	}
	
	/**
	 * main program of the application server side
	 * */
	public static void main(String[] args) {
		launch(args);
	}
}
