package taskManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import DataBase.DBController;
import DataBase.SqlQueries;
import entities.Question;
import entities.QuestionForExam;
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
				case "getQustionBankById":
		    		return getQuestionsById(hm.get("param"));
				case "getCourses":
					return getCourses();
				case "addNewQuestion":
					return addNewQuestion(hm.get("param"));
				case "updateQuestion":
					return updateQuestion(hm.get("param"));
				case "deleteQuestion":
					return deleteQuestion(hm.get("param"));
				case "getCoursesNameById":
					return getCoursesNameById(hm.get("param"));
				case "getAllQuestions":
					return getAllQuestions();
				case "AddDurationRequest":
					return AddDurationRequest(hm.get("param"));
				case "getLecturerExams":
					return getLecturerExams(hm.get("param"));
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
				case "updateExamBankById":
					return updateExamBankById(hm.get("param"));
				case "getDepartmentNameById":
					return getDepartmentNameById(hm.get("param"));
				case "insertQuestionsForExam":
					return insertQuestionToExam(hm.get("param"));
				case "LockExamById":
					return LockExamById(hm.get("param"));
				case "getQuestionBank":
					return getQB(hm.get("param"));
				case "updateQuestionBank":
					return updateQuestionBankById(hm.get("param"));
				case "getInfoForExamStats":
					return getInfoForExamStats(hm.get("param"));
		    	default: 
		    		System.out.println("no such method for lecturer");
				}
				
		} catch( Exception ex) { ex.printStackTrace(); }
		return null;
	}


	private ArrayList<HashMap<String, Object>> getInfoForExamStats(ArrayList<String> arrayList) throws SQLException {
		 DBController dbController = DBController.getInstance();
		 ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getInfoForExamStats(arrayList.get(0)));
		 return rs;
	}

	private ArrayList<HashMap<String, Object>> updateQuestionBankById(ArrayList<Object> arrayList) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.updateQueries(SqlQueries.updateQuestionBankById(arrayList));
		return rs;
	}

	private ArrayList<HashMap<String, Object>> getQB(ArrayList<Object> arrayList) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getQBByLecId(arrayList.get(0)));
		return rs;
	}

	private ArrayList<HashMap<String, Object>> insertQuestionToExam(ArrayList<Object> param) {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.insertQueries(SqlQueries.InsertQuestionToExamInDB(param));
		return rs;
	}

	private ArrayList<HashMap<String, Object>> getDepartmentNameById(ArrayList<Object> param) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getDepartmentNameById(param.get(0)));
		return rs;
	}

	private ArrayList<HashMap<String, Object>> updateExamBankById(ArrayList<Object> param) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.updateQueries(SqlQueries.updateExamBankById(param));
		return rs;
	}
	
	private ArrayList<HashMap<String, Object>> insertExam(ArrayList<Object> arrayList,ArrayList<Object> arrayList2) {
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
//		String insert = "INSERT INTO exam (examName, courseId, subject, duration,lecturerNote, studentNote, composerId, code, examNum, bankId, isLocked)\r\n" +
//		"VALUES ('" + param.get(9)+ "','" + param.get(0)+ "','" + param.get(1)+ "', '"+param.get(2)+"', '" +  param.get(3)+ "','" + param.get(4)+ "', '"+param.get(5)+"', '"
//				+param.get(6)+"', '"+param.get(7)+"','"+param.get(8)+"', '0');";
            ArrayList<HashMap<String, Object>> rs = dbController.insertQueries(SqlQueries.InsertExamToDB(), parameterValuesList);
            Path fileToDelete = Paths.get(filePath);
            Files.delete(fileToDelete);
            return rs;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


	private ArrayList<HashMap<String, Object>> getExamCountByLecId(ArrayList<Object> arrayList) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getExamCountByLecId(arrayList.get(0)));
		return rs;
	}

	private ArrayList<HashMap<String, Object>> getExamBankByLecId(ArrayList<Object> arrayList) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getExamBankByLecId(arrayList.get(0)));
		return rs;
	}

	private ArrayList<HashMap<String, Object>> getQuestionsByLecIdCourseId(ArrayList<Object> arrayList) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getQuestionsByLecIdAndCourse(arrayList.get(0),arrayList.get(1)));
		return rs;
	}

	private ArrayList<HashMap<String, Object>> getCoursesByCourseId(ArrayList<Object> arrayList) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getCoursesByCourseId(arrayList.get(0)));
		return rs;
	}

	/**
	 *execute get all questions query
	 *@return ArrayList of the result of the query
	 * */
	public ArrayList<HashMap<String, Object>> getAllQuestions() throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getAllTable(dbController.getquestionsTable()));
		return rs;
	}
	
	public ArrayList<HashMap<String, Object>> getQuestionsById(ArrayList<Object> arrayList) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getQuestionsById(arrayList.get(0)));
		return rs;
	}
	public ArrayList<HashMap<String, Object>> getCourses() throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getAllTable("courses"));
		return rs;
	}
	
	public ArrayList<HashMap<String, Object>> addNewQuestion(ArrayList<Object> param) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.insertAndGetKeysQueries(SqlQueries.InsertQuestionToDB(param));
		return rs;
	}
	
	public ArrayList<HashMap<String, Object>> updateQuestion(ArrayList<Object> param) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.updateQueries(SqlQueries.updateQuestion(param));
		return rs;
	}
	
	private ArrayList<HashMap<String, Object>> deleteQuestion(ArrayList<Object> param) {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.insertQueries(SqlQueries.deleteQuestion(param));
		return rs;
	}
	
	private ArrayList<HashMap<String, Object>> getCoursesNameById(ArrayList<Object> param) {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.insertQueries(SqlQueries.getCoursesNameById(param));
		return rs;
	}
	
	private ArrayList<HashMap<String, Object>> AddDurationRequest(ArrayList<Object> param) {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.insertQueries(SqlQueries.AddDurationRequest(param));
		return rs;
	}
	
	private ArrayList<HashMap<String, Object>> getLecturerExams(ArrayList<Object> param) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.executeQueries(SqlQueries.getLecturerExams(param));
		return rs;
	}
	
	public ArrayList<HashMap<String, Object>> LockExamById(ArrayList<Object> arrayList) throws SQLException {
		DBController dbController = DBController.getInstance();
		ArrayList<HashMap<String, Object>> rs = dbController.updateQueries(SqlQueries.LockExamById(arrayList));
		return rs;
	}
}
	

