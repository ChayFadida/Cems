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

public class LockAreYouSureController extends AbstractController{
	Exam exam;

    @FXML
    private Button btnNo;

    @FXML
    private Button btnYes;
    
	public void setExam(Exam exam) {
		this.exam = exam;
	}

    @FXML
    void getNoBtn(ActionEvent event) {
    	((Stage) ((Node)event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    void getYesBtn(ActionEvent event) {
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> arr = new ArrayList<>();
		arr.add("Lecturer");
		msg.put("client", arr);
		ArrayList<String> arr1 = new ArrayList<>();
		arr1.add("LockExamById");
		msg.put("task",arr1);
		ArrayList<String> arr2 = new ArrayList<>();
		arr2.add(exam.getExamId() + "");
		msg.put("param", arr2);
		super.sendMsgToServer(msg);
		((Stage) ((Node)event.getSource()).getScene().getWindow()).close();
    }


}
