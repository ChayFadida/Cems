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
		HashMap<String,ArrayList<Object>> hm = (HashMap<String,ArrayList<Object>>)msg;
		ArrayList<HashMap<String, Object>> msgBack = new ArrayList<HashMap<String, Object>>();
		String task = (String) hm.get("task").get(0);
		try {
			switch (task) {
				case "getAllbyPosition":
					switch((String)hm.get("position").get(0)) {
						case("Student"):
							return getAllPositionUsersInDepartment("Student",(String)hm.get("department").get(0));
						case("Lecturer"):
							return getAllPositionUsersInDepartment("Lecturer",(String)hm.get("department").get(0));
						}
				case "getViewQuestionsById":
					return getViewQuestionsById(hm.get("param"));
				case"getViewExamById":
					return getViewExamById(hm.get("param"));
				//case "getStudentNameByID":
					//return getStudentNameByID(hm.get("name"));
				case "getStudentDoneExamsIdANDgradeByID":
					return getStudentDoneExamsIdANDgradeByID(hm.get("param"));
				case "getStudentDoneExamsGradeByID":
					return getStudentDoneExamsGradeByID(hm.get("param"));
				default: 
			    	System.out.println("no such method for HOD");
		    		return msgBack;
				}
		}catch( Exception ex) { ex.printStackTrace(); }
		return null;
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



	/*private ArrayList<HashMap<String, Object>> getStudentNameByID(ArrayList<String> arrayList) throws SQLException {
	    DBController dbController = DBController.getInstance();
	    ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getStudentNameByID(arrayList.get(0)));
	    return rs;
	}*/




	private ArrayList<HashMap<String, Object>> getAllPositionUsersInDepartment(String position,String department) throws SQLException{
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getUserByPositionAndDepartment(position,department));
		return rs;
	}

	
	public ArrayList<HashMap<String, Object>> getViewQuestionsById(ArrayList<String> param) throws SQLException {
		ArrayList<HashMap<String, Object>> res = new ArrayList<>();
		DBController dbController = DBController.getInstance(); 
		HashMap<String, Object> rs = dbController.executeQueries(SqlQueries.getViewQuestionsById(param.get(0))).get(0);
		String lecturerId = (String) rs.get("firstName");
		String jsonQuestionArray = (String) rs.get("questions");
		HashMap HashMapQuestion = JsonHandler.convertJsonToHashMap(jsonQuestionArray, String.class, ArrayList.class);
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

