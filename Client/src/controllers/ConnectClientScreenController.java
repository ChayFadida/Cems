package controllers;

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
import client.ConnectionServer;
public class ConnectClientScreenController {

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
    private int getPort() {
		return Integer.parseInt(txtPort.getText());
    }

    private String getIP() {
		return txtServerIP.getText();
	}
	public void getConnectBtn(ActionEvent event) throws Exception {
		ConnectionServer.getInstance(getIP(), getPort());
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		LecturerMenuScreenController lecturerMenuScreenController = new LecturerMenuScreenController();
		lecturerMenuScreenController.start(primaryStage);	
	}
	
	public void getShutdownBtn(ActionEvent event) throws Exception {
		System.out.println("exit Academic Tool");
		System.exit(0);
	}
	
	public void start(Stage primaryStage) throws Exception {
		
		Parent root = FXMLLoader.load(getClass().getResource("/gui/ConnectClientScreen.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Academic Managment Tool");
		primaryStage.setScene(scene);
		
		primaryStage.show();	 	   
	}
	
}