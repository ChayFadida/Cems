package server;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.HashMap;
import DataBase.DBController;

 
public class ServerController  {
	DBController dbController = DBController.getInstance();
	private double xOffset = 0; 
	private double yOffset = 0;

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
	
	/**
	 * activate the server from the start
	 *@param event this is a mouse event and this method activate
	 *when the user click on connect 
	 * */
	@FXML
	void clickConnectBtn(MouseEvent event) {
		/*
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
		}*/
		HashMap<String, String> db_info_temp = new HashMap<>() {{
			put("ip","localhost");
			put("password", "EyalMySql");
			put("username", "root");
			put("scheme", "sys");
			put("port", "8000");
		}};

		//startServer(db_info);
		startServer(db_info_temp);
    	((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		ConnectedScreenController connectedScreenController = new ConnectedScreenController();
		try {
			connectedScreenController.start(primaryStage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * end the application with return code 0
	 *@param event this is a mouse event and this method activate
	 *when the user click on exit
	 * */
	@FXML
	void clickExitBtn(MouseEvent event) {
		System.out.println("Exit from Cems server application");
		System.exit(0);
	}
	
	/**
	 * set database drive connect to the database and the activate the server 
	 *@param db_info this is a hashmap of database info that the
	 *user is desire to implement
	 * */
	void startServer(HashMap<String, String> db_info) {
		dbController.setDbDriver();
		dbController.setDbInfo(db_info);
		dbController.connectToDb();
		ClientHandler.getInstance(Integer.parseInt((String) db_info.get("port"))).runServer();
		
	}
	
	/**
	 * this method upload the first screen to connect to the server 
	 *@param primaryStage 
	 * */
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/gui/ServerConnectionInfoScreen.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/gui/ServerConnectionScreenCSS.css").toExternalForm());
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.getIcons().add(new Image("/Images/CemsIcon32-Color.png"));
		primaryStage.setTitle("Server");
		primaryStage.setScene(scene);
		primaryStage.show();
		root.setOnMousePressed((EventHandler<? super MouseEvent>) new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	primaryStage.setX(event.getScreenX() - xOffset);
            	primaryStage.setY(event.getScreenY() - yOffset);
            }
        });
	}
	
}
