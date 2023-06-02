package controllersStudent;

import abstractControllers.AbstractController;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

public class MyExamController extends AbstractController{

    @FXML
    private TableView<?> ExamTable;

    @FXML
    private Text getCheckedCopyButton;

}
