package controllersStudent;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.ArrayList;
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
import javafx.stage.FileChooser;
import java.io.File;

public class ChayScreenController extends AbstractController{
    private List<byte[]> fileBytesList = new ArrayList<>();
    private List<File> fileList = new ArrayList<>();
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
    void dragOverR(DragEvent event) {

    }
    @FXML
    void submitAction() {
        if (!fileBytesList.isEmpty()) {
            for (byte[] fileBytes : fileBytesList) {
                try {
                   System.out.println("Submit files");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            fileBytesList.clear(); // Clear the file bytes list after submitting
        }
    }


    @FXML
    void dragDroppedP(DragEvent event) {
        boolean success = false;
        if (event.getDragboard().hasFiles()) {
            List<File> files = event.getDragboard().getFiles();
            // Process the dropped files
            for (File file : files) {
                System.out.println("Dropped file: " + file.getAbsolutePath());
                fileList.add(file);
                try {
					byte[] fileBytes = Files.readAllBytes(file.toPath());
                    fileBytesList.add(fileBytes);

				} catch (Exception e) {
					e.printStackTrace();
				}
            }
            success = true;
        }
        event.setDropCompleted(success);
        event.consume();
    }

    @FXML
    void dragOverP(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(javafx.scene.input.TransferMode.COPY);
        }
        event.consume();
    }
    
    @FXML
    void browseAction() {
        FileChooser fileChooser = new FileChooser();
        Stage stage = new Stage();
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(stage);

        if (selectedFiles != null) {
            for (File file : selectedFiles) {
                System.out.println("Dropped file: " + file.getAbsolutePath());
                fileList.add(file);
                try {
					byte[] fileBytes = Files.readAllBytes(file.toPath());
                    fileBytesList.add(fileBytes);

				} catch (Exception e) {
					e.printStackTrace();
				}
            }
        }
    }

}
