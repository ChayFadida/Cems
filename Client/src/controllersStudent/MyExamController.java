package controllersStudent;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import abstractControllers.AbstractController;
import client.ConnectionServer;
import entities.ExamResult;
import entities.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
/**
 * Controller class for the Student.
 * In this controller the Student can view all of his taken exams.
 * Extends AbstractController.
 * Implements Initializable.
 */
public class MyExamController extends AbstractController implements Initializable{
	
	private ArrayList<ExamResult> examResArr ;

    @FXML
    private TableView<ExamResult> ExamTable;

    @FXML
    private TableColumn<ExamResult, Integer> courseId;

    @FXML
    private TableColumn<ExamResult, String> examName;

    @FXML
    private TableColumn<ExamResult, Integer> grade;

    @FXML
    private TableColumn<ExamResult, String> status;

    @FXML
    private TableColumn<ExamResult, String> subject;
    
    /**
	 * Loads the relevant information to get from the DB
	 * @param ExamResult ResultSet Hash Map from the DB
	 * @throws Exception in case of error while loading.
	 */
    public void loadRequests(ArrayList<HashMap<String, Object>> rs) throws Exception {
    	examResArr = new ArrayList<ExamResult>();
    	if(rs == null) {
			System.out.println("rs is null");
		}
		for (int i = 0 ; i < rs.size() ; i++) {
			int examId = (int) rs.get(i).get("examId");
			int courseId = (int) rs.get(i).get("courseId");
			String subject = (String) rs.get(i).get("subject");
			String examName = (String) rs.get(i).get("examName");
			String status = (String) rs.get(i).get("status");
			if(status.equals("waiting for approve")) {
				Integer grade = null;
				examResArr.add(new ExamResult(examId, courseId, ConnectionServer.user.getId(), grade, examName, status, subject));
			}
			else {
				Integer grade = (Integer)rs.get(i).get("grade");
				examResArr.add(new ExamResult(examId, courseId, ConnectionServer.user.getId(), grade, examName, status, subject));		
			}
		}
	}
    
    /**
	 * send to the server message for activate the relevant query and activate initTableView
	 */
    public void showTable() {
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> user = new ArrayList<>();
		user.add("Student");
		msg.put("client", user);
		ArrayList<String> query = new ArrayList<>();
		query.add("getExams");
		msg.put("task", query);
		ArrayList<String> parameter = new ArrayList<>();
		parameter.add("" + ((Student) ConnectionServer.user).getId());
		msg.put("studentId",parameter);
		sendMsgToServer(msg);
		try {
			this.loadRequests(ConnectionServer.rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		initTableView(examResArr);
	}
    /**
	 * Initialize the Table with the provided ArrayList of ExamResult
	 * @param arr ArrayList of ExamResult to display.
	 */
    private void initTableView(ArrayList<ExamResult> arr) {
		ObservableList<ExamResult> list = FXCollections.observableArrayList(arr);
		PropertyValueFactory<ExamResult, String> pvfExamName = new PropertyValueFactory<ExamResult, String>("examName");
		PropertyValueFactory<ExamResult, String> pvfStatus = new PropertyValueFactory<ExamResult, String>("status");
		PropertyValueFactory<ExamResult, Integer> pvfCourseId = new PropertyValueFactory<ExamResult, Integer>("courseId");
		PropertyValueFactory<ExamResult, String> pvfSubject = new PropertyValueFactory<ExamResult, String>("subject");
		PropertyValueFactory<ExamResult, Integer> pvfGrade = new PropertyValueFactory<ExamResult, Integer>("grade");
		examName.setCellValueFactory(pvfExamName);
		status.setCellValueFactory(pvfStatus);
		courseId.setCellValueFactory(pvfCourseId);
		subject.setCellValueFactory(pvfSubject);
		grade.setCellValueFactory(pvfGrade);
		ExamTable.setItems(list);
		ExamTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}
    
    /**
	 * Initialize the table
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		showTable();				
	}
}