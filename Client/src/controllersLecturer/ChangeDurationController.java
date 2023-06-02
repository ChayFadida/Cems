package controllersLecturer;

import abstractControllers.AbstractController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class ChangeDurationController extends AbstractController{

    @FXML
    private TextField HODidTXT;

    @FXML
    private Button SendRequestButton;

    @FXML
    private ImageView backButton;

    @FXML
    private TextField changeDurationTxt;

}
