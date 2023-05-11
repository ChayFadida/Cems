package client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ClientConnectController {

    @FXML
    private Button btnConnect;

    @FXML
    private Button btnShutdown;

    @FXML
    private ImageView imgBackground;

    @FXML
    private ImageView imgShutdown;

    @FXML
    private Text lblConnectToServer;

    @FXML
    private Text lblServerIP;

    @FXML
    private TextArea txtServerIP;
    
    private String getIP() {
		return txtServerIP.getText();			
	}
    
    @FXML
	 void clickConnectBtn(MouseEvent event) throws Exception {
		String serverIP;
		serverIP=getIP();
		if(serverIP.trim().isEmpty()) {
			System.out.println("You must enter server ip");		
		}
		else {
			DisplayQuestionListController displayQuestionListController = new DisplayQuestionListController ();
			Stage stage = new Stage();
			((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
			displayQuestionListController.start(stage);

		}
	}
    @FXML
	 void clickShutdownBtn(MouseEvent event) {
	    Stage stage = (Stage) btnShutdown.getScene().getWindow();
	    stage.close();
		System.exit(0);
	}
    
	public void start(Stage primaryStage) throws Exception {	
		Parent root = FXMLLoader.load(getClass().getResource("/clientGui/CEMS_GUI_ConnectClientScreen.fxml"));
		Scene scene = new Scene(root);
		//scene.getStylesheets().add(getClass().getResource("/gui/ServerPort.css").toExternalForm());
		primaryStage.setTitle("Client Connect");
		primaryStage.setScene(scene);
		
		primaryStage.show();		
	}

}
