package controllersHod;

import java.net.URL;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.ResourceBundle;

import abstractControllers.AbstractController;
import client.ConnectionServer;
import entities.Student;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class HODviewAllStudentsController extends AbstractController implements Initializable{
	private ArrayList<User> stdArr ;

	@FXML
    private TableView<User> AllStudentsTable;
    @FXML
    public TableColumn<User, Integer> id;
    @FXML
    public TableColumn<User, String> firstName;
    @FXML
    public TableColumn<User, String> lastName;
    @FXML
    public TableColumn<User, String> email;

    public void loadStudents(ArrayList<HashMap<String, Object>> rs) throws Exception {
    	stdArr = new ArrayList<User>();
    	if(rs == null) {
			System.out.println("rs is null");
		}
		for (int i = 0; i < rs.size(); i++) {
//		    HashMap<String, Object> element = rs.get(i);
//		    HashMap <String,Object> usrInfo = new HashMap<String,Object>();
//		    String department = null;
//		    usrInfo.put("id",(int)element.get("id"));
//		    usrInfo.put("firstName",(String)element.get("firstName"));
//		    usrInfo.put("lastName",(String)element.get("lastName"));
//		    usrInfo.put("email",(String)element.get("email"));
//		    usrInfo.put("position",(String)element.get("position"));
//		    usrInfo.put("pass",(String)element.get("pass"));
//		    usrInfo.put("username",(String)element.get("username"));
//		    usrInfo.put("isLogged",(boolean)element.get("isLogged"));
		    stdArr.add(new User(rs.get(i)));
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
	private void initTableView(ArrayList<User> arr) {
		ObservableList<User> list = FXCollections.observableArrayList(arr);
		PropertyValueFactory<User, Integer> pvfId = new PropertyValueFactory<User, Integer>("id");
		PropertyValueFactory<User, String> pvfFirstName = new PropertyValueFactory<User, String>("firstName");
		PropertyValueFactory<User, String> pvfLastName = new PropertyValueFactory<User, String>("lastName");
		PropertyValueFactory<User, String> pvfEmail = new PropertyValueFactory<User, String>("email");
		id.setCellValueFactory(pvfId);
		firstName.setCellValueFactory(pvfFirstName);
		lastName.setCellValueFactory(pvfLastName);
		email.setCellValueFactory(pvfEmail);
		AllStudentsTable.setItems(list);
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		showTable();		
	}
}
