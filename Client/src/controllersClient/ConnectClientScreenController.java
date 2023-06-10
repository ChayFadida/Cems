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
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import abstractControllers.AbstractController;
import client.ConnectionServer;


public class ConnectClientScreenController extends AbstractController{

    @FXML
    private Button btnConnect;

    @FXML
    private Button btnShutDown;

    @FXML
    private Text lblConnectServer;

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
			LogInController logInController = new LogInController();
			logInController.start(primaryStage);

		}catch (Exception e) {
			System.out.println("Wrong input, try again");
		}	
	}
	
	/**
	 *this method implements the exit button and terminate the process
	 *@param event
	 * */
	public void getShutdownBtn(ActionEvent event) throws Exception {
		ConnectionServer.getInstance().quit();
		System.out.println("exit Academic Tool");
		System.exit(0);
	}
	
	/**
	 *this method launch the screen
	 *@param Stage primaryStage
	 * */
	public void start(Stage primaryStage) throws Exception {	
		Parent root = FXMLLoader.load(getClass().getResource("/guiClient/ConnectClientScreen.fxml"));
		Scene scene = new Scene(root);
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.getIcons().add(new Image("/Images/CemsIcon32-Color.png"));
		primaryStage.setScene(scene);
		primaryStage.show();	
		super.setPrimaryStage(primaryStage);
        PressHandler<MouseEvent> press = new PressHandler<>();
        DragHandler<MouseEvent> drag = new DragHandler<>();
        root.setOnMousePressed(press);
        root.setOnMouseDragged(drag);
	}
}