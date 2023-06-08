package timer;

import java.io.IOException;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TimerExampleApp extends Application{
	 private static Scene scene;

	 @Override
	 public void start(Stage stage) {
	     try {
			scene = new Scene(FXMLLoader.load(getClass().getResource("TimerExample.fxml")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     stage.setScene(scene);
	     stage.show();
	 }

//	 static void setRoot(String fxml) throws IOException {
//	     scene.setRoot(loadFXML(fxml));
//	 }

//	 private static Parent loadFXML(String fxml) throws IOException {
//	     //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml + ".fxml"));
//	     //return FXMLLoader.load(getClass().getResource(fxml + ".fxml"));
//	 }

	 @Override
	 public void stop() {
	     System.exit(0);
	 }

	 public static void main(String[] args) {
	     launch(args);
	 }

}
