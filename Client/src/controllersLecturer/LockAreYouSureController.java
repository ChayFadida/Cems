package controllersLecturer;

import java.util.ArrayList;
import java.util.HashMap;

import abstractControllers.AbstractController;
import entities.Exam;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * 
 * controller class for the lecturer.
 * In this controller the lecturer choose if he/she is sure in activating the lock exam feature.
 *
 */
public class LockAreYouSureController extends AbstractController{
	Exam exam;
	ManageExamsController manageExamsController;

	@FXML
    private Button btnNo;

    @FXML
    private Button btnYes;
    /**
     *  Set the exam.
     * @param exam
     */
	public void setExam(Exam exam) {
		this.exam = exam;
	}
  
	/**
	 * Setter for ManageExamController field
	 * @param event Action event
	 */
    public void setManageExamsController(ManageExamsController manageExamsController) {
		this.manageExamsController = manageExamsController;
	}
  /**
	 * If lecturer press no -> nothing happens and the window is closed.
	 * @param event Action event
	 */
    @FXML
    void getNoBtn(ActionEvent event) {
    	((Stage) ((Node)event.getSource()).getScene().getWindow()).close();
    }
    /**
     * If lecturer press yes -> the exam is locked.
     * @param event Action event
     */
    @FXML
    void getYesBtn(ActionEvent event) {
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> user = new ArrayList<>();
		user.add("Lecturer");
		msg.put("client", user);
		ArrayList<String> query = new ArrayList<>();
		query.add("LockExamById");
		msg.put("task",query);
		ArrayList<String> parameter = new ArrayList<>();
		parameter.add(exam.getExamId() + "");
		msg.put("param", parameter);
		super.sendMsgToServer(msg);
		manageExamsController.showTable();
		((Stage) ((Node)event.getSource()).getScene().getWindow()).close();
    }


}
