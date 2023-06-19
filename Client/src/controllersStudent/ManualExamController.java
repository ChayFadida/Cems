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
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.FileOutputStream;

import abstractControllers.AbstractController;
import client.ConnectionServer;
import entities.Student;
import javafx.stage.Stage;
import timer.TimerHandler;
import timer.Clock;
import timer.TimeMode;
import timer.TimerController;
import javafx.stage.FileChooser;

/**
 * Controller class for the student.
 * In this controller the student can preform manual exam.
 * extends AbstractController.
 * @author pisto
 *
 */
public class ManualExamController extends AbstractController {
    private List<byte[]> fileBytesList = new ArrayList<>();
    private List<File> fileList = new ArrayList<>();
	private HashMap<String, Object> examInfo = new HashMap<>();
	private Stage thisStage;
	private ArrayList<HashMap<String,Object>> rs= new ArrayList<>();
	private String startTime;

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
    private Button btnMinimize;

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
    
    /**
     * Handles the event when files are dropped onto the associated control during a drag and drop operation.
     * @param event The DragEvent object representing the drag and drop event.
     */
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
    
    /**
     * Handles the event when the browse button is clicked.
     * It opens a file chooser dialog to allow the user to select multiple files.
     * @param event The ActionEvent object representing the browse button click event.
     */
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
    
    /**
     * This method handles the event when the download button is clicked.
     * It sends a request to the server to retrieve the exam file and saves it to the local filesystem.
     * The file is saved with a .doc extension and displayed in the file save dialog.
     *
     * @param event The ActionEvent object representing the download button click event.
     */
    @FXML
    void getDownloadBtn(ActionEvent event) {
    	HashMap<String,ArrayList<Object>> msg = new HashMap<>();
    	ArrayList<Object> user = new ArrayList<>();
    	user.add("Student");
    	msg.put("client", user);
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
    
    /**
     * Handles the drag over event when files are dragged over the designated area.
     * If the drag event contains files, it accepts the transfer mode as COPY.
     * @param event DragEvent 
     */
    @FXML
    void getDragOver(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(javafx.scene.input.TransferMode.COPY);
        }
        event.consume();
        pane.getStyleClass().add("dragged");
    }

    /**
     * Handles the upload button click event.
     * If the `fileBytesList` is not empty, it prepares the necessary information to upload the tests to the database.
     * @param event ActionEvent 
     */
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
            thisStage.close();
            if(TakeExamController.checkInProgressStudents((int) examInfo.get("examId"))==0)
            	TakeExamController.lockExam((int) examInfo.get("examId"));
        }        
    }
    /**
     * Sets the exam information and initializes the exam session.
     * It receives the server response containing the exam details,
     * as well as the stage of the current window.
     * @param rs    The server response containing the exam details
     * @param stage The stage of the current window
     */
    public void setExamInfo(ArrayList<HashMap<String,Object>> rs,Stage stage) {
    	this.rs=rs;
    	thisStage=stage;
    	examInfo.put("examId", (int)rs.get(0).get("examId"));
    	startTime =TimerHandler.GetCurrentTimestamp();
    	timeMode = new TimeMode((int)rs.get(0).get("duration") + 1);
        timerController = new TimerController();
        Student student = (Student) ConnectionServer.getInstance().getUser();
		clock = new Clock(timerController,lblHour,lblMin,lblSec,progressBar,timeMode);
		student.setExamSession(clock);
		timerController.start(clock, timeMode,"Manual",this);
		int inserted = TakeExamController.insertToExamresults((Integer) rs.get(0).get("examId"),ConnectionServer.user.getId(),"Manual",startTime);
		if(inserted!=1) {
			System.out.println("Problem at inserting to examresults");
			timerController.countdown.stop();
			thisStage.close();
			return;
		}
    }
    /**
     * retrun current stage.
     * @return current stage
     */
	public Stage getStage() {
		return thisStage;
	}
	/**
	 * return the result set from the data base.
	 * @return result set from the db.
	 */
	public ArrayList<HashMap<String, Object>> getRs() {
		return rs;
	}
	/**
	 * minimize current window.
	 * @param event ActionEvent
	 */
    @FXML
    void getMinimizeBtn(ActionEvent event) {
    	Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    
}