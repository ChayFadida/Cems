package taskManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import DataBase.DBController;
import DataBase.SqlQueries;

public class LecturerTaskManager implements TaskHandler {
	public LecturerTaskManager() {}
	@Override
	public ArrayList<HashMap<String, Object>> executeUserCommand(Object msg) {
		HashMap<String,ArrayList<String>> hm = (HashMap<String,ArrayList<String>>)msg;
		
		String task = (String) hm.get("task").get(0);
		try {
			switch (task) {
				case "getAllQuestions":
		    		return getAllQuestions(hm.get("task"));
				case "updateQuestionById":
					return updateQuestionById(hm.get("task"),hm.get("param"));
		    	default: 
		    		System.out.println("no such method for lecturer");
				}
				
		} catch( Exception ex) { ex.printStackTrace(); }
		return null;
	}
	
	public ArrayList<HashMap<String, Object>> getAllQuestions(ArrayList<String> msg) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getAllTable(dbController.getquestionsTable()));
		return rs;
	}
	public ArrayList<HashMap<String, Object>> updateQuestionById(ArrayList<String> task,ArrayList<String> param) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.updateQueries(SqlQueries.updateQuestionById(param));
		return rs;
	}
}
