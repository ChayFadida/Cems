package DataBase;

import java.util.ArrayList;
import java.util.HashMap;

import thirdPart.JsonHandler;

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

	public static String getDepartmentByStudentId(int id) {
		String query = "Select S.departmentId FROM student AS S WHERE S.userId = '" +id+ "';";
		return query;
	}
	
	public static String getDepartmentByHodId(int id) {
		String query = "Select H.departmentId FROM hod AS H WHERE H.userId = '" +id+ "';";
		return query;
	}

	public static String getCoursesByLecturerId(int id) {
		String query = "Select L.courseId FROM lecturer AS L WHERE L.userId = '" +id+ "';";
		return query;
	}
	
	public static String getDepartmentByLecturerId(int id) {
		String query = "Select L.departmentId FROM lecturer AS L WHERE L.userId = '" +id+ "';";
		return query;
	}
	
	public static String getLoggedFlag(String username, int flag) {
		return "SELECT * FROM users WHERE username = '" + username +  "' AND isLogged = "+flag+";" ;
	}
	public static String getUserByUserName(String username) {
		String query = "SELECT * FROM users WHERE username = '" + username +  "';" ;
		return query;
	}
	
	public static String updateUserByIdLogout(String id) {
		String query = "UPDATE users SET isLogged = "+0+" WHERE id = '"+ id +"' ;";
		return query;
	}
	public static String getUserByPositionAndDepartment(String position,String department) {
		String query = "SELECT users.id, users.firstName , users.lastName , users.email,users.position,users.pass,users.username,users.isLogged FROM users , "+position.toLowerCase()+" WHERE users.id = "+position.toLowerCase()+".userId AND "+position.toLowerCase()+".departmentId = "+ department+";";
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

	public static String getCoursesByCourseId(String id) {
		String query = "SELECT * FROM courses WHERE courseID = '" + id +  "';" ;
		return query;
	}
	
	public static String getQuestionsByLecIdAndCourse(String lecId, String courseId) {
		String query = "SELECT Q.*"
				+ " FROM questions AS Q, users AS U, questionBank AS B"
				+ " WHERE U.id = '"+lecId+"' AND Q.questionBankId=B.bankID AND"
				+ " B.lecturerId = U.id AND LOCATE('"+courseId+"', Q.courses) > 0;";
		return query;
	}

	public static String getExamBankByLecId(String id) {
		String query = "SELECT B.* FROM examsbank AS B WHERE lecturerId = '" + id +  "';" ;
		return query;
	}

	public static String getExamCountByLecId(String id) {
		String query = "SELECT COUNT(*) AS count FROM exam WHERE composerId = '"+ id+"';";
		return query;
	}

	public static String updateExamBankById(ArrayList<String> param) {
		String query = "UPDATE examsbank SET exams = '"+param.get(1)+"' WHERE bankId = '"+ param.get(0) +"' ;";
		return query;
	}

	public static ArrayList<String> InsertExamToDB(ArrayList<String> param) {
		String insert = "INSERT INTO exam (examName, courseId, subject, duration,lecturerNote, studentNote, composerId, code, examNum, bankId, isLocked)\r\n" +
				"VALUES ('" + param.get(9)+ "','" + param.get(0)+ "','" + param.get(1)+ "', '"+param.get(2)+"', '" +  param.get(3)+ "','" + param.get(4)+ "', '"+param.get(5)+"', '"
						+param.get(6)+"', '"+param.get(7)+"','"+param.get(8)+"', '0');";
		String select = "SELECT LAST_INSERT_ID();";
		ArrayList<String> queries = new ArrayList<>();
		queries.add(insert);
		queries.add(select);
		return queries;
	}

	public static String getDepartmentNameById(String id) {
		String query = "SELECT D.* FROM department AS D WHERE id = '" + id +  "';" ;
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

	public static String InsertQuestionToExamInDB(ArrayList<String> param) {
		String query = "INSERT INTO questionsinexam (examId, questions, scores)"
				+ " VALUES ('"+param.get(0) +"', '"+ param.get(1)+"', '"+ param.get(2)+"');";
		return query;
	}

	public static String getExamBank(int id) {
		return "SELECT * FROM examsbank WHERE lecturerId = '" + id +  "' ;" ;
	}

	public static String getQuestionBank(int id) {
		return "SELECT * FROM questionbank WHERE lecturerId = '" + id +  "' ;" ;
	}

	public static String insertQuestionBankForId(int id) {
		System.out.println("  '{\"questions\": [] }' ");
		String query = "INSERT INTO questionbank (lecturerId, questions) VALUES ("+id+","
				+ " '{\"questions\": [] }');";
		return query;
	}

	public static String insertExamBankForId(int id) {
		String query = "INSERT INTO examsbank (lecturerId, exams) VALUES ("+id+", '{\"exams\": []}');";
		return query;
	}

}
