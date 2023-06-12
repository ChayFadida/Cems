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
import thirdPart.JsonHandler;

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
				case "logoutAttempt":
					msgBack.add(lougoutAttempt(hm));
					return msgBack;
				default: 
			    	System.out.println("no such method for user");
		    		return msgBack;
				}
		}catch( Exception ex) { ex.printStackTrace(); }
		return null;
	}
private HashMap<String, Object> lougoutAttempt(HashMap<String, ArrayList<String>> hm) {
		HashMap<String,Object> res = new HashMap<>();
		String id = hm.get("details").get(0);
		try {
			boolean isUpdated = updateUserByIdLogout(id);
			if(isUpdated) {
				res.put("access","approved");
				res.put("response",id);
				return res;
			}
			res.put("access", "denied");
			res.put("response",id);
		} catch (SQLException e) {
			res.put("access","denied");
			res.put("response",-1);
			e.printStackTrace();
		}
		return res;
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
		ArrayList <HashMap<String,Object>> userArr = getUserByUserName(username);
		ArrayList <HashMap<String,Object>> userQ= getUserByUserNameAndPass(password, username);
		if(userArr.isEmpty()) {
			res.put("access","deny");
			res.put("response", "not exist");
			return res; //need to handle no permission this later
		}
		//check if logged in
		//if(logged in
		if(userQ.isEmpty()) {
			res.put("access","deny");
			res.put("response", "wrong credentials");
			return res; //need to handle no permission this later
		}
		loginFlag = updateUserByUserNameAndPassLoggedIn(password,username,1);
		if(loginFlag) {
			HashMap<String,Object> userHM = userQ.get(0);
			HashMap<String,Object> coursesIdHM;

			User user=null;
			String coursesId;
			Integer department;
			switch((String)userHM.get("position")) {
				case "Lecturer":
					coursesId = (String) getCoursesByLecturerId((int)userHM.get("id")).get("courseId");
					coursesIdHM = JsonHandler.convertJsonToHashMap(coursesId, String.class, ArrayList.class);
					user = new Lecturer(userHM,coursesIdHM);
					user.setIsLogged(true);
					break;
				case "Student":
					department = getDepartmentByStudentId((int)userHM.get("id"));
					user = new Student(userHM,department);
					user.setIsLogged(true);
					break;
				case "HOD":
					department = getDepartmentByHodId((int)userHM.get("id"));
					user = new Hod(userHM,department);
					user.setIsLogged(true);
					break;
				case "Super":
					coursesId = (String) getCoursesByLecturerId((int)userHM.get("id")).get("courseId");
					coursesIdHM = JsonHandler.convertJsonToHashMap(coursesId, String.class, ArrayList.class);
					department = getDepartmentByHodId((int)userHM.get("id"));
					user = new Super(userHM,coursesIdHM,department);
					user.setIsLogged(true);
					break;
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
	private  ArrayList<HashMap<String, Object>> getUserByUserName(String username) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getUserByUserName(username));
		return rs;
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
		ArrayList<HashMap<String, Object>> rs = dbController.updateQueries(SqlQueries.updateUserByUserNameAndPassIsLogged(pass, username, loginFlag));
		return ((int)rs.get(0).get("affectedRows"))==1;
	}
	
	private boolean isLogged(String username) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getLoggedFlag(username,1));
		return !rs.isEmpty();
	}

	private HashMap<String, Object> getCoursesByLecturerId(int id) throws SQLException{
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getCoursesByLecturerId(id));
		return rs.get(0);

		
	}
	private Integer getDepartmentByStudentId(int id) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getDepartmentByStudentId(id));
		return (Integer) rs.get(0).get("departmentId");

	}
	private Integer getDepartmentByHodId(int id) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getDepartmentByHodId(id));
		return (Integer) rs.get(0).get("departmentId");

	}
	
	private boolean updateUserByIdLogout(String id) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.updateQueries(SqlQueries.updateUserByIdLogout(id));
		return ((int)rs.get(0).get("affectedRows"))==1;
	} 
}
