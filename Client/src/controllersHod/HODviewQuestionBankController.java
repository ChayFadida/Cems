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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import thirdPart.JsonHandler;

/**
 * Controller class for the HOD 
 * In this controller the HOD can view the question bank of specific lecturer by insert her id.
 * Extends AbstractController.
 */
public class HODviewQuestionBankController extends AbstractController {
	
	private ArrayList<QuestionBankView> questionArr ;

    @FXML
    private Button Apply;

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
    private Label notFoundLbl;

    /**
     * Activate the relevant query and sends massage to the server.
     * @param event JAVAFX event.
     */
    @FXML
    void showTable(ActionEvent event) {
    	String LecturerId = getid();
    	notFoundLbl.setText("");
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> user = new ArrayList<>();
		user.add("HOD");
		msg.put("client", user);
		ArrayList<String> query = new ArrayList<>();
		query.add("getViewQuestionsById");
		msg.put("task",query);
		ArrayList<String> parameter = new ArrayList<>();
		parameter.add(LecturerId);
		msg.put("param", parameter);
		sendMsgToServer(msg);
		 try {
	            ArrayList<HashMap<String, Object>> questionResultSet = ConnectionServer.rs;
	            if (questionResultSet == null || questionResultSet.isEmpty()) {
	                notFoundLbl.setText("Lecturer not found");
	                QuestionTable.setItems(null);
	            } else {
	                loadQuestions(questionResultSet);
	                initTableView(questionArr);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
    }
    
	/**
	 * Load the data from the result set.
	 * @param QuestionResultSet the data from the DB.
	 */
    @SuppressWarnings({ "unchecked", "unused" })
	private void loadQuestions(ArrayList<HashMap<String, Object>> QuestionResultSet) {
    	questionArr= new ArrayList<>();
		if(QuestionResultSet == null) {
			System.out.println("Could not get questions.");
			return;
		}
		HashMap<Integer, String> getCourseid_courseName = getCourseid_courseName();
        StringBuilder courseNames = new StringBuilder();
        for (HashMap<String, Object> tmpHashMap : QuestionResultSet) {
        	ArrayList<Double> courseLstJson =(ArrayList<Double>) JsonHandler.convertJsonToHashMap((String)tmpHashMap.get("courses"), String.class, ArrayList.class).get("courses");
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
            questionArr.add(new QuestionBankView((String)tmpHashMap.get("details"),
		    		(String)tmpHashMap.get("subject"),(String)tmpHashMap.get("lastName"),
		    		(String)tmpHashMap.get("firstName"),courseNames1.toString()));
        }
	}
    
    
    
	
	/**
	 * Get the id that the user insert.
	 * @return the lecturer id.
	 */
	private String getid() {
    	return LecturerIdText.getText();
    }
	
    /**
     * Initialize the table with the data we need.
     * @param arr ArrayList of exams.
     */
    private void initTableView(ArrayList<QuestionBankView> ArrQuestion) {
    	ObservableList<QuestionBankView> Questionlist = FXCollections.observableArrayList(ArrQuestion);
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
		QuestionTable.setItems(Questionlist);
	}
    
    
    
    
    
}
