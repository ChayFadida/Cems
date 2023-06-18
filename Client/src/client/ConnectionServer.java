// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import ocsf.client.*;
import timer.CountDown;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import controllersStudent.TakeExamController;
import entities.User;
import javafx.application.Platform;
 

public class ConnectionServer extends AbstractClient{
 
	private static ConnectionServer instance;
	public static ArrayList<HashMap<String,Object>> rs;
	public static User user;
	public static boolean awaitResponse = false;

	public ConnectionServer(String host, int port) 
      throws IOException {
		super(host, port); //Call the superclass constructor
		openConnection();
    }


    /**
	 *this method handle with command from the server
	 *@param msg from the server
	 * */
    public void handleMessageFromServer(Object msg) {
    	if (msg instanceof ArrayList) {
        	awaitResponse = false;
        	rs = (ArrayList<HashMap<String,Object>>) msg;
    	    // Handle the ArrayList return type here
    	} else if (msg instanceof HashMap) {
    		specialMethod((HashMap<String, Object>) msg);
    	} else {
    	    System.out.println("Not a valid return from the server");
    	    rs = null;
    	}

    }
    
    /**
	 *this method handle with command from the clientUI, wait for response of the server
	 *@param Object message
	 * */
    public void handleMessageFromClientUI(Object message){
	  
    	try
        {
        	openConnection();//in order to send more than one message
           	awaitResponse = true;
        	sendToServer(message);
    		// wait for response
    		while (awaitResponse) {
    			try {
    				Thread.sleep(100);
    			} catch (InterruptedException e) {
    				e.printStackTrace();
    			}
    		}
        }
        catch(IOException e)
        {
        	e.printStackTrace();
          System.out.println("Could not send message to server: Terminating client."+ e);
          quit();
        }
    }
  
    /**
	 *this method close the connection and terminate the process
	 * */
    public void quit(){
	  
        try{
            closeConnection();
           } catch(IOException e) {
      	    System.exit(0);
           }
    }
 
    /**
	 *this method connects to the server
	 *@param String host, int port
	 *@return instance of the connection
	 * */
	public static ConnectionServer getInstance(String host, int port){
		try {
			if (instance == null)
				instance = new ConnectionServer(host, port);
		} catch (Exception exception) {
			System.out.println("could not create instance of Connection Server");
		}
		return instance;
	}
	
	/**
	 *this method connects to the server
	 *@return instance of the connection
	 * */
	public static ConnectionServer getInstance(){
		if (instance == null) {
			return null;
		}
		return instance;
	}
	
	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		ConnectionServer.user = user;
	}
	
	public void specialMethod(HashMap<String, Object> msg) {
	    String message = (String) msg.get("Special Method");
	    if (message.equals("EXAM_BLOCKED")) {
	    	ArrayList<Integer> idToLock = (ArrayList<Integer>) msg.get("idToLock");
	        int userId = ConnectionServer.instance.getUser().getId();
	    	if(idToLock.contains(userId)){
	    		Platform.runLater(() -> {
	            	TakeExamController.showBlockedPage();
	            	CountDown.blockExam();
	        	});
	    	}
	    }
	}
}