package controllersStudent;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class BlockedPopupController {
    private StudentMenuController sharedController;

    public void setSharedController(StudentMenuController sharedController) {
        this.sharedController = sharedController;
    }
    
    @FXML
    private Button btnExit;

    @FXML
    void getExitBtn(ActionEvent event) {
    	((Stage) ((Node)event.getSource()).getScene().getWindow()).close();
    	
    }

}
