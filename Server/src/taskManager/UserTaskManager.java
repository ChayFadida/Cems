package taskManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import DataBase.DBController;
import DataBase.SqlQueries;

public class UserTaskManager implements TaskHandler{

	@Override
	public ArrayList<HashMap<String, Object>> executeUserCommand(Object msg) {
		HashMap<String,ArrayList<String>> hm = (HashMap<String,ArrayList<String>>)msg;
		ArrayList<HashMap<String, Object>> msgBack = new ArrayList<HashMap<String, Object>>();
		String task = (String) hm.get("task").get(0);
		try {
			switch (task) {
				case "loginAttempt":
					return loginAttempt(hm);
						
				default: 
			    		System.out.println("no such method for user");
		    		return msgBack;
				}
		}catch( Exception ex) { ex.printStackTrace(); }
		return null;
	}
	/**
	 *execute get all questions query
	 *@return ArrayList of the result of the query
	 * */
	public ArrayList<HashMap<String, Object>> getAllUsers() throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getAllTable(dbController.getUsersTable()));
		return rs;
	}
	//[{'permission' : 'student'}]
	public ArrayList<HashMap<String, Object>> loginAttempt(HashMap<String,ArrayList<String>> hm) throws SQLException{
		String password = hm.get("details").get(0);
		String username = hm.get("details").get(1);
		ArrayList <HashMap<String,Object>> userArr = getUserByUserNameAndPass(password, username);
		if(userArr.isEmpty()) {
			return null; //need to handle no permission this later
		}
		updateUserByUserNameAndPassLoggedIn(password,username);
		return userArr;
	}
	
	public ArrayList<HashMap<String, Object>> getUserByUserNameAndPass(String pass, String username) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getUserByUserNameAndPass(pass,username));
		return rs;
	}
	public ArrayList<HashMap<String, Object>> updateUserByUserNameAndPassLoggedIn(String pass , String username) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.updateQueries(SqlQueries.updateUserByUserNameAndPassIsLogged(pass, username));
		return rs;
	}
}
