package controllersLecturer;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import abstractControllers.AbstractController;
import client.ConnectionServer;
import entities.ExamResult;
import entities.Lecturer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class CheckResultController extends AbstractController implements Initializable{
	private ArrayList<ExamResult> examResArr ;
    @FXML
    private Button ApproveGradeButton;

    @FXML
    private Button EditGradeButton;
    
    @FXML
    private Button closeButton;
    @FXML
    private Button btnViewExam;
    
    @FXML
    private Button minimizeButton;
    @FXML
    private Label lblNonSelected;
    
    @FXML
    private TableColumn<ExamResult, Integer> courseId;

    @FXML
    private TableColumn<ExamResult, String> examName;

    @FXML
    private TableColumn<ExamResult, Integer> grade;

    @FXML
    private TableColumn<ExamResult, Integer> studentId;

    @FXML
    private TableColumn<ExamResult, String> subject;
    
    @FXML
    private TableView<ExamResult> resultTable;
    
    @FXML
    private TextField txtNewGrade;

    @FXML
    private TextField txtNotes;

    public void loadRequests(ArrayList<HashMap<String, Object>> rs) throws Exception {
    	examResArr = new ArrayList<ExamResult>();
    	if(rs == null) {
			System.out.println("rs is null");
		}
		for (int i = 0; i < rs.size(); i++) {
			int examId = (int)rs.get(i).get("examId");
			int courseId = (int)rs.get(i).get("courseId");
			int studentId = (int)rs.get(i).get("studentId");
			String subject = (String)rs.get(i).get("subject");
			String examName = (String)rs.get(i).get("examName");
			String status = (String)rs.get(i).get("status");
			Integer grade = (Integer)rs.get(i).get("grade");
			examResArr.add(new ExamResult(examId,courseId,studentId,grade,examName,status,subject));
		}
	}
    public void showTable() {
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> arr = new ArrayList<>();
		arr.add("Lecturer");
		msg.put("client", arr);
		ArrayList<String> arr1 = new ArrayList<>();
		arr1.add("getExamsResults");
		msg.put("task",arr1);
		ArrayList<String> arr3 = new ArrayList<>();
		arr3.add(""+((Lecturer) ConnectionServer.user).getId());
		msg.put("lecturerId",arr3);
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
		PropertyValueFactory<ExamResult, Integer> pvfStudentId = new PropertyValueFactory<ExamResult, Integer>("studentId");
		PropertyValueFactory<ExamResult, String> pvfExamName = new PropertyValueFactory<ExamResult, String>("examName");
		PropertyValueFactory<ExamResult, Integer> pvfCourseId = new PropertyValueFactory<ExamResult, Integer>("courseId");
		PropertyValueFactory<ExamResult, String> pvfSubject = new PropertyValueFactory<ExamResult, String>("subject");
		PropertyValueFactory<ExamResult, Integer> pvfGrade = new PropertyValueFactory<ExamResult, Integer>("grade");
		studentId.setCellValueFactory(pvfStudentId);
		examName.setCellValueFactory(pvfExamName);
		courseId.setCellValueFactory(pvfCourseId);
		subject.setCellValueFactory(pvfSubject);
		grade.setCellValueFactory(pvfGrade);
		resultTable.setItems(list);
		resultTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		showTable();				
	}
//    @FXML
//    void getViewBtn(ActionEvent event) {
//    	//simulation of getting json string of student answers from DB
//    	//and converting it to hashmap with <key , value> = <question number ,chosen answer>.
//    	HashMap<String,Integer>  simulatedAnswers= new HashMap<String,Integer>();
//    	simulatedAnswers.put("q1", 1);
//    	simulatedAnswers.put("q2", 3);
//    	simulatedAnswers.put("q3", 2);
//    	simulatedAnswers.put("q4", 1);
//    	simulatedAnswers.put("q5", 4);
//    	//simulation END.
//    	ArrayList<ExamResult> selectedId = new ArrayList<>();
//		selectedId.addAll(resultTable.getSelectionModel().getSelectedItems());
//		if(selectedId.isEmpty()) {
//			lblNonSelected.setText("No entry was selected!");
//		}
//		else {
//			lblNonSelected.setText("");
//			HashMap<String,ArrayList<String>> msg = new HashMap<>();
//			ArrayList<String> arr = new ArrayList<>();
//			arr.add("Lecturer");
//			msg.put("client", arr);
//			ArrayList<String> arr2 = new ArrayList<>();
//			arr2.add(""+selectedId.get(0).getExamId());
//			msg.put("examId",arr2);
//			ArrayList<String> arr3 = new ArrayList<>();
//			arr3.add("Done");
//			msg.put("status",arr3);
//			ArrayList<String> arr1 = new ArrayList<>();
//			arr1.add("getExamResultPriview");
//			msg.put("task",arr1);
//			sendMsgToServer(msg);
//			if(ConnectionServer.rs != null) {
//				//add conversion from json string to hash map to work with here.(json of answers of student)
//				System.out.println("status changed to Done successfuly!");
//				showTable();
//			}
//		}
//    	
//    }

    @FXML
    void getApproveBtn(ActionEvent event) {
		ArrayList<ExamResult> selectedId = new ArrayList<>();
		selectedId.addAll(resultTable.getSelectionModel().getSelectedItems());
		if(selectedId.isEmpty()) {
			lblNonSelected.setText("No entry was selected!");
		}
		else {
			lblNonSelected.setText("");
			HashMap<String,ArrayList<String>> msg = new HashMap<>();
			ArrayList<String> arr = new ArrayList<>();
			arr.add("Lecturer");
			msg.put("client", arr);
			ArrayList<String> arr2 = new ArrayList<>();
			arr2.add(""+selectedId.get(0).getExamId());
			msg.put("examId",arr2);
			ArrayList<String> arr3 = new ArrayList<>();
			arr3.add("Done");
			msg.put("status",arr3);
			ArrayList<String> arr1 = new ArrayList<>();
			arr1.add("updateExamResultStatus");
			msg.put("task",arr1);
			sendMsgToServer(msg);
			if(ConnectionServer.rs != null) {
				System.out.println("status changed to Done successfuly!");
				showTable();
			}
		}
    }

    @FXML
    void getEditBtn(ActionEvent event) {
    	ArrayList<ExamResult> selectedId = new ArrayList<>();
		selectedId.addAll(resultTable.getSelectionModel().getSelectedItems());
		if(selectedId.isEmpty()) {
			lblNonSelected.setText("No entry was selected!");
		}
		else {
			lblNonSelected.setText("");
			HashMap<String,ArrayList<String>> msg = new HashMap<>();
			ArrayList<String> arr = new ArrayList<>();
			arr.add("Lecturer");
			msg.put("client", arr);
			ArrayList<String> arr2 = new ArrayList<>();
			arr2.add(""+selectedId.get(0).getExamId());
			msg.put("examId",arr2);
			ArrayList<String> arr3 = new ArrayList<>();
			arr3.add("Done");
			msg.put("status",arr3);
			ArrayList<String> arr4 = new ArrayList<>();
			if(txtNotes.getLength() == 0 ||txtNotes.getLength() == 0) {
				lblNonSelected.setText("No Grade or Notes ,please fill all fields!");
				return;
			}
			lblNonSelected.setText("");
			arr4.add(txtNotes.getText());
			arr4.add(txtNewGrade.getText());
			msg.put("params",arr4);
			ArrayList<String> arr1 = new ArrayList<>();
			arr1.add("updateExamResultGradeNotes");
			msg.put("task",arr1);
			sendMsgToServer(msg);
			if(ConnectionServer.rs != null) {
				System.out.println("Grade and Notes changed to Done successfuly!");
				txtNotes.setText("");
				txtNewGrade.setText("");
				showTable();
			}
		}
    }
    
    @FXML
    void Minimize(ActionEvent event) {
    	Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    
    @FXML
    void Close(ActionEvent event) {
    	System.exit(0);
    }

}
