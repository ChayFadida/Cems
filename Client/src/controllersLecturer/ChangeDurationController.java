package controllersLecturer;

import abstractControllers.AbstractController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ChangeDurationController extends AbstractController{

    @FXML
    private Button CloseBtn;
    @FXML
    private TextField HODidTXT;

    @FXML
    private Button MinimizeBtn;

    @FXML
    private Button SendRequestButton;

    @FXML
    private TextField changeDurationTxt;
    
    @FXML
    private TextField txtOldTime;
    @FXML
    private TextField txtNewTime;

    
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
	        Parent root =  FXMLLoader.load(getClass().getResource("/guiLecturer/ChangeDuration.fxml"));
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