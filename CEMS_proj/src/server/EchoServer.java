package server;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ocsf.server.*;

/*
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
*/
public class EchoServer extends AbstractServer 
{
	
  //Class variables 
  
  /**
   * The default port to listen on.
   */
	private Connection conn;
	final public static int DEFAULT_PORT = 5555;
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
	public EchoServer(int port) 
	{
		super(port);
	}

  
  //Instance methods ************************************************
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
	public void handleMessageFromClient(Object msg, ConnectionToClient client){
		    if(msg instanceof String) {
		    	Statement stmt;
    			ResultSet rs;
		    	switch((String)msg){
		    		case "getQuery":
		    			
		    			try{
		    				stmt = conn.createStatement();
		    				rs = stmt.executeQuery("SELECT * FROM cems_db.question");
		    				while(rs.next()) {
		    					System.out.format("id: %s , subject: %s , courseName: %s, questionText: %s, questionNumber: %s, lecturer(author): %s \n",rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6));
		    				}
		    			}catch (SQLException e) {e.printStackTrace();}
		    		
		    		default:
		    			System.out.println("ACID");
		    		}	
		    	}
		    
		    if(msg instanceof ArrayList) {
		    	Statement stmt;
				PreparedStatement pst;
				try{
					stmt = conn.createStatement();
					pst  = conn.prepareStatement("UPDATE ex5engimethods.student SET stdID = ?,stdName = ?,stdLastName = ?");
					@SuppressWarnings("unchecked")
					ArrayList<String> arr = (ArrayList<String>)msg;
					pst.setString(1, arr.get(0));
					pst.setString(2, arr.get(1));
					pst.setString(3, arr.get(2));
					pst.executeUpdate();
				}catch (SQLException e) {e.printStackTrace();}
			    }    
	}
  private void connectToDB(String dbName , String userID, String password) {
	  StringBuilder connectionPath = new StringBuilder();
	  connectionPath.append("jdbc:mysql://localhost/");
	  connectionPath.append(dbName);
	  connectionPath.append("?serverTimezone=IST"); 
	  try 
		{
		//Class.forName("com.mysql.cj.jdbc.Driver").newInstance();//deprecated since ver 9.
	    Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
	    System.out.println("Driver definition succeed");
	    } catch (Exception ex) {
	    /* handle the error*/
	    System.out.println("Driver definition failed");
	    } 
	    try 
	    {
		   conn = DriverManager.getConnection(connectionPath.toString(),userID,password);
	       //conn = DriverManager.getConnection("jdbc:mysql://localhost/cems_db?serverTimezone=IST","root","EyalMySql");
	       System.out.println("SQL connection succeed");
	    } catch (SQLException ex) {/* handle any errors*/
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
           }
  }
	

  

    
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
    System.out.println
      ("Server listening for connections on port " + getPort());
    connectToDB("cems_db" , "root", "EyalMySql");

  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
    System.out.println
      ("Server has stopped listening for connections.");
  }
  
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of 
   * the server instance (there is no UI in this phase).
   *
   * @param args[0] The port number to listen on.  Defaults to 5555 
   *          if no argument is entered.
   */
  public static void main(String[] args) 
  {
    int port = 0; //Port to listen on

    try
    {
      port = Integer.parseInt(args[0]); //Get port from command line
    }
    catch(Throwable t)
    {
      port = DEFAULT_PORT; //Set port to 5555
    }
	
    EchoServer sv = new EchoServer(port);
    
    try 
    {
      sv.listen(); //Start listening for connections
    } 
    catch (Exception ex) 
    {
      System.out.println("ERROR - Could not listen for clients!");
    }
  }
}
//End of EchoServer class
