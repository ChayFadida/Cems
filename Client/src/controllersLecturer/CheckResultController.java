package controllersLecturer;

import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import thirdPart.JsonHandler;

/**
 * Controller class for the lecturer.
 * In this controller the lecturer can check the automatic results of the exams and approve them or change the result.
 */
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

    /**
     * Loads from the DB the exam results.
     * @param ExamResultSet data from the server with the exams results.
     * @throws Exception
     */
    public void loadRequests(ArrayList<HashMap<String, Object>> ExamResultSet) throws Exception {
    	examResArr = new ArrayList<ExamResult>();
    	if(ExamResultSet == null) {
			System.out.println("could not load exams.");
		}
		for (int i = 0; i < ExamResultSet.size(); i++) {
			int examId = (int)ExamResultSet.get(i).get("examId");
			int courseId = (int)ExamResultSet.get(i).get("courseId");
			int studentId = (int)ExamResultSet.get(i).get("studentId");
			String subject = (String)ExamResultSet.get(i).get("subject");
			String examName = (String)ExamResultSet.get(i).get("examName");
			String status = (String)ExamResultSet.get(i).get("status");
			Integer grade = (Integer)ExamResultSet.get(i).get("grade");
			examResArr.add(new ExamResult(examId,courseId,studentId,grade,examName,status,subject));
		}
	}
    
    /**
     * Show the relevant exams on a table.
     */
    public void showTable() {
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> user = new ArrayList<>();
		user.add("Lecturer");
		msg.put("client", user);
		ArrayList<String> query = new ArrayList<>();
		query.add("getExamsResults");
		msg.put("task",query);
		ArrayList<String> parameter = new ArrayList<>();
		parameter.add(""+((Lecturer) ConnectionServer.user).getId());
		msg.put("lecturerId",parameter);
		sendMsgToServer(msg);
		try {
			this.loadRequests(ConnectionServer.rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		initTableView(examResArr);
	}
    
    /**
     * Initialize the exam table with the data from the DB.
     * @param arr ArrayList of exams to show in the table.
     */
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
	}
    
    /**
     * Initialize the table.
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		showTable();				
	}
	
	/**
	 * By activate, the lecturer can view the exam after pressing the specific exam.
	 * @param event
	 */
    @FXML
    void getViewBtn(ActionEvent event) {
    	ExamResult selectedExamResult = resultTable.getSelectionModel().getSelectedItem();
		if(selectedExamResult == null) {
			lblNonSelected.setText("No entry was selected!");
			return;
		}
		ArrayList<Integer> studentAnswers = getStdAnswers(selectedExamResult.getExamId(), selectedExamResult.getStudentId());
		if(studentAnswers == null) {
			System.out.println("The student dont have answers");
			return;
		}
		else {
			lblNonSelected.setText("");
				ArrayList<Integer> questionsInExam = getExamQuestions(selectedExamResult.getExamId() + "");
				ArrayList<String> rightAnswers = new ArrayList<String>();
				ArrayList<String> questionDetails = new ArrayList<String>();
				for(int i = 0 ; i < questionsInExam.size() ; i++) {
					rightAnswers.add(getRightAnswerForQuestion(questionsInExam.get(i) + ""));
					questionDetails.add((String)ConnectionServer.rs.get(0).get("details"));
				}
				//build exam string here.
				StringBuilder examString = new StringBuilder();
				for(int j = 0 ; j < studentAnswers.size() ; j++) {
					examString.append("Question" + (j+1) + ": \n");
					examString.append(questionDetails.get(j) + "\n");
					examString.append("Student Answer: " + studentAnswers.get(j) + "\n");
					examString.append("Right Answer: " + rightAnswers.get(j) + "\n");
					examString.append("\n");
				}
				examString.append("\n Exam End.");
				Stage seconderyStage = new Stage();
				 try {
			        	FXMLLoader loader = new FXMLLoader();
						Parent root = loader.load(getClass().getResource("/guiLecturer/viewExamResult.fxml").openStream());
						Scene scene = new Scene(root);
						LecturerViewExamResultController lecturerViewExamResultController=loader.getController();
						lecturerViewExamResultController.viewResult(examString.toString());
						scene.getStylesheets().add("/gui/GenericStyleSheet.css");
						seconderyStage.initStyle(StageStyle.UNDECORATED);
						seconderyStage.getIcons().add(new Image("/Images/CemsIcon32-Color.png"));
						seconderyStage.setScene(scene);
						seconderyStage.show();
				        super.setPrimaryStage(seconderyStage);
				        PressHandler<MouseEvent> press = new PressHandler<>();
				        DragHandler<MouseEvent> drag = new DragHandler<>();
				        root.setOnMousePressed(press);
				        root.setOnMouseDragged(drag);
					} catch (IOException e) {
						e.printStackTrace();
					}
				System.out.println("view exam successfuly!");
		}
	}
    
    /**
     * By insert the question id, gets the right answer to the question.
     * @param questionId question id.
     * @return question id.
     */
    private String getRightAnswerForQuestion(String questionId){
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> user = new ArrayList<>();
		user.add("Lecturer");
		msg.put("client", user);
		ArrayList<String> parameter = new ArrayList<>();
		parameter.add(questionId);
		msg.put("questionId",parameter);
		ArrayList<String> query = new ArrayList<>();
		query.add("getRightAnswerForQuestion");
		msg.put("task",query);
		sendMsgToServer(msg);
    	return (String)ConnectionServer.rs.get(0).get("rightAnswer");
    }
    
    /**
     * by insert the exam id, gets the exam questions.
     * @param examId exam id.
     * @return the exam id.
     */
    private ArrayList<Integer> getExamQuestions(String examId){
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> user = new ArrayList<>();
		user.add("Lecturer");
		msg.put("client", user);
		ArrayList<String> parameter = new ArrayList<>();
		parameter.add(examId);
		msg.put("examId",parameter);
		ArrayList<String> query = new ArrayList<>();
		query.add("getExamQuestions");
		msg.put("task",query);
		sendMsgToServer(msg);
    	String questionsColumn = (String)ConnectionServer.rs.get(0).get("questions");
		HashMap<String,ArrayList<Integer>> examQuestions = JsonHandler.convertJsonToHashMap(questionsColumn, String.class, ArrayList.class ,Integer.class);
		return examQuestions.get("questions");
    }
    
    /**
     * By insert exam id, gets the student answers.
     * @param examId 
     * @return student answers
     */
    private ArrayList<Integer> getStdAnswers(Integer examId, Integer StdId){
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> arr = new ArrayList<>();
		arr.add("Lecturer");
		msg.put("client", arr);
		ArrayList<String> arr1 = new ArrayList<>();
		arr1.add("getExamResultChosenAnswers");
		msg.put("task",arr1);
		ArrayList<String> arr2 = new ArrayList<>();
		arr2.add(examId + "");
		arr2.add(StdId + "");
		msg.put("param",arr2);
		sendMsgToServer(msg);
		ArrayList<HashMap<String,Object>> rs = ConnectionServer.rs;
		if(rs == null) {
			System.out.println("rs in null");
			return null;
		}
		if(rs.isEmpty()) {
			System.out.println("empty table from DB");
			return new ArrayList<Integer>();
		}
    	String answersChosen = (String)ConnectionServer.rs.get(0).get("answersChosen");
		HashMap<String,ArrayList<Integer>> stdAnswers = JsonHandler.convertJsonToHashMap(answersChosen, String.class, ArrayList.class, Integer.class);
		return stdAnswers.get("answers");
    }

    /**
     * By activate the approve button the lecturer approve student results and update the exam grade status to Done.
     * @param event Action event
     */
    @FXML
    void getApproveBtn(ActionEvent event) {
		ExamResult selectedId = resultTable.getSelectionModel().getSelectedItem();
		if(selectedId==null) {
			lblNonSelected.setText("No entry was selected!");
		}
		else {
			lblNonSelected.setText("");
			HashMap<String,ArrayList<String>> msg = new HashMap<>();
			ArrayList<String> user = new ArrayList<>();
			user.add("Lecturer");
			msg.put("client", user);
			ArrayList<String> query = new ArrayList<>();
			query.add("updateExamResultStatus");
			msg.put("task",query);
			ArrayList<String> parameter = new ArrayList<>();
			parameter.add(selectedId.getExamId() + "");
			parameter.add(selectedId.getStudentId() + "");
			msg.put("param", parameter);
			sendMsgToServer(msg);
			System.out.println(ConnectionServer.rs);
			if(ConnectionServer.rs != null) {
				System.out.println("status changed to Done successfuly!");
				simulatePopUp();
				showTable();
			}
		}
    }
    
    /**
     * Handles the event when the edit button is clicked.
     * It retrieves the selected exam result from the table, validates the input fields,
     * and sends a request to update the exam result grade and notes.
     * If the update is successful, the table is refreshed.
     *
     * @param event The ActionEvent object representing the edit button click event.
     */
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
			ArrayList<String> arr1 = new ArrayList<>();
			arr1.add("updateExamResultGradeNotes");
			msg.put("task",arr1);
			ArrayList<String> arr2 = new ArrayList<>();
			arr2.add(selectedId.get(0).getExamId() + "");
			arr2.add(selectedId.get(0).getStudentId() + "");

			if(txtNewGrade.getLength() == 0 || txtNotes.getLength() == 0) {
				lblNonSelected.setText("No Grade or Notes,\nplease fill all fields!");
				return;
			}
			
			try {
				if(Integer.parseInt(txtNewGrade.getText()) < 0 ||  Integer.parseInt(txtNewGrade.getText()) > 100 ) {
					lblNonSelected.setText("Invalid Grade, try again!");
					return;
				}
			}catch(Exception e) {
				lblNonSelected.setText("Invalid Grade, try again!");
				return;
			}
			
			lblNonSelected.setText("");
			arr2.add(txtNotes.getText());
			arr2.add(txtNewGrade.getText());
			msg.put("param",arr2);
			sendMsgToServer(msg);
			
			if(ConnectionServer.rs != null) {
				System.out.println("Grade and Notes changed to Done successfuly!");
				txtNotes.setText("");
				txtNewGrade.setText("");
				simulatePopUp();
				showTable();
			}
		}
    }
    
    /**
     * Simulates a pop-up window by displaying the email of the selected student in a separate stage.
     * It retrieves the selected exam result from the table, sends a request to get the student's email,
     * and opens a new stage to display the email using the SimulationPopUp fxml.
     */
    private void simulatePopUp() {
    	ArrayList<ExamResult> selectedId = new ArrayList<>();
		selectedId.add(resultTable.getSelectionModel().getSelectedItem());
		if(selectedId.isEmpty()) {
			lblNonSelected.setText("No entry was selected!");
		}
		else {
			lblNonSelected.setText("");
			HashMap<String,ArrayList<String>> msg = new HashMap<>();
			ArrayList<String> user = new ArrayList<>();
			user.add("Lecturer");
			msg.put("client", user);
			ArrayList<String> query = new ArrayList<>();
			query.add("getStudentEmail");
			msg.put("task",query);
			ArrayList<String> parameter = new ArrayList<>();
			parameter.add(selectedId.get(0).getStudentId() + "");
			msg.put("param",parameter);
			sendMsgToServer(msg);
			
			if(ConnectionServer.rs != null) {
				String email = (String)ConnectionServer.rs.get(0).get("email");
				Stage seconderyStage = new Stage();
		        try {
		        	FXMLLoader loader = new FXMLLoader();
					Parent root = loader.load(getClass().getResource("/guiLecturer/SimulationPopUp.fxml").openStream());
					Scene scene = new Scene(root);
					SimulationPopUpController simulationPopUpController=loader.getController();
					simulationPopUpController.viewEmail(email);
					scene.getStylesheets().add("/gui/GenericStyleSheet.css");
					seconderyStage.initStyle(StageStyle.UNDECORATED);
					seconderyStage.getIcons().add(new Image("/Images/CemsIcon32-Color.png"));
					seconderyStage.setScene(scene);
					seconderyStage.show();
			        super.setPrimaryStage(seconderyStage);
			        PressHandler<MouseEvent> press = new PressHandler<>();
			        DragHandler<MouseEvent> drag = new DragHandler<>();
			        root.setOnMousePressed(press);
			        root.setOnMouseDragged(drag);
				} catch (IOException e) {
					e.printStackTrace();
				}			}
		}
    }
    /**
     * by activate it minimize the current window. 
     * @param event Action event 
     */
    @FXML
    void Minimize(ActionEvent event) {
    	Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    /**
     * by activate it close the current window. 
     * @param event Action event 
     */
    @FXML
    void Close(ActionEvent event) {
    	System.exit(0);
    }

}
