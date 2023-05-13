package taskManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import ocsf.server.ConnectionToClient;
import DataBase.DBController;
import DataBase.SqlQueries;
import DataBase.SqlHandler;

public class LecturerTaskManager implements TaskHandler {

	@Override
	public ResultSet executeUserCommand(Object msg) {
		HashMap<String,ArrayList<String>> hm = (HashMap)msg;
		
		String task = (String) hm.get("task").get(0);
		switch (task) {
		    case "getStudents":
		    	try {
		    		return getAllStudents(hm.get("task"));
		    	} catch (SQLException e) {
		    		e.printStackTrace();
		    	}
		    case "getAllQuestions":
		    	try {
		    		getAllQuestions(hm.get("task"));
		    	} catch (SQLException e) {
				// TODO Auto-generated catch block
		    		e.printStackTrace();
		    	}
		}
		return null;
	}
	
	public ResultSet getAllStudents(ArrayList<String> msg) throws SQLException {
		DBController dbController = DBController.getInstance();
		SqlHandler sqlHandler = new SqlHandler();
		ResultSet rs = dbController.executeQueries(sqlHandler.getQuery(SqlQueries.getAllUsers()));
		return rs;
	}
	public ResultSet getAllQuestions(ArrayList<String> msg) throws SQLException {
		DBController dbController = DBController.getInstance();
		SqlHandler sqlHandler = new SqlHandler();
		ResultSet rs = dbController.executeQueries(sqlHandler.getQuery(SqlQueries.getAllQuestions()));
		return rs;
	}
}
