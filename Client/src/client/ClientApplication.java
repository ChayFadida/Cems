package client;

import controllersClient.ConnectClientScreenController;
import javafx.application.Application;
import javafx.stage.Stage;


public class ClientApplication extends Application {


	/**
	 *this method launch the first screen of the client
	 *@param primaryStage
	 * */
	public void start(Stage primaryStage) throws Exception {
		ConnectClientScreenController connectClientScreenController = new ConnectClientScreenController();
		connectClientScreenController.start(primaryStage);
	}
	
	/**
	 * loads main.
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
