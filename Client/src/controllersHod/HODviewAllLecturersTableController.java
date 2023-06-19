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
/**
 * Controller class for the HOD.
 * In this controller the HOD can view in a table all the lecturers that belongs the HOD's department.
 * Extends AbstractController.
 * Implement Initializable.
 */
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
    
    /**
     * Loads ArrayList of lecturers according to the result set. 
     * @param rs the data about the lecturers from the query.
     * @throws Exception in case there is an error while loads.
     */
    public void loadLecturers(ArrayList<HashMap<String, Object>> AllLecurers) throws Exception {
    	lecArr = new ArrayList<Lecturer>();
    	if(AllLecurers == null) {
			System.out.println("No lecturers found");
		}
		for (int i = 0; i < AllLecurers.size(); i++) {
		    lecArr.add(new Lecturer(AllLecurers.get(i),null,null));
		}
	}
    
    /**
     * Show the table of all the lecturers from the HOD department
     */
    @FXML
	public void showTable() {
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> user = new ArrayList<>();
		user.add("HOD");
		msg.put("client", user);
		ArrayList<String> position = new ArrayList<>();
		position.add("Lecturer");
		msg.put("position",position);
		ArrayList<String> query = new ArrayList<>();
		query.add("getAllbyPosition");
		msg.put("task",query);
		ArrayList<String> department = new ArrayList<>();
		department.add(""+((Hod)ConnectionServer.user).getDepartment());
		msg.put("department",department);
		sendMsgToServer(msg);
		try {
			this.loadLecturers(ConnectionServer.rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		initTableView(lecArr);
	}
    
    /**
     * Initialize the table and sets the columns with the wanted data
     * @param arr
     */
	private void initTableView(ArrayList<Lecturer> LecturerArr) {
		ObservableList<Lecturer> Lecturerlist = FXCollections.observableArrayList(LecturerArr);
		PropertyValueFactory<Lecturer, Integer> pvfId = new PropertyValueFactory<Lecturer, Integer>("id");
		PropertyValueFactory<Lecturer, String> pvfFirstName = new PropertyValueFactory<Lecturer, String>("firstName");
		PropertyValueFactory<Lecturer, String> pvfLastName = new PropertyValueFactory<Lecturer, String>("lastName");
		PropertyValueFactory<Lecturer, String> pvfEmail = new PropertyValueFactory<Lecturer, String>("email");
		id.setCellValueFactory(pvfId);
		firstName.setCellValueFactory(pvfFirstName);
		lastName.setCellValueFactory(pvfLastName);
		email.setCellValueFactory(pvfEmail);
		AllLecturersTable.setItems(Lecturerlist);
	}
	
	/**
	 * Initialize the controller
	 * @param location
	 * @param resources 
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		showTable();		
	}
}

