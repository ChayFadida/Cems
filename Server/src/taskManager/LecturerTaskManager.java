package taskManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import DataBase.DBController;
import DataBase.SqlQueries;

public class LecturerTaskManager implements TaskHandler {
	public LecturerTaskManager() {}
	@Override
	public ArrayList executeUserCommand(Object msg) {
		HashMap<String,ArrayList<String>> hm = (HashMap)msg;
		
		String task = (String) hm.get("task").get(0);
		try {
			switch (task) {
				case "getAllQuestions":
		    		return getAllQuestions(hm.get("task"));
		    	
		    	default: 
		    		System.out.println("no such method for lecturer");
				}
				
		} catch( Exception ex) { ex.printStackTrace(); }
		return null;
	}
	
	public ArrayList getAllQuestions(ArrayList<String> msg) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList rs = dbController.executeQueries(SqlQueries.getAllTable(dbController.getquestionsTable()));
		return rs;
	}
}
