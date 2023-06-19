package taskManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import DataBase.DBController;
import DataBase.SqlQueries;


/**
 * StudentTaskManager implements the TaskHandler interface and provides methods to handle various tasks for the student.
 */
public class StudentTaskManager implements TaskHandler{

	
	/**
     * Executes the user command based on the received message.
     *
     * @param msg the user command message
     * @return an ArrayList of HashMaps containing the execution result
     */
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
				case "getExamresultByExamAndUserId":
					return getExamresultByExamAndUserId(hm.get("param"));
				case "checkIsLockedByExamId":
					return checkIsLockedByExamId(hm.get("param"));
				case "insertToExamresults":
					return insertToExamresults(hm.get("param"));
				case "updateExamresults":
					return updateExamresults(hm.get("param"));
				case "checkCountInProgressByExamId":
					return checkCountInProgressByExamId(hm.get("param"));
				case "lockExamById":
					return lockExamById(hm.get("param"));
				case "getExamresultsOfOtherStudentsByExamId":
					return getExamresultsOfOtherStudentsByExamId(hm.get("param"));
				case "getLecturerEmailByExamId":
					return getLecturerEmailByExamId(hm.get("param"));
				case "getExamFile":
					return getExamFile(hm.get("param").get(0));
				default: 
			    	System.out.println("no such method for Student");
				}
		}catch( Exception ex) { ex.printStackTrace(); }
		return null;
	}

	/**
     * Retrieves the lecturer email for a given exam ID.
     *
     * @param param the list of parameters
     * @return an ArrayList of HashMaps containing the lecturer email
     * @throws SQLException if a SQL error occurs
     */
	private ArrayList<HashMap<String, Object>> getLecturerEmailByExamId(ArrayList<Object> param) throws SQLException {
		DBController dbController = DBController.getInstance();
	    ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getLecturerEmailByExamId(param.get(0)));
	    return rs;
	}

	/**
     * Retrieves the exam results of other students for a given exam ID.
     *
     * @param param the list of parameters
     * @return an ArrayList of HashMaps containing the exam results of other students
     * @throws SQLException if a SQL error occurs
     */
	private ArrayList<HashMap<String, Object>> getExamresultsOfOtherStudentsByExamId(ArrayList<Object> param) throws SQLException {
		DBController dbController = DBController.getInstance();
	    ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getExamresultsOfOtherStudentsByExamId(param));
	    return rs;
	}

	 /**
     * Locks an exam by its ID.
     *
     * @param param the list of parameters
     * @return an ArrayList of HashMaps containing the result of the lock operation
     * @throws SQLException if a SQL error occurs
     */
	private ArrayList<HashMap<String, Object>> lockExamById(ArrayList<Object> param) throws SQLException {
		DBController dbController = DBController.getInstance();
	    ArrayList<HashMap<String, Object>> rs = dbController.updateQueries(SqlQueries.lockExamByExamId(param.get(0)));
	    return rs;
	}

	 /**
     * Checks the count of in-progress exams for a given exam ID.
     *
     * @param param the list of parameters
     * @return an ArrayList of HashMaps containing the count of in-progress exams
     * @throws SQLException if a SQL error occurs
     */
	private ArrayList<HashMap<String, Object>> checkCountInProgressByExamId(ArrayList<Object> param) throws SQLException {
		DBController dbController = DBController.getInstance();
	    ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.checkCountInProgressByExamId(param.get(0)));
	    return rs;
	}

	

    /**
     * Updates the exam results based on the provided parameters.
     *
     * @param param the list of parameters
     * @return an ArrayList of HashMaps containing the result of the update operation
     * @throws SQLException if a SQL error occurs
     */
	private ArrayList<HashMap<String, Object>> updateExamresults(ArrayList<Object> param) throws SQLException {
		DBController dbController = DBController.getInstance();
	    ArrayList<HashMap<String, Object>> rs = dbController.updateQueries(SqlQueries.updateExamresults(param));
	    return rs;
	}

	

    /**
     * Inserts the exam results based on the provided parameters.
     *
     * @param param the list of parameters
     * @return an ArrayList of HashMaps containing the result of the insert operation
     * @throws SQLException if a SQL error occurs
     */
	private ArrayList<HashMap<String, Object>> insertToExamresults(ArrayList<Object> param) throws SQLException {
		DBController dbController = DBController.getInstance();
	    ArrayList<HashMap<String, Object>> rs = dbController.insertQueries(SqlQueries.insertToExamresults(param));
	    return rs;
	}

	/**
     * Checks if an exam is locked by its ID.
     *
     * @param param the list of parameters
     * @return an ArrayList of HashMaps containing the result of the lock check
     * @throws SQLException if a SQL error occurs
     */
	private ArrayList<HashMap<String, Object>> checkIsLockedByExamId(ArrayList<Object> param) throws SQLException {
		DBController dbController = DBController.getInstance();
	    ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.checkIsLockedByExamId(param));
	    return rs;
	}

	
    /**
     * Retrieves the exam result for a given exam and user ID.
     *
     * @param param the list of parameters
     * @return an ArrayList of HashMaps containing the exam result
     * @throws SQLException if a SQL error occurs
     */

	private ArrayList<HashMap<String, Object>> getExamresultByExamAndUserId(ArrayList<Object> param) throws SQLException {
		DBController dbController = DBController.getInstance();
	    ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getExamresultByExamAndUserId(param));
	    return rs;
	}

	
	/**
     * Retrieves the exam file for a given exam ID.
     *
     * @param examId the exam ID
     * @return an ArrayList of HashMaps containing the exam file information
     * @throws SQLException if a SQL error occurs
     */
	private ArrayList<HashMap<String, Object>> getExamFile(Object examId) throws SQLException{
		DBController dbController = DBController.getInstance();
		return dbController.executeQueries(SqlQueries.getExamFileByExamId((Integer) examId));
	}
	
	  /**
     * Uploads tests to the database based on the provided parameters.
     *
     * @param param the parameter values for the upload
     * @return an ArrayList of HashMaps containing the result of the upload operation
     */
	private ArrayList<HashMap<String, Object>> UploadTestsToDB(Object param){
		HashMap<String, Object> argument = (HashMap<String, Object>) param;
		DBController dbController = DBController.getInstance();
		List<Object[]> parameterValuesList = new ArrayList<>();
		Object[] valuesRow = {argument.get("endTime"), argument.get("byte"), argument.get("status"),
				argument.get("examId"), argument.get("studentId")};
		parameterValuesList.add(valuesRow);
		return dbController.updateQueries(SqlQueries.uploadExamResultFromManualTakeExam(), parameterValuesList);
	}
	
    /**
     * Retrieves question information by ID.
     *
     * @param param the list of parameters
     * @return an ArrayList of HashMaps containing the question information
     * @throws SQLException if a SQL error occurs
     */

    private ArrayList<HashMap<String, Object>> getQuestionById(ArrayList<Object> param) throws SQLException {
		DBController dbController = DBController.getInstance();
	    ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getQuestionById((String)param.get(0)));
	    return rs;
	}

    /**
     * Retrieves questions and scores for a given exam ID.
     *
     * @param param the list of parameters
     * @return an ArrayList of HashMaps containing the questions and scores
     * @throws SQLException if a SQL error occurs
     */

	private ArrayList<HashMap<String, Object>> getQuestionsAndScoresByExamId(ArrayList<Object> param) throws SQLException {
		DBController dbController = DBController.getInstance();
	    ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getQuestionsAndScoresByExamId((String)param.get(0)));
	    return rs;
	}
	
	
	 /**
     * Retrieves an exam by its code.
     *
     * @param param the list of parameters
     * @return an ArrayList of HashMaps containing the exam information
     * @throws SQLException if a SQL error occurs
     */
	private ArrayList<HashMap<String, Object>> getExamByCode(ArrayList<Object> param) throws SQLException {
		DBController dbController = DBController.getInstance();
	    ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getExamByCode((String)param.get(0)));
	    return rs;
	}
	
	
	/**
     * Retrieves the exams for a given student ID.
     *
     * @param id the student ID
     * @return an ArrayList of HashMaps containing the exams information
     * @throws SQLException if a SQL error occurs
     */
	private ArrayList<HashMap<String, Object>> getExamsByStudentId(String id) throws SQLException {
	    DBController dbController = DBController.getInstance();
	    ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getExamsByUserId(id));
	    return rs;
	}
}
