package controllersLecturer;

import abstractControllers.AbstractController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

public class ManageExamsController extends AbstractController{

    @FXML
    private Button AnalyizeExamButton;

    @FXML
    private Button ChangeDurationButton;

    @FXML
    private Button LockExamButton;

    @FXML
    private Button ViewExamButton;

    @FXML
    private TableView<?> examTable;

}
