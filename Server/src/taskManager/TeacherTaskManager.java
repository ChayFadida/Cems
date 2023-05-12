package taskManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import ocsf.server.ConnectionToClient;
import DataBase.DBController;
import DataBase.SqlQueries;
import DataBase.SqlHandler;

public class TeacherTaskManager implements TaskHandler {

	@Override
	public ResultSet executeUserCommand(Object msg, ConnectionToClient client) {
		if(!(msg instanceof ArrayList)) {
			System.out.println("msg object from client is not arraylist");
			return null;
		}
	    ArrayList<String> msglist = (ArrayList<String>) msg;
	    if(msglist.isEmpty()) {
	        System.out.println("empty command");
	        return null;
	    }
	    String cmd = msglist.get(0);
		switch (cmd) {
		    case "getStudents":
			try {
				return getAllStudents(msglist, client);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public ResultSet getAllStudents(ArrayList msg, ConnectionToClient client) throws SQLException {
		DBController dbController = DBController.getInstance();
		SqlHandler sqlHandler = new SqlHandler();
		ResultSet rs = dbController.executeQueries(sqlHandler.getQuery(SqlQueries.getAllUsers()));
		return rs;
	}
}
