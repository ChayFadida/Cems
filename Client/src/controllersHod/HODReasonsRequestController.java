package controllersHod;

import java.io.IOException;

import abstractControllers.AbstractController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class HODReasonsRequestController extends AbstractController{
	Image img = new Image("Images/exit.png");
    @FXML
    private Label txtReasons;
    @FXML
    private Button btnExit;
    @FXML
    private ImageView imgExit;
    
    public void viewReason(String reasons) {
        txtReasons.setText(reasons);
    }
    public void setExitImage() {
    	imgExit.setImage(img);
    }
    @FXML
	public void getExitBtn(ActionEvent event) {
		Stage currentStage = (Stage) btnExit.getScene().getWindow();
        currentStage.close();
	}
	
    
//    public void start(Stage primaryStage) throws IOException {
////		try {
//	        Parent root = FXMLLoader.load(getClass().getResource("/guiClient/AreYouSureScreen.fxml"));
////			Scene scene = new Scene(root);
////			primaryStage.initStyle(StageStyle.UNDECORATED);
//////			primaryStage.getIcons().add(new Image("/Images/CemsIcon32-Color.png"));
//////			scene.getStylesheets().add(getClass().getResource("/guiHod/HODmenuCSS.css").toExternalForm());
////			primaryStage.setScene(scene);
////			primaryStage.show();
////			 //handle dragging the window
////	        PressHandler<MouseEvent> press = new PressHandler<>();
////	        DragHandler<MouseEvent> drag = new DragHandler<>();
////	        root.setOnMousePressed(press);
////	        root.setOnMouseDragged(drag);
////		} catch(Exception e) {
////			e.printStackTrace();
////		}
//		Scene scene = new Scene(root);
//		primaryStage.setTitle("Reasons");
//		primaryStage.setScene(scene);
//		primaryStage.show();
//	}

}

