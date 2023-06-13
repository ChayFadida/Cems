package DataBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
	
	public static String getLoggedFlag(String username, int flag) {
		return "SELECT * FROM users WHERE username = '" + username +  "' AND isLogged = "+flag+";" ;
	}
	public static String getUserByUserName(String username) {
		String query = "SELECT * FROM users WHERE username = '" + username +  "';" ;
		return query;
	}

	public static String getViewQuestionsById(String string) {
		//returns question id's from questionBank
		String quert = "SELECT * FROM questionbank WHERE lecturerId = '" + string + "'" + ";" ; 
		return quert;
	}
	
	public static String getQuestionByQyestionIdArray(ArrayList<Integer> arr) {
		   String query = "SELECT Q.questionId, Q.courses,Q.details,Q.subject,U.firstName,U.lastName FROM questions AS Q, questionBank AS QB,  users AS U WHERE questionId IN (%s) AND Q.questionBankId=QB.bankID AND QB.lecturerId=U.id";
		// String query = "SELECT * FROM questions WHERE questionId IN (%s)";
		   String idList = listToCsv(arr);
		   return String.format(query, idList);
		}
	
	//query that by giving an id returns the exam id, first and last name of the exam compose, relevant course and subject.
	public static String getViewExamById(String string) {
		String quert = "SELECT e.examID, e.subject, u.firstName, u.lastName, c.courseName FROM exam AS e JOIN users AS u ON e.composerId = u.id JOIN courses AS c ON c.courseID = e.courseID WHERE e.composerId ='" + string + "'" + ";" ;
		return quert;
	}
	
	public static String InsertQuestionToDB(ArrayList<String> hm) {
		String query = "INSERT INTO questions (details, answers, rightAnswer, questionBankId, subject, notes, composer, courses)\r\n" + "VALUES ('" + hm.get(0)+ "','" + hm.get(1)+ "','" + hm.get(2)+ "', '1', '" +  hm.get(3)+ "','" + hm.get(4)+ "', 'Yoni', '" + hm.get(5) + "');";
		return query;
	}

	public static String updateUserByIdLogout(String id) {
		String query = "UPDATE users SET isLogged = "+0+" WHERE id = '"+ id +"' ;";
		return query;
	}

	public static String getStudentByPositionAndDepartment(String position, String department) {
		return "SELECT DISTINCT id,firstName,lastName,position,hod.department,email,pass,username,isLogged FROM users,hod JOIN student ON hod.department =student.department WHERE users.position ='"+ position + "' AND student.department ='" + department + "';" ;
	}
  
	public static String getUserByPosition(String position) {
		return "SELECT * FROM users WHERE position = '" + position +";" ;
	}
	
	public static String getAllCourses() {
		return "SELECT * FROM courses;";
	}
	
	//Tomer: view student statistic HOD //
	
	public static String getStudentNameByID(String id) {
		return "SELECT users.firstName, users.lastName FROM users WHERE id = '" + id +"' ;";
	}
	
	public static String getStudentDoneExamsGradeByID(String id) {
		return "SELECT grade  FROM examresults WHERE studentId = '" + id + "' AND status = 'Done';";
	}
	
	public static String getStudentDoneExamsIdByID(String id) {
		return "SELECT examId  FROM examresults WHERE studentId = '" + id + "' AND status = 'Done';";
	}
	
	public static String getStudentDoneExamsIdANDgradeByID(String id) {//this query returns the student done exams id and their grades
		return "SELECT examId, grade FROM examresults WHERE studentId = '" + id + "' AND status = 'Done';";
	}
	
	public static String getInfoForStudentStats(String id) {
		return "SELECT ex.examId, ex.grade, u.firstName, u.lastName, e.examName FROM examresults AS ex JOIN users AS u ON ex.studentId = u.id JOIN exam AS e ON ex.examId = e.examId WHERE ex.studentId = '" + id + "' AND ex.status = 'Done' GROUP BY ex.examId, ex.grade, u.firstName, u.lastName, e.examName";
	}
	
	
	
	
    private static String listToCsv(List<Integer> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(list.get(i));
        }
        return sb.toString();
    }

}
