package controllersHod;

import abstractControllers.AbstractController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.*;

public class HODviewStatisticsController extends AbstractController{

    @FXML
    private Button ByCourseButton;

    @FXML
    private Button ByLecturerButton;

    @FXML
    private Button ByStudentButton;

    @FXML
    private AnchorPane ap;

    @FXML
    void ByCourse(ActionEvent event) {
    	//((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
    	Stage primaryStage = new Stage();
    	HODviewStatisticsByCourseController hODviewStatisticsByCourseController = new HODviewStatisticsByCourseController();
   		//need to implement start method in AddNewQuestionController and then -->
   		hODviewStatisticsByCourseController.start(primaryStage);
    }

    @FXML
    void ByLecturer(ActionEvent event) {
    	//((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
    	Stage primaryStage = new Stage();
    	HODviewStatisticsByLecturerController hODviewStatisticsByLecturerController = new HODviewStatisticsByLecturerController();
   		//need to implement start method in AddNewQuestionController and then -->
   		hODviewStatisticsByLecturerController.start(primaryStage);
    }

    @FXML
    void ByStudent(ActionEvent event) {
    	//((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
    	Stage primaryStage = new Stage();
    	HODviewStatisticsByStudentController hODviewStatisticsByStudentController = new HODviewStatisticsByStudentController();
   		//need to implement start method in AddNewQuestionController and then -->
   		hODviewStatisticsByStudentController.start(primaryStage);
    }

}