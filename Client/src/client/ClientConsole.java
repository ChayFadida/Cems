package client;
// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import java.sql.ResultSet;
import java.util.ArrayList;

import client.*;
import common.*;
import javafx.stage.Stage;

/**
 * This class constructs the UI for a chat client.  It implements the
 * chat interface in order to activate the display() method.
 * Warning: Some of the code here is cloned in ServerConsole 
 *
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Dr Timothy C. Lethbridge  
 * @author Dr Robert Lagani&egrave;re
 * @version July 2000
 */
public class ClientConsole implements ChatIF 
{
  //Class variables *************************************************
  
  /**
   * The default port to connect on.
   */
  final public static int DEFAULT_PORT = 5555;
  
  //Instance variables **********************************************
  
  /**
   * The instance of the client that created this ConsoleChat.
   */
  ChatClient client;

  
  //Constructors ****************************************************

  /**
   * Constructs an instance of the ClientConsole UI.
   *
   * @param host The host to connect to.
   * @param port The port to connect on.
   */
  public ClientConsole(String host, int port) 
  {
    try 
    {
      client= new ChatClient(host, port, this);
    } 
    catch(IOException exception) 
    {
      System.out.println("Error: Can't setup connection!"
                + " Terminating client.");
      System.exit(1);
    }
  }

  
  //Instance methods ************************************************
  
  /**
   * This method waits for input from the console.  Once it is 
   * received, it sends it to the client's message handler.
   */
  public void accept() 
  {
    try
    {
      //BufferedReader fromConsole = new BufferedReader(new InputStreamReader(System.in));
      //String message;
    	ArrayList<Integer> arr = new ArrayList<Integer>(2);
    	arr.add(5);
    	arr.add(7);
    	ArrayList<String> quary = new ArrayList<String>(3) ;
    	quary.add("456");
    	quary.add("nisim");
    	quary.add("garame");
    	String msg = "getQuary";
    	int i = 0;
      while (i<1) 
      {
        //message = fromConsole.readLine();
        //client.handleMessageFromClientUI(arr);
        client.handleMessageFromClientUI(msg);
        //client.handleMessageFromClientUI(quary);
        i++;
      }
    } 
    catch (Exception ex) 
    {
      System.out.println
        ("Unexpected error while reading from console!");
    }
  }

  /**
   * This method overrides the method in the ChatIF interface.  It
   * displays a message onto the screen.
   *
   * @param message The string to be displayed.
   */
  public void display(Object message) 
  {
	  if(message instanceof ArrayList) {
		  @SuppressWarnings("unchecked")
		  ArrayList<Integer> arr = (ArrayList<Integer>)message;
    System.out.println(">array: " + arr.get(0) + ", " + arr.get(1));
	  }
	  if(message instanceof String) {
		  System.out.println("> " + (String)message.toString());	
	  }
	  if(message instanceof ResultSet) {//handling recieving RS from server.
		  System.out.println(message.toString());
	  }
  }

  
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of the Client UI.
   *
   * @param args[0] The host to connect to.
   * 
   //Eyal edit from here delete exception , delete the call for a stage.
    * @throws Exception 
   */
  public static void main(String[] args) throws Exception 
  {
    String host = "";
    int port = 0;  //The port number

    try
    {
      host = args[0];
    }
    catch(ArrayIndexOutOfBoundsException e)
    {
      host = "localhost";
    }
    //ClientConsole chat= new ClientConsole(host, DEFAULT_PORT);
   // chat.accept();  //Wait for console data
    try {
    	ClientConnectController clientConnectController = new ClientConnectController();
    	Stage stage = new Stage();
    	clientConnectController.start(stage);
    	}catch(Exception e) {e.printStackTrace();}
  }
}
//End of ConsoleChat class
