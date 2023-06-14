package controllersLecturer;

import java.util.ArrayList;
import java.util.HashMap;

import abstractControllers.AbstractController;
import client.ConnectionServer;
import entities.Exam;
import entities.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import thirdPart.JsonHandler;

public class ChangeDurationController extends AbstractController{
	Exam exam;

	@FXML
	private Button CloseBtn;

	@FXML
	private Button MinimizeBtn;

	@FXML
	private Button SendRequestButton;

	@FXML
	private TextField txtNewDuration;

	@FXML
	private TextField txtOldDuration;

	@FXML
	private TextField txtRequestDetails;
	
	public void setExam(Exam exam) {
		this.exam = exam;
	}
    
    @FXML
    void Close(ActionEvent event) {
        Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    void Minimize(ActionEvent event) {
    	Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    
    @FXML
    void sendRequest(ActionEvent event) {
    	HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> arr = new ArrayList<>();
		arr.add("Lecturer");
		msg.put("client", arr);
		ArrayList<String> arr1 = new ArrayList<>();
		arr1.add("AddDurationRequest");
		msg.put("task",arr1);
		ArrayList<String> arr2 = new ArrayList<>();
		arr2.add(exam.getExamId() + "");
		arr2.add(ConnectionServer.user.getId() + "");
		arr2.add(exam.getCourseId() + "");
		arr2.add(exam.getSubject());
		arr2.add(exam.getDuration() + "");
		arr2.add(txtNewDuration.getText());
		arr2.add("inProgress");
		arr2.add(txtRequestDetails.getText());
		msg.put("param", arr2);
		super.sendMsgToServer(msg);

		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
    }
    
    void LoadExamOldDuration(Exam e) {
    	txtOldDuration.setText(e.getDuration() + "");
    	txtOldDuration.setEditable(false);
    }
}