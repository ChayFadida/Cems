package controllers;

import client.ConnectionServer;

public class AbstractController {
	public static String myUser = "Teacher";  //need to implement teacher class
	
	ConnectionServer connectionServer;
	
	public void sendMsgToServer(Object msg) {
		try {
			ConnectionServer.getInstance("localhost", 8000).handleMessageFromClientUI(msg);
			//connectionServer.handleMessageFromClientUI(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
