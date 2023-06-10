package controllersHod;

import java.util.ArrayList;
import java.util.HashMap;

import abstractControllers.AbstractController;
import client.ConnectionServer;
import entities.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class HODviewAllStudentsController extends AbstractController {
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

    public void loadStudents(ArrayList<HashMap<String, Object>> rs) throws Exception {
    	stdArr = new ArrayList<Student>();
    	if(rs == null) {
			System.out.println("rs is null");
		}
		for (int i = 0; i < rs.size(); i++) {
//		    HashMap<String, Object> element = rs.get(i);
//		    HashMap <String,Object> usrInfo = new HashMap<String,Object>();
		    String department = null;
//		    usrInfo.put("id",(int)element.get("id"));
//		    usrInfo.put("firstName",(String)element.get("firstName"));
//		    usrInfo.put("lastName",(String)element.get("lastName"));
//		    usrInfo.put("email",(String)element.get("email"));
//		    usrInfo.put("position",(String)element.get("position"));
//		    usrInfo.put("pass",(String)element.get("pass"));
//		    usrInfo.put("username",(String)element.get("username"));
//		    usrInfo.put("isLogged",(boolean)element.get("isLogged"));
		    stdArr.add(new Student(rs.get(i),department));
		}
	}
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
		sendMsgToServer(msg);
		try {
			this.loadStudents(ConnectionServer.rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		initTableView(stdArr);
	}
	private void initTableView(ArrayList<Student> arr) {
		ObservableList<Student> list = FXCollections.observableArrayList(arr);
		PropertyValueFactory<Student, Integer> pvfId = new PropertyValueFactory<Student, Integer>("id");
		PropertyValueFactory<Student, String> pvfFirstName = new PropertyValueFactory<Student, String>("firstName");
		PropertyValueFactory<Student, String> pvfLastName = new PropertyValueFactory<Student, String>("lastName");
		PropertyValueFactory<Student, String> pvfEmail = new PropertyValueFactory<Student, String>("email");
		firstName.setCellValueFactory(pvfFirstName);
		id.setCellValueFactory(pvfId);
		lastName.setCellValueFactory(pvfLastName);
		email.setCellValueFactory(pvfEmail);
		AllStudentsTable.setItems(list);
	}
}
