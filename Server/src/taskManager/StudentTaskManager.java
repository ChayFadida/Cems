package taskManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import DataBase.DBController;
import DataBase.SqlQueries;

public class StudentTaskManager implements TaskHandler{

	@Override
	public ArrayList<HashMap<String, Object>> executeUserCommand(Object msg) {
		HashMap<String,ArrayList<String>> hm = (HashMap<String,ArrayList<String>>)msg;
		ArrayList<HashMap<String, Object>> msgBack = new ArrayList<HashMap<String, Object>>();
		String task = (String) hm.get("task").get(0);
		try {
			switch (task) {
				case "getExamByCode":
					return getExamByCode(hm.get("param"));
				case"getQuestionsAndScoresByExamId":
					return getQuestionsAndScoresByExamId(hm.get("param"));
				case"getQuestionById":
					return getQuestionById(hm.get("param"));
				default: 
			    	System.out.println("no such method for Student");
				case "getExams":
					return getExamsByStudentId((String)hm.get("studentId").get(0));
					
				default: 
			    	System.out.println("no such method for HOD");
		    		return msgBack;
				}
		}catch( Exception ex) { ex.printStackTrace(); }
		return null;
	}

  private ArrayList<HashMap<String, Object>> getQuestionById(ArrayList<String> param) throws SQLException {
		DBController dbController = DBController.getInstance();
	    ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getQuestionById(param.get(0)));
	    return rs;
	}

	private ArrayList<HashMap<String, Object>> getQuestionsAndScoresByExamId(ArrayList<String> param) throws SQLException {
		DBController dbController = DBController.getInstance();
	    ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getQuestionsAndScoresByExamId(param.get(0)));
	    return rs;
	}

	private ArrayList<HashMap<String, Object>> getExamByCode(ArrayList<String> param) throws SQLException {
		DBController dbController = DBController.getInstance();
	    ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getExamByCode(param.get(0)));
	    return rs;
	}
	
	private ArrayList<HashMap<String, Object>> getExamsByStudentId(String id) throws SQLException {
	    DBController dbController = DBController.getInstance();
	    ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getExamsByUserId(id));
	    return rs;
	}
}
