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
		HashMap<String,ArrayList<Object>> hm = (HashMap<String,ArrayList<Object>>)msg;
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
				case "getAllRequests":
					return getAllRequestsInDepartment((String)hm.get("department").get(0),(String)hm.get("status").get(0));
				case "updateRequest":
					return updateRequestStatus((String)hm.get("status").get(0),(String)hm.get("requestId").get(0));
							
				default: 
			    	System.out.println("no such method for HOD");
		    		return msgBack;
				}
		}catch( Exception ex) { ex.printStackTrace(); }
		return null;
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
	
}
