package server;


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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;

import DataBase.DBController;
//import server.ServerUI;
public class ServerController {


    @FXML
    private Button btnConnect;

    @FXML
    private Button btnExit;
    
    @FXML
    private Button btnDisconnect;

    @FXML
    private Text lblHost;

    @FXML
    private Text lblIP;

    @FXML
    private Text lblPass;

    @FXML
    private Text lblPort;

    @FXML
    private Text lblScheme;

    @FXML
    private TextArea txtHost;

    @FXML
    private TextArea txtIP;

    @FXML
    private TextArea txtPass;

    @FXML
    private TextArea txtPort;

    @FXML
    private TextArea txtScheme;

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
	private String getScheme() {
		return txtScheme.getText();			
	}
	
	@FXML
	void clickConnectBtn(MouseEvent event) {
		HashMap<String, String> db_info = new HashMap<>() {{
			put("ip", getIP());
			put("password", getPass());
			put("username", getHost());
			put("scheme", getScheme());
		}};
		if(db_info.containsValue("")) {
			System.out.println("You must enter values");
			return;			
		}
		DBController dbController = DBController.getInstance();
		dbController.setDbDriver();
		dbController.setDbInfo(db_info);
		dbController.connectToDb();
		
	}

	@FXML
	void clickExitBtn(MouseEvent event) {
		System.out.println("Exit server tool");
		System.exit(0);		
	}
	/*
	@FXML
	void clickConnectBtn(ActionEvent event){
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
	@FXML
	void clickExitBtn(ActionEvent event){
		System.out.println("Exit server tool");
		System.exit(0);			
	}
	*/

}
