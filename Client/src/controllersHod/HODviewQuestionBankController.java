package controllersHod;


import java.util.ArrayList;
import java.util.HashMap;

import abstractControllers.AbstractController;
import client.ConnectionServer;
import entities.QuestionBankView;
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

public class HODviewQuestionBankController extends AbstractController {
	
	private ArrayList<QuestionBankView> qArr ;
	private HODmenuController hODmenuController;

    @FXML
    private Button ApplyTemp;

    @FXML
    private TextField LecturerIdText;

    @FXML
    private TableView<QuestionBankView> QuestionTable;

    @FXML
    private TableColumn<QuestionBankView, String> clmCourse;

    @FXML
    private TableColumn<QuestionBankView, String> clmDetails;

    @FXML
    private TableColumn<QuestionBankView, String> clmLecturerFirstName;
    
    @FXML
    private TableColumn<QuestionBankView, String> clmLecturerLastName;

    @FXML
    private TableColumn<QuestionBankView, String> clmSubject;

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
		HashMap<Integer, String> getCourseid_courseName = getCourseid_courseName();
        StringBuilder courseNames = new StringBuilder();
        for (HashMap<String, Object> tmp : rs) {
        	ArrayList<Double> courseLstJson =(ArrayList<Double>) JsonHandler.convertJsonToHashMap((String)tmp.get("courses"), String.class, ArrayList.class).get("courses");
            StringBuilder courseNames1 = new StringBuilder();
            ArrayList<Integer> integerQuestionList = new ArrayList<>();
            for (Double d : courseLstJson) {
            	integerQuestionList.add(d.intValue());
            }
            for (int i = 0 ; i<integerQuestionList.size(); i++) {
        		courseNames1.append(getCourseid_courseName.get(integerQuestionList.get(i)));
                if (i < integerQuestionList.size() - 1)
                	courseNames1.append(", ");
            }
//            for (Integer courseCode : integerQuestionList) {
//                if (getCourseid_courseName.get(courseCode).length() > 0) {
//                    courseNames1.append(", ");
//                }
//                courseNames1.append(getCourseid_courseName.get(courseCode));
//            }
            System.out.println(courseNames1.toString());
		    qArr.add(new QuestionBankView((String)tmp.get("details"),
		    		(String)tmp.get("subject"),(String)tmp.get("lastName"),
		    		(String)tmp.get("firstName"),courseNames1.toString()));
        }
	}
    
    
    

	private String getid() {
    	return LecturerIdText.getText();
    }
    
    private void initTableView(ArrayList<QuestionBankView> arr) {
    	ObservableList<QuestionBankView> list = FXCollections.observableArrayList(arr);
		PropertyValueFactory<QuestionBankView, String> pvfDetails = new PropertyValueFactory<>("details");
		PropertyValueFactory<QuestionBankView, String> pvfLecutrerFirstName = new PropertyValueFactory<>("firstName");
		PropertyValueFactory<QuestionBankView, String> pvfLecutrerLasttName = new PropertyValueFactory<>("lastName");
		PropertyValueFactory<QuestionBankView, String> pvfCourse = new PropertyValueFactory<>("courses");
		PropertyValueFactory<QuestionBankView, String> pvfSubject = new PropertyValueFactory<>("subject");
		clmLecturerFirstName.setCellValueFactory(pvfLecutrerFirstName);
		clmLecturerLastName.setCellValueFactory(pvfLecutrerLasttName);
		clmDetails.setCellValueFactory(pvfDetails);
		clmSubject.setCellValueFactory(pvfSubject);
		clmCourse.setCellValueFactory(pvfCourse);
		QuestionTable.setItems(list);
	}
    
    
    
    
    
}
