package server;

import java.util.Vector;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import taskManager.TaskHandlerFactory;
/**
 * Server Application class
 * 
 * */
public class ServerApplication extends Application {
	ServerController serverController;
	public static Vector<TaskHandlerFactory>  taskHandlerFactory= new Vector<>();
	
	/**
	 * Start the Cems application from server side.
	 *@param primaryStage that starts the application
	 * */
	@Override
	public void start(Stage primaryStage) throws Exception {
		serverController = new ServerController();
		serverController.start(primaryStage);
	}
	
	/**
	 * main program of the application server side
	 * @param arg
	 * */
	public static void main(String[] args) {
		launch(args);
	}
}
