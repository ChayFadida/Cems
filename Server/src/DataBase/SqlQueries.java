package DataBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.*;

/**
 * class that Represents all the SQL queries.
 */
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
	
	/**
     * Generates an SQL update query to upload a file and update the corresponding table entry.
     *
     * @param data      the data input stream of the file
     * @param table     the table name to update
     * @param examId    the ID of the exam
     * @param studentId the ID of the student
     * @return a hash map containing the SQL query and parameter values for the file upload
     */
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
	
	   /**
     * Retrieves a query to get the file data from a specific table for a given exam and student.
     *
     * @param table     the table name to execute the query on
     * @param examId    the ID of the exam
     * @param studentId the ID of the student
     * @return the SQL query to retrieve the file data from the specified table
     */
	public static String getFile(String table, int examId, int studentId) {
		StringBuilder getFile = new StringBuilder();
		String query = "SELECT pdfBytes FROM ";
		getFile.append(query);
		getFile.append(table);
		getFile.append(" WHERE examId = " + examId + " AND studentId = " + studentId);
		return getFile.toString();
	}

	/**
     * Retrieves a query to get questions by lecturer ID from the database.
     *
     * @param string the lecturer ID
     * @return the SQL query to retrieve questions by lecturer ID
     */
	public static String getQuestionsById(String string) {
		String query = "SELECT Q.* FROM questionbank AS B, lecturer AS L, questions AS Q WHERE L.userId ='" + string + "'AND B.lecturerId = L.userId AND Q.questionBankId = B.bankID;";
		return query;
	}
	
	public static String getExamResultChosenAnswersByExamId(ArrayList<Object> param) {
		String query = "SELECT er.answersChosen   FROM exam as e ,examresults as er WHERE er.examId = " + param.get(0) + " AND er.studentId = " + param.get(1) + ";";
		return query;
	}
	
	
    /**
     * Retrieves a query to get questions by question bank ID and course IDs from the database.
     *
     * @param arrayList a list containing the question bank ID and course IDs
     * @return the SQL query to retrieve questions by question bank ID and course IDs
     */
	public static String getQuestionsByIdByCourse(ArrayList<Object> arrayList) {
		String query = "SELECT *\r\n"
				+ "FROM questions\r\n"
				+ "WHERE questionBankId = " + arrayList.get(0) + " AND JSON_CONTAINS(courses, CAST('[";
		for(int i = 1 ; i < arrayList.size()-1 ; i++) {
			query += arrayList.get(i) + ",";
		}
		query +=  arrayList.get(arrayList.size()-1) + "]' AS JSON), '$.courses');";
		return query;
	}
	
    /**
     * Retrieves a query to get exams by composer ID from the database.
     *
     * @param string the composer ID
     * @return the SQL query to retrieve exams by composer ID
     */
	public static String getExamsById(String string) {
		String quert = "SELECT e.*\r\n"
				+ "FROM exam AS e\r\n"
				+ "JOIN examsbank AS eb ON e.bankId = eb.bankId\r\n"
				+ "WHERE e.composerId = " + string + " AND eb.lecturerId = " + string + ";";
		return quert;
  }
  
	 /**
     * Retrieves a query to get questions by lecturer ID from the database.
     *
     * @param object the lecturer ID
     * @return the SQL query to retrieve questions by lecturer ID
     */
	public static String getQuestionsById(Object object) {
		String query = "SELECT Q.* FROM questionbank AS B, lecturer AS L, questions AS Q WHERE L.userId ='" + object + "'AND B.lecturerId = L.userId AND Q.questionBankId = B.bankID;";
		return query;
	}
	
	/**
     * Retrieves a query to get the right answer and details for a question by its ID.
     *
     * @param object the question ID
     * @return the SQL query to retrieve the right answer and details for a question
     */
	public static String getRightAnswerAndDetailsForQuestionById(Object object) {
		String query = "SELECT details ,rightAnswer FROM questions WHERE questionId = " + (String)object + ";";
		return query;
	}
	
	/**
     * Retrieves a query to get the questions included in an exam by its ID.
     *
     * @param object the exam ID
     * @return the SQL query to retrieve the questions included in an exam
     */
	public static String getExamQuestionsById(Object object) {
		String query = "SELECT qie.questions FROM questionsinexam as qie WHERE qie.examId = " + (String)object + " ;";
		return query;
	}
	
	 /**
     * Retrieves a query to get exams by user ID from the database.
     *
     * @param id the user ID
     * @return the SQL query to retrieve exams by user ID
     */
	public static String getExamsByUserId(String id) {
		String query = "SELECT DISTINCT er.examId,e.examName ,er.status,e.courseId,e.subject,er.grade   FROM exam as e ,examresults as er WHERE er.studentId = " + id + " AND er.examId = e.examId;";
		return query;
	}
	
	/**
     * Retrieves a query to get the chosen answers for an exam result by its ID.
     *
     * @param id the exam ID
     * @return the SQL query to retrieve the chosen answers for an exam result
     */
	public static String getExamResultChosenAnswersByExamId(String id) {
		String query = "SELECT DISTINCT er.answersChosen   FROM exam as e ,examresults as er WHERE er.examId = " + id + ";";
		return query;
	}
	
	
	 /**
     * Retrieves a query to get exams by composer ID from the database.
     *
     * @param id the composer ID
     * @return the SQL query to retrieve exams by composer ID
     */
	public static String getExamsByComposerId(String id) {
		String query = "SELECT er.examId,e.examName ,er.status,e.courseId,e.subject,er.grade,er.studentId   FROM exam as e ,examresults as er WHERE e.composerId = " + id + " AND er.examId = e.examId AND er.status = 'waiting for approve';";
		return query;
	}
	
	 /**
     * Retrieves a query to get a user by username and password from the database.
     *
     * @param pass     the password
     * @param userName the username
     * @return the SQL query to retrieve a user by username and password
     */
	public static String getUserByUserNameAndPass(String pass,String userName) {
		String query = "SELECT * FROM users WHERE username = '" + userName +  "'AND pass = '" + pass+ "';" ;
		return query;
	}
	

    /**
     * Retrieves a query to update the logged-in status of a user by username and password.
     *
     * @param pass       the password
     * @param userName   the username
     * @param loggedFlag the logged-in flag
     * @return the SQL query to update the logged-in status of a user
     */
	public static String updateUserByUserNameAndPassIsLogged(String pass,String userName, int loggedFlag) {
		String query = "UPDATE users SET isLogged = "+loggedFlag+" WHERE username = '"+ userName +"' AND pass = '" + pass + "' ;";
		return query;
	}
	
    /**
     * Retrieves a query to update the status of an exam by exam ID.
     *
     * @param examId the exam ID
     * @param status the exam status
     * @return the SQL query to update the status of an exam
     */
	public static String updateExamStatusByExamId(String examId,String status) {
		String query = "UPDATE examresults SET status = '"+ status +"' WHERE examId = "+ examId +";";
		return query;
	}
	

    /**
     * Retrieves a query to update the grade and notes of an exam result by exam ID.
     *
     * @param examId    the exam ID
     * @param arrayList a list containing the notes and grade
     * @return the SQL query to update the grade and notes of an exam result
     */
	public static String updateExamResultGradeNotesByExamId(String examId,ArrayList<Object> arrayList) {
		String query = "UPDATE examresults SET notes = '"+ arrayList.get(0) +"' , status = 'Done' ,grade = "+ arrayList.get(1) +" WHERE examId = "+ examId +";";
		return query;
	}


    /**
     * Retrieves a query to get the department ID by student ID.
     *
     * @param id the student ID
     * @return the SQL query to retrieve the department ID by student ID
     */
	public static String getDepartmentByStudentId(int id) {
		String query = "Select S.departmentId FROM student AS S WHERE S.userId = '" +id+ "';";
		return query;
	}
	
	
	  /**
     * Retrieves a query to get the department ID by HOD ID.
     *
     * @param id the HOD ID
     * @return the SQL query to retrieve the department ID by HOD ID
     */
	public static String getDepartmentByHodId(int id) {
		String query = "Select H.departmentId FROM hod AS H WHERE H.userId = '" +id+ "';";
		return query;
	}

	 /**
     * Retrieves a query to get the course ID by lecturer ID.
     *
     * @param id the lecturer ID
     * @return the SQL query to retrieve the course ID by lecturer ID
     */
	public static String getCoursesIdByLecturerId(int id) {
		String query = "Select L.courseId FROM lecturer AS L WHERE L.userId = '" +id+ "';";
		return query;
	}
	
	 /**
     * Retrieves a query to get the department ID by lecturer ID.
     *
     * @param id the lecturer ID
     * @return the SQL query to retrieve the department ID by lecturer ID
     */
	public static String getDepartmentByLecturerId(int id) {
		String query = "Select L.departmentId FROM lecturer AS L WHERE L.userId = '" +id+ "';";
		return query;
	}
	
    /**
     * Retrieves a query to check the logged-in flag of a user by username and flag value.
     *
     * @param username the username
     * @param flag     the flag value
     * @return the SQL query to check the logged-in flag of a user
     */

	public static String getLoggedFlag(String username, int flag) {
		return "SELECT * FROM users WHERE username = '" + username +  "' AND isLogged = "+flag+";" ;
	}
	
	 /**
     * Retrieves a query to get a user by username from the database.
     *
     * @param username the username
     * @return the SQL query to retrieve a user by username
     */
	public static String getUserByUserName(String username) {
		String query = "SELECT * FROM users WHERE username = '" + username +  "';" ;
		return query;
	}

	/**
     * Retrieves a query to get a user by ID from the database.
     *
     * @param id the user ID
     * @return the SQL query to retrieve a user by ID
     */
	public static String getUserById(String id) {
		String query = "SELECT * FROM users WHERE id = '" + id +  "';" ;
		return query;
	}

	 /**
     * Retrieves a query to view questions by ID from the question bank.
     *
     * @param string the ID
     * @return the SQL query to view questions by ID from the question bank
     */
	public static String getViewQuestionsById(String string) {
		//returns question id's from questionBank
		String quert = "SELECT * FROM questionbank WHERE lecturerId = '" + string + "'" + ";" ; 
		return quert;
	}

	/**
	 * Updates the status of an exam result to "Done" based on the exam ID and student ID.
	 *
	 * @param param an ArrayList containing the exam ID at index 0 and the student ID at index 1
	 * @return the SQL query string to update the exam result status
	 */
	public static String updateExamResultStatus(ArrayList<Object> param) {
	    String query = "UPDATE examresults SET status = 'Done' WHERE examId = " + param.get(0) + " And studentId = " + param.get(1) + ";";
	    return query;
	}

	/**
	 * Updates the grade and notes of an exam result and sets the status to "Done" based on the exam ID, student ID, grade, and notes.
	 *
	 * @param param an ArrayList containing the exam ID at index 0, the student ID at index 1, the notes at index 2, and the grade at index 3
	 * @return the SQL query string to update the exam result grade, notes, and status
	 */
	public static String updateExamResultGradeNotes(ArrayList<Object> param) {
	    String query = "UPDATE examresults SET notes = '" + param.get(2) + "' , status = 'Done' ,grade = " + param.get(3) + " WHERE examId = " + param.get(0) + " AND studentId = " + param.get(1) + ";";
	    return query;
	}
	   /**
     * Retrieves a query to get questions by question ID array from the database.
     *
     * @param arr the question ID array
     * @return the SQL query to retrieve questions by question ID array
     */
	public static String getQuestionByQyestionIdArray(ArrayList<Integer> arr) {
		   String query = "SELECT Q.questionId, Q.courses,Q.details,Q.subject,U.firstName,U.lastName FROM questions AS Q, questionBank AS QB,  users AS U WHERE questionId IN (%s) AND Q.questionBankId=QB.bankID AND QB.lecturerId=U.id";
		   String idList = listToCsv(arr);
		   return String.format(query, idList);
		}
	
	  /**
     * Retrieves a query to view an exam by ID from the database.
     *
     * @param string the ID
     * @return the SQL query to view an exam by ID
     */
	public static String getViewExamById(String string) {
		String quert = "SELECT e.examID, e.examName, e.subject, u.firstName, u.lastName, c.courseName FROM exam AS e JOIN users AS u ON e.composerId = u.id JOIN courses AS c ON c.courseID = e.courseID WHERE e.composerId ='" + string + "'" + ";" ;
		return quert;
	}
	
	/**
     * Retrieves a query to update the logged-out status of a user by ID.
     *
     * @param id the user ID
     * @return the SQL query to update the logged-out status of a user
     */
	public static String updateUserByIdLogout(String id) {
		String query = "UPDATE users SET isLogged = "+0+" WHERE id = '"+ id +"' ;";
		return query;
	}

	  /**
     * Retrieves a query to get students by position and department from the database.
     *
     * @param position   the position
     * @param department the department
     * @return the SQL query to retrieve students by position and department
     */
	public static String getStudentByPositionAndDepartment(String position, String department) {
		return "SELECT DISTINCT id,firstName,lastName,position,hod.department,email,pass,username,isLogged FROM users,hod JOIN student ON hod.department =student.department WHERE users.position ='"+ position + "' AND student.department ='" + department + "';" ;
	}
  

    /**
     * Retrieves a query to get a user by position from the database.
     *
     * @param position the position
     * @return the SQL query to retrieve a user by position
     */
	public static String getUserByPosition(String position) {
		return "SELECT * FROM users WHERE position = '" + position +";" ;
	}
	
    /**
     * Retrieves a query to get the IDs of exams that a student has completed by ID.
     *
     * @param id the student ID
     * @return the SQL query to retrieve the IDs of exams that a student has completed
     */
	public static String getStudentDoneExamsIdByID(String id) {
		return "SELECT examId  FROM examresults WHERE studentId = '" + id + "' AND status = 'Done';";
	}
	

    /**
     * Retrieves a query to get information for course statistics by course ID.
     *
     * @param courseId the course ID
     * @return the SQL query to retrieve information for course statistics
     */
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
	
	 /**
     * Retrieves a query to get information for lecturer statistics by lecturer ID.
     *
     * @param lecturerId the lecturer ID
     * @return the SQL query to retrieve information for lecturer statistics
     */
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
	
	
	/**
     * Converts a list of integers to a comma-separated string.
     *
     * @param list the list of integers
     * @return the comma-separated string
     */
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

    /**
     * Retrieves a query to get a user by position and department from the database.
     *
     * @param position   the position
     * @param department the department
     * @return the SQL query to retrieve a user by position and department
     */
	public static String getUserByPositionAndDepartment(String position,String department) {
		String query = "SELECT users.id, users.firstName , users.lastName , users.email,users.position,users.pass,users.username,users.isLogged FROM users , "+position.toLowerCase()+" WHERE users.id = "+position.toLowerCase()+".userId AND "+position.toLowerCase()+".departmentId = "+ department+";";
		return query;
	}

	 /**
     * Retrieves a query to get all requests in a department of a specific status.
     *
     * @param department the department
     * @param status     the status
     * @return the SQL query to retrieve all requests in a department of a specific status
     */
	public static String getAllRequestsInDepartmentOfStatus(String department,String status) {
		String query = "SELECT dr.requestId, dr.examId,dr.lecturerId,dr.courseId,dr.subject,dr.oldDuration,dr.newDuration,dr.status,dr.reasons FROM durationrequest as dr, lecturer as lec  WHERE lec.departmentId =" + department +" AND lec.userId = dr.lecturerId AND dr.status = '" + status +"';";
		return query;
	}

	   /**
     * Generates SQL queries to insert a question into the database.
     *
     * @param param the question parameters
     * @return an ArrayList containing the insert and select queries
     */
	public static ArrayList<String> InsertQuestionToDB(ArrayList<Object> param) {
		String insert = "INSERT INTO questions (details, answers, rightAnswer, questionBankId, subject, notes, courses)\r\n" + "VALUES ('" + param.get(0)+ "','" + param.get(1)+ "','" + param.get(2)+ "','" +  param.get(3)+ "','" + param.get(4)+ "','" + param.get(5) + "','" + param.get(6) +  "');";
		String select = "SELECT LAST_INSERT_ID();";
		ArrayList<String> queries = new ArrayList<>();
		queries.add(insert);
		queries.add(select);
		return queries;
	}
	
	 /**
     * Retrieves a query to update a question in the database.
     *
     * @param param the question parameters
     * @return the SQL query to update a question
     */
	public static String updateQuestion(ArrayList<Object> param) {
		String query = "UPDATE questions "
	            + "SET details = '" + param.get(0) + "', "
	            + "answers = '" + param.get(1) + "', "
	            + "rightAnswer = '" + param.get(2) + "', "
	            + "questionBankId = '" + param.get(3) + "', "
	            + "subject = '" + param.get(4) + "', "
	            + "notes = '" + param.get(5) + "', "
	            + "courses = '" + param.get(6) + "' "
	            + "WHERE questionId = " + param.get(7) + ";";

		return query;
	}

	 /**
     * Retrieves a query to update the status of a duration request.
     *
     * @param param the duration request parameters
     * @return the SQL query to update the status of a duration request
     */
	public static String updateDurationRequest(ArrayList<Object> param) {
		String query = "update durationrequest set status = '"+ param.get(7) +"'  WHERE requestId = " + param.get(0) +";";
		return query;
	}

	/**
     * Retrieves a query to get a course by course ID from the database.
     *
     * @param object the course ID
     * @return the SQL query to retrieve a course by course ID
     */
	public static String getCoursesByCourseId(Object object) {
		String query = "SELECT * FROM courses WHERE courseID = '" + object +  "';" ;
		return query;
	}
	
	/**
     * Retrieves a query to get questions by lecturer ID and course from the database.
     *
     * @param object  the lecturer ID
     * @param object2 the course
     * @return the SQL query to retrieve questions by lecturer ID and course
     */
	public static String getQuestionsByLecIdAndCourse(Object object, Object object2) {
		String query = "SELECT Q.*"
				+ " FROM questions AS Q, users AS U, questionBank AS B"
				+ " WHERE U.id = '"+object+"' AND Q.questionBankId=B.bankID AND"
				+ " B.lecturerId = U.id AND LOCATE('"+object2+"', Q.courses) > 0;";
		return query;
	}

	  /**
     * Retrieves a query to get the exams bank by lecturer ID from the database.
     *
     * @param object the lecturer ID
     * @return the SQL query to retrieve the exams bank by lecturer ID
     */
	public static String getExamBankByLecId(Object object) {
		String query = "SELECT B.* FROM examsbank AS B WHERE lecturerId = '" + object +  "';" ;
		return query;
	}
    
	/**
     * Retrieves a query to get the count of exams by composer ID from the database.
     *
     * @param object the composer ID
     * @return the SQL query to retrieve the count of exams by composer ID
     */
	public static String getExamCountByLecId(Object object) {
		String query = "SELECT COUNT(*) AS count FROM exam WHERE composerId = '"+ object+"';";
		return query;
	}

	 /**
     * Retrieves a query to update the exams bank by ID in the database.
     *
     * @param param the parameters (bankId, exams)
     * @return the SQL query to update the exams bank by ID
     */
	public static String updateExamBankById(ArrayList<Object> param) {
		String query = "UPDATE examsbank SET exams = '"+param.get(1)+"' WHERE bankId = '"+ param.get(0) +"' ;";
		return query;
	}
  	
	 /**
     * Retrieves a query to get all courses from the database.
     *
     * @return the SQL query to retrieve all courses
     */
	public static String getAllCourses() {
		return "SELECT * FROM courses;";
	}
	


    /**
     * Retrieves a query to get the student name by ID from the database.
     *
     * @param id the student ID
     * @return the SQL query to retrieve the student name by ID
     */
	public static String getStudentNameByID(String id) {
		return "SELECT users.firstName, users.lastName FROM users WHERE id = '" + id +"' ;";
	}
	
	 /**
     * Retrieves a query to get the grades of exams done by a student by ID from the database.
     *
     * @param id the student ID
     * @return the SQL query to retrieve the grades of exams done by a student by ID
     */
	public static String getStudentDoneExamsGradeByID(String id) {
		return "SELECT grade  FROM examresults WHERE studentId = '" + id + "' AND status = 'Done';";
	}
	
	
	 /**
     * Retrieves a query to get the IDs and grades of exams done by a student by ID from the database.
     *
     * @param id the student ID
     * @return the SQL query to retrieve the IDs and grades of exams done by a student by ID
     */
	public static String getStudentDoneExamsIdANDgradeByID(String id) {//this query returns the student done exams id and their grades
		return "SELECT examId, grade FROM examresults WHERE studentId = '" + id + "' AND status = 'Done';";
	}
	
	 /**
     * Retrieves a query to get information for student statistics by student ID.
     *
     * @param id the student ID
     * @return the SQL query to retrieve information for student statistics
     */
	public static String getInfoForStudentStats(String id) {
		return "SELECT ex.examId, ex.grade, u.firstName, u.lastName, e.examName FROM examresults AS ex JOIN users AS u ON ex.studentId = u.id JOIN exam AS e ON ex.examId = e.examId WHERE ex.studentId = '" + id + "' AND ex.status = 'Done' GROUP BY ex.examId, ex.grade, u.firstName, u.lastName, e.examName";
	}
	
	
	 /**
     * Retrieves a query to get information for exam statistics by exam ID.
     *
     * @param object the exam ID
     * @return the SQL query to retrieve information for exam statistics
     */
	public static String getInfoForExamStats(Object object) {
		return "SELECT ex.examId, ex.grade, ex.studentId, u.firstName FROM examresults AS ex JOIN users AS u ON ex.studentId = u.id WHERE ex.status = 'Done' AND ex.examId= '" + object + "' ;";
	}
	
	 /**
     * Retrieves a query to get the department name by ID from the database.
     *
     * @param object the department ID
     * @return the SQL query to retrieve the department name by ID
     */
	public static String getDepartmentNameById(Object object) {
		String query = "SELECT D.* FROM department AS D WHERE id = '" + object +  "';" ;
		return query;
	}


    /**
     * Generates an SQL query to insert a question into an exam in the database.
     *
     * @param param the question parameters (examId, questions, scores)
     * @return the SQL query to insert a question into an exam
     */
	public static String InsertQuestionToExamInDB(ArrayList<Object> param) {
		String query = "INSERT INTO questionsinexam (examId, questions, scores)"
				+ " VALUES ('"+param.get(0) +"', '"+ param.get(1)+"', '"+ param.get(2)+"');";
		return query;
	}
	
	
	public static String updateQuestionInExamInDB(ArrayList<Object> param) {
	    String query = "UPDATE questionsinexam SET "
	            + "questions = '" + param.get(1) + "', "
	            + "scores = '" + param.get(2) + "' "
	            + "WHERE examId = '" + param.get(0) + "';";
	    return query;
	}

	
	 /**
     * Generates an SQL query to update a question in an exam in the database.
     *
     * @param param the question parameters (examId, questions, scores)
     * @return the SQL query to update a question in an exam
     */
	public static String insertQuestionBankForId(int id) {
		String query = "INSERT INTO questionbank (lecturerId, questions) VALUES ("+id+","
				+ " '{\"questions\": [] }');";
		return query;
	}

	/**
     * Generates an SQL query to insert an exams bank for a specific ID in the database.
     *
     * @param id the lecturer ID
     * @return the SQL query to insert an exams bank for the ID
     */
	public static String insertExamBankForId(int id) {
		String query = "INSERT INTO examsbank (lecturerId, exams) VALUES ("+id+", '{\"exams\": []}');";
		return query;
	}

	  /**
     * Generates an SQL query to delete a question from the database.
     *
     * @param param the question ID
     * @return the SQL query to delete a question
     */
	public static String deleteQuestion(ArrayList<Object> param) {
		String query = "DELETE FROM questions\r\n" + "WHERE questionId = " + param.get(0) + ";";
		return query;
	}
	
	   /**
     * Generates an SQL query to delete an exam from the database.
     *
     * @param param the exam ID
     * @return the SQL query to delete an exam
     */
	public static String deleteExam(ArrayList<Object> param) {
		String query = "DELETE FROM exam\r\n" + "WHERE examId = '" + param.get(0) + "';";
		return query;
	}

	/**
     * Generates an SQL query to get the course names by their IDs from the database.
     *
     * @param param the course IDs
     * @return the SQL query to retrieve the course names by IDs
     */
	public static String getCoursesNameById(ArrayList<Object> param) {
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT courseName, courseID FROM courses WHERE courseID IN (");
		for (int i = 1; i <= Integer.parseInt((String) param.get(0)); i++) {
		  queryBuilder.append(param.get(i));
		  if (i < Integer.parseInt((String) param.get(0))) {
		    queryBuilder.append(", ");
		  }
		}
		queryBuilder.append(");");
		return queryBuilder.toString();
	}

	  /**
     * Generates an SQL query to add a duration request to the database.
     *
     * @param param the duration request parameters
     * @return the SQL query to add a duration request
     */
	public static String AddDurationRequest(ArrayList<Object> param) {
		String query = "INSERT INTO durationrequest (examId, lecturerId, courseId, subject, oldDuration, newDuration, status, reasons)\r\n" + "VALUES (" + param.get(0)+ "," + param.get(1)+ "," + param.get(2)+ ",'" + param.get(3)+ "'," +  param.get(4)+ "," + param.get(5)+ ",'" + param.get(6) + "','" +  param.get(7) + "');";
		return query;
	}
	  
    /**
     * Generates an SQL query to get the exams of a lecturer from the database.
     *
     * @param param the lecturer ID
     * @return the SQL query to retrieve the exams of a lecturer
     */
	public static String getLecturerExams(ArrayList<Object> param) {
		String query = "SELECT e.*, c.courseName\r\n"
				+ "FROM exam e\r\n"
				+ "JOIN courses c ON c.courseID = e.courseID\r\n" 
				+ "WHERE e.composerId = " + param.get(0) + ";";
		return query;
	}
	
	 /**
     * Generates an SQL query to get the exams of a lecturer by course from the database.
     *
     * @param arrayList the lecturer ID and course name
     * @return the SQL query to retrieve the exams of a lecturer by course
     */
	public static String getLecturerExamsByCourse(ArrayList<Object> arrayList) {
		String query = "SELECT e.*, c.courseName\r\n"
				+ "FROM exam e\r\n"
				+ "JOIN courses c ON c.courseID = e.courseId\r\n"
				+ "WHERE e.composerId = " + arrayList.get(0) + " AND c.courseName = '" + arrayList.get(1) + "';\r\n"
				+ "";
		return query;
	}
	/**
     * Generates an SQL query to get the question bank by lecturer ID from the database.
     *
     * @param id the lecturer ID
     * @return the SQL query to retrieve the question bank by lecturer ID
     */
	public static String getQuestionBank(String id) {
		String query= "SELECT * FROM questionbank WHERE lecturerId= "+id+";";
		return query;
  }
  
	 /**
     * Generates an SQL query to lock an exam by ID in the database.
     *
     * @param arrayList the exam ID
     * @return the SQL query to lock an exam by ID
     */
	public static String LockExamById(ArrayList<Object> arrayList) {
		String query = "UPDATE exam " +
	               "JOIN examresults ON exam.examId = examresults.examId " +
	               "SET examresults.status = 'Locked' " +
	               "WHERE exam.examId = " + arrayList.get(0)+ " AND examresults.status = 'inProgress'; ";
		return query;
	}

	 /**
     * Generates an SQL query to get the question bank by lecturer ID from the database.
     *
     * @param object the lecturer ID
     * @return the SQL query to retrieve the question bank by lecturer ID
     */
	public static String getQBByLecId(Object object) {
		String query= "SELECT * FROM questionbank WHERE lecturerId='"+object+"';";
		return query;
	}

    /**
     * Generates an SQL query to update the question bank by ID in the database.
     *
     * @param arrayList the question bank parameters (bankId, questions)
     * @return the SQL query to update the question bank by ID
     */
	public static String updateQuestionBankById(ArrayList<Object> arrayList) {
		String query = "UPDATE questionbank SET questions = '"+arrayList.get(1)+"' WHERE bankID = '"+ arrayList.get(0) +"' ;";
		return query;
	}

	/**
     * Generates an SQL query to get an exam by its code from the database.
     *
     * @param code the exam code
     * @return the SQL query to retrieve an exam by its code
     */
	public static String getExamByCode(String code) {
		String query = "SELECT * FROM exam WHERE code='"+code+"';";
		return query;
	}
	
	  /**
     * Generates an SQL query to retrieve questions and scores by exam ID from the database.
     *
     * @param id the exam ID
     * @return the SQL query to retrieve questions and scores by exam ID
     */
	public static String getQuestionsAndScoresByExamId(String id) {
		String query = "SELECT * FROM questionsinexam WHERE examId='"+id+"';";
		return query;
	}	

	 /**
     * Generates an SQL query to retrieve a question by its ID from the database.
     *
     * @param id the question ID
     * @return the SQL query to retrieve a question by its ID
     */
	public static String getQuestionById(String id) {
		String query = "SELECT * FROM questions WHERE questionId='"+id+"';";
		return query;
	}
	
	/**
     * Generates an SQL query to update the exam result from a manual take exam in the database.
     *
     * @return the SQL query to update the exam result from a manual take exam
     */
	public static String uploadExamResultFromManualTakeExam() {
		String query = "UPDATE examresults SET endTime = ? , pdfBytes = ? , status = ? WHERE examId = ? AND studentId = ?";
		return query;
	}
	
	/**
     * Generates an SQL query to insert an exam into the database.
     *
     * @return the SQL query to insert an exam
     */
	public static String InsertExamToDB() {
		return "INSERT INTO exam (examName, courseId, subject, duration,lecturerNote, studentNote, composerId, code, examNum, bankId, isLocked, examFile) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	}

	/**
     * Generates an SQL query to retrieve an exam result by exam ID and user ID from the database.
     *
     * @param param the exam ID and user ID
     * @return the SQL query to retrieve an exam result by exam ID and user ID
     */	
	public static String getExamresultByExamAndUserId(ArrayList<Object> param) {
		String query = "SELECT R.* FROM examresults AS R WHERE R.examId='"+param.get(0)+"' AND R.studentId='"+param.get(1)+"' ;";
		return query;
	}

	  /**
     * Generates an SQL query to check if an exam is locked by exam ID in the database.
     *
     * @param param the exam ID
     * @return the SQL query to check if an exam is locked by exam ID
     */
	public static String checkIsLockedByExamId(ArrayList<Object> param) {
		String query = "SELECT * FROM exam WHERE examId='"+param.get(0)+"' AND isLocked='1' ;";
		return query;
	}

	   /**
     * Generates an SQL query to insert a new record into the exam results table in the database.
     *
     * @param param the exam result parameters
     * @return the SQL query to insert a new record into the exam results table
     */
	public static String insertToExamresults(ArrayList<Object> param) {
		String query = "INSERT INTO examresults (examId, studentId, selected, startTime, status)"
				+ " VALUES ('"+param.get(0)+"', '"+param.get(1)+"', '"+param.get(2)+"', '"+param.get(3)+"', '"+param.get(4)+"');";
		return query;

	}

	   /**
     * Generates an SQL query to update the exam results in the database.
     *
     * @param param the exam result parameters
     * @return the SQL query to update the exam results
     */
	public static String updateExamresults(ArrayList<Object> param) {
		String query = "UPDATE examresults SET endTime = '"+param.get(2)+"', status = '"+param.get(3)+"' ,"
				+ " grade = '"+param.get(4)+"', answersChosen = '"+param.get(5)+"' "
				+ "WHERE examId = '"+ param.get(0) +"'"
				+ " AND studentId = '"+param.get(1)+"';";
		return query;
	}

	 /**
     * Generates an SQL query to check the count of in-progress exam results by exam ID in the database.
     *
     * @param id the exam ID
     * @return the SQL query to check the count of in-progress exam results by exam ID
     */
	public static String checkCountInProgressByExamId(Object id) {
		String query = "SELECT COUNT(*) AS count FROM examresults WHERE examId='"+id+"' AND status='inProgress' ;";
		return query;
	}

    /**
     * Generates an SQL query to lock an exam by exam ID in the database.
     *
     * @param id the exam ID
     * @return the SQL query to lock an exam by exam ID
     */

	public static String lockExamByExamId(Object id) {
		String query = "UPDATE exam SET isLocked = '1' WHERE examId='"+id+"' ;";
		return query;
	}

	/**
     * Generates an SQL query to get the exam results of other students by exam ID from the database.
     *
     * @param param the exam ID and student ID
     * @return the SQL query to retrieve the exam results of other students by exam ID
     */
	public static String getExamresultsOfOtherStudentsByExamId(ArrayList<Object> param) {
		String query = "SELECT * FROM examresults WHERE examId='"+param.get(0)+"' AND "
				+ "status IN ('waiting for approve','Done') AND studentId != '"+param.get(1)+"' ;";
		return query;
	}

	 /**
     * Generates an SQL query to get the lecturer's email by exam ID from the database.
     *
     * @param id the exam ID
     * @return the SQL query to retrieve the lecturer's email by exam ID
     */
	public static String getLecturerEmailByExamId(Object id) {
		String query = "SELECT U.email FROM exam AS E "
	            + "JOIN users AS U ON E.composerId = U.id "
	            + "WHERE E.examId = " + id + ";";
	    return query;
	}
	
	 /**
     * Generates an SQL query to update the exam in the database.
     *
     * @return the SQL query to update the exam
     */
	public static String updateExamInDB() {
	    return "UPDATE exam SET examName = ?, courseId = ?, subject = ?, duration = ?, lecturerNote = ?, studentNote = ?, composerId = ?, code = ?, examNum = ?, bankId = ?, isLocked = ?, examFile = ? WHERE examId = ?";
	}
	
	
	/**
     * Generates an SQL query to retrieve the questions in an exam from the database.
     *
     * @param arrayList the exam ID
     * @return the SQL query to retrieve the questions in an exam
     */

	public static String getQuestionsInExam(ArrayList<Object> arrayList) {
		return "SELECT questions, scores\r\n"
				+ "FROM questionsinexam\r\n"
				+ "WHERE examId = " + arrayList.get(0) + ";";
	}
	

	
    /**
     * Generates an SQL query to retrieve the exam file by exam ID from the database.
     *
     * @param examId the exam ID
     * @return the SQL query to retrieve the exam file by exam ID
     */
	public static String getExamFileByExamId(Integer examId) {
		return "SELECT examFile FROM exam WHERE examId ='"+ examId + "';";
	}
	
	 /**
     * Generates an SQL query to retrieve the students with exam IDs in progress from the database.
     *
     * @param examIds the list of exam IDs
     * @return the SQL query to retrieve the students with exam IDs in progress
     */
	public static String getStudentWithExamIdAndInProgress(ArrayList<Object> examIds) {
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT * FROM examresults WHERE examId IN (");

		// Build the comma-separated list of examId values
		for (int i = 0; i < examIds.size(); i++) {
		    queryBuilder.append(examIds.get(i));
		    if (i < examIds.size() - 1) {
		        queryBuilder.append(", ");
		    }
		}
		queryBuilder.append(") AND status = 'inProgress';");
		return queryBuilder.toString();
	}
	
	   /**
     * Generates an SQL query to retrieve the student with a specific exam ID in progress from the database.
     *
     * @param examId the exam ID
     * @return the SQL query to retrieve the student with the exam ID in progress
     */
	public static String getStudentWithExamIdAndInProgress(Integer examId) {
	    StringBuilder queryBuilder = new StringBuilder();
	    queryBuilder.append("SELECT * FROM examresults WHERE examId = '")
	            .append(examId)
	            .append("' AND status = 'inProgress';");
	    return queryBuilder.toString();
	}

	/**
     * Generates an SQL query to view all exams with course names from the database.
     *
     * @return the SQL query to view all exams with course names
     */
	public static String getViewAllExams() {
		String query = "SELECT e.*, c.courseName FROM exam AS e "
				+ "JOIN courses AS c ON c.courseID = e.courseID ;" ;
		return query;
	}

	/**
	 * Updates the duration of an exam based on its ID.
	 *
	 * @param param an ArrayList containing the exam ID at index 0 and the new duration at index 1
	 * @return the SQL query string to update the exam duration
	 */
	public static String updateExamDurationById(ArrayList<Object> param) {
	    String query = "UPDATE exam SET duration = '" + param.get(1) + "' WHERE examId = '" + param.get(0) + "' ;";
	    return query;
	}

	/**
	 * Retrieves the email of a student based on their ID.
	 *
	 * @param param an ArrayList containing the student ID at index 0
	 * @return the SQL query string to retrieve the student's email
	 */
	public static String getStudentEmail(ArrayList<Object> param) {
	    String query = "SELECT u.email FROM users AS u "
	            + "WHERE u.id = " + param.get(0) + ";";
	    return query;
	}
	
}
