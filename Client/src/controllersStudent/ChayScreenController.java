package controllersStudent;

import abstractControllers.AbstractController;
import abstractControllers.AbstractController.DragHandler;
import abstractControllers.AbstractController.PressHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ChayScreenController extends AbstractController{

    @FXML
    private Button browse;

    @FXML
    private Button btn;

    @FXML
    private Pane pane;

    @FXML
    private Region region;

    @FXML
    private TextField txt;
    
    public void start(Stage primaryStage) {
	    try {
	        Parent root =  FXMLLoader.load(getClass().getResource("/guiStudent/ChayScreen.fxml"));
	        Scene scene = new Scene(root);
//			primaryStage.initStyle(StageStyle.UNDECORATED);
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
    void dragDetectP(MouseEvent event) {

    }
    @FXML
    void dragDetectR(MouseEvent event) {

    }

    @FXML
    void dragDoneP(DragEvent event) {

    }

    @FXML
    void dragDoneR(DragEvent event) {

    }

    @FXML
    void dragDroppedP(DragEvent event) {

    }

    @FXML
    void dragDroppedR(DragEvent event) {

    }

    @FXML
    void dragEnterP(DragEvent event) {

    }

    @FXML
    void dragEnterR(DragEvent event) {

    }

    @FXML
    void dragExitP(DragEvent event) {

    }

    @FXML
    void dragExitR(DragEvent event) {

    }

    
    @FXML
    void dragMenterP(MouseDragEvent event) {

    }

    @FXML
    void dragMenterR(MouseDragEvent event) {

    }

    @FXML
    void dragMexitP(MouseDragEvent event) {

    }

    @FXML
    void dragMexitR(MouseDragEvent event) {

    }

    @FXML
    void dragMoverP(MouseDragEvent event) {

    }

    @FXML
    void dragMoverR(MouseDragEvent event) {

    }

    @FXML
    void dragMreleaseP(MouseDragEvent event) {

    }

    @FXML
    void dragMreleaseR(MouseDragEvent event) {

    }

    @FXML
    void dragOverP(DragEvent event) {

    }

    @FXML
    void dragOverR(DragEvent event) {

    }
}
