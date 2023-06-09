package taskManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import DataBase.DBController;
import DataBase.SqlQueries;
import entities.Hod;
import entities.Lecturer;
import entities.Student;
import entities.Super;
import entities.User;

public class UserTaskManager implements TaskHandler{

	@Override
	public ArrayList<HashMap<String, Object>> executeUserCommand(Object msg) {
		HashMap<String,ArrayList<String>> hm = (HashMap<String,ArrayList<String>>)msg;
		ArrayList<HashMap<String, Object>> msgBack = new ArrayList<HashMap<String, Object>>();
		String task = (String) hm.get("task").get(0);
		try {
			switch (task) {
				case "loginAttempt":
					msgBack.add(loginAttempt(hm));
					return msgBack;
				default: 
			    	System.out.println("no such method for user");
		    		return msgBack;
				}
		}catch( Exception ex) { ex.printStackTrace(); }
		return null;
	}
//	/**
//	 *execute get all questions query
//	 *@return ArrayList of the result of the query
//	 * */
//	public ArrayList<HashMap<String, Object>> getAllUsers() throws SQLException {
//		DBController dbController = DBController.getInstance();
//		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getAllTable(dbController.getUsersTable()));
//		return rs;
//	}
	//[{'permission' : 'student'}]
	public HashMap<String,Object> loginAttempt(HashMap<String,ArrayList<String>> hm) throws SQLException{
		boolean loginFlag;
		HashMap<String,Object> res = new HashMap<>();
		String password = hm.get("details").get(0);
		String username = hm.get("details").get(1);
		ArrayList <HashMap<String,Object>> userArr = getUserByUserNameAndPass(password, username);
		//check if logged in
		//if(logged in
		if(userArr.isEmpty()) {
			res.put("access","deny");
			res.put("response", "not exist");
			return res; //need to handle no permission this later
		}
		loginFlag = updateUserByUserNameAndPassLoggedIn(password,username,1);
		if(loginFlag) {
			HashMap<String,Object> userHM = userArr.get(0);
			User user=null;
			ArrayList<Integer> coursesId = new ArrayList<>();
			String department;
			switch((String)userHM.get("position")) {
				case "Lecturer":
					coursesId = getCoursesByLecturerId((int)userHM.get("id"));
					user = new Lecturer(userHM,coursesId);
					break;
				case "Student":
					department = getDepartmentByStudentId((int)userHM.get("id"));
					user = new Student(userHM,department);
					break;
				case "HOD":
					department = getDepartmentByHodId((int)userHM.get("id"));
					user = new Hod(userHM,department);
				case "Super":
					coursesId = getCoursesByLecturerId((int)userHM.get("id"));
					department = getDepartmentByHodId((int)userHM.get("id"));
					user = new Super(userHM,coursesId,department);
				default:
					System.out.println("Problam at switch in login attempt");
					user=null;
					break;
					
			}
			res.put("access", "approve");
			res.put("response", user);
			return res;
		}
		res.put("access","deny");
		res.put("response", "logged in");
		return res;
	}
	
	
	private ArrayList<HashMap<String, Object>> getUserByUserNameAndPass(String pass, String username) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getUserByUserNameAndPass(pass,username));
		return rs;
	}
	private boolean updateUserByUserNameAndPassLoggedIn(String pass , String username, int loginFlag) throws SQLException {
		if(isLogged(username))
			return false;
		DBController dbController = DBController.getInstance();
		dbController.updateQueriesFirst(SqlQueries.updateUserByUserNameAndPassIsLogged(pass, username, loginFlag));
		return true;
	}
	
	private boolean isLogged(String username) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getLoggedFlag(username,1));
		return !rs.isEmpty();
	}

	private ArrayList<Integer> getCoursesByLecturerId(int id) throws SQLException{
		ArrayList<Integer> coursesId = new ArrayList<>();
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getCoursesByLecturerId(id));
		for(HashMap<String,Object> hm :rs) {
			coursesId.add((Integer) hm.get("courseId"));
		}
		return coursesId;

		
	}
	private String getDepartmentByStudentId(int id) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getDepartmentByStudentId(id));
		return (String) rs.get(0).get("department");

	}
	private String getDepartmentByHodId(int id) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getDepartmentByHodId(id));
		return (String) rs.get(0).get("department");

	}
	
}
