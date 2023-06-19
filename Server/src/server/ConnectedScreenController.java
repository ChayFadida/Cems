package server;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logic.Client;
import logic.ClientConnection;
import ocsf.server.ConnectionToClient;

import java.net.UnknownHostException;
import java.util.ArrayList;

import DataBase.DBController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.text.Text;

/**
 * Controller class for the Connected Screen.
 */
public class ConnectedScreenController {
	ClientHandler clientHandler= ClientHandler.getInstance();
	private double xOffset = 0; 
	private double yOffset = 0;
    @FXML
    private Button btnRefresh;
    @FXML
    private TableColumn<Client, String> clmHost;

    @FXML
    private TableColumn<Client, String> clmIp;

    @FXML
    private TableColumn<Client, ClientConnection> clmStatus;

    @FXML
    private TableView<Client> tblClientConnections;
    @FXML
    private Button btnDisconnect;

    @FXML
    private Label lblServerConnected;
    
    @FXML
    private Button btnMinimize;
    
    @FXML
    private Text lblServer;

    /**
     * Handles the action event when the Disconnect button is clicked.
     *
     * @param event the action event
     * @return an instance of the DBController
     */
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
		DBController.getInstance().disconnect();
    }
    
    /**
     * Starts the Connected Screen stage.
     *
     * @param primaryStage the primary stage
     * @throws Exception if an error occurs during stage start
     */
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/gui/ServerConnectedScreen.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/gui/ServerConnectedScreenCSS.css").toExternalForm());
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.getIcons().add(new Image("/Images/CemsIcon32-Color.png"));
		primaryStage.setTitle("Server Connected");
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
	
    /**
     * Starts the Connected Screen stage.
     *
     * @param primaryStage the primary stage
     * @throws Exception if an error occurs during stage start
     */
	@FXML
    void getRefreshBtn(MouseEvent event) throws UnknownHostException {
			// this list save the details of all connected clients
		ArrayList<Client> allClients = new ArrayList<>();
		String clientAddress; // the whole client's data
		String[] ca; // tmp for string clientAddress for split method
		String ip; // client's ip address
		String hostName; // client's host name

		// there is at least 1 client that connected to server
		if (clientHandler.getNumberOfClients() != 0) {
			Thread[] clientConnected = new Thread[clientHandler.getNumberOfClients()];
			clientConnected = clientHandler.getClientConnections();
			for (Thread client : clientConnected) {
				// get client's ip address and host's name
				clientAddress = (((ConnectionToClient) client).getInetAddress().getLocalHost()).toString();
				ca = clientAddress.split("/");
				hostName = ca[0];
				ip = ca[1];
				// add the details to the table
				allClients.add(new Client(ip, hostName, ClientConnection.CONNECTED));
			}
		}
		setTable(allClients);

		}
	
    /**
     * Sets the table with the provided list of clients.
     *
     * @param clients the list of clients to display in the table
     */

	public void setTable(ArrayList<Client> clients) {
		ObservableList<Client> clientList = FXCollections.observableArrayList(clients);
		PropertyValueFactory<Client, String> pvfIp = new PropertyValueFactory<>("ip");
		PropertyValueFactory<Client, String> pvfHost = new PropertyValueFactory<>("hostName");
		PropertyValueFactory<Client, ClientConnection> pvfStatus = new PropertyValueFactory<>("status");
		clmIp.setCellValueFactory(pvfIp);
		clmHost.setCellValueFactory(pvfHost);
		clmStatus.setCellValueFactory(pvfStatus);
		tblClientConnections.setItems(clientList);
	}
	@FXML
	void getMinimizeBtn(ActionEvent event) {
	    Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
	    stage.setIconified(true);
	}
	

}
