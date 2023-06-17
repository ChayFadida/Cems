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
import timer.TimerHandler;
import thirdPart.ExamGenerator;
import timer.Clock;
import timer.TimeMode;
import timer.TimerController;
import timer.timerHandler;
import javafx.stage.FileChooser;
import java.io.File;


public class ManualExamController extends AbstractController {
    private List<byte[]> fileBytesList = new ArrayList<>();
    private List<File> fileList = new ArrayList<>();
	private HashMap<String, Object> examInfo = new HashMap<>();
	private boolean filesDragged = false;

	TimeMode timeMode;
	TimerController timerController;
	Clock clock;
	int time;
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
            // Display a visual element or text inside the Pane to indicate the dragged files
            Label fileLabel = new Label("Files Dropped");
            fileLabel.setStyle("-fx-font-size: 18px;");
            fileLabel.setLayoutX(10);
            fileLabel.setLayoutY(10);
            pane.getChildren().add(fileLabel);
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
    	HashMap<String,ArrayList<Object>> msg = new HashMap<>();
    	ArrayList<Object> arr = new ArrayList<>();
    	arr.add("Student");
    	msg.put("client", arr);
    	ArrayList<Object> task = new ArrayList<>();
		task.add("getExamFile");
		msg.put("task",task);
		ArrayList<Object> param = new ArrayList<>();
		param.add(examInfo.get("examId"));
		msg.put("param", param);
		sendMsgToServer(msg);
		ArrayList<HashMap<String,Object>> rs = ConnectionServer.rs;
		byte[] fileBytes = (byte[])rs.get(0).get("examFile");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Word Documents (*.doc)", "*.doc"));

        // Show the file save dialog
        Stage stage = new Stage();
        File saveFile = fileChooser.showSaveDialog(stage);

        if (saveFile != null) {
            if (!saveFile.getName().toLowerCase().endsWith(".doc")) {
                saveFile = new File(saveFile.getParentFile(), saveFile.getName() + ".doc");
            }

            try (FileOutputStream outputStream = new FileOutputStream(saveFile)) {
                outputStream.write(fileBytes);
                outputStream.close();
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
        filesDragged = true;
        pane.getStyleClass().add("dragged");
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
    		info.put("endTime", TimerHandler.GetCurrentTimestamp());
    		info.put("examId", examInfo.get("examId"));
    		info.put("studentId", ConnectionServer.getInstance().getUser().getId());
    		info.put("status", "Under Check");
    		param.add(info);
    		msg.put("param", param);
    		super.sendMsgToServer(msg);
    		ArrayList<HashMap<String,Object>> rs = ConnectionServer.rs;
            fileBytesList.clear();
            timerController.countdown.stop();
        }        
    }
    
    public void setExamInfo(ArrayList<HashMap<String,Object>> rs) {
    	examInfo.put("examId", (int)rs.get(0).get("examId"));
    	examInfo.put("startTime", timerHandler.GetCurrentTimestamp());
    	timeMode = new TimeMode((int)rs.get(0).get("duration") + 1);
        timerController = new TimerController();
		clock = new Clock(timerController,lblHour,lblMin,lblSec,progressBar,timeMode);
		timerController.start(clock, timeMode);
    }

}