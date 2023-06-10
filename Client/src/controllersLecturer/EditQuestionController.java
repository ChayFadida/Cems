package controllersLecturer;

import java.awt.Label;
import java.awt.TextField;

import abstractControllers.AbstractController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class EditQuestionController extends AbstractController{
	@FXML
	private Button CloseBtn;

	@FXML
	private MenuButton CoursesMenu;

	@FXML
	private Button MinimizeBtn;

	@FXML
	private TextField NotesField;

	@FXML
	private TextField QuestionField;

	@FXML
	private Button SaveChangesButton;

	@FXML
	private TextField answer1Field;

	@FXML
	private TextField answer2Field;

	@FXML
	private TextField answer3Field;

	@FXML
	private TextField answer4Field;

	@FXML
	private ComboBox<?> cmbRightAnswer;

	@FXML
	private Label lblCourses;

	@FXML
	private Label lblError;

	@FXML
	private TextField txtSubject;

    
    @FXML
    void Close(ActionEvent event) {
        Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    void Minimize(ActionEvent event) {
    	Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    
    public void start(Stage primaryStage) {
	    try {
	        Parent root =  FXMLLoader.load(getClass().getResource("/guiLecturer/EditQuestion.fxml"));
	        Scene scene = new Scene(root);
	        primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.getIcons().add(new Image("/Images/CemsIcon32-Color.png"));
	        // Set the scene to the primary stage
	        primaryStage.setScene(scene);
	        primaryStage.show();
	        super.setPrimaryStage(primaryStage);
	        PressHandler<MouseEvent> press = new PressHandler<>();
	        DragHandler<MouseEvent> drag = new DragHandler<>();
	        root.setOnMousePressed(press);
	        root.setOnMouseDragged(drag);
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
	}

}
