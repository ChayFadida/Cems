package abstractControllers;

import client.ConnectionServer;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public abstract class AbstractController {	
	ConnectionServer connectionServer;
	Stage primaryStage;
	double xOffset = 0; 
	double yOffset = 0;
	/**
	 *this method sends message tot he server
	 *@param Object msg
	 * */	
	public void sendMsgToServer(Object msg) {
		try {
			ConnectionServer.getInstance().handleMessageFromClientUI(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	
	public class PressHandler<T extends Event> implements EventHandler<T>{
		public PressHandler() {
			super();
		}
		@Override
		public void handle(T event) {
			xOffset = ((MouseEvent) event).getSceneX();
            yOffset = ((MouseEvent) event).getSceneY();
			
		}
	}
	public class DragHandler<T extends Event> implements EventHandler<T>{
		@Override
		public void handle(T event) {
			primaryStage.setX(((MouseEvent) event).getScreenX() - xOffset);
        	primaryStage.setY(((MouseEvent) event).getScreenY() - yOffset);
		}
		
	}
}
