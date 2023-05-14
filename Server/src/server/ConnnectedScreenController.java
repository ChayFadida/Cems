package server;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import server.ServerController;

public class ConnnectedScreenController {
	private String fxml ="/gui/ServerConnectedScreen.fxml";
    @FXML
    private Button btnDiscconect;

    @FXML
    private ImageView imgBackground;

    @FXML
    private Label lblServerIsConnected;

    @FXML
    void getDisconnectBtn(MouseEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		ServerController  serverController= new ServerController();
		serverController.start(primaryStage);
    }
    
    public void start(Stage primaryStage) throws Exception {	
		Parent root = FXMLLoader.load(getClass().getResource(fxml));
		Scene scene = new Scene(root);
		//scene.getStylesheets().add(getClass().getResource("/gui/AcademicFrame.css").toExternalForm());
		primaryStage.setTitle("Server Is Connected");
		primaryStage.setScene(scene);
		primaryStage.show();	 	   
	}
}
