package DataBase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import server.ServerController;


/**
 * Represents a DBController class that defines the databaes connection.
 */
public class DBController {
	private static Connection conn = null;
	private static HashMap<String, String> db_info = new HashMap<>() {{
		put("ip", "localhost");
		put("password", null);
		put("username", null);
		put("scheme", null);
		
	}};
	private String mysql_cmd = "jdbc:mysql://" + db_info.get("ip")+ "/";
	private String mysql_timezone_flag = "?serverTimezone=IST";
	private String driver_cmd = "com.mysql.cj.jdbc.Driver";
	private static boolean driverIsSet;
	private static DBController instance;
	private static String questionsTable = "questions";
	private static String usersTable = "users";
	
	/**
	 * Returns the Users table
	 *@return return the Users table
	 * */

	public String getUsersTable() {
		return usersTable;
	}
	
	/**
	 * Returns the question table
	 *@return return the question table
	 * */
	public String getquestionsTable() {
		return questionsTable;
	}
	
	/**
	 * empty constructor controller
	 * */
	private DBController() {}
	
	/**
	 * implement singleton design pattert 
	 * @return instance of the dbcontroller
	 * */
	public static synchronized DBController getInstance() {
		if (instance == null) {
			instance = new DBController();
		}
		return instance;
	}
	
	/**
	 * Sets mysql driver
	 * @return return true if driver is set else if any exception happened
	 * */
	public boolean setDbDriver() {
	    if (driverIsSet) {
	        System.out.println("Driver is already set");
	        return driverIsSet;
	    }
	    try {
	        Class.forName(driver_cmd).getDeclaredConstructor().newInstance();
	        System.out.println("Driver definition succeeded");
	        driverIsSet = true;
	    } catch (Exception e) {
	        System.out.println("Driver definition failed");
	        driverIsSet = false;
	    }
	    return driverIsSet;
	}
	
	/**
	 * connect to database with db_info parameters.
	 * @param ServerController sc
	 * */
	public void connectToDb(ServerController sc) {
		StringBuilder mysql = new StringBuilder();
		mysql.append(mysql_cmd);
		mysql.append(db_info.get("scheme"));
		mysql.append(mysql_timezone_flag);
		try {
			conn = DriverManager.getConnection(mysql.toString(),db_info.get("username"), 
					db_info.get("password"));
	        System.out.println("");
		} catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
		}
		if(conn == null) {
			sc.setErrorLbl("Wrong Parameters!");
			System.out.println("NOT connected to db");
			return;
		}
		sc.setErrorLbl("");
		System.out.println("connected to db");
	}
	
	
	/**
	 * sets new database information to connect with
	 * @param db_new_info
	 * */
	public void setDbInfo(HashMap<String, String> db_new_info) {
		for (String key : db_info.keySet())
			db_info.put(key, db_new_info.get(key));
	}
	
	
	/**
	 * disconnect from database by sets conn obj to null
	 * */
	public void disconnect() {
		conn = null;
	}
	
	
	/**this method execute queries from our db
	 *@param  sqlQueries  the sql query the server need to execute
	 *@return return array list of the query result
	 * */
	public ArrayList<HashMap<String, Object>> executeQueries(String sqlQueries) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
	    ArrayList<HashMap<String, Object>> resultList = new ArrayList<>();
		try {
			stmt = conn.createStatement();
		} catch(Exception ex) {
			System.out.println("could not create a statement");
		}
		try {
			rs = stmt.executeQuery(sqlQueries);
	        ResultSetMetaData metaData = rs.getMetaData();
	        int columnCount = metaData.getColumnCount();
	        while (rs.next()) {
	            HashMap<String, Object> row = new HashMap<>();
	            for (int i = 1; i <= columnCount; i++)
	                row.put(metaData.getColumnName(i), rs.getObject(i));
	            resultList.add(row);
	        }
		} catch(Exception ex) {
			System.out.println("could not execute sql command");
		}
		return resultList;
	}
	
	
	/**this method execute update queries from our db
	 *@param  sqlQueries  the sql query the server need to execute
	 *@return return array list of the query result
	 * */
	
	public ArrayList<HashMap<String, Object>> updateQueries(String sqlQueries) throws SQLException {
		Statement stmt = null;
	    ArrayList<HashMap<String, Object>> result = new ArrayList<>();
		try {
			stmt = conn.createStatement();
			int affectedRows = stmt.executeUpdate(sqlQueries);
			HashMap<String, Object> hm = new HashMap<>();
			hm.put("affectedRows",affectedRows);
			result.add(hm);
		
		} catch(Exception ex) {
			System.out.println("could not execute sql command !!!");
		}
		return result;
	}
	

	/**
	 * Executes the given SQL queries for inserting data into the database.
	 *
	 * @param sqlQueries the SQL queries to be executed
	 * @return a list of hash maps containing the result of each query execution
	 */
	
	public ArrayList<HashMap<String, Object>> insertQueries(String sqlQueries) {
	    ArrayList<HashMap<String, Object>> result = new ArrayList<>();
	    try {
	        PreparedStatement statement = conn.prepareStatement(sqlQueries);

	        int affectedRows = statement.executeUpdate();
	        HashMap<String, Object> hm = new HashMap<>();
	        hm.put("affectedRows", affectedRows);
	        result.add(hm);

	        statement.close();
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return result;
	}
	
	
	/**
	 * Executes the given SQL queries with parameter values for inserting data into the database.
	 *
	 * @param sqlQueries         the SQL query with placeholders for parameter values
	 * @param parameterValuesList a list of arrays containing the parameter values for each query execution
	 * @return a list of hash maps containing the result of each query execution, including the generated keys (if any)
	 */
	public ArrayList<HashMap<String, Object>> insertQueries(String sqlQueries, List<Object[]> parameterValuesList) {
	    ArrayList<HashMap<String, Object>> result = new ArrayList<>();
	    try {
	        PreparedStatement statement = conn.prepareStatement(sqlQueries, Statement.RETURN_GENERATED_KEYS);

	        for (Object[] parameterValues : parameterValuesList) {
	            // Set values for each parameter
	            for (int i = 0; i < parameterValues.length; i++) {
	                statement.setObject(i + 1, parameterValues[i]);
	            }

	            int affectedRows = statement.executeUpdate();
	            HashMap<String, Object> hm = new HashMap<>();
	            hm.put("affectedRows", affectedRows);

	            // Retrieve generated keys
	            ResultSet generatedKeys = statement.getGeneratedKeys();
	            if (generatedKeys.next()) {
	                hm.put("id", generatedKeys.getObject(1));
	            }

	            result.add(hm);
	        }

	        statement.close();
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return result;
	}
	
	/**
	 * Executes the given SQL query with parameter values for updating data in the database.
	 *
	 * @param sqlQuery           the SQL query with placeholders for parameter values
	 * @param parameterValuesList a list of arrays containing the parameter values for each query execution
	 * @return a list of hash maps containing the result of each query execution
	 */
	public ArrayList<HashMap<String, Object>> updateQueries(String sqlQuery, List<Object[]> parameterValuesList) {
	    ArrayList<HashMap<String, Object>> result = new ArrayList<>();
	    try {
	        PreparedStatement statement = conn.prepareStatement(sqlQuery);

	        for (Object[] parameterValues : parameterValuesList) {
	            // Set values for each parameter
	            for (int i = 0; i < parameterValues.length; i++) {
	                statement.setObject(i + 1, parameterValues[i]);
	            }

	            int affectedRows = statement.executeUpdate();
	            HashMap<String, Object> hm = new HashMap<>();
	            hm.put("affectedRows", affectedRows);
	            result.add(hm);
	        }

	        statement.close();
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return result;
	}
	
	

/**
 * Executes the given SQL queries for inserting data into the database and retrieves the generated keys.
 *
 * @param sqlQueries a list of SQL queries for insertion and selection of generated keys
 * @return a list of hash maps containing the generated keys (if any) for each query execution
 */
	public ArrayList<HashMap<String, Object>> insertAndGetKeysQueries(ArrayList<String> sqlQueries) {
	    ArrayList<HashMap<String, Object>> result = new ArrayList<>();
	    try {
	        PreparedStatement insertionStatement = conn.prepareStatement(sqlQueries.get(0));
	        Statement selectionStatement = conn.createStatement();

	        int affectedRows = insertionStatement.executeUpdate();
	        HashMap<String, Object> hm = new HashMap<>();
	        if (affectedRows>0) {
                ResultSet rs = selectionStatement.executeQuery(sqlQueries.get(1));
                if (rs.next()) {
                    long generatedId = rs.getLong(1);
                    hm.put("keys", generatedId);
                } else {
                	hm.put("keys", -1);
                }
            }
	        result.add(hm);
	        insertionStatement.close();
	        selectionStatement.close();
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return result;
	}
	
	/**
	 * Imports user data from external text files into the corresponding database tables.
	 *
	 * @return true if the import operation is successful, false otherwise
	 */
	public static boolean importUsers() {
		Statement stmt;
		if(conn == null)
			return false;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate("SET GLOBAL local_infile=1");
			stmt.executeUpdate("load data local infile \"C:/users.txt\" into table users");
			stmt.executeUpdate("load data local infile \"C:/customer.txt\" into table customer");
			stmt.executeUpdate("load data local infile \"C:/region_employee.txt\" into table region_employee");

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}

