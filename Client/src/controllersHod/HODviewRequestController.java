package controllersHod;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import abstractControllers.AbstractController;
import client.ConnectionServer;
import entities.Hod;
import entities.Request;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class HODviewRequestController extends AbstractController implements Initializable{
	private ArrayList<Request> examArr ;
	ArrayList<CheckMenuItem> requestsSelected;
    @FXML
    public TableColumn<Request, Integer> examId;
    @FXML
    public TableColumn<Request, String> lecturerId;
    @FXML
    public TableColumn<Request, String> courseId;
    @FXML
    public TableColumn<Request, String> subject;
    @FXML
    public TableColumn<Request, Integer> oldDuration;
    @FXML
    public TableColumn<Request, Integer> newDuration;
    @FXML
    public TableColumn<Request, Integer> requestId;
    @FXML
    public TableColumn<Request, String> reasons;

    @FXML
    private Label lblNonSelected;
    @FXML
    private Button ApproveButton;

    
    @FXML
    private Button DenyButton;

    @FXML
    private TableView<Request> RequestsTable;

	
	public void loadRequests(ArrayList<HashMap<String, Object>> rs) throws Exception {
    	examArr = new ArrayList<Request>();
    	if(rs == null) {
			System.out.println("rs is null");
		}
		for (int i = 0; i < rs.size(); i++) {
			int requestId = (int)rs.get(i).get("requestId");
			int examId = (int)rs.get(i).get("examId");
			int lecturerId = (int)rs.get(i).get("lecturerId");
			int courseId = (int)rs.get(i).get("courseId");
			String subject = (String)rs.get(i).get("subject");
			int oldDuration = (int)rs.get(i).get("oldDuration");
			int newDuration = (int)rs.get(i).get("newDuration");
			String status = (String)rs.get(i).get("status");
			String reasons = (String)rs.get(i).get("reasons");
		    examArr.add(new Request(requestId,examId,lecturerId,courseId,subject,oldDuration,newDuration,status,reasons));
		}
	}
		
	public void showTable() {
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> arr = new ArrayList<>();
		arr.add("HOD");
		msg.put("client", arr);
		ArrayList<String> arr2 = new ArrayList<>();
		arr2.add("inProgress");
		msg.put("status",arr2);
		ArrayList<String> arr1 = new ArrayList<>();
		arr1.add("getAllRequests");
		msg.put("task",arr1);
		ArrayList<String> arr3 = new ArrayList<>();
		arr3.add(""+((Hod) ConnectionServer.user).getDepartment());
		msg.put("department",arr3);
		sendMsgToServer(msg);
		try {
			this.loadRequests(ConnectionServer.rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		initTableView(examArr);
	}
	private void initTableView(ArrayList<Request> arr) {
		ObservableList<Request> list = FXCollections.observableArrayList(arr);
		PropertyValueFactory<Request, Integer> pvfrequestId = new PropertyValueFactory<Request, Integer>("requestId");
		PropertyValueFactory<Request, Integer> pvfexamId = new PropertyValueFactory<Request, Integer>("examId");
		PropertyValueFactory<Request, String> pvfLecturer = new PropertyValueFactory<Request, String>("lecturerId");
		PropertyValueFactory<Request, String> pvfCourse = new PropertyValueFactory<Request, String>("courseId");
		PropertyValueFactory<Request, String> pvfSSubject = new PropertyValueFactory<Request, String>("subject");
		PropertyValueFactory<Request, Integer> pvfSOldDuration = new PropertyValueFactory<Request, Integer>("oldDuration");
		PropertyValueFactory<Request, Integer> pvfSNewDuration = new PropertyValueFactory<Request, Integer>("newDuration");
		requestId.setCellValueFactory(pvfrequestId);
		examId.setCellValueFactory(pvfexamId);
		lecturerId.setCellValueFactory(pvfLecturer);
		courseId.setCellValueFactory(pvfCourse);
		subject.setCellValueFactory(pvfSSubject);
		oldDuration.setCellValueFactory(pvfSOldDuration);
		newDuration.setCellValueFactory(pvfSNewDuration);
		RequestsTable.setItems(list);
		RequestsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	showTable();				
	}
	@FXML
	public void getviewReasonsbtn(ActionEvent event) {
		ArrayList<Request> selectedId = new ArrayList<>();
		selectedId.addAll(RequestsTable.getSelectionModel().getSelectedItems());
		if(selectedId.isEmpty()) {
			lblNonSelected.setText("No entry was selected!");
		}
		else {
			lblNonSelected.setText("");
			HashMap<String,ArrayList<String>> msg = new HashMap<>();
			ArrayList<String> arr = new ArrayList<>();
			arr.add("HOD");
			msg.put("client", arr);
			ArrayList<String> arr2 = new ArrayList<>();
			arr2.add("inProgress");
			msg.put("status",arr2);
			ArrayList<String> arr1 = new ArrayList<>();
			arr1.add("getAllRequests");
			msg.put("task",arr1);
			ArrayList<String> arr3 = new ArrayList<>();
			arr3.add(""+((Hod) ConnectionServer.user).getDepartment());
			msg.put("department",arr3);
			sendMsgToServer(msg);
			if(ConnectionServer.rs != null) {
				Stage seconderyStage = new Stage();
		        try {
		        	FXMLLoader loader = new FXMLLoader();
					Parent root = loader.load(getClass().getResource("/guiHod/ReasonsRequestScreen.fxml").openStream());
					Scene scene = new Scene(root);
					HODReasonsRequestController hodReasonsRequestController=loader.getController();
					hodReasonsRequestController.viewReason((String)ConnectionServer.rs.get(0).get("reasons"));
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
				System.out.println("view reasons successfuly!");

			}
		}
	}
	
    @FXML
	private void getApproveButton(ActionEvent event) {
		ArrayList<Request> selectedId = new ArrayList<>();
		selectedId.addAll(RequestsTable.getSelectionModel().getSelectedItems());
		if(selectedId.isEmpty()) {
			lblNonSelected.setText("No entry was selected!");
		}
		else {
			lblNonSelected.setText("");
			HashMap<String,ArrayList<String>> msg = new HashMap<>();
			ArrayList<String> arr = new ArrayList<>();
			arr.add("HOD");
			msg.put("client", arr);
			ArrayList<String> arr2 = new ArrayList<>();
			arr2.add(""+selectedId.get(0).getRequestId());
			msg.put("requestId",arr2);
			ArrayList<String> arr3 = new ArrayList<>();
			arr3.add("approved");
			msg.put("status",arr3);
			ArrayList<String> arr1 = new ArrayList<>();
			arr1.add("updateRequest");
			msg.put("task",arr1);
			sendMsgToServer(msg);
			if(ConnectionServer.rs != null) {
				System.out.println("request approved successfuly!");
				simulatePopUp();
				showTable();
			}
		}
	}
    private void simulatePopUp() {
    	ArrayList<Request> selectedId = new ArrayList<>();
		selectedId.addAll(RequestsTable.getSelectionModel().getSelectedItems());
		if(selectedId.isEmpty()) {
			lblNonSelected.setText("No entry was selected!");
		}
		else {
			lblNonSelected.setText("");
			HashMap<String,ArrayList<String>> msg = new HashMap<>();
			ArrayList<String> arr = new ArrayList<>();
			arr.add("HOD");
			msg.put("client", arr);
			ArrayList<String> arr2 = new ArrayList<>();
			arr2.add(""+selectedId.get(0).getLecturerId());
			msg.put("lecturerId",arr2);
			ArrayList<String> arr1 = new ArrayList<>();
			arr1.add("getUser");
			msg.put("task",arr1);
			sendMsgToServer(msg);
			if(ConnectionServer.rs != null) {
				String email = (String)ConnectionServer.rs.get(0).get("email");
				Stage seconderyStage = new Stage();
		        try {
		        	FXMLLoader loader = new FXMLLoader();
					Parent root = loader.load(getClass().getResource("/guiHod/SimulationPopUp.fxml").openStream());
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
				}
				System.out.println("view Email successfuly!");
			}
		}
    }
    @FXML
	private void getDenyButton(ActionEvent event) {
		ArrayList<Request> selectedId = new ArrayList<>();
		selectedId.addAll(RequestsTable.getSelectionModel().getSelectedItems());
		if(selectedId.isEmpty()) {
			lblNonSelected.setText("No entry was selected!");
		}
		else {
			lblNonSelected.setText("");
			HashMap<String,ArrayList<String>> msg = new HashMap<>();
			ArrayList<String> arr = new ArrayList<>();
			arr.add("HOD");
			msg.put("client", arr);
			ArrayList<String> arr2 = new ArrayList<>();
			arr2.add(""+selectedId.get(0).getRequestId());
			msg.put("requestId",arr2);
			ArrayList<String> arr3 = new ArrayList<>();
			arr3.add("denied");
			msg.put("status",arr3);
			ArrayList<String> arr1 = new ArrayList<>();
			arr1.add("updateRequest");
			msg.put("task",arr1);
			sendMsgToServer(msg);
			if(ConnectionServer.rs != null) {
				System.out.println("request denied successfuly!");
				simulatePopUp();
				showTable();
			}
		}
		
	}
}
