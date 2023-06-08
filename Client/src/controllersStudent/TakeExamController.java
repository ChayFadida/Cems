package controllersStudent;

import abstractControllers.AbstractController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class TakeExamController extends AbstractController{

    @FXML
    private Button BeginExamButton;

    @FXML
    private TextField examCodeTxt;

    @FXML
    private TableView<?> examTable;

}
