// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import ocsf.client.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;


public class ConnectionServer extends AbstractClient{
 
	private static ConnectionServer instance;
	public static ArrayList<HashMap<String,Object>> rs;
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
    	if (!( msg instanceof ArrayList)) {
    		System.out.println("not valid return from server");
    		return;
    	}
    	awaitResponse = false;
    	rs = (ArrayList<HashMap<String,Object>>) msg;
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
	public static ConnectionServer getInstance(String host, int port) throws IOException {
		if (instance == null)
			instance = new ConnectionServer(host, port);
		return instance;
	}
	
	/**
	 *this method connects to the server
	 *@return instance of the connection
	 * */
	public static ConnectionServer getInstance() throws IOException {
		if (instance == null) {
			System.out.println("you must fill the fields in the first connection");
			return null;
		}
		return instance;
	}
	

}