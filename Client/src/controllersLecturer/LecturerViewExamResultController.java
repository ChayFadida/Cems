package controllersLecturer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class LecturerViewExamResultController {

    @FXML
    private Button btnExit;

    @FXML
    private Label lblExamInfo;

    public void viewReason(String reasons) {
    	lblExamInfo.setText(reasons);
    }
    @FXML
	public void getExitBtn(ActionEvent event) {
		Stage currentStage = (Stage) btnExit.getScene().getWindow();
        currentStage.close();
	}

}