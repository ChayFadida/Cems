package controllersLecturer;

import abstractControllers.AbstractController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;

public class MyExamBankController extends AbstractController{

    @FXML
    private ComboBox<?> CourseComboBox;

    @FXML
    private Button DeleteExamButton;

    @FXML
    private Button EditExamButton;

    @FXML
    private TableView<?> examTable;

}
