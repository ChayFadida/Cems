package controllersLecturer;

import java.util.ArrayList;
import java.util.HashMap;

import abstractControllers.AbstractController;
import client.ConnectionServer;
import entities.Exam;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
/**
 * Controller class for the lecturer.
 * In this controller the lecturer can ask the HOD to approve his request to change exam duration.  
 */
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
	
	/**
	 * Setter for the exam.
	 * @param exam to set.
	 */
	public void setExam(Exam exam) {
		this.exam = exam;
	}
    
	/**
	 * Close the current window.
	 * @param event Action event
	 */
    @FXML
    void Close(ActionEvent event) {
        Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    /**
     * Minimize the current window.
     * @param event Action event
     */
    @FXML
    void Minimize(ActionEvent event) {
    	Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    
    /**
     * By activate, the lecturer send to the HOD's from the same department the change duration request.
     * @param event Action event
     */
    @FXML
    void sendRequest(ActionEvent event) {
    	HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> user = new ArrayList<>();
		user.add("Lecturer");
		msg.put("client", user);
		ArrayList<String> query = new ArrayList<>();
		query.add("AddDurationRequest");
		msg.put("task",query);
		ArrayList<String> parameter = new ArrayList<>();
		parameter.add(exam.getExamId() + "");
		parameter.add(ConnectionServer.user.getId() + "");
		parameter.add(exam.getCourseId() + "");
		parameter.add(exam.getSubject());
		parameter.add(exam.getDuration() + "");
		parameter.add(txtNewDuration.getText());
		parameter.add("inProgress");
		parameter.add(txtRequestDetails.getText());
		msg.put("param", parameter);
		super.sendMsgToServer(msg);

		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
    }
    
    /**
     * Loads the old duration
     * @param e exam.
     */
    void LoadExamOldDuration(Exam e) {
    	txtOldDuration.setText(e.getDuration() + "");
    	txtOldDuration.setEditable(false);
    }
}