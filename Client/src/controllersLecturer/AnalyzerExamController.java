package controllersLecturer;

import abstractControllers.AbstractController;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class AnalyzerExamController extends AbstractController{

    @FXML
    private BarChart<?, ?> BarChart;

    @FXML
    private TextField ExamAvaregeTxt;

    @FXML
    private TextField ExamMedianTxt;

    @FXML
    private TextField ExamNumberTxt;

    @FXML
    private ImageView backButton;

}

