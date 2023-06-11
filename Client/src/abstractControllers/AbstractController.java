package abstractControllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import client.ConnectionServer;
import entities.User;
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
	public boolean logoutRequest(User user) throws Exception {
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> arr = new ArrayList<>();
		arr.add("User");
		msg.put("client", arr);
		ArrayList<String> arr1 = new ArrayList<>();
		arr1.add("logoutAttempt");
		msg.put("task",arr1);
		ArrayList<String> arr2 = new ArrayList<>();
		arr2.add(user.getId()+"");
		msg.put("details",arr2);
		sendMsgToServer(msg);
		ArrayList<HashMap<String, Object>> rs;
		rs = ConnectionServer.rs;
		if(!rs.isEmpty()) {
			String access = (String)rs.get(0).get("access");
			switch (access){
				case "approved":
					return Integer.parseInt((String)rs.get(0).get("response"))==(Integer)user.getId();
				case "denied":
					if(Integer.parseInt((String)rs.get(0).get("response"))==(Integer)user.getId()) {
						return false;
					}
					throw new Exception();
			}
		}
		return false;
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
