package taskManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import DataBase.DBController;
import DataBase.SqlQueries;

public class StudentTaskManager implements TaskHandler{

	@Override
	public ArrayList<HashMap<String, Object>> executeUserCommand(Object msg) {
		HashMap<String,ArrayList<Object>> hm = (HashMap<String,ArrayList<Object>>)msg;
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
				case "getExams":
					return getExamsByStudentId((String)hm.get("studentId").get(0));
				case "UploadTestsToDB":
					return UploadTestsToDB(hm.get("param").get(0));
				default: 
			    	System.out.println("no such method for Student");
				}
		}catch( Exception ex) { ex.printStackTrace(); }
		return null;
	}

	private ArrayList<HashMap<String, Object>> UploadTestsToDB(Object param){
		HashMap<String, Object> argument = (HashMap<String, Object>) param;
		DBController dbController = DBController.getInstance();
		List<Object[]> parameterValuesList = new ArrayList<>();
		Object[] valuesRow = { argument.get("examId"), argument.get("studentId"), argument.get("startTime"),
				argument.get("endTime"), argument.get("byte") };
		parameterValuesList.add(valuesRow);
		return dbController.insertQueries(SqlQueries.uploadExamResultFromManualTakeExam(), parameterValuesList);
	}
	
    private ArrayList<HashMap<String, Object>> getQuestionById(ArrayList<Object> param) throws SQLException {
		DBController dbController = DBController.getInstance();
	    ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getQuestionById((String)param.get(0)));
	    return rs;
	}

	private ArrayList<HashMap<String, Object>> getQuestionsAndScoresByExamId(ArrayList<Object> param) throws SQLException {
		DBController dbController = DBController.getInstance();
	    ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getQuestionsAndScoresByExamId((String)param.get(0)));
	    return rs;
	}
	
	

	private ArrayList<HashMap<String, Object>> getExamByCode(ArrayList<Object> param) throws SQLException {
		DBController dbController = DBController.getInstance();
	    ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getExamByCode((String)param.get(0)));
	    return rs;
	}
	
	private ArrayList<HashMap<String, Object>> getExamsByStudentId(String id) throws SQLException {
	    DBController dbController = DBController.getInstance();
	    ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getExamsByUserId(id));
	    return rs;
	}
}
