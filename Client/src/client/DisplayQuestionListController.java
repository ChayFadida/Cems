package client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
public class DisplayQuestionListController {

	

	@FXML
	private Button btnDisplayQuestionList;

	@FXML
	private Text txtHello;

	@FXML
	private Text txtSelectOption;

	
    @FXML
	 void clickDisplayQuestionListBTN(MouseEvent event) throws Exception {
		Stage stage = new Stage();
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		//new page .start(stage);
	}
	
	public void start(Stage primaryStage) throws Exception {	
		Parent root = FXMLLoader.load(getClass().getResource("/clientGui/CEMS_GUI_DisplayQuestionListButtonScreen.fxml"));
		Scene scene = new Scene(root);
		//scene.getStylesheets().add(getClass().getResource("/gui/ServerPort.css").toExternalForm());
		primaryStage.setTitle("Lecturer Menu");
		primaryStage.setScene(scene);
		primaryStage.show();		
	}

}
