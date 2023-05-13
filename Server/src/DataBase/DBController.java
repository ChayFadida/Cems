package DataBase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;



public class DBController {
	private static Connection conn;
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
	
	
	private DBController() {}
	
	/**
	 * implement singleton design pattert 
	 * */
	public static synchronized DBController getInstance() {
		if (instance == null) {
			instance = new DBController();
		}
		return instance;
	}
	
	/**
	 * Sets mysql driver
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
	 * connect to database with db_info parameters
	 * */
	public void connectToDb() {
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
		System.out.println("coonected to db");
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
	
	public ResultSet executeQueries(SqlHandler sqlQueries) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
		} catch(Exception ex) {
			System.out.println("could not create a statement");
		}
		try {
			rs = stmt.executeQuery(getQueryString(sqlQueries).toString());
		} catch(Exception ex) {
			System.out.println("could not execute sql command");
		}
		return rs;
	}
	
	private StringBuilder getQueryString(SqlHandler sqlQuery) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT ");
		String selectString = String.join(",", sqlQuery.getSelect());
		query.append(selectString);
		if (!(sqlQuery.getWhere().isEmpty())) {
			query.append(" WHERE ");
			String whereString = String.join(",", sqlQuery.getWhere());
			query.append(whereString);
		}
		query.append(" FROM ");
		String fromString = String.join(",", sqlQuery.getFrom());
		query.append(fromString);
		query.append(";");
		return query;
	}
	
}

