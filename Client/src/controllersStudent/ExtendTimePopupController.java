package controllersStudent;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
/**
 * Controller class for the students.
 * In this controller the sudent notify in a pop up window that his exam duration has been changed.
 */
public class ExtendTimePopupController {
    
    @FXML
    private Button btnExit;
    /**
     * By activate, close the current pop up window.
     * @param event Action event
     */
    @FXML
    void getExitBtn(ActionEvent event) {
    	((Stage) ((Node)event.getSource()).getScene().getWindow()).close();
    	
    }

}
