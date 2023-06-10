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
	public static String updateUserByUserNameAndPassIsLogged(String pass,String userName, int loggedFlag) {
		String query = "UPDATE users SET isLogged = "+loggedFlag+" WHERE username = '"+ userName +"' AND pass = '" + pass + "' ;";
		return query;
	}

	public static String InsertQuestionToDB(ArrayList<String> hm) {
		String query = "INSERT INTO questions (details, answers, rightAnswer, questionBankId, subject, notes, composer)\r\n" + "VALUES ('" + hm.get(0)+ "','" + hm.get(1)+ "','" + hm.get(2)+ "', '1', '" +  hm.get(3)+ "','" + hm.get(4)+ "', 'Yoni');";
		return query;
	}


	public static String getDepartmentByStudentId(int id) {
		String query = "Select S.department FROM student AS S WHERE S.userId = '" +id+ "';";
		return query;
	}
	
	public static String getDepartmentByHodId(int id) {
		String query = "Select H.department FROM hod AS H WHERE H.userId = '" +id+ "';";
		return query;
	}

	public static String getCoursesByLecturerId(int id) {
		String query = "Select L.courseId FROM lecturer AS L WHERE L.userId = '" +id+ "';";
		return query;
	}
	
	public static String getLoggedFlag(String username, int flag) {
		return "SELECT * FROM users WHERE username = '" + username +  "' AND isLogged = "+flag+";" ;
	}

	public static String getUserByUserName(String username) {
		String query = "SELECT * FROM users WHERE username = '" + username +  "';" ;
		return query;
	}
	public static String getViewQuestionsById(String string) {
		String quert = "SELECT Q.questionId , Q.details, Q.subject, U.firstName, C.courseName FROM questionbank AS B, lecturer AS L, questions AS Q, courses AS C, question_courses AS QC, users AS U WHERE L.userId ='" + string + "'AND B.lecturerId = L.userId AND Q.questionBankId = B.bankID AND C.courseID = QC.courseId AND QC.questionId = Q.questionId AND U.id = L.userId";
		return quert;
	}
	public static String getViewExamById(String string) {
		String quert = "SELECT e.* FROM examsbank eb JOIN exam e ON eb.name = e.bank WHERE eb.lecturerId = '" + string + "'" + ";" ;
		return quert;
	}
	
	/*SELECT e.examId, e.course, e.subject, e.ecomposer
	FROM examsbank eb
	JOIN exam e ON eb.name = e.bank
	WHERE eb.lecturerId = 5;*/
	
}
