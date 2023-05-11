package server;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import java.util.HashMap;
import DataBase.DBController;

 
public class ServerController  {
	DBController dbController = DBController.getInstance();


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
			put("port", getPort());
		}};
		if(db_info.containsValue("")) {
			System.out.println("You must enter values");
			return;			
		}
		startServer(db_info);
	}

	@FXML
	void clickExitBtn(MouseEvent event) {
		System.out.println("Exit from Sems server application");
		System.exit(0);
	}
	
	void disconnectServer() {
		
	}
	
	void startServer(HashMap db_info) {
		dbController.setDbDriver();
		dbController.setDbInfo(db_info);
		dbController.connectToDb();
		ClientHandler clientHandler = new ClientHandler(Integer.parseInt((String) db_info.get("port")));
		clientHandler.runServer();
	}
}
