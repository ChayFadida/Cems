package controllersHod;
import java.util.ArrayList;
import java.util.HashMap;
import abstractControllers.AbstractController;
import client.ConnectionServer;
import entities.ExamBankView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controller class for the HOD 
 * In this controller the HOD can view the exam bank of specific lecturer by insert her id.
 * Extends AbstractController.
 */
public class HODviewExamBankController extends AbstractController {
	
	private ArrayList<ExamBankView> examArr ;

    @FXML
    private Button Apply;

    @FXML
    private TableView<ExamBankView> ExamsTable;

    @FXML
    private TextField LecturerIdText;

    @FXML
    private TableColumn<ExamBankView,String> clmCourse;

    @FXML
    private TableColumn<ExamBankView,Integer> clmExamId;

    @FXML
    private TableColumn<ExamBankView,String> clmLecturerFirstName;
    
    @FXML
    private TableColumn<ExamBankView,String> clmLecturerLastName;

    @FXML
    private TableColumn<ExamBankView,String> clmSubject;
    
    @FXML
    private Label notFoundLbl;
    
    
    /**
     * Activate the relevant query and sends massage to the server.
     * @param event JAVAFX event.
     */
    @FXML
    void showTable(ActionEvent event) {
    	String LecturerId = getid();
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> user = new ArrayList<>();
		user.add("HOD");
		msg.put("client", user);
		ArrayList<String> query = new ArrayList<>();
		query.add("getViewExamById");
		msg.put("task", query);
		ArrayList<String> parameter = new ArrayList<>();
		parameter.add(LecturerId);
		msg.put("param", parameter);
		sendMsgToServer(msg);
		try {
			this.loadExam(ConnectionServer.rs);
			if(examArr.isEmpty()) {
				notFoundLbl.setText("The ID is not valid or the exam bank is empty");
				ExamsTable.setItems(null);
			}else {
				notFoundLbl.setText("");
				initTableView(examArr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		initTableView(examArr);
    }
    
     /**
      * Initialize the table with the data we need.
      * @param arr ArrayList of exams.
      */
	private void initTableView(ArrayList<ExamBankView> EXarr) {
	   	ObservableList<ExamBankView> Examlist = FXCollections.observableArrayList(EXarr);
			PropertyValueFactory<ExamBankView,String> pvfCourse = new PropertyValueFactory<>("courses");
			PropertyValueFactory<ExamBankView,Integer> pvfExamId = new PropertyValueFactory<>("examName");
			PropertyValueFactory<ExamBankView,String> pvfLecturerFirstName = new PropertyValueFactory<>("firstName");
			PropertyValueFactory<ExamBankView,String> pvfLecturerLastName = new PropertyValueFactory<>("lastName");
			PropertyValueFactory<ExamBankView,String> pvfSubject = new PropertyValueFactory<>("subject");
			clmCourse.setCellValueFactory(pvfCourse);
			clmExamId.setCellValueFactory(pvfExamId);
			clmLecturerFirstName.setCellValueFactory(pvfLecturerFirstName);
			clmLecturerLastName.setCellValueFactory(pvfLecturerLastName);
			clmSubject.setCellValueFactory(pvfSubject);
			ExamsTable.setItems(Examlist);
	}
	
	/**
	 * Load the data from the result set.
	 * @param QuestionResultSet the data from the DB.
	 */
	private void loadExam(ArrayList<HashMap<String, Object>> ExamResultSet) {
		examArr = new ArrayList<>();
		if(ExamResultSet == null) {
			System.out.println("Could not get exams.");
			return;
		}
        for (HashMap<String, Object> tmpHashMap : ExamResultSet) {
        	examArr.add(new ExamBankView((Integer)tmpHashMap.get("examId"),
		    		(String) tmpHashMap.get("firstName"),(String)tmpHashMap.get("lastName"),
		    		(String) tmpHashMap.get("subject"), (String)tmpHashMap.get("courseName"), (String) tmpHashMap.get("examName")));
        }
	}
	
	/**
	 * Get the id that the user insert.
	 * @return the lecturer id.
	 */
	private String getid() {
		return LecturerIdText.getText();
	}
}
