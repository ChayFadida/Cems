package controllersClient;

import java.io.IOException;

import abstractControllers.AbstractController;
import client.ConnectionServer;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AreYouSureController extends AbstractController{
	private User user;
    @FXML
    private Button btnNo;

    @FXML
    private Button btnYes;

    @FXML
    void getNoBtn(ActionEvent event) {
    	((Stage) ((Node)event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    void getYesBtn(ActionEvent event) {
    	//need to implement log out
    	try {
    		boolean res = super.logoutRequest(user);
    		int id = user.getId();
    		if(res) {
    			user = null;
				System.out.println("User id: "+id + " Logout successfully");
    			ConnectionServer.getInstance().quit();
    			System.out.println("exit Academic Tool");
    			System.exit(0);
    		}
    		else {
    			System.out.println("Problem at checkout, requester id is different in rs");
    		}
		} catch (IOException e) {
			System.out.println("Problem at quit connection server");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Exception at invoking logout");
			e.printStackTrace();
		}
		
    }
    
    public void start(Stage primaryStage,User user) {
	    try {
	    	this.user=user;
	        Parent root = FXMLLoader.load(getClass().getResource("/guiClient/AreYouSureScreen.fxml"));
	        Scene scene = new Scene(root);
	        primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.getIcons().add(new Image("/Images/CemsIcon32-Color.png"));
	        // Set the scene to the primary stage
	        primaryStage.setScene(scene);
	        primaryStage.show();
	        //handle dragging the window
	        super.setPrimaryStage(primaryStage);
	        PressHandler<MouseEvent> press = new PressHandler<>();
	        DragHandler<MouseEvent> drag = new DragHandler<>();
	        root.setOnMousePressed(press);
	        root.setOnMouseDragged(drag);
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
	}
    

}
