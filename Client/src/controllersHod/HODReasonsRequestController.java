package controllersHod;

import abstractControllers.AbstractController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 * Controller class for the HOD.
 * In this controller the HOD will see all of his reasons for changing the time duration.
 * Extends AbstractController.
 */

public class HODReasonsRequestController extends AbstractController{
	Image img = new Image("Images/exit.png");
    @FXML
    private Label txtReasons;
    @FXML
    private Button btnExit;
    @FXML
    private ImageView imgExit;
    
    /**
     * Display the lecturer reasons for changing the exam duration
     * @param reasons Show the reasons
     */
    public void viewReason(String reasons) {
        txtReasons.setText(reasons);
    }
    /**
     * exit image for the btnExit
     */
    public void setExitImage() {
    	imgExit.setImage(img);
    }
    
    /**
     * close current window when clicked.
     * @param event 
     */
    @FXML
	public void getExitBtn(ActionEvent event) {
		Stage currentStage = (Stage) btnExit.getScene().getWindow();
        currentStage.close();
	}
}

