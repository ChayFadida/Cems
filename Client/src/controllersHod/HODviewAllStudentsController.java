package controllersHod;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import abstractControllers.AbstractController;
import client.ConnectionServer;
import entities.Hod;
import entities.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controller class for the HOD.
 * In this controller the HOD can view in a table all the Students that belongs to the HOD's department.
 * Extends AbstractController.
 * Implement Initializable.
 */
public class HODviewAllStudentsController extends AbstractController implements Initializable{
	private ArrayList<Student> stdArr ;

	@FXML
    private TableView<Student> AllStudentsTable;
    @FXML
    public TableColumn<Student, Integer> id;
    @FXML
    public TableColumn<Student, String> firstName;
    @FXML
    public TableColumn<Student, String> lastName;
    @FXML
    public TableColumn<Student, String> email;

    /**
     * Loads the ArrayList of students that belongs to the HOD department.
     * @param rs the data about the Students
     * @throws Exception in case of error while loading
     */
    public void loadStudents(ArrayList<HashMap<String, Object>> rs) throws Exception {
    	stdArr = new ArrayList<Student>();
    	if(rs == null) {
			System.out.println("rs is null");
		}
		for (int i = 0; i < rs.size(); i++) {
		    stdArr.add(new Student(rs.get(i),null));
		}
	}
    
    /**
     * Loads the student table by sending the relevant query to the server.
     */
    @FXML
	public void showTable() {
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> arr = new ArrayList<>();
		arr.add("HOD");
		msg.put("client", arr);
		ArrayList<String> arr2 = new ArrayList<>();
		arr2.add("Student");
		msg.put("position",arr2);
		ArrayList<String> arr1 = new ArrayList<>();
		arr1.add("getAllbyPosition");
		msg.put("task",arr1);
		ArrayList<String> arr3 = new ArrayList<>();
		arr3.add(""+((Hod) ConnectionServer.user).getDepartment());
		msg.put("department",arr3);
		sendMsgToServer(msg);
		try {
			this.loadStudents(ConnectionServer.rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		initTableView(stdArr);
	}
    /**
     * Initialize the table with the wanted data.
     * @param arr of students from the result set
     */
	private void initTableView(ArrayList<Student> arr) {
		ObservableList<Student> list = FXCollections.observableArrayList(arr);
		PropertyValueFactory<Student, Integer> pvfId = new PropertyValueFactory<Student, Integer>("id");
		PropertyValueFactory<Student, String> pvfFirstName = new PropertyValueFactory<Student, String>("firstName");
		PropertyValueFactory<Student, String> pvfLastName = new PropertyValueFactory<Student, String>("lastName");
		PropertyValueFactory<Student, String> pvfEmail = new PropertyValueFactory<Student, String>("email");
		id.setCellValueFactory(pvfId);
		firstName.setCellValueFactory(pvfFirstName);
		lastName.setCellValueFactory(pvfLastName);
		email.setCellValueFactory(pvfEmail);
		AllStudentsTable.setItems(list);
	}
	
	/**
	 * initialize the controller.
	 * @param location
	 * @param resources	  
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		showTable();		
	}

}