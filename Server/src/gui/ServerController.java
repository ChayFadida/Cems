package gui;
import javafx.collections.FXCollections;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import server.EchoServer;
import server.ServerUI;
public class ServerController {
	@FXML
	private Button btnExit = null;
	@FXML
	private Button btnConnect = null;
	@FXML
	private TextField txtPort;
	@FXML
	private TextField txtPass;
	@FXML
	private TextField txtHost;
	@FXML
	private TextField txtStatus1;
	@FXML
	private TextField txtIP;
	
	private String getIP() {
		return txtIP.getText();			
	}
	private String getPort() {
		return txtPort.getText();			
	}
	private String getPass() {
		return txtPass.getText();			
	}
	private String getHost() {
		return txtHost.getText();			
	}
	private String getStatus() {
		return txtStatus1.getText();			
	}
	public void clickExitBtn(ActionEvent event) throws Exception {
		System.out.println("Exit server tool");
		System.exit(0);			
	}
	public void clickConnectBtn(ActionEvent event) throws Exception {
		String port,host,pass,ip;
		host=getHost();
		pass=getPass();
		ip=getIP();
		port=getPort();
		if(port.trim().isEmpty()||host.trim().isEmpty()||pass.trim().isEmpty()||ip.trim().isEmpty()) {
			System.out.println("You must enter values");		
		}
		else
		{
			((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			//EchoServer.runServer(port); //please add dbname to fxml and pass the parameters
		}
	}
	
	public void start(Stage primaryStage) throws Exception {	
		Parent root = FXMLLoader.load(getClass().getResource("/gui/ServerConnectionInfoScreen.fxml"));
				
		Scene scene = new Scene(root);
		//scene.getStylesheets().add(getClass().getResource("/gui/ServerPort.css").toExternalForm());
		primaryStage.setTitle("Server");
		primaryStage.setScene(scene);
		
		primaryStage.show();		
	}
}
