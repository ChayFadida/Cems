package controllersHod;

import abstractControllers.AbstractController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class HODReasonsRequestController extends AbstractController{
	Image img = new Image("Images/exit.png");
    @FXML
    private Label txtReasons;
    @FXML
    private Button btnExit;
    @FXML
    private ImageView imgExit;
    
    public void viewReason(String reasons) {
        txtReasons.setText(reasons);
    }
    public void setExitImage() {
    	imgExit.setImage(img);
    }
    @FXML
	public void getExitBtn(ActionEvent event) {
		Stage currentStage = (Stage) btnExit.getScene().getWindow();
        currentStage.close();
	}
}

