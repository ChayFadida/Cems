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
						case("Student"):
							return getAllStudents(hm.get("department").get(0));
						case("Lecturer"):
							return getAllLecturers();
						}
				default: 
			    	System.out.println("no such method for HOD");
		    		return msgBack;
				}
		}catch( Exception ex) { ex.printStackTrace(); }
		return null;
	}
	
	private ArrayList<HashMap<String, Object>> getAllStudents(String id) throws SQLException{
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getStudentByPositionAndDepartment("Student",id));
		return rs;
	}
	private ArrayList<HashMap<String, Object>> getAllLecturers() throws SQLException{
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getUserByPosition("Lecturer"));
		return rs;	
	}
	
	public ArrayList<HashMap<String, Object>> getViewQuestionsById(ArrayList<String> param) throws SQLException {
		DBController dbController = DBController.getInstance(); 
		HashMap<String, Object> rs = dbController.executeQueries(SqlQueries.getViewQuestionsById(param.get(0))).get(0); 
		HashMap HashMapQuestion = jsonHandler.convertJsonToArrayHashMap((String)rs.get("questions"),ArrayList.class); 
		System.out.println("hello"); System.out.println(HashMapQuestion); 
		return null; 
	} 
	
	public ArrayList<HashMap<String, Object>> getViewExamById(ArrayList<String> param) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getViewExamById(param.get(0))); 
		return rs; 
	}
}