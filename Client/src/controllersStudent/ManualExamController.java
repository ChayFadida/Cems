package controllersStudent;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.Pane;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.FileOutputStream;

import abstractControllers.AbstractController;
import abstractControllers.AbstractController.DragHandler;
import abstractControllers.AbstractController.PressHandler;
import client.ConnectionServer;
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
import timer.timerHandler;
import javafx.stage.FileChooser;
import java.io.File;

public class ManualExamController extends AbstractController {
    private List<byte[]> fileBytesList = new ArrayList<>();
    private List<File> fileList = new ArrayList<>();
	private HashMap<String, Object> examInfo = new HashMap<>();
    @FXML
    private Button btnBrowse;

    @FXML
    private Button btnDownload;

    @FXML
    private Button btnUpload;

    @FXML
    private Label lblHour;

    @FXML
    private Label lblMin;

    @FXML
    private Label lblSec;

    @FXML
    private Pane pane;

    @FXML
    private ProgressBar progressBar;

    @FXML
    void gerDragDropped(DragEvent event) {
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
    void getBrowseBtn(ActionEvent event) {
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

    @FXML
    void getDownloadBtn(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files", "*.*"));
        
        // Show the file save dialog
        Stage stage = new Stage();
        File saveFile = fileChooser.showSaveDialog(stage);
        
        if (saveFile != null) {
            try (FileOutputStream outputStream = new FileOutputStream(saveFile)) {
                //outputStream.write(fileBytes);
                System.out.println("File saved successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File save cancelled by the user.");
        }
    }

    @FXML
    void getDragOver(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(javafx.scene.input.TransferMode.COPY);
        }
        event.consume();
    }

    @FXML
    void getUploadBtn(ActionEvent event) {
        if (!fileBytesList.isEmpty()) {
    		HashMap<String,ArrayList<Object>> msg = new HashMap<>();
    		ArrayList<Object> arr = new ArrayList<>();
    		arr.add("Student");
    		msg.put("client", arr);
    		ArrayList<Object> task = new ArrayList<>();
    		task.add("UploadTestsToDB");
    		msg.put("task",task);
    		ArrayList<Object> param = new ArrayList<>();

    		

    		HashMap<String, Object> info = new HashMap<String, Object>();
    		info.put("byte", fileBytesList.get(0));
    		info.put("startTime", examInfo.get("startTime"));
    		info.put("endTime", timerHandler.GetCurrentTimestamp());
    		info.put("examId", examInfo.get("examId"));
    		info.put("studentId", ConnectionServer.getInstance().getUser().getId());
    		info.put("status", "Under Check");
    		param.add(info);
    		msg.put("param", param);
    		super.sendMsgToServer(msg);
    		ArrayList<HashMap<String,Object>> rs = ConnectionServer.rs;
            fileBytesList.clear();
        }        
    }
    
    public void setExamInfo(int ExamId) {
    	examInfo.put("examId", ExamId);
    	examInfo.put("startTime", timerHandler.GetCurrentTimestamp());
    }
}