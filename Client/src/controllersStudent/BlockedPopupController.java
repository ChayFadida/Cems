
package controllersStudent;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Controller class for the student.
 * In this controller the student get in a pop up window that the exam has been blocked by the lecturer.
 * @author pisto
 *
 */
public class BlockedPopupController {
    
    @FXML
    private Button btnExit;
    /**
     * by activate close the current pop up  window 
     * @param event
     */
    @FXML
    void getExitBtn(ActionEvent event) {
    	((Stage) ((Node)event.getSource()).getScene().getWindow()).close();
    	
    }
}
