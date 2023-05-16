package controllers;

import client.ConnectionServer;

public class AbstractController {	
	ConnectionServer connectionServer;
	
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
}
