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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import thirdPart.JsonHandler;


public class HODviewExamBankController extends AbstractController {
	private ArrayList<ExamBankView> eArr ;
	private HODmenuController hODmenuController;

    @FXML
    private Button ApplyTemp;

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
    void showTable(ActionEvent event) {
    	String LecturerId = getid();
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> arr = new ArrayList<>();
		arr.add("HOD");
		msg.put("client", arr);
		ArrayList<String> arr1 = new ArrayList<>();
		arr1.add("getViewExamById");
		msg.put("task",arr1);
		ArrayList<String> arr2 = new ArrayList<>();
		arr2.add(LecturerId);
		msg.put("param", arr2);
		sendMsgToServer(msg);
		try {
			this.loadExam(ConnectionServer.rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		initTableView(eArr);
    }

	private void initTableView(ArrayList<ExamBankView> arr) {
	   	ObservableList<ExamBankView> list = FXCollections.observableArrayList(arr);
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
			ExamsTable.setItems(list);
		
	}

	private void loadExam(ArrayList<HashMap<String, Object>> rs) {
		eArr= new ArrayList<>();
		if(rs == null) {
			System.out.println("rs is null");
			return;
		}
        for (HashMap<String, Object> tmp : rs) {
		    eArr.add(new ExamBankView((Integer)tmp.get("examId"),
		    		(String)tmp.get("firstName"),(String)tmp.get("lastName"),
		    		(String)tmp.get("subject"), (String)tmp.get("courseName"), (String)tmp.get("examName")));
        }
	
	}

	private String getid() {
		return LecturerIdText.getText();
	}
    
}
