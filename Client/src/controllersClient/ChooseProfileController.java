package controllersClient;

import java.io.IOException;

import abstractControllers.AbstractController;
import abstractControllers.AbstractController.DragHandler;
import abstractControllers.AbstractController.PressHandler;
import client.ConnectionServer;
import controllersHod.HODmenuController;
import controllersLecturer.LecturerMenuController;
import entities.Super;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ChooseProfileController extends AbstractController{
	public Super s;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnHOD;

    @FXML
    private Button btnLecturer;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnMinimize;

    
   
    public ChooseProfileController() {
		super();
		try {
			s = (Super) ConnectionServer.getInstance().getUser();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
    void getHodBtn(ActionEvent event) {
    	System.out.println("HOD Login Successfuly.");
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		HODmenuController hodMenuController = new HODmenuController();	
		hodMenuController.start(new Stage());
    }

    @FXML
    void getLecBtn(ActionEvent event) {
    	System.out.println("Lecturer Login Successfuly.");
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		LecturerMenuController lecturerMenuController = new LecturerMenuController();	
		lecturerMenuController.start(new Stage());
    }

	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/guiClient/SuperChooseScreen.fxml"));
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
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	

    @FXML
    void getLogoutBtn(ActionEvent event) {
    	//need to implement log out
    	System.exit(0);
    }

    @FXML
    void getMinimizeBtn(ActionEvent event) {
    	Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    
    @FXML
    void getExitBtn(ActionEvent event) {
    	AreYouSureController areYouSureController = new AreYouSureController();
    	areYouSureController.start(new Stage());
    }

}
