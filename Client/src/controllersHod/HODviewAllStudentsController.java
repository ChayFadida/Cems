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
     * @param AllStudents the data about the Students
     * @throws Exception in case of error while loading
     */
    public void loadStudents(ArrayList<HashMap<String, Object>> AllStudents) throws Exception {
    	stdArr = new ArrayList<Student>();
    	if(AllStudents == null) {
			System.out.println("No students found.");
		}
		for (int i = 0; i < AllStudents.size(); i++) {
		    stdArr.add(new Student(AllStudents.get(i),null));
		}
	}
    
    /**
     * Loads the student table by sending the relevant query to the server.
     */
    @FXML
	public void showTable() {
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> user = new ArrayList<>();
		user.add("HOD");
		msg.put("client", user);
		ArrayList<String> position = new ArrayList<>();
		position.add("Student");
		msg.put("position",position);
		ArrayList<String> query = new ArrayList<>();
		query.add("getAllbyPosition");
		msg.put("task",query);
		ArrayList<String> department = new ArrayList<>();
		department.add(""+((Hod) ConnectionServer.user).getDepartment());
		msg.put("department",department);
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
	private void initTableView(ArrayList<Student> StudentsArr) {
		ObservableList<Student> Studentlist = FXCollections.observableArrayList(StudentsArr);
		PropertyValueFactory<Student, Integer> pvfId = new PropertyValueFactory<Student, Integer>("id");
		PropertyValueFactory<Student, String> pvfFirstName = new PropertyValueFactory<Student, String>("firstName");
		PropertyValueFactory<Student, String> pvfLastName = new PropertyValueFactory<Student, String>("lastName");
		PropertyValueFactory<Student, String> pvfEmail = new PropertyValueFactory<Student, String>("email");
		id.setCellValueFactory(pvfId);
		firstName.setCellValueFactory(pvfFirstName);
		lastName.setCellValueFactory(pvfLastName);
		email.setCellValueFactory(pvfEmail);
		AllStudentsTable.setItems(Studentlist);
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