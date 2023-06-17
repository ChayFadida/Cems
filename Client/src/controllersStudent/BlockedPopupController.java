package controllersStudent;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class BlockedPopupController {

    @FXML
    private Button btnExit;

    @FXML
    void getExitBtn(ActionEvent event) {
    	((Stage) ((Node)event.getSource()).getScene().getWindow()).close();
    }

}
