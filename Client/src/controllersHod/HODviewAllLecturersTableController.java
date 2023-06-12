package controllersHod;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import abstractControllers.AbstractController;
import client.ConnectionServer;
import entities.Hod;
import entities.Lecturer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class HODviewAllLecturersTableController extends AbstractController implements Initializable{
	
	private ArrayList<Lecturer> lecArr ;
	@FXML
    public TableColumn<Lecturer, Integer> id;
    @FXML
    public TableColumn<Lecturer, String> firstName;
    @FXML
    public TableColumn<Lecturer, String> lastName;
    @FXML
    public TableColumn<Lecturer, String> email;
    @FXML
    private TableView<Lecturer> AllLecturersTable;

    public void loadLecturers(ArrayList<HashMap<String, Object>> rs) throws Exception {
    	lecArr = new ArrayList<Lecturer>();
    	if(rs == null) {
			System.out.println("rs is null");
		}
		for (int i = 0; i < rs.size(); i++) {
		    lecArr.add(new Lecturer(rs.get(i),null,null));
		}
	}
    @FXML
	public void showTable() {
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> arr = new ArrayList<>();
		arr.add("HOD");
		msg.put("client", arr);
		ArrayList<String> arr2 = new ArrayList<>();
		arr2.add("Lecturer");
		msg.put("position",arr2);
		ArrayList<String> arr1 = new ArrayList<>();
		arr1.add("getAllbyPosition");
		msg.put("task",arr1);
		ArrayList<String> arr3 = new ArrayList<>();
		arr3.add(""+((Hod)ConnectionServer.user).getDepartment());
		msg.put("department",arr3);
		sendMsgToServer(msg);
		try {
			this.loadLecturers(ConnectionServer.rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		initTableView(lecArr);
	}
	private void initTableView(ArrayList<Lecturer> arr) {
		ObservableList<Lecturer> list = FXCollections.observableArrayList(arr);
		PropertyValueFactory<Lecturer, Integer> pvfId = new PropertyValueFactory<Lecturer, Integer>("id");
		PropertyValueFactory<Lecturer, String> pvfFirstName = new PropertyValueFactory<Lecturer, String>("firstName");
		PropertyValueFactory<Lecturer, String> pvfLastName = new PropertyValueFactory<Lecturer, String>("lastName");
		PropertyValueFactory<Lecturer, String> pvfEmail = new PropertyValueFactory<Lecturer, String>("email");
		id.setCellValueFactory(pvfId);
		firstName.setCellValueFactory(pvfFirstName);
		lastName.setCellValueFactory(pvfLastName);
		email.setCellValueFactory(pvfEmail);
		AllLecturersTable.setItems(list);
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		showTable();		
	}
}

