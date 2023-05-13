package server;


import java.util.ArrayList;
import java.util.HashMap;

import ocsf.server.*;
import taskManager.TaskHandler;
import taskManager.TaskHandlerFactory;

import java.io.IOException;
import java.sql.ResultSet;
//echo server
public class ClientHandler extends AbstractServer {
	

	private int port = 8000;
	
	/**
	 *constructor for default port for server
	 * */
	public ClientHandler() {
		super(8000);
		this.port = 8000;
	}

	/**
	 *constructor for user to set server port
	 * */
	public ClientHandler(int port) {
		super(port);
		this.port = port;
	}
	
	/**
	 *let server to start listening to this.port
	 * */
	public void runServer() {
		try {
			listen();
		} catch (Exception ex) {
			System.out.println("ERROR - Could not listen for clients!");
		}
	}
	
	/**
	 *print to which port server is listening for
	 * */
	protected void serverStarted(){
		System.out.println("Server listening for connections on port " + getPort());
	}
	
	/**
	 *print when server is stopped
	 * */
	protected void serverStopped(){
		System.out.println("Server has stopped listening for connections.");
	}

	/**
	 * execute command from the client side
	 *@param msg message from the user to executer server comsand
	 *@param client client object of who sent the request
	 * */
	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		
		if(! (msg instanceof HashMap)) {
			System.out.println("client send object that is not hashmap");;
		}
		HashMap<String, ArrayList<String>> hm = (HashMap)msg;
		String str = getUserType(hm);
		TaskHandlerFactory taskHandlerFactory = new TaskHandlerFactory();
	    TaskHandler handlerMap = (TaskHandler) taskHandlerFactory.getTaskHadler().get(str);
	    ResultSet rs = handlerMap.executeUserCommand(msg);
	    try {
			client.sendToClient(rs);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 *get user type Teacher/Student/HOD
	 *@param client client object of who sent the request
	 * */
	private String getUserType(HashMap<String, ArrayList<String>> msg) {
		System.out.println("the user is " + msg.get("client").get(0));
		return msg.get("client").get(0);
	}
}
