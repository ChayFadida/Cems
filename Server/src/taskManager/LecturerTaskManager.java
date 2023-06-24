package taskManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DataBase.DBController;
import DataBase.SqlQueries;
import server.ClientHandler;
import thirdPart.ExamGenerator;

public class LecturerTaskManager implements TaskHandler {
	public LecturerTaskManager() {}

	/**
	 *this method handle with command from the user
	 *@param msg from the client
	 *@return ArrayList of the command that this class catch
	 * */
	@Override
	public ArrayList<HashMap<String, Object>> executeUserCommand(Object msg) {
		@SuppressWarnings("unchecked")
		HashMap<String,ArrayList<Object>> hm = (HashMap<String,ArrayList<Object>>)msg;		
		String task = (String) hm.get("task").get(0);
		try {
			switch (task) {
				case "getQuestionsById":
		    		return getQuestionsById(hm.get("param"));
				case "getQuestionsByIdByCourse":
		    		return getQuestionsByIdByCourse(hm.get("param"));
				case "getExamsById":
		    		return getExamsById(hm.get("param"));
				case "getCoursesIdByLecturerId":
					return getCoursesIdByLecturerId(hm.get("param"));
				case "addNewQuestion":
					return addNewQuestion(hm.get("param"));
				case "updateQuestion":
					return updateQuestion(hm.get("param"));
				case "deleteQuestion":
					return deleteQuestion(hm.get("param"));
				case "deleteExam":
					return deleteExam(hm.get("param"));
				case "getCoursesNameById":
					return getCoursesNameById(hm.get("param"));
				case "getAllQuestions":
					return getAllQuestions();
				case "AddDurationRequest":
					return AddDurationRequest(hm.get("param"));
				case "getLecturerExams":
					return getLecturerExams(hm.get("param"));
				case "getLecturerExamsByCourse":
					return getLecturerExamsByCourse(hm.get("param"));
				case "getCoursesByCourseId":
					return getCoursesByCourseId(hm.get("param"));
				case "getQuestionsByIdAndCourse":
					return getQuestionsByLecIdCourseId(hm.get("param"));
				case "getExamBankByLecId":
					return getExamBankByLecId(hm.get("param"));
				case "getExamCountByLecId":
					return getExamCountByLecId(hm.get("param"));
				case "insertExam":
					return insertExam(hm.get("param"), hm.get("questions"));
				case "updateExam":
					return updateExam(hm.get("param"), hm.get("questions"));
				case "updateExamBankById":
					return updateExamBankById(hm.get("param"));
				case "getDepartmentNameById":
					return getDepartmentNameById(hm.get("param"));
				case "insertQuestionsForExam":
					return insertQuestionToExam(hm.get("param"));
				case "LockExamById":
					return LockExamById(hm.get("param"));
				case "getQuestionBank":
					return getQuestionBank(hm.get("param"));
				case "updateQuestionBank":
					return updateQuestionBankById(hm.get("param"));
				case "getExamsResults":
					return getExamsByLecturerId((String)hm.get("lecturerId").get(0));
				case "updateExamResultStatus":
					return updateExamResultStatus(hm.get("param"));
				case "updateExamResultGradeNotes":
					return updateGradeNotesExamResult(hm.get("param"));
				case "getInfoForExamStats":
					return getInfoForExamStats(hm.get("param"));
				case "updateQuestionInExamInDB":
					return updateQuestionInExamInDB(hm.get("param"));
				case "getQuestionsInExam":
					return getQuestionsInExam(hm.get("param"));
				case "getExamResultChosenAnswers":
					return getExamResultChosenAnswers(hm.get("param"));
				case "getExamQuestions":
					return getExamQuestions((String) hm.get("examId").get(0));
				case "getRightAnswerForQuestion":
					return getRightAnswerForQuestion((String) hm.get("questionId").get(0));
				case "getAllExams":
					return getAllExams();
				case "getStudentEmail":
					return getStudentEmail(hm.get("param"));
				default: 
		    		System.out.println("no such method for lecturer");
				}
				
		} catch( Exception ex) { ex.printStackTrace(); }
		return null;
	}
	/**
	 * Retrieves the email of a student based on the provided parameters.
	 *
	 * @param param an ArrayList containing the necessary parameters
	 * @return an ArrayList of HashMaps containing the student email information
	 * @throws SQLException if there is an error executing the SQL query
	 */
	private ArrayList<HashMap<String, Object>> getStudentEmail(ArrayList<Object> param) throws SQLException {
		DBController dbController = DBController.getInstance();
	    ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getStudentEmail(param));
	    return rs;
	}
	/**
	 * Retrieves all exams from the database.
	 *
	 * @return an ArrayList of HashMaps containing the exam information
	 * @throws SQLException if there is an error executing the SQL query
	 */
	private ArrayList<HashMap<String, Object>> getAllExams() throws SQLException {
		DBController dbController = DBController.getInstance();
	    ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getViewAllExams());
	    return rs;
	}
	/**
	 * Retrieves the questions included in an exam based on the provided parameters.
	 *
	 * @param arrayList an ArrayList containing the necessary parameters
	 * @return an ArrayList of HashMaps containing the exam question information
	 * @throws SQLException if there is an error executing the SQL query
	 */
	private ArrayList<HashMap<String, Object>> getQuestionsInExam(ArrayList<Object> arrayList) throws SQLException {
	    DBController dbController = DBController.getInstance();
	    ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getQuestionsInExam(arrayList));
	    return rs;
	}
	/**
	 * Retrieves the questions in an exam based on the provided question ID.
	 *
	 * @param questionId the ID of the question
	 * @return an ArrayList of HashMaps containing the exam question information
	 * @throws SQLException if there is an error executing the SQL query
	 */
	private ArrayList<HashMap<String, Object>> getExamQuestions(String questionId) throws SQLException {
	    DBController dbController = DBController.getInstance();
	    ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getExamQuestionsById(questionId));
	    return rs;
	}
	/**
	 * Retrieves the right answer and details for a question based on the provided question ID.
	 *
	 * @param questionId the ID of the question
	 * @return an ArrayList of HashMaps containing the right answer and question details
	 * @throws SQLException if there is an error executing the SQL query
	 */
	private ArrayList<HashMap<String, Object>> getRightAnswerForQuestion(String questionId) throws SQLException {
	    DBController dbController = DBController.getInstance();
	    ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getRightAnswerAndDetailsForQuestionById(questionId));
	    return rs;
	}
	/**
	 * Retrieves the chosen answers for an exam result based on the provided parameters.
	 *
	 * @param param an ArrayList containing the necessary parameters
	 * @return an ArrayList of HashMaps containing the chosen answers for the exam result
	 * @throws SQLException if there is an error executing the SQL query
	 */
	private ArrayList<HashMap<String, Object>> getExamResultChosenAnswers(ArrayList<Object> param) throws SQLException {
	    DBController dbController = DBController.getInstance();
	    ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getExamResultChosenAnswersByExamId(param));
	    return rs;
	}
	/**
	 * Retrieves the exams associated with a lecturer ID.
	 *
	 * @param id the ID of the lecturer
	 * @return an ArrayList of HashMaps containing the exams associated with the lecturer ID
	 * @throws SQLException if there is an error executing the SQL query
	 */
	private ArrayList<HashMap<String, Object>> getExamsByLecturerId(String id) throws SQLException {
	    DBController dbController = DBController.getInstance();
	    ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getExamsByComposerId(id));
	    return rs;
	}
	/**
	 * Updates the status of an exam result based on the provided parameters.
	 *
	 * @param param an ArrayList containing the necessary parameters
	 * @return an ArrayList of HashMaps containing the updated exam result status
	 * @throws SQLException if there is an error executing the SQL query
	 */
	private ArrayList<HashMap<String, Object>> updateExamResultStatus(ArrayList<Object> param) throws SQLException {
	    DBController dbController = DBController.getInstance();
	    ArrayList<HashMap<String, Object>> rs = dbController.updateQueries(SqlQueries.updateExamResultStatus(param));
	    return rs;
	}
	/**
	 * Updates the grade and notes of an exam result based on the provided parameters.
	 *
	 * @param param an ArrayList containing the necessary parameters
	 * @return an ArrayList of HashMaps containing the updated exam result grade and notes
	 * @throws SQLException if there is an error executing the SQL query
	 */
	private ArrayList<HashMap<String, Object>> updateGradeNotesExamResult(ArrayList<Object> param) throws SQLException {
	    DBController dbController = DBController.getInstance();
	    ArrayList<HashMap<String, Object>> rs = dbController.updateQueries(SqlQueries.updateExamResultGradeNotes(param));
	    return rs;
	}
	/**
	 * Retrieves information for exam statistics based on the provided parameters.
	 *
	 * @param arrayList an ArrayList containing the necessary parameters
	 * @return an ArrayList of HashMaps containing the exam statistics information
	 * @throws SQLException if there is an error executing the SQL query
	 */
	private ArrayList<HashMap<String, Object>> getInfoForExamStats(ArrayList<Object> arrayList) throws SQLException {
		 DBController dbController = DBController.getInstance();
		 ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getInfoForExamStats(arrayList.get(0)));
		 return rs;
	}
	/**
	 * Updates a question in the question bank based on the provided parameters.
	 *
	 * @param arrayList an ArrayList containing the necessary parameters
	 * @return an ArrayList of HashMaps containing the updated question bank information
	 * @throws SQLException if there is an error executing the SQL query
	 */
	private ArrayList<HashMap<String, Object>> updateQuestionBankById(ArrayList<Object> arrayList) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.updateQueries(SqlQueries.updateQuestionBankById(arrayList));
		return rs;
	}
	/**
	 * Retrieves the question bank based on the provided parameters.
	 *
	 * @param param an ArrayList containing the necessary parameters
	 * @return an ArrayList of HashMaps containing the question bank information
	 * @throws SQLException if there is an error executing the SQL query
	 */
	private ArrayList<HashMap<String, Object>> getQuestionBank(ArrayList<Object> param) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getQuestionBank((String) param.get(0)));
		return rs;
	}
	/**
	 * Inserts a question into an exam based on the provided parameters.
	 *
	 * @param param an ArrayList containing the necessary parameters
	 * @return an ArrayList of HashMaps containing the inserted question information
	 */
	private ArrayList<HashMap<String, Object>> insertQuestionToExam(ArrayList<Object> param) {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.insertQueries(SqlQueries.InsertQuestionToExamInDB(param));
		return rs;
	}
	/**
	 * Updates a question in an exam based on the provided parameters.
	 *
	 * @param param an ArrayList containing the necessary parameters
	 * @return an ArrayList of HashMaps containing the updated question information
	 * @throws SQLException if there is an error executing the SQL query
	 */
	private ArrayList<HashMap<String, Object>> updateQuestionInExamInDB(ArrayList<Object> param) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.updateQueries(SqlQueries.updateQuestionInExamInDB(param));
		return rs;
	}
	/**
	 * Retrieves the department name based on the provided parameters.
	 *
	 * @param param an ArrayList containing the necessary parameters
	 * @return an ArrayList of HashMaps containing the department name information
	 * @throws SQLException if there is an error executing the SQL query
	 */
	private ArrayList<HashMap<String, Object>> getDepartmentNameById(ArrayList<Object> param) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getDepartmentNameById(param.get(0)));
		return rs;
	}
	/**
	 * Updates an exam in the exam bank based on the provided parameters.
	 *
	 * @param param an ArrayList containing the necessary parameters
	 * @return an ArrayList of HashMaps containing the updated exam bank information
	 * @throws SQLException if there is an error executing the SQL query
	 */
	private ArrayList<HashMap<String, Object>> updateExamBankById(ArrayList<Object> param) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.updateQueries(SqlQueries.updateExamBankById(param));
		return rs;
	}
	/**
	 * Inserts an exam into the database based on the provided parameters.
	 *
	 * @param arrayList an ArrayList containing the necessary parameters
	 * @param arrayList2 an ArrayList containing additional parameters
	 * @return an ArrayList of HashMaps containing the inserted exam information
	 * @throws Exception 
	 */
	private ArrayList<HashMap<String, Object>> insertExam(ArrayList<Object> arrayList,ArrayList<Object> arrayList2) throws Exception {
		DBController dbController = DBController.getInstance();
		try {
            Path tempDir = Files.createTempDirectory("my-temp-dir");
            String dirPath = tempDir.toString();
            String filePath = dirPath + "/wordFile.doc";
            ExamGenerator examGenerator = new ExamGenerator();
            examGenerator.generateExamDoc(arrayList2, filePath, (String)arrayList.get(0), (String)arrayList.get(9), (String)arrayList.get(2));
            byte[] fileBytes = Files.readAllBytes(Path.of(filePath));
		
            List<Object[]> parameterValuesList = new ArrayList<>();
            Object[] valuesRow = { (String)arrayList.get(9), (String)arrayList.get(0), (String)arrayList.get(1),
            		(String)arrayList.get(2), (String)arrayList.get(3), (String)arrayList.get(4), (String)arrayList.get(5),(String)arrayList.get(6),
            		(String)arrayList.get(7), (String)arrayList.get(8), 0, fileBytes };
            parameterValuesList.add(valuesRow);
            ArrayList<HashMap<String, Object>> rs = dbController.insertQueries(SqlQueries.InsertExamToDB(), parameterValuesList);
            Path fileToDelete = Paths.get(filePath);
            Files.delete(fileToDelete);
            return rs;
		} catch (IOException e) {
			System.out.println("could not execute insertExam");
		}
		return null;
	}
	/**
	 * Updates an existing exam in the database based on the provided parameters.
	 *
	 * @param arrayList an ArrayList containing the necessary parameters
	 * @param arrayList2 an ArrayList containing additional parameters
	 * @return an ArrayList of HashMaps containing the updated exam information
	 */
	private ArrayList<HashMap<String, Object>> updateExam(ArrayList<Object> arrayList, ArrayList<Object> arrayList2) {
	    DBController dbController = DBController.getInstance();
	    try {
	        Path tempDir = Files.createTempDirectory("my-temp-dir");
	        String filePath = tempDir.resolve("wordFile.doc").toString();
	        ExamGenerator examGenerator = new ExamGenerator();
	        examGenerator.generateExamDoc(arrayList2, filePath, (String) arrayList.get(0), (String) arrayList.get(9), (String) arrayList.get(2));
	        byte[] fileBytes = Files.readAllBytes(Path.of(filePath));

	        List<Object[]> parameterValuesList = new ArrayList<>();
	        Object[] valuesRow = {(String) arrayList.get(9), (String) arrayList.get(0), (String) arrayList.get(1),
	                (String) arrayList.get(2), (String) arrayList.get(3), (String) arrayList.get(4), (String) arrayList.get(5), (String) arrayList.get(6),
	                (String) arrayList.get(7), (String) arrayList.get(8), 0, fileBytes,(String) arrayList.get(10) };
	        parameterValuesList.add(valuesRow);

	        ArrayList<HashMap<String, Object>> rs = dbController.updateQueries(SqlQueries.updateExamInDB(), parameterValuesList);

	        // Cleanup temporary files
	        Files.deleteIfExists(Path.of(filePath));
	        Files.deleteIfExists(tempDir);

	        return rs;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	/**
	 * Retrieves the count of exams for a lecturer based on the provided parameters.
	 *
	 * @param arrayList an ArrayList containing the necessary parameters
	 * @return an ArrayList of HashMaps containing the exam count for the lecturer
	 * @throws SQLException if there is an error executing the SQL query
	 */
	private ArrayList<HashMap<String, Object>> getExamCountByLecId(ArrayList<Object> arrayList) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getExamCountByLecId(arrayList.get(0)));
		return rs;
	}
	/**
	 * Retrieves the exam bank for a lecturer based on the provided parameters.
	 *
	 * @param arrayList an ArrayList containing the necessary parameters
	 * @return an ArrayList of HashMaps containing the exam bank for the lecturer
	 * @throws SQLException if there is an error executing the SQL query
	 */
	private ArrayList<HashMap<String, Object>> getExamBankByLecId(ArrayList<Object> arrayList) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getExamBankByLecId(arrayList.get(0)));
		return rs;
	}
	/**
	 * Retrieves the questions for a lecturer and course based on the provided parameters.
	 *
	 * @param arrayList an ArrayList containing the necessary parameters
	 * @return an ArrayList of HashMaps containing the questions for the lecturer and course
	 * @throws SQLException if there is an error executing the SQL query
	 */
	private ArrayList<HashMap<String, Object>> getQuestionsByLecIdCourseId(ArrayList<Object> arrayList) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getQuestionsByLecIdAndCourse(arrayList.get(0),arrayList.get(1)));
		return rs;
	}
	/**
	 * Retrieves the course information for a specific course ID.
	 *
	 * @param arrayList an ArrayList containing the necessary parameters
	 * @return an ArrayList of HashMaps containing the course information
	 * @throws SQLException if there is an error executing the SQL query
	 */
	private ArrayList<HashMap<String, Object>> getCoursesByCourseId(ArrayList<Object> arrayList) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getCoursesByCourseId(arrayList.get(0)));
		return rs;
	}

	/**
	 * Retrieves all questions from the database.
	 *
	 * @return an ArrayList of HashMaps containing all the questions
	 * @throws SQLException if there is an error executing the SQL query
	 */
	public ArrayList<HashMap<String, Object>> getAllQuestions() throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getAllTable(dbController.getquestionsTable()));
		return rs;
	}
	/**
	 * Retrieves questions based on the provided question ID.
	 *
	 * @param arrayList an ArrayList containing the necessary parameters
	 * @return an ArrayList of HashMaps containing the questions for the specified ID
	 * @throws SQLException if there is an error executing the SQL query
	 */
	public ArrayList<HashMap<String, Object>> getQuestionsById(ArrayList<Object> arrayList) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getQuestionsById(arrayList.get(0)));
		return rs;
	}
	/**
	 * Retrieves questions based on the provided question ID and course ID.
	 *
	 * @param arrayList an ArrayList containing the necessary parameters
	 * @return an ArrayList of HashMaps containing the questions for the specified IDs
	 * @throws SQLException if there is an error executing the SQL query
	 */
	public ArrayList<HashMap<String, Object>> getQuestionsByIdByCourse(ArrayList<Object> arrayList) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getQuestionsByIdByCourse(arrayList));
		return rs;
	}
	/**
	 * Retrieves exams based on the provided exam ID.
	 *
	 * @param param an ArrayList containing the necessary parameters
	 * @return an ArrayList of HashMaps containing the exams for the specified ID
	 * @throws SQLException if there is an error executing the SQL query
	 */
	public ArrayList<HashMap<String, Object>> getExamsById(ArrayList<Object> param) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getExamsById((String) param.get(0)));
		return rs;
	}
	/**
	 * Retrieves course IDs based on the provided lecturer ID.
	 *
	 * @param param an ArrayList containing the necessary parameters
	 * @return an ArrayList of HashMaps containing the course IDs for the specified lecturer ID
	 * @throws SQLException if there is an error executing the SQL query
	 */
	public ArrayList<HashMap<String, Object>> getCoursesIdByLecturerId(ArrayList<Object> param) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getCoursesIdByLecturerId(Integer.parseInt((String) param.get(0))));
		return rs;
	}
	/**
	 * Adds a new question to the database based on the provided parameters.
	 *
	 * @param param an ArrayList containing the necessary parameters
	 * @return an ArrayList of HashMaps containing the inserted question information
	 * @throws SQLException if there is an error executing the SQL query
	 */
	public ArrayList<HashMap<String, Object>> addNewQuestion(ArrayList<Object> param) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.insertAndGetKeysQueries(SqlQueries.InsertQuestionToDB(param));
		return rs;
	}
	/**
	 * Updates a question in the database based on the provided parameters.
	 *
	 * @param param an ArrayList containing the necessary parameters
	 * @return an ArrayList of HashMaps containing the updated question information
	 * @throws SQLException if there is an error executing the SQL query
	 */
	public ArrayList<HashMap<String, Object>> updateQuestion(ArrayList<Object> param) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.updateQueries(SqlQueries.updateQuestion(param));
		return rs;
	}
	/**
	 * Deletes a question from the database based on the provided parameters.
	 *
	 * @param param an ArrayList containing the necessary parameters
	 * @return an ArrayList of HashMaps containing the result of the deletion operation
	 * @throws SQLException if there is an error executing the SQL query
	 */
	private ArrayList<HashMap<String, Object>> deleteQuestion(ArrayList<Object> param) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.insertQueries(SqlQueries.deleteQuestion(param));
		return rs;
	}
	/**
	 * Deletes an exam from the database based on the provided parameters.
	 *
	 * @param param an ArrayList containing the necessary parameters
	 * @return an ArrayList of HashMaps containing the result of the deletion operation
	 * @throws SQLException if there is an error executing the SQL query
	 */
	private ArrayList<HashMap<String, Object>> deleteExam(ArrayList<Object> param) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.insertQueries(SqlQueries.deleteExam(param));
		return rs;
	}
	/**
	 * Retrieves the course names for the specified IDs.
	 *
	 * @param param an ArrayList containing the necessary parameters
	 * @return an ArrayList of HashMaps containing the course names
	 * @throws SQLException if there is an error executing the SQL query
	 */
	private ArrayList<HashMap<String, Object>> getCoursesNameById(ArrayList<Object> param) throws SQLException{
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getCoursesNameById(param));
		return rs;
	}
	/**
	 * Adds a duration request to the database based on the provided parameters.
	 *
	 * @param param an ArrayList containing the necessary parameters
	 * @return an ArrayList of HashMaps containing the result of the insertion operation
	 */
	private ArrayList<HashMap<String, Object>> AddDurationRequest(ArrayList<Object> param) {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.insertQueries(SqlQueries.AddDurationRequest(param));
		return rs;
	}
	/**
	 * Retrieves the exams for a lecturer based on the provided parameters.
	 *
	 * @param param an ArrayList containing the necessary parameters
	 * @return an ArrayList of HashMaps containing the exams for the lecturer
	 * @throws SQLException if there is an error executing the SQL query
	 */
	private ArrayList<HashMap<String, Object>> getLecturerExams(ArrayList<Object> param) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getLecturerExams(param));
		return rs;
	}
	/**
	 * Retrieves the exams for a lecturer and course based on the provided parameters.
	 *
	 * @param arrayList an ArrayList containing the necessary parameters
	 * @return an ArrayList of HashMaps containing the exams for the lecturer and course
	 * @throws SQLException if there is an error executing the SQL query
	 */
	private ArrayList<HashMap<String, Object>> getLecturerExamsByCourse(ArrayList<Object> arrayList) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getLecturerExamsByCourse(arrayList));
		return rs;
	}
	/**
	 * Locks an exam by its ID and sends a message to the clients.
	 *
	 * @param arrayList an ArrayList containing the necessary parameters
	 * @return an ArrayList of HashMaps containing the student information for the locked exam
	 * @throws SQLException if there is an error executing the SQL query
	 */
	@SuppressWarnings("unused")
	public ArrayList<HashMap<String, Object>> LockExamById(ArrayList<Object> arrayList) throws SQLException {
		DBController dbController = DBController.getInstance();
		HashMap<String, Object> msgToClient = new HashMap<String, Object>();
		msgToClient.put("Special Method", "EXAM_BLOCKED");
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getStudentWithExamIdAndInProgress(arrayList));
		ArrayList<HashMap<String, Object>> lockExam = dbController.updateQueries(SqlQueries.lockExamByExamId(arrayList.get(0)));
		ArrayList<HashMap<String, Object>> lockedExam = dbController.updateQueries(SqlQueries.LockExamById(arrayList));
        ArrayList<Integer> idToLock = new ArrayList<Integer>();
		for (Map<String, Object> dict : rs) {
            Integer studentId = (int) dict.get("studentId");
            idToLock.add(studentId);
        }
		msgToClient.put("idToLock", idToLock);
		ClientHandler.getInstance().sendToAllClients(msgToClient);
		return rs;
	}
}
	

