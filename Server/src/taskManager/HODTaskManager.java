package taskManager;
import thirdPart.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import DataBase.DBController;
import DataBase.SqlQueries;
import ocsf.server.ConnectionToClient;

public class HODTaskManager implements TaskHandler{	
	public HODTaskManager() {
	}

	/**
	 *this method handle with command from the user
	 *@param msg from the client
	 *@return ArrayList of the command that this class catch
	 * */
	@Override
	public ArrayList<HashMap<String, Object>> executeUserCommand(Object msg) {
		HashMap<String,ArrayList<String>> hm = (HashMap<String,ArrayList<String>>)msg;
		
		String task = (String) hm.get("task").get(0);
		try {
			switch (task) {
				case "getViewQuestionsById":
		    		return getViewQuestionsById(hm.get("param"));
				case "getViewExamById":
		    		return getViewExamById(hm.get("param"));	
		    	default: 
		    		System.out.println("no such method for lecturer");
				}
				
		} catch( Exception ex) { ex.printStackTrace(); }
		return null;
	}

	public ArrayList<HashMap<String, Object>> getViewQuestionsById(ArrayList<String> param) throws SQLException {
		DBController dbController = DBController.getInstance();
		HashMap<String, Object> rs = dbController.executeQueries(SqlQueries.getViewQuestionsById(param.get(0))).get(0);
		HashMap HashMapQuestion = jsonHandler.convertJsonToArrayHashMap((String)rs.get("questions"),ArrayList.class);
		System.out.println("hello");
		System.out.println(HashMapQuestion);
		return null;
	}
	
	public ArrayList<HashMap<String, Object>> getViewExamById(ArrayList<String> param) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getViewExamById(param.get(0)));
		return rs;
	}

}