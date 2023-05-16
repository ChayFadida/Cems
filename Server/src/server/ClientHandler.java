package server;


import java.util.ArrayList;

import java.util.HashMap;

import ocsf.server.*;
import taskManager.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
//echo server
public class ClientHandler extends AbstractServer {
	
	private static ClientHandler instance;
	private int port = 8000;
	
	/**
	 *constructor for default port for server
	 * */
	private ClientHandler() {
		super(8000);
		this.port = 8000;
	}

	/**
	 *constructor for user to set server port
	 * */
	private ClientHandler(int port) {
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
	 *singletone design pattern
	 *@return instance the instance of the class
	 * */
	public static ClientHandler getInstance() {
		if (instance == null) {
			instance = new ClientHandler();
		}
		return instance;
	}
	
	/**
	 *singletone design pattern
	 *@param port the port to listen for
	 *@return instance the instance of the class
	 * */
	public static ClientHandler getInstance(int port) {
		if (instance == null) {
			instance = new ClientHandler(port);
		}
		return instance;
	}
	
	/**
	 *stop listening for port
	 * */
	public void stopserver() {
		try {
			close();
		} catch (IOException e) {
			e.printStackTrace();
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
	 *@param msg message from the user to executer server command
	 *@param client client object of who sent the request
	 * */
	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		
		if(! (msg instanceof HashMap)) {
			System.out.println("client send object that is not hashmap");;
		}
		HashMap<String, ArrayList<String>> hm = (HashMap<String, ArrayList<String>>)msg;
		String clientType = getUserType(hm);
	    TaskHandler handlerMap = (TaskHandler) TaskHandlerFactory.getInstance().getTaskHandler().get(clientType);
	    ArrayList<HashMap<String, Object>> rs = handlerMap.executeUserCommand(msg);
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
		return msg.get("client").get(0);
	}
}