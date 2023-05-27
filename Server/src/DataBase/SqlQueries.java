package DataBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;

public class SqlQueries {
	
	/** this method gets whole table from db
	 *@param  table  the table to execute the query from
	 *@return getAllTable string of the desired query
	 * */
	public static String getAllTable(String table) {
		StringBuilder getAllTable = new StringBuilder();
		String query = "SELECT * FROM ";
		getAllTable.append(query);
		getAllTable.append(table + ';');
		return getAllTable.toString();
	}
	
	/** this method update the question table by id
	 *@param param is arraylist = ["question_number", "question",
	 *								"id"]
	 *@return query updateQuestionById string of the desired query
	 * */
//	public static String updateQuestionById(ArrayList<String> param) {
//	    HashMap<String, Object> res = new HashMap<>();
//	    String sql = "UPDATE questions + SET pdfBytes = ? WHERE examId = ? AND studentId = ?";
//
//		
//		String query= "UPDATE questions SET question_number = '"
//				+ param.get(1)+"', question = '"+ param.get(2)+"' WHERE id ="+param.get(0);
//		return query;
//	}
	
	//should re write the update question by id
	
	public static HashMap<String, Object> uploadFile(DataInputStream data, String table, int examId, int studentId) {
	    HashMap<String, Object> res = new HashMap<>();
	    String sql = "UPDATE " + table + " SET pdfBytes = ? WHERE examId = ? AND studentId = ?";
	    res.put("sql", sql);
	    ArrayList<Object> params = new ArrayList<Object>();
	    params.add(data);
	    params.add(examId);
	    params.add(studentId);
	    res.put("params", params);
        return res;
	}
	
	public static String getFile(String table, int examId, int studentId) {
		StringBuilder getFile = new StringBuilder();
		String query = "SELECT pdfBytes FROM ";
		getFile.append(query);
		getFile.append(table);
		getFile.append(" WHERE examId = " + examId + " AND studentId = " + studentId);
		return getFile.toString();
	}
}
