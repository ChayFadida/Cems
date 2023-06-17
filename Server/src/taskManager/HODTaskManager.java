package taskManager;

import java.util.ArrayList;
import java.util.HashMap;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import DataBase.DBController;
import DataBase.SqlQueries;
import ocsf.server.ConnectionToClient;
import thirdPart.*;


public class HODTaskManager implements TaskHandler{

	@Override
	public ArrayList<HashMap<String, Object>> executeUserCommand(Object msg) {
		HashMap<String,ArrayList<String>> hm = (HashMap<String,ArrayList<String>>)msg;
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
					return updateRequestStatus((String)hm.get("status").get(0),(String)hm.get("requestId").get(0));
				case "getUser":
					return getUserById((String)hm.get("lecturerId").get(0));
				default: 
			    	System.out.println("no such method for HOD");
		    		return msgBack;
				}
		}catch( Exception ex) { ex.printStackTrace(); }
		return null;
	}
  
	private ArrayList<HashMap<String, Object>> getInfoForLecturerStats(ArrayList<String> arrayList) throws SQLException {
		 DBController dbController = DBController.getInstance();
		 ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getInfoForLecturerStats(arrayList.get(0)));
		 return rs;
	}

	private ArrayList<HashMap<String, Object>> getInfoForCourseStats(ArrayList<String> arrayList) throws SQLException {
		 DBController dbController = DBController.getInstance();
		 ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getInfoForCourseStats(arrayList.get(0)));
		 return rs;
	}

	private ArrayList<HashMap<String, Object>> getStudentDoneExamsGradeByID(ArrayList<String> arrayList) throws SQLException {
	    DBController dbController = DBController.getInstance();
	    ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getInfoForStudentStats(arrayList.get(0)));
	    return rs;
	}

	private ArrayList<HashMap<String, Object>> getStudentDoneExamsIdANDgradeByID(ArrayList<String> arrayList) throws SQLException {
	    DBController dbController = DBController.getInstance();
	    ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getStudentDoneExamsIdANDgradeByID(arrayList.get(0)));
	    return rs;
	}
	private ArrayList<HashMap<String, Object>> getUserById(String id) throws SQLException {
	    DBController dbController = DBController.getInstance();
	    ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getUserById(id));
	    return rs;
	}

	private ArrayList<HashMap<String, Object>> getAllPositionUsersInDepartment(String position,String department) throws SQLException{
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getUserByPositionAndDepartment(position,department));
		return rs;
	}

	private ArrayList<HashMap<String, Object>> getAllRequestsInDepartment(String department,String status) throws SQLException{
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getAllRequestsInDepartmentOfStatus(department,status));
		return rs;
	}
	private ArrayList<HashMap<String, Object>> updateRequestStatus(String status,String id) throws SQLException{
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.updateQueries(SqlQueries.updateDurationRequest(status,id));
		return rs;
  }
	
	public ArrayList<HashMap<String, Object>> getViewQuestionsById(ArrayList<String> param) throws SQLException {
		ArrayList<HashMap<String, Object>> res = new ArrayList<>();
		DBController dbController = DBController.getInstance(); 
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getViewQuestionsById(param.get(0)));
		if(rs.isEmpty()) {
			return rs;
		}
		HashMap<String, Object> row = rs.get(0);
		String lecturerId = (String) row.get("firstName");
		String jsonQuestionArray = (String) row.get("questions");
		HashMap<String,ArrayList<Double>> HashMapQuestion = JsonHandler.convertJsonToHashMap(jsonQuestionArray, String.class, ArrayList.class);
		ArrayList<Double> questionIdArr =  (ArrayList<Double>) HashMapQuestion.get("questions");
        ArrayList<Integer> integerQuestionList = new ArrayList<>();
        for (Double d : questionIdArr) {
        	integerQuestionList.add(d.intValue());
        }
		ArrayList<HashMap<String, Object>> rs1 = dbController.executeQueries(SqlQueries.getQuestionByQyestionIdArray(integerQuestionList));
		return rs1; 
	} 
	
	public ArrayList<HashMap<String, Object>> getViewExamById(ArrayList<String> param) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getViewExamById(param.get(0))); 
		return rs; 
	}
}
