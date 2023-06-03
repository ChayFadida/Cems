package taskManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import DataBase.DBController;
import DataBase.SqlQueries;

public class LecturerTaskManager implements TaskHandler {
	public LecturerTaskManager() {}

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
				case "getAllQuestions":
		    		return getAllQuestions();
//				case "updateQuestionById":
//					return updateQuestionById(hm.get("param"));
		    	default: 
		    		System.out.println("no such method for lecturer");
				}
				
		} catch( Exception ex) { ex.printStackTrace(); }
		return null;
	}

	
	/**
	 *execute get all questions query
	 *@return ArrayList of the result of the query
	 * */
	public ArrayList<HashMap<String, Object>> getAllQuestions() throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getAllTable(dbController.getquestionsTable()));
		return rs;
	}
	
//	/**
//	 *execute update query by id
//	 *@return ArrayList of the result of the query
//	 * */
//	public ArrayList<HashMap<String, Object>> updateQuestionById(ArrayList<String> param) throws SQLException {
//		DBController dbController = DBController.getInstance();
//		ArrayList<HashMap<String, Object>> rs = dbController.updateQueriesFirst(SqlQueries.updateQuestionById(param));
//		return rs;
//	}
	
	//need to rewrite
}
