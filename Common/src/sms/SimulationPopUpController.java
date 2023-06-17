package sms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class SimulationPopUpController{

    @FXML
    private Button btnExit;
    
    @FXML
    private Label lblMsg;

    @FXML
    private Label txtEmail;

    @FXML
    void getExitBtn(ActionEvent event) {
    	Stage currentStage = (Stage) btnExit.getScene().getWindow();
        currentStage.close();
    }
    public void viewEmail(String email) {
        txtEmail.setText(email);
    }
	public void setLblMsg(String msg) {
		lblMsg.setText(msg);
	}
    
}