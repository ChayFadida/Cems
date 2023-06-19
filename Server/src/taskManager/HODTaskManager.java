package taskManager;

import java.util.ArrayList;


import java.util.HashMap;
import java.util.Map;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import DataBase.DBController;
import DataBase.SqlQueries;
import ocsf.server.ConnectionToClient;
import server.ClientHandler;
import thirdPart.*;

/**
 * HODTaskManager implements the TaskHandler interface and provides methods to handle various tasks for the HOD (Head of Department).
 */
public class HODTaskManager implements TaskHandler{

	   /**
     * Executes the user command based on the received message.
     *
     * @param msg the user command message
     * @return an ArrayList of HashMaps containing the execution result
     */
	@Override
	public ArrayList<HashMap<String, Object>> executeUserCommand(Object msg) {
		HashMap<String,ArrayList<Object>> hm = (HashMap<String, ArrayList<Object>>)msg;
		ArrayList<HashMap<String, Object>> msgBack = new ArrayList<HashMap<String, Object>>();
		String task = (String) hm.get("task").get(0);
		try {
			switch (task) {
				case "getAllbyPosition":
					switch((String)hm.get("position").get(0)) {
						case"Student":
							return getAllPositionUsersInDepartment("Student",(String)hm.get("department").get(0));
						case"Lecturer":
							return getAllPositionUsersInDepartment("Lecturer",(String)hm.get("department").get(0));
						}
				case "getViewQuestionsById":
					return getViewQuestionsById(hm.get("param"));
				case"getViewExamById":
					return getViewExamById(hm.get("param"));
				case "getStudentDoneExamsIdANDgradeByID":
					return getStudentDoneExamsIdANDgradeByID(hm.get("param"));
				case "getStudentDoneExamsGradeByID":
					return getStudentDoneExamsGradeByID(hm.get("param"));
				case"getInfoForCourseStats":
					return getInfoForCourseStats(hm.get("param"));
				case"getInfoForLecturerStats":
					return getInfoForLecturerStats(hm.get("param"));
				case "getAllRequests":
					return getAllRequestsInDepartment((String)hm.get("department").get(0),(String)hm.get("status").get(0));
				case "updateRequest":
					return updateRequestStatus(hm.get("param"));
				case "getUser":
					return getUserById((String)hm.get("lecturerId").get(0));
				case "updateExamDurationById":
					return updateExamDurationById(hm.get("param"));
				default: 
			    	System.out.println("no such method for HOD");
		    		return msgBack;
				}
		}catch( Exception ex) { ex.printStackTrace(); }
		return null;
	}
  
	/**
	 * Updates the duration of an exam in the database based on the provided parameters.
	 *
	 * @param param an ArrayList containing the necessary parameters
	 * @return an ArrayList of HashMaps containing the updated exam information
	 * @throws SQLException if there is an error executing the SQL query
	 */
	private ArrayList<HashMap<String, Object>> updateExamDurationById(ArrayList<Object> param) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.updateQueries(SqlQueries.updateExamDurationById(param));
		return rs;
	}
	
	   /**
     * Executes the user command based on the received message.
     *
     * @param msg the user command message
     * @return an ArrayList of HashMaps containing the execution result
     *  @throws SQLException if there is an error executing the SQL query
     */
	private ArrayList<HashMap<String, Object>> getInfoForLecturerStats(ArrayList<Object> arrayList) throws SQLException {
		 DBController dbController = DBController.getInstance();
		 ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getInfoForLecturerStats((String) arrayList.get(0)));
		 return rs;
	}

	
	/**
     * Retrieves information for course statistics based on the provided parameters.
     *
     * @param arrayList the list of parameters
     * @return an ArrayList of HashMaps containing the information for course statistics
     * @throws SQLException if a SQL error occurs
     */
	private ArrayList<HashMap<String, Object>> getInfoForCourseStats(ArrayList<Object> arrayList) throws SQLException {
		 DBController dbController = DBController.getInstance();
		 ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getInfoForCourseStats((String) arrayList.get(0)));
		 return rs;
	}

	
	 /**
     * Retrieves the grades of student done exams based on the provided parameters.
     *
     * @param arrayList the list of parameters
     * @return an ArrayList of HashMaps containing the grades of student done exams
     * @throws SQLException if a SQL error occurs
     */
	private ArrayList<HashMap<String, Object>> getStudentDoneExamsGradeByID(ArrayList<Object> arrayList) throws SQLException {
	    DBController dbController = DBController.getInstance();
	    ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getInfoForStudentStats((String) arrayList.get(0)));
	    return rs;
	}

	
	/**
     * Retrieves the IDs and grades of student done exams based on the provided parameters.
     *
     * @param arrayList the list of parameters
     * @return an ArrayList of HashMaps containing the IDs and grades of student done exams
     * @throws SQLException if a SQL error occurs
     */
	private ArrayList<HashMap<String, Object>> getStudentDoneExamsIdANDgradeByID(ArrayList<Object> arrayList) throws SQLException {
	    DBController dbController = DBController.getInstance();
	    ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getStudentDoneExamsIdANDgradeByID((String) arrayList.get(0)));
	    return rs;
	}
	
	 /**
     * Retrieves user information by ID.
     *
     * @param id the ID of the user
     * @return an ArrayList of HashMaps containing the user information
     * @throws SQLException if a SQL error occurs
     */
	private ArrayList<HashMap<String, Object>> getUserById(String id) throws SQLException {
	    DBController dbController = DBController.getInstance();
	    ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getUserById(id));
	    return rs;
	}

	 /**
     * Retrieves all users with a specific position in the given department.
     *
     * @param position   the position of the users
     * @param department the department of the users
     * @return an ArrayList of HashMaps containing the users' information
     * @throws SQLException if a SQL error occurs
     */
	private ArrayList<HashMap<String, Object>> getAllPositionUsersInDepartment(String position,String department) throws SQLException{
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getUserByPositionAndDepartment(position,department));
		return rs;
	}

	
	 /**
     * Retrieves all requests in a department with a specific status.
     *
     * @param department the department of the requests
     * @param status     the status of the requests
     * @return an ArrayList of HashMaps containing the requests' information
     * @throws SQLException if a SQL error occurs
     */
	private ArrayList<HashMap<String, Object>> getAllRequestsInDepartment(String department,String status) throws SQLException{
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getAllRequestsInDepartmentOfStatus(department,status));
		return rs;
	}
	
	/**
     * Updates the status of a request based on the provided parameters.
     *
     * @param param the list of parameters
     * @return an ArrayList of HashMaps containing the result of the update operation
     * @throws SQLException if a SQL error occurs
     */
	private ArrayList<HashMap<String, Object>> updateRequestStatus(ArrayList<Object> param) throws SQLException{
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.updateQueries(SqlQueries.updateDurationRequest(param));
		ArrayList<HashMap<String, Object>> studentToExtend = dbController.executeQueries(SqlQueries.getStudentWithExamIdAndInProgress((Integer)param.get(1)));
        ArrayList<Integer> idToLock = new ArrayList<Integer>();
		for (Map<String, Object> dict : studentToExtend) {
            Integer studentId = (int) dict.get("studentId");
            idToLock.add(studentId);
        }
		HashMap<String, Object> msgToClient = new HashMap<String, Object>(); 
		msgToClient.put("Special Method", "EXTENDS_TIME");
		msgToClient.put("idToExtend", idToLock);
		msgToClient.put("Time To Extend", param.get(6));
		ClientHandler.getInstance().sendToAllClients(msgToClient);
		return rs;
  }
	
	 /**
     * Retrieves questions information by ID.
     *
     * @param arrayList the list of parameters
     * @return an ArrayList of HashMaps containing the questions information
     * @throws SQLException if a SQL error occurs
     */
	public ArrayList<HashMap<String, Object>> getViewQuestionsById(ArrayList<Object> arrayList) throws SQLException {
		ArrayList<HashMap<String, Object>> res = new ArrayList<>();
		DBController dbController = DBController.getInstance(); 
		HashMap<String, Object> rs = dbController.executeQueries(SqlQueries.getViewQuestionsById((String) arrayList.get(0))).get(0);
		String lecturerId = (String) rs.get("firstName");
		String jsonQuestionArray = (String) rs.get("questions");
		HashMap<String,ArrayList<Double>> HashMapQuestion = JsonHandler.convertJsonToHashMap(jsonQuestionArray, String.class, ArrayList.class);
		ArrayList<Double> questionIdArr =  (ArrayList<Double>) HashMapQuestion.get("questions");
        ArrayList<Integer> integerQuestionList = new ArrayList<>();
        for (Double d : questionIdArr) { 
        	integerQuestionList.add(d.intValue());
        }
		ArrayList<HashMap<String, Object>> rs1 = dbController.executeQueries(SqlQueries.getQuestionByQyestionIdArray(integerQuestionList));
		
		return rs1; 
	} 
	
	
	/**
     * Retrieves exam information by ID.
     *
     * @param arrayList the list of parameters
     * @return an ArrayList of HashMaps containing the exam information
     * @throws SQLException if a SQL error occurs
     */
	public ArrayList<HashMap<String, Object>> getViewExamById(ArrayList<Object> arrayList) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getViewExamById((String) arrayList.get(0))); 
		return rs; 
	}
}
