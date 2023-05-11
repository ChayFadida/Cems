package server;


import ocsf.server.*;

public class ClientHandler extends AbstractServer {
	

	private int port = 8000;
  
	public ClientHandler() {
		super(8000);
		this.port = 8000;
	}

	
	public ClientHandler(int port) {
		super(port);
		this.port = port;
	}
	
	
	public void runServer() {
		try {
			listen();
		} catch (Exception ex) {
			System.out.println("ERROR - Could not listen for clients!");
		}
	}
	
	
	protected void serverStarted(){
		System.out.println("Server listening for connections on port " + getPort());
	}
	
	protected void serverStopped(){
		System.out.println("Server has stopped listening for connections.");
	}


	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		// TODO Auto-generated method stub
	}
}
	
//	public void handleMessageFromClient(Object msg, ConnectionToClient client){
//    	Statement stmt;
//		ResultSet rs;  
//		PreparedStatement pst;
//		if(msg instanceof String) {
//    			switch((String)msg){
//		    		case "getQuery":
//		    			try{
//		    				stmt = conn.createStatement();
//		    				rs = stmt.executeQuery("SELECT * FROM cems_db.question");
//		    				while(rs.next()) {
//		    					System.out.format("id: %s , subject: %s , courseName: %s, questionText: %s, questionNumber: %s, lecturer(author): %s \n",rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6));
//		    				}
//		    			}catch (SQLException e) {e.printStackTrace();}
//		    			break;
//		    		default:
//		    			System.out.println("Default print");
//		    			break;
//		    		}	
//		    	}
//		    if(msg instanceof ArrayList) {
//				try{
//					stmt = conn.createStatement();
//					pst  = conn.prepareStatement("UPDATE ex5engimethods.student SET stdID = ?,stdName = ?,stdLastName = ?");
//					@SuppressWarnings("unchecked")
//					ArrayList<String> arr = (ArrayList<String>)msg;
//					pst.setString(1, arr.get(0));
//					pst.setString(2, arr.get(1));
//					pst.setString(3, arr.get(2));
//					pst.executeUpdate();
//				}catch (SQLException e) {e.printStackTrace();}
//			    }    
//	}
