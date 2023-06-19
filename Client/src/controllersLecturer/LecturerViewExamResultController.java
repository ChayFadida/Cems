package controllersLecturer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * controller class for the lecturer.
 * In this controller the lecturer can view the automatic system exam check result.
 *
 */
public class LecturerViewExamResultController {

    @FXML
    private Button btnExit;

    @FXML
    private Label lblExamInfo;

    /**
     * set the result in the correct lable.
     * @param result
     */
    public void viewResult(String result) {
    	lblExamInfo.setText(result);
    }
    /**
     * close the current window.
     * @param event Action event.
     */
    @FXML
	public void getExitBtn(ActionEvent event) {
		Stage currentStage = (Stage) btnExit.getScene().getWindow();
        currentStage.close();
	}

}