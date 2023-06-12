package DataBase;

import java.util.ArrayList;
import java.util.HashMap;

import thirdPart.jsonHandler;

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

	public static String getQuestionsById(String string) {
		String quert = "SELECT Q.* FROM questionbank AS B, lecturer AS L, questions AS Q WHERE L.userId ='" + string + "'AND B.lecturerId = L.userId AND Q.questionBankId = B.bankID;";
		return quert;
	}
	public static String getUserByUserNameAndPass(String pass,String userName) {
		String query = "SELECT * FROM users WHERE username = '" + userName +  "'AND pass = '" + pass+ "';" ;
		return query;
	}
	public static String updateUserByUserNameAndPassIsLogged(String pass,String userName) {
		String query = "UPDATE users SET isLogged = 1 WHERE username = '"+ userName +"' AND pass = '" + pass + "' ;";
		return query;
	}
	public static String InsertQuestionToDB(ArrayList<String> param) {
		String query = "INSERT INTO questions (details, answers, rightAnswer, questionBankId, subject, notes, courses)\r\n" + "VALUES ('" + param.get(0)+ "','" + param.get(1)+ "','" + param.get(2)+ "', '1', '" +  param.get(3)+ "','" + param.get(4)+ "','" + param.get(5) + "');";
		return query;
	}
	
	public static String updateQuestion(ArrayList<String> param) {
		String query = "UPDATE questions "
	            + "SET details = '" + param.get(0) + "', "
	            + "answers = '" + param.get(1) + "', "
	            + "rightAnswer = '" + param.get(2) + "', "
	            + "questionBankId = 1, "
	            + "subject = '" + param.get(3) + "', "
	            + "notes = '" + param.get(4) + "', "
	            + "courses = '" + param.get(5) + "' "
	            + "WHERE questionId = " + param.get(6) + ";";
		return query;
	}

	public static String deleteQuestion(ArrayList<String> param) {
		String query = "DELETE FROM questions\r\n" + "WHERE questionId = " + param.get(0) + ";";
		return query;
	}

	public static String getCoursesNameById(ArrayList<String> param) {
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT courseName, courseID FROM courses WHERE courseID IN (");
		for (int i = 1; i < Integer.parseInt(param.get(0)); i++) {
		  queryBuilder.append(param.get(i));
		  if (i < Integer.parseInt(param.get(0)) - 1) {
		    queryBuilder.append(", ");
		  }
		}
		queryBuilder.append(");");
		return queryBuilder.toString();
	}
	
	
}
