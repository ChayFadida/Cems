package server;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.event.ActionEvent;


public class ConnectedScreenController {

    @FXML
    private Button btnDisconnect;

    @FXML
    private Label lblServerConnected;

    @FXML
    void getDisconnectBtn(ActionEvent event) {
    	((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		ServerController serverController = new ServerController();
		try {
			serverController.start(primaryStage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ClientHandler.getInstance().stopListening();

    }
    
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/gui/ServerConnectedScreen.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Server Connected");
		primaryStage.setScene(scene);
		primaryStage.show();	
	}

}
