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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import logic.Client;
import logic.ClientConnection;
import ocsf.server.ConnectionToClient;

import java.net.UnknownHostException;
import java.util.ArrayList;

import DataBase.DBController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;


public class ConnectedScreenController {
	ClientHandler clientHandler= ClientHandler.getInstance();
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

	/**
	 * this method stop listening to the port 
	 * and then disconnect from the database
	 * @return instance of the dbcontroller
	 * */
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
    
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/gui/ServerConnectedScreen.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Server Connected");
		primaryStage.setScene(scene);
		primaryStage.show();	
	}    
	
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
	

}
