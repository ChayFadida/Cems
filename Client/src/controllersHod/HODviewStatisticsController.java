package controllersHod;

import abstractControllers.AbstractController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.*;

/**
 * HOD Controller class.
 * Showing to the HOD three options to get his statistic reports: by student/course/lecturer.
 * Extends AbstractController. 
 *
 */
public class HODviewStatisticsController extends AbstractController{

    @FXML
    private Button ByCourseButton;

    @FXML
    private Button ByLecturerButton;

    @FXML
    private Button ByStudentButton;

    @FXML
    private AnchorPane ap;

    /**
     * Handle action event when user press "by course" button.
     * @param event Action Event.
     */
    @FXML
    void ByCourse(ActionEvent event) {
    	Stage primaryStage = new Stage();
    	HODviewStatisticsByCourseController hODviewStatisticsByCourseController = new HODviewStatisticsByCourseController();
   		hODviewStatisticsByCourseController.start(primaryStage);
    }

    /**
     * Handle action event when user press "by lecturer" button.
     * @param event Action Event.
     */
    @FXML
    void ByLecturer(ActionEvent event) {
    	Stage primaryStage = new Stage();
    	HODviewStatisticsByLecturerController hODviewStatisticsByLecturerController = new HODviewStatisticsByLecturerController();
   		hODviewStatisticsByLecturerController.start(primaryStage);
    }

    /**
     * Handle action event when user press "by student" button.
     * @param event Action Event.
     */
    @FXML
    void ByStudent(ActionEvent event) {
    	Stage primaryStage = new Stage();
    	HODviewStatisticsByStudentController hODviewStatisticsByStudentController = new HODviewStatisticsByStudentController();
   		hODviewStatisticsByStudentController.start(primaryStage);
    }
}