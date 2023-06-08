package controllersHod;

import abstractControllers.AbstractController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class HODviewStatisticsByCourseController extends AbstractController{
	private double xOffset = 0; 
	private double yOffset = 0;
	

    @FXML
    private TextField CourseAvaregeTxt;

    @FXML
    private BarChart<?, ?> CourseBarChart;

    @FXML
    private TextField CourseMedianTxt;

    @FXML
    private TextField CourseNumberTxt;

    @FXML
    private Text ExamMedianTxt;

    @FXML
    private ImageView backButton;
    @FXML
    private Button exitBtn;

    @FXML
    private Button minimizeBtn;


	public void start(Stage primaryStage) {
		try {
	        Parent root =  FXMLLoader.load(getClass().getResource("/guiHod/ViewStatisticsByCourse.fxml"));
	        Scene scene = new Scene(root);
	        primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.getIcons().add(new Image("/Images/CemsIcon32-Color.png"));
	        // Set the scene to the primary stage
	        primaryStage.setScene(scene);
	        primaryStage.show();
	        
	        root.setOnMousePressed((EventHandler<? super MouseEvent>) new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	                xOffset = event.getSceneX();
	                yOffset = event.getSceneY();
	            }
	        });
	        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	            	primaryStage.setX(event.getScreenX() - xOffset);
	            	primaryStage.setY(event.getScreenY() - yOffset);
	            }
	        });
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
		
	}
}
