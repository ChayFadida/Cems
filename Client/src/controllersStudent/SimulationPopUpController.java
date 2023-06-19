package controllersStudent;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Controller class for the student.
 * It Simulats Pop Up Controller
 */
public class SimulationPopUpController{

    @FXML
    private Button btnExit;
    
    @FXML
    private Label lblMsg;

    @FXML
    private Label txtEmail;
    /**
     * by activate the program is closed.
     * @param event
     */
    @FXML
    void getExitBtn(ActionEvent event) {
    	Stage currentStage = (Stage) btnExit.getScene().getWindow();
        currentStage.close();
    }
    /**
     * sets the email is the txtEmail lable.
     * @param email the email to set
     */
    public void viewEmail(String email) {
        txtEmail.setText(email);
    }
    /**
     * sets the message in the lblMsg lable.
     * @param msg the message to set.
     */
	public void setLblMsg(String msg) {
		lblMsg.setText(msg);
	}
    
}