package DataBase;

import java.util.ArrayList;
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
  
	public static String getUserById(String id) {
		String query = "SELECT * FROM users WHERE id = '" + id +  "';" ;
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
	
	public static String getViewExamById(String string) {
		String quert = "SELECT e.examID, e.subject, u.firstName, u.lastName, c.courseName FROM exam AS e JOIN users AS u ON e.composerId = u.id JOIN courses AS c ON c.courseID = e.courseID WHERE e.composerId ='" + string + "'" + ";" ;
		return quert;
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
	
	public static String getStudentDoneExamsIdByID(String id) {
		return "SELECT examId  FROM examresults WHERE studentId = '" + id + "' AND status = 'Done';";
	}
	
	
	public static String getInfoForCourseStats(String CourseId) {
		return "SELECT er.examId, er.grade, c.courseName, ex.examName, avg_grades.avgGrade\r\n"
				+ "FROM examresults er\r\n"
				+ "JOIN exam ex ON er.examId = ex.examId\r\n"
				+ "JOIN courses c ON ex.courseId = c.courseID\r\n"
				+ "JOIN (\r\n"
				+ "    SELECT examId, AVG(grade) AS avgGrade\r\n"
				+ "    FROM examresults\r\n"
				+ "    WHERE status = 'Done'\r\n"
				+ "    GROUP BY examId\r\n"
				+ ") avg_grades ON er.examId = avg_grades.examId\r\n"
				+ "WHERE ex.courseId = '" + CourseId + "' AND er.status = 'Done';\r\n"
				+ ";" ;
	}
	
	public static String getInfoForLecturerStats(String LecurerId) {
		return "SELECT er.examId, e.examName, er.grade, u.firstName, u.lastName, avg_grade.avgGrade\r\n"
				+ "FROM examresults er\r\n"
				+ "JOIN exam e ON er.examId = e.examId\r\n"
				+ "JOIN users u ON e.composerId = u.id\r\n"
				+ "JOIN (\r\n"
				+ "    SELECT examId, AVG(grade) AS avgGrade\r\n"
				+ "    FROM examresults\r\n"
				+ "    GROUP BY examId\r\n"
				+ ") avg_grade ON er.examId = avg_grade.examId\r\n"
				+ "WHERE er.status = 'Done' AND u.id = '" + LecurerId + "';";
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

	public static String getUserByPositionAndDepartment(String position,String department) {
		String query = "SELECT users.id, users.firstName , users.lastName , users.email,users.position,users.pass,users.username,users.isLogged FROM users , "+position.toLowerCase()+" WHERE users.id = "+position.toLowerCase()+".userId AND "+position.toLowerCase()+".departmentId = "+ department+";";
		return query;
	}

	public static String getAllRequestsInDepartmentOfStatus(String department,String status) {
		String query = "SELECT dr.requestId, dr.examId,dr.lecturerId,dr.courseId,dr.subject,dr.oldDuration,dr.newDuration,dr.status,dr.reasons FROM durationrequest as dr, lecturer as lec  WHERE lec.departmentId =" + department +" AND lec.userId = dr.lecturerId AND dr.status = '" + status +"';";
		return query;
	}

	public static ArrayList<String> InsertQuestionToDB(ArrayList<String> param) {
		String insert = "INSERT INTO questions (details, answers, rightAnswer, questionBankId, subject, notes, courses)\r\n" + "VALUES ('" + param.get(0)+ "','" + param.get(1)+ "','" + param.get(2)+ "', '1', '" +  param.get(3)+ "','" + param.get(4)+ "','" + param.get(5) + "');";
		String select = "SELECT LAST_INSERT_ID();";
		ArrayList<String> queries = new ArrayList<>();
		queries.add(insert);
		queries.add(select);
		return queries;
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

	public static String updateDurationRequest(String status,String id) {
		String query = "update durationrequest set status = '"+ status +"' WHERE requestId = " + id +";";
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
	
	public static String getStudentDoneExamsIdANDgradeByID(String id) {//this query returns the student done exams id and their grades
		return "SELECT examId, grade FROM examresults WHERE studentId = '" + id + "' AND status = 'Done';";
	}
	
	public static String getInfoForStudentStats(String id) {
		return "SELECT ex.examId, ex.grade, u.firstName, u.lastName, e.examName FROM examresults AS ex JOIN users AS u ON ex.studentId = u.id JOIN exam AS e ON ex.examId = e.examId WHERE ex.studentId = '" + id + "' AND ex.status = 'Done' GROUP BY ex.examId, ex.grade, u.firstName, u.lastName, e.examName";
	}
	

	public static String getDepartmentNameById(String id) {
		String query = "SELECT D.* FROM department AS D WHERE id = '" + id +  "';" ;
		return query;
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

	public static String AddDurationRequest(ArrayList<String> param) {
		String query = "INSERT INTO durationrequest (examId, lecturerId, courseId, subject, oldDuration, newDuration, status, reasons)\r\n" + "VALUES (" + param.get(0)+ "," + param.get(1)+ "," + param.get(2)+ ",'" + param.get(3)+ "'," +  param.get(4)+ "," + param.get(5)+ ",'" + param.get(6) + "','" +  param.get(7) + "');";
		return query;
	}
	
	public static String getLecturerExams(ArrayList<String> param) {
		String query = "SELECT e.*, c.courseName\r\n"
				+ "FROM exam e\r\n"
				+ "JOIN courses c ON c.courseID = e.courseID\r\n" 
				+ "WHERE e.composerId = " + param.get(0) + ";";
		return query;
	}
	
	public static String LockExamById(ArrayList<String> param) {
		String query = "UPDATE exam " +
	               "JOIN examresults ON exam.examId = examresults.examId " +
	               "SET exam.isLocked = 1, examresults.status = 'Locked' " +
	               "WHERE exam.examId = " + param.get(0);



	public static String getQBByLecId(String id) {
		String query= "SELECT * FROM questionbank WHERE lecturerId='"+id+"';";
		return query;
	}

	public static String updateQuestionBankById(ArrayList<String> param) {
		String query = "UPDATE questionbank SET questions = '"+param.get(1)+"' WHERE bankID = '"+ param.get(0) +"' ;";
		return query;
	}
	
}
