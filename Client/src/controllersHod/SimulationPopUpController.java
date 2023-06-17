package controllersHod;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * HOD controller class.
 * This controller is for pop up window 
 *
 */
public class SimulationPopUpController {

    @FXML
    private Button btnExit;

    @FXML
    private Label txtEmail;

    /**
     * Close the program when pressing close button.
     * @param event Action event
     */
    @FXML
    void getExitBtn(ActionEvent event) {
    	Stage currentStage = (Stage) btnExit.getScene().getWindow();
        currentStage.close();
    }
    
    /**
     * Sets the email into the txtEmail label.
     * @param email
     */
    public void viewEmail(String email) {
        txtEmail.setText(email);
    }
}