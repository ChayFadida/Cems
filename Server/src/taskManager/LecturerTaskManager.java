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
				case "getQustionBankById":
		    		return getQuestionsById(hm.get("param"));
				case "getCourses":
					return getCourses();
				case "addNewQuestion":
					return addNewQuestion(hm.get("param"));
				case "getAllQuestions":
					return getAllQuestions();
				case "getCoursesByCourseId":
					return getCoursesByCourseId(hm.get("param"));
				case "getQuestionsByIdAndCourse":
					return getQuestionsByLecIdCourseId(hm.get("param"));
				case "getExamBankByLecId":
					return getExamBankByLecId(hm.get("param"));
				case "getExamCountByLecId":
					return getExamCountByLecId(hm.get("param"));
				case "insertExam":
					return insertExam(hm.get("param"));
				case "updateExamBankById":
					return updateExamBankById(hm.get("param"));
				case "getDepartmentNameById":
					return getDepartmentNameById(hm.get("param"));
		    	default: 
		    		System.out.println("no such method for lecturer");
				}
				
		} catch( Exception ex) { ex.printStackTrace(); }
		return null;
	}

	private ArrayList<HashMap<String, Object>> getDepartmentNameById(ArrayList<String> param) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getDepartmentNameById(param.get(0)));
		return rs;
	}

	private ArrayList<HashMap<String, Object>> updateExamBankById(ArrayList<String> param) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.updateQueries(SqlQueries.updateExamBankById(param));
		return rs;
	}
	
	private ArrayList<HashMap<String, Object>> insertExam(ArrayList<String> param) {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.insertAndGetKeysQueries(SqlQueries.InsertExamToDB(param));
		return rs;
	}


	private ArrayList<HashMap<String, Object>> getExamCountByLecId(ArrayList<String> param) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getExamCountByLecId(param.get(0)));
		return rs;
	}

	private ArrayList<HashMap<String, Object>> getExamBankByLecId(ArrayList<String> param) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getExamBankByLecId(param.get(0)));
		return rs;
	}

	private ArrayList<HashMap<String, Object>> getQuestionsByLecIdCourseId(ArrayList<String> param) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getQuestionsByLecIdAndCourse(param.get(0),param.get(1)));
		return rs;
	}

	private ArrayList<HashMap<String, Object>> getCoursesByCourseId(ArrayList<String> param) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getCoursesByCourseId(param.get(0)));
		return rs;
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
	
	public ArrayList<HashMap<String, Object>> getQuestionsById(ArrayList<String> param) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getQuestionsById(param.get(0)));
		return rs;
	}
	public ArrayList<HashMap<String, Object>> getCourses() throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getAllTable("courses"));
		return rs;
	}
	
	public ArrayList<HashMap<String, Object>> addNewQuestion(ArrayList<String> param) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.insertQueries(SqlQueries.InsertQuestionToDB(param));
		return rs;
	}
}
	

