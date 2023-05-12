package server;


import ocsf.server.*;
import taskManager.TaskHandler;
import taskManager.TaskHandlerFactory;

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
	 *@param msg message from the user to executer server command
	 *@param client client object of who sent the request
	 * */
	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
	    TaskHandler handlerMap = (TaskHandler) TaskHandlerFactory.getTaskHadler().get(getUserType(client));
	    handlerMap.executeUserCommand(msg, client);
	}
	
	/**
	 *get user type Teacher/Student/HOD
	 *@param client client object of who sent the request
	 * */
	private String getUserType(ConnectionToClient client) {
		// TODO Auto-generated method stub  // need to implement the method
		return "hello";
	}
}
