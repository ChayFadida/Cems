package controllersClient;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import abstractControllers.AbstractController;
import client.ConnectionServer;
import controllers.LecturerMenuScreenController;
import controllersLecturer.LecturerMenuController;

public class ConnectClientScreenController extends AbstractController{

    @FXML
    private Button btnConnect;

    @FXML
    private Button btnShutDown;

    @FXML
    private Label lblConnectServer;

    @FXML
    private Label lblServerIP;

    @FXML
    private TextField txtServerIP;
    @FXML
    private TextField txtPort;
    @FXML
    
    /**
	 *port getter
	 *@return int the number of the port
	 * */
    private int getPort() {
		return Integer.parseInt(txtPort.getText());
    }

    /**
	 *IP getter
	 *@return String of the IP
	 * */
    private String getIP() {
		return txtServerIP.getText();
	}
    
    /**
	 *this method implements the connection button and connect to the server
	 *@param event
	 * */
	public void getConnectBtn(ActionEvent event) throws Exception {
		try {
			ConnectionServer.getInstance(getIP(), getPort());
			((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
			Stage primaryStage = new Stage();
			LecturerMenuController lecturerMenuController = new LecturerMenuController();
			lecturerMenuController.start(primaryStage);
		}catch (Exception e) {
			System.out.println("Wrong input, try again");
		}	
	}
	
	/**
	 *this method implements the exit button and terminate the process
	 *@param event
	 * */
	public void getShutdownBtn(ActionEvent event) throws Exception {
		System.out.println("exit Academic Tool");
		System.exit(0);
	}
	
	/**
	 *this method launch the screen
	 *@param Stage primaryStage
	 * */
	public void start(Stage primaryStage) throws Exception {
		
		Parent root = FXMLLoader.load(getClass().getResource("/gui/ConnectClientScreen.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Academic Managment Tool");
		primaryStage.setScene(scene);
		
		primaryStage.show();	 	   
	}
}