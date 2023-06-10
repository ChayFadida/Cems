package controllersHod;


import java.util.ArrayList;
import java.util.HashMap;

import abstractControllers.AbstractController;
import client.ConnectionServer;
import entities.Exam;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


public class HODviewExamBankController extends AbstractController {
	private ArrayList<Exam> eArr ;
	private HODmenuController hODmenuController;

    @FXML
    private Button ApplyTemp;

    @FXML
    private TableView<Exam> ExamsTable;

    @FXML
    private TextField LecturerIdText;

    @FXML
    private TableColumn<Exam,String> clmCourse;

    @FXML
    private TableColumn<Exam,Integer> clmExamId;

    @FXML
    private TableColumn<Exam,String> clmLecturer;

    @FXML
    private TableColumn<Exam,String> clmSubject;

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

	private void initTableView(ArrayList<Exam> arr) {
	   	ObservableList<Exam> list = FXCollections.observableArrayList(arr);
			PropertyValueFactory<Exam,String> pvfCourse = new PropertyValueFactory<>("Course");
			PropertyValueFactory<Exam,Integer> pvfExamId = new PropertyValueFactory<>("examID");
			PropertyValueFactory<Exam,String> pvfLecturer = new PropertyValueFactory<>("ecomposer");
			PropertyValueFactory<Exam,String> pvfSubject = new PropertyValueFactory<>("subject");
			clmCourse.setCellValueFactory(pvfCourse);
			clmExamId.setCellValueFactory(pvfExamId);
			clmLecturer.setCellValueFactory(pvfLecturer);
			clmSubject.setCellValueFactory(pvfSubject);
			ExamsTable.setItems(list);
		
	}

	private void loadExam(ArrayList<HashMap<String, Object>> rs) {
		eArr= new ArrayList<>();
		if(rs == null) {
			System.out.println("rs is null");
			return;
		}
		for (int i = 0; i < rs.size(); i++) {
		    eArr.add(new Exam(rs.get(i)));
		}
		
	}

	private String getid() {
		return LecturerIdText.getText();
	}
    
}
