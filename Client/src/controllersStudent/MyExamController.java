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

public class MyExamController extends AbstractController implements Initializable{
	private ArrayList<ExamResult> examResArr ;

    @FXML
    private TableView<ExamResult> ExamTable;

    @FXML
    private TableColumn<ExamResult, Integer> courseId;
    @FXML
    private TableColumn<ExamResult, Integer> examId;
    @FXML
    private TableColumn<ExamResult, String> examName;

    @FXML
    private TableColumn<ExamResult, Integer> grade;

    @FXML
    private TableColumn<ExamResult, String> status;

    @FXML
    private TableColumn<ExamResult, String> subject;

    public void loadRequests(ArrayList<HashMap<String, Object>> rs) throws Exception {
    	examResArr = new ArrayList<ExamResult>();
    	if(rs == null) {
			System.out.println("rs is null");
		}
		for (int i = 0; i < rs.size(); i++) {
			int examId = (int)rs.get(i).get("examId");
			int courseId = (int)rs.get(i).get("courseId");
			String subject = (String)rs.get(i).get("subject");
			String examName = (String)rs.get(i).get("examName");
			String status = (String)rs.get(i).get("status");
			if(status.equals("waiting for approve")) {
				Integer grade = null;
				examResArr.add(new ExamResult(examId,courseId,ConnectionServer.user.getId(),grade,examName,status,subject));
			}
			else {
				Integer grade = (Integer)rs.get(i).get("grade");
				examResArr.add(new ExamResult(examId,courseId,ConnectionServer.user.getId(),grade,examName,status,subject));		
			}
		}
	}
    public void showTable() {
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> arr = new ArrayList<>();
		arr.add("Student");
		msg.put("client", arr);
		ArrayList<String> arr1 = new ArrayList<>();
		arr1.add("getExams");
		msg.put("task",arr1);
		ArrayList<String> arr3 = new ArrayList<>();
		arr3.add(""+((Student) ConnectionServer.user).getId());
		msg.put("studentId",arr3);
		sendMsgToServer(msg);
		try {
			this.loadRequests(ConnectionServer.rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		initTableView(examResArr);
	}
    private void initTableView(ArrayList<ExamResult> arr) {
		ObservableList<ExamResult> list = FXCollections.observableArrayList(arr);
		PropertyValueFactory<ExamResult, Integer> pvfExamId = new PropertyValueFactory<ExamResult, Integer>("examId");
		PropertyValueFactory<ExamResult, String> pvfExamName = new PropertyValueFactory<ExamResult, String>("examName");
		PropertyValueFactory<ExamResult, String> pvfStatus = new PropertyValueFactory<ExamResult, String>("status");
		PropertyValueFactory<ExamResult, Integer> pvfCourseId = new PropertyValueFactory<ExamResult, Integer>("courseId");
		PropertyValueFactory<ExamResult, String> pvfSubject = new PropertyValueFactory<ExamResult, String>("subject");
		PropertyValueFactory<ExamResult, Integer> pvfGrade = new PropertyValueFactory<ExamResult, Integer>("grade");
		examId.setCellValueFactory(pvfExamId);
		examName.setCellValueFactory(pvfExamName);
		status.setCellValueFactory(pvfStatus);
		courseId.setCellValueFactory(pvfCourseId);
		subject.setCellValueFactory(pvfSubject);
		grade.setCellValueFactory(pvfGrade);
		ExamTable.setItems(list);
		ExamTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		showTable();				
	}
    
    
    

}