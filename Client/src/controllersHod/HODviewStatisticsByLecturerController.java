package controllersHod;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.text.DecimalFormat;
import abstractControllers.AbstractController;
import client.ConnectionServer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Controller class for the HOD.
 * In this controller the HOD can view statistic report about specific Lecturer by insert the course id.
 * Extends AbstractController.
 * Implements Initializable.
 */
public class HODviewStatisticsByLecturerController extends AbstractController implements Initializable {
	private ArrayList<Integer> gradesArr = new ArrayList<>();
	private HashMap<Integer, String> examId_ExamName = new HashMap<>();
	private HashMap<Double, String> examAvg_ExamName = new HashMap<>();
	
	CategoryAxis xAxis = new CategoryAxis();
    NumberAxis yAxis = new NumberAxis();

    @FXML
    private Button Apply;

    @FXML
    private TextField LecturerAvaregeTxt;

    @FXML
    private BarChart<String, Number> LecturerBarChart = new BarChart<>(xAxis,yAxis);

    @FXML
    private TextField LecturerIdTxt;

    @FXML
    private TextField LecturerMedianTxtbox;

    @FXML
    private TextField LecturerNameTxt;

    @FXML
    private NumberAxis clmGrade;

    @FXML
    private Button exitBtn;

    @FXML
    private Button minimizeBtn;

    @FXML
    private CategoryAxis rowExamName;
    
    @FXML
    private Label notFoundLbl;

    /**
     * Close current window.
     * @param event Action event that triggered when pressing close button.
     */
    @FXML
    void Close(ActionEvent event) {
        Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
    
    /**
     * Minimize the current window.
     * @param event Action event that triggered when pressing minimize button.
     */
    @FXML
    void Minimize(ActionEvent event) {
    	Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    /**
     * Sends message to the server with the relevant query to activate.
     * @param event JavaFX event handler that activates when pressing apply button.
     */
    @FXML
    void showStats(ActionEvent event) {
		LecturerNameTxt.setText("");
		LecturerAvaregeTxt.setText("");
		LecturerMedianTxtbox.setText("");
       	String StudentId = getid();
    	HashMap<String,ArrayList<String>> msg = new HashMap<>();
    	ArrayList<String> arr = new ArrayList<>();
    	arr.add("HOD");
    	msg.put("client", arr);
    	ArrayList<String> arr1 = new ArrayList<>();
    	arr1.add("getInfoForLecturerStats");
    	msg.put("task",arr1);
    	ArrayList<String> arr2 = new ArrayList<>();
    	arr2.add(StudentId);
    	msg.put("param", arr2);
    	sendMsgToServer(msg);
    	try {
    	    ArrayList<HashMap<String, Object>> rs = ConnectionServer.rs;
    	    if (rs.isEmpty()) {
    	        notFoundLbl.setText("Lecturer has not found or there is no exams to show");
    	        LecturerBarChart.getData().clear();
    	    } else {
    	    	notFoundLbl.setText("");
    	        this.loadStats(ConnectionServer.rs);
    	    }
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    
	/**
	 * Function that loads the statistic and insert for the text field the correct data.
	 * @param StatisticResultSet result set of data from the DB. 
	 */
    private void loadStats(ArrayList<HashMap<String, Object>> StatisticResultSet) {
        if (StatisticResultSet.isEmpty()) {
        	System.out.println("Could not get statistic data.");
            return;
        }
        LecturerBarChart.getData().clear();
        setAvg(StatisticResultSet);
        setName(StatisticResultSet.get(0));
        setMedian();
        setBarChart();
        gradesArr.clear();
        examId_ExamName.clear();
        examAvg_ExamName.clear();
		
	}
    
    /**
     * Function that calculate the average of the grades.
     * @param rs result set of data from the DB.
     */
    private void setAvg(ArrayList<HashMap<String, Object>> StatisticResultSet) {
    	double total = 0;
        int count = 0;
        for (HashMap<String, Object> row : StatisticResultSet) {
            if (row.containsKey("grade")) {
                Object gradeObj = row.get("grade");
                Object avgObj = row.get("avgGrade");
                if (gradeObj instanceof Integer) {
                	Integer grade = (Integer) gradeObj;
                	Double avg = ((BigDecimal) avgObj).doubleValue();
                	examId_ExamName.put(grade,(String)row.get("examName"));
                	examAvg_ExamName.put(avg,(String)row.get("examName"));
                	gradesArr.add(grade);
                    total += grade;
                    count++;
                }
            }
        }
        if (count > 0) {
            double average = total / count;
            LecturerAvaregeTxt.setText(String.format("%.1f", average));
        } else {
        	LecturerAvaregeTxt.setText("No grades!");
            System.out.println("No grades!");
        }
    }
    
    /**
     * Sets the bar chart with the data.
     */
    private void setBarChart() {
    	LecturerBarChart.setTitle("Lecturer's grades");
    	LecturerBarChart.getData().clear();
		XYChart.Series<String,Number> dataSeries = new XYChart.Series<>();
		for(Double obj : examAvg_ExamName.keySet()) {
			dataSeries.getData().add(new XYChart.Data<>(examAvg_ExamName.get(obj), obj));
		}
		LecturerBarChart.getData().add(dataSeries);
    }
    
    /**
     * Function that calculate the median.
     */
    private void setMedian() {
        Collections.sort(gradesArr);

        int length = gradesArr.size();
        if (length % 2 == 0) {
            int middleIndex1 = length / 2 - 1;
            int middleIndex2 = length / 2;
            double median = (gradesArr.get(middleIndex1) + gradesArr.get(middleIndex2)) / 2.0;

            // Format median to one digit after the decimal point
            DecimalFormat decimalFormat = new DecimalFormat("#.0");
            String formattedMedian = decimalFormat.format(median);
            LecturerMedianTxtbox.setText(formattedMedian);
        } else {
            int middleIndex = length / 2;
            LecturerMedianTxtbox.setText(String.valueOf(gradesArr.get(middleIndex)));
        }
    }

	/**
	 * gets the course id from user input.
	 * @return course id.
	 */
	private String getid() {
		return LecturerIdTxt.getText();
	}
	
    /**
     * Sets for the LecturerNameTxt the lecturer first and last name.
     * @param StatisticResultSet result set of information from the DB. 
     */
    private void setName(HashMap<String, Object> StatisticResultSet) {
    	LecturerNameTxt.setText(StatisticResultSet.get("firstName") + " " + StatisticResultSet.get("lastName"));
    }

    /**
     * Initialize Text Fields with empty strings.
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		LecturerNameTxt.setText("");
		LecturerAvaregeTxt.setText("");
		LecturerMedianTxtbox.setText("");
	}
	
    /**
     * Loads the current window.
     * @param primaryStage
     */
	public void start(Stage primaryStage) {
		try {
	        Parent root =  FXMLLoader.load(getClass().getResource("/guiHod/ViewStatisticsByLecturer.fxml"));
	        Scene scene = new Scene(root);
	        primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.getIcons().add(new Image("/Images/CemsIcon32-Color.png"));
	        // Set the scene to the primary stage
	        primaryStage.setScene(scene);
	        primaryStage.show();
	        super.setPrimaryStage(primaryStage);
	        PressHandler<MouseEvent> press = new PressHandler<>();
	        DragHandler<MouseEvent> drag = new DragHandler<>();
	        root.setOnMousePressed(press);
	        root.setOnMouseDragged(drag);
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
		
	}


}