// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import ocsf.client.*;
import common.*;
import java.io.*;
import java.sql.ResultSet;


public class ConnectionServer extends AbstractClient{
 
	private static ConnectionServer instance;
	public static ResultSet rs;
	public static boolean awaitResponse = false;
    public ConnectionServer(String host, int port) 
      throws IOException {
      super(host, port); //Call the superclass constructor
      openConnection();
    }

  
    public void handleMessageFromServer(Object msg) {
    	if (!( msg instanceof ResultSet)) {
    		System.out.println("not valid return from server");
    		return;
    	}
    	this.rs = (ResultSet) msg;
        System.out.println(msg.toString());
    }

    public void handleMessageFromClientUI(Object message){
	  
        try{	  
      	  sendToServer(message);
        } catch(IOException e){
      	 System.out.println("Could not send message to server.  Terminating client.");
    	 quit();
        }
    }
  
  
    public void quit(){
	  
        try{
            closeConnection();
           } catch(IOException e) {
      	    System.exit(0);
           }
    }
 
    
	public static ConnectionServer getInstance(String host, int port) throws IOException {
		if (instance == null)
			instance = new ConnectionServer(host, port);
		return instance;
	}

}