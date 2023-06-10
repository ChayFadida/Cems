package controllersHod;


import java.util.ArrayList;
import java.util.HashMap;

import abstractControllers.AbstractController;
import client.ConnectionServer;
import entities.Question;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class HODviewQuestionBankController extends AbstractController {
	
	private ArrayList<Question> qArr ;
	private HODmenuController hODmenuController;

    @FXML
    private Button ApplyTemp;

    @FXML
    private TextField LecturerIdText;

    @FXML
    private TableView<Question> QuestionTable;

    @FXML
    private TableColumn<Question, String> clmCourse;

    @FXML
    private TableColumn<Question, String> clmDetails;

    @FXML
    private TableColumn<Question, String> clmLecturer;

    @FXML
    private TableColumn<Question, String> clmSubject;

    @FXML
    void showTable(ActionEvent event) {
    	String LecturerId = getid();
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> arr = new ArrayList<>();
		arr.add("HOD");
		msg.put("client", arr);
		ArrayList<String> arr1 = new ArrayList<>();
		arr1.add("getViewQuestionsById");
		msg.put("task",arr1);
		ArrayList<String> arr2 = new ArrayList<>();
		arr2.add(LecturerId);
		msg.put("param", arr2);
		sendMsgToServer(msg);
		try {
			this.loadQuestions(ConnectionServer.rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		initTableView(qArr);
    }
    
    private void loadQuestions(ArrayList<HashMap<String, Object>> rs) {
		qArr= new ArrayList<>();
		if(rs == null) {
			System.out.println("rs is null");
			return;
		}
		for (int i = 0; i < rs.size(); i++) {
		    HashMap<String, Object> element = rs.get(i);
		    qArr.add(new Question((Integer)element.get("lecturer"), (String)element.get("details"),
		    (String)element.get("rightAnswer"), (Integer)element.get("questionBank"), (String)element.get("subject"),
		    (String)element.get("composer"),(String)element.get("answers"),(String)element.get("notes")));
		}
	}
    
    
    

	private String getid() {
    	return LecturerIdText.getText();
    }
    
    private void initTableView(ArrayList<Question> arr) {
    	ObservableList<Question> list = FXCollections.observableArrayList(arr);
		PropertyValueFactory<Question, String> pvfDetails = new PropertyValueFactory<>("details");
		PropertyValueFactory<Question, String> pvfLecutrer = new PropertyValueFactory<>("lecturer");
		PropertyValueFactory<Question, String> pvfCourse = new PropertyValueFactory<>("course");
		PropertyValueFactory<Question, String> pvfSubject = new PropertyValueFactory<>("subject");
		clmLecturer.setCellValueFactory(pvfLecutrer);
		clmDetails.setCellValueFactory(pvfDetails);
		clmSubject.setCellValueFactory(pvfSubject);
		clmCourse.setCellValueFactory(pvfCourse);
		QuestionTable.setItems(list);
	}
    
    
    
    
    
}
