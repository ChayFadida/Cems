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
/**
 * Represents the User Task Manager class.
 */
public class UserTaskManager implements TaskHandler{
	/**
     * Executes the user command based on the provided message.
     *
     * @param msg the user command message
     * @return an ArrayList of HashMaps containing the response message
     */
	@SuppressWarnings("unchecked")
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
				case "initializeCourses":
					return initializeCourses();
				default: 
			    	System.out.println("no such method for user");
		    		return msgBack;
				}
		}catch( Exception ex) { ex.printStackTrace(); }
		return null;
	}
	/**
     * Initializes the courses by retrieving them from the database.
     *
     * @return an ArrayList of HashMaps containing the courses information
     * @throws SQLException if there is an error executing the SQL query
     */
	private ArrayList<HashMap<String, Object>> initializeCourses() throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getAllCourses());
		return rs;
	}
	/**
     * Attempts to log out the user with the provided details.
     *
     * @param hm a HashMap containing the user details
     * @return a HashMap containing the response message
     */
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
	/**
     * Attempts to log in the user with the provided credentials.
     *
     * @param hm a HashMap containing the user credentials
     * @return a HashMap containing the response message
     * @throws SQLException if there is an error executing the SQL query
     */
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
			return res; 
		}
		
		if(userQ.isEmpty()) {
			res.put("access","deny");
			res.put("response", "wrong credentials");
			return res; 
		}
		loginFlag = updateUserByUserNameAndPassLoggedIn(password,username,1);
		if(loginFlag) {
			HashMap<String,Object> userHM = userQ.get(0);
			HashMap<String,ArrayList<Integer>> coursesIdHM;
			User user=null;
			String coursesId;
			Integer department;
			boolean flag =false;
			switch((String)userHM.get("position")) {
				case "Lecturer":
					coursesId = (String) getCoursesIdByLecturerId((int)userHM.get("id")).get("courseId");
					coursesIdHM = JsonHandler.convertJsonToHashMap(coursesId, String.class, ArrayList.class,Integer.class);
					department = (Integer)getDepartmentByLecturerId((int)userHM.get("id"));
					user = new Lecturer(userHM,coursesIdHM,department);
					if(!hasQuestionBank((int)userHM.get("id")))
						if(insertQuestionBank((int)userHM.get("id"))==false)
							flag=true;
					if(!hasExamBank((int)userHM.get("id")))
						if(insertExamBank((int)userHM.get("id"))==false)
							flag=true;
					user.setIsLogged(true);
					if(flag) {
						res.put("access","deny");
						res.put("response", "error in create exam and question bank");
						return res;
					}
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
					coursesId = (String) getCoursesIdByLecturerId((int)userHM.get("id")).get("courseId");
					coursesIdHM = JsonHandler.convertJsonToHashMap(coursesId, String.class, ArrayList.class,Integer.class);
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
	/**
     * Inserts an exam bank for the specified ID.
     *
     * @param id the ID of the user
     * @return true if the exam bank is inserted successfully, false otherwise
     */
	private boolean insertExamBank(int id) {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.insertQueries(SqlQueries.insertExamBankForId(id));
		return ((int)rs.get(0).get("affectedRows"))==1;
	}
	/**
	 * Checks if the user with the specified ID has an exam bank.
	 *
	 * @param id the ID of the user
	 * @return true if the user has an exam bank, false otherwise
	 * @throws SQLException if there is an error executing the SQL query
	 */
	private boolean hasExamBank(int id) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getExamBankByLecId(id + ""));
		return !rs.isEmpty();
	}
	/**
	 * Inserts a question bank for the specified ID.
	 *
	 * @param id the ID of the user
	 * @return true if the question bank is inserted successfully, false otherwise
	 */
	private boolean insertQuestionBank(int id) {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.insertQueries(SqlQueries.insertQuestionBankForId(id));
		return ((int)rs.get(0).get("affectedRows"))==1;
	}
	/**
	 * Checks if the user with the specified ID has a question bank.
	 *
	 * @param id the ID of the user
	 * @return true if the user has a question bank, false otherwise
	 * @throws SQLException if there is an error executing the SQL query
	 */
	private boolean hasQuestionBank(int id) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getQuestionBank(id +""));
		return !rs.isEmpty();
	}
	/**
	 * Retrieves a user by the given username.
	 *
	 * @param username the username of the user
	 * @return an ArrayList of HashMaps containing the user information
	 * @throws SQLException if there is an error executing the SQL query
	 */
	private  ArrayList<HashMap<String, Object>> getUserByUserName(String username) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getUserByUserName(username));
		return rs;
	}
	/**
	 * Retrieves a user by the given username and password.
	 *
	 * @param pass     the password of the user
	 * @param username the username of the user
	 * @return an ArrayList of HashMaps containing the user information
	 * @throws SQLException if there is an error executing the SQL query
	 */
	private ArrayList<HashMap<String, Object>> getUserByUserNameAndPass(String pass, String username) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getUserByUserNameAndPass(pass,username));
		return rs;
	}
	/**
	 * Updates the login flag of a user with the given username and password.
	 *
	 * @param pass       the password of the user
	 * @param username   the username of the user
	 * @param loginFlag  the login flag value
	 * @return true if the update is successful, false otherwise
	 * @throws SQLException if there is an error executing the SQL query
	 */
	private boolean updateUserByUserNameAndPassLoggedIn(String pass , String username, int loginFlag) throws SQLException {
		if(isLogged(username))
			return false;
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.updateQueries(SqlQueries.updateUserByUserNameAndPassIsLogged(pass, username, loginFlag));
		return ((int)rs.get(0).get("affectedRows"))==1;

	}
	/**
	 * Checks if the user with the given username is already logged in.
	 *
	 * @param username the username of the user
	 * @return true if the user is logged in, false otherwise
	 * @throws SQLException if there is an error executing the SQL query
	 */
	private boolean isLogged(String username) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getLoggedFlag(username,1));
		return !rs.isEmpty();
	}
	/**
	 * Retrieves the courses ID associated with a lecturer.
	 *
	 * @param id the ID of the lecturer
	 * @return a HashMap containing the courses ID
	 * @throws SQLException if there is an error executing the SQL query
	 */
	private HashMap<String, Object> getCoursesIdByLecturerId(int id) throws SQLException{
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getCoursesIdByLecturerId(id));
		return rs.get(0);

	}
	/**
	 * Retrieves the department ID associated with a student.
	 *
	 * @param id the ID of the student
	 * @return the department ID
	 * @throws SQLException if there is an error executing the SQL query
	 */
	private Integer getDepartmentByStudentId(int id) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getDepartmentByStudentId(id));
		return (Integer) rs.get(0).get("departmentId");

	}
	/**
	 * Retrieves the department ID associated with a HOD (Head of Department).
	 *
	 * @param id the ID of the HOD
	 * @return the department ID
	 * @throws SQLException if there is an error executing the SQL query
	 */
	private Integer getDepartmentByHodId(int id) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getDepartmentByHodId(id));
		return (Integer) rs.get(0).get("departmentId");

	}
	/**
	 * Retrieves the department ID associated with a lecturer.
	 *
	 * @param id the ID of the lecturer
	 * @return the department ID
	 * @throws SQLException if there is an error executing the SQL query
	 */
	private Integer getDepartmentByLecturerId(int id) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getDepartmentByLecturerId(id));
		return (Integer) rs.get(0).get("departmentId");
	}
	/**
	 * Updates the logout status of a user with the given ID.
	 *
	 * @param id the ID of the user
	 * @return true if the update is successful, false otherwise
	 * @throws SQLException if there is an error executing the SQL query
	 */
	private boolean updateUserByIdLogout(String id) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.updateQueries(SqlQueries.updateUserByIdLogout(id));
		return ((int)rs.get(0).get("affectedRows"))==1;
	}
}
