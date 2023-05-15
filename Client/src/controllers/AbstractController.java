package controllers;

import client.ConnectionServer;

public class AbstractController {	
	ConnectionServer connectionServer;
	
	public void sendMsgToServer(Object msg) {
		try {
			ConnectionServer.getInstance("localhost",8000).handleMessageFromClientUI(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
