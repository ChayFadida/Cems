package controllersHod;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.ResourceBundle;

import abstractControllers.AbstractController;
import client.ConnectionServer;
import entities.ExamResults;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class HODviewStatisticsByCourseController extends AbstractController implements Initializable{
	private ArrayList<ExamResults> crArr ;
	private ArrayList<Integer> gradesArr = new ArrayList<>();
	private HashMap<Integer, String> examId_ExamName = new HashMap<>();
	private HashMap<Double, String> examAvg_ExamName = new HashMap<>();
	CategoryAxis xAxis = new CategoryAxis();
    NumberAxis yAxis = new NumberAxis();
	

    @FXML
    private Button ApplyTemp;

    @FXML
    private TextField CourseAvaregeTxt;

    @FXML
    private BarChart<String, Number> CourseBarChart = new BarChart<>(xAxis,yAxis);

    @FXML
    private TextField CourseMedianTxt;

    @FXML
    private TextField CourseNameTxt;

    @FXML
    private TextField CourseNumberTxt;

    @FXML
    private Text ExamMedianTxt;

    @FXML
    private CategoryAxis ExamNameRow;

    @FXML
    private Button exitBtn;

    @FXML
    private NumberAxis gradeCol;

    @FXML
    private Button minimizeBtn;

    @FXML
    void Close(ActionEvent event) {
        Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    void Minimize(ActionEvent event) {
    	Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void showStats(ActionEvent event) {
       	String StudentId = getid();
    		HashMap<String,ArrayList<String>> msg = new HashMap<>();
    		ArrayList<String> arr = new ArrayList<>();
    		arr.add("HOD");
    		msg.put("client", arr);
    		ArrayList<String> arr1 = new ArrayList<>();
    		arr1.add("getInfoForCourseStats");
    		msg.put("task",arr1);
    		ArrayList<String> arr2 = new ArrayList<>();
    		arr2.add(StudentId);
    		msg.put("param", arr2);
    		sendMsgToServer(msg);
    		try {
    			this.loadAverage(ConnectionServer.rs);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    }
    
    private void setName(HashMap<String, Object> rs) {
    	CourseNameTxt.setText((String) rs.get("courseName"));
    }

    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		CourseNameTxt.setText("");
		CourseAvaregeTxt.setText("");
		CourseMedianTxt.setText("");
		
		
	}
    
    private void loadAverage(ArrayList<HashMap<String, Object>> rs) {
        if (rs.isEmpty()) {
        	System.out.println("rs is null");
            return;
        }
        CourseBarChart.getData().clear();
        setAvg(rs);
        setName(rs.get(0));
        setMedian();
        setBarChart();
        gradesArr.clear();
        examId_ExamName.clear();
        examAvg_ExamName.clear();
		
	}
    
    private void setAvg(ArrayList<HashMap<String, Object>> rs) {
    	double total = 0;
        int count = 0;
        for (HashMap<String, Object> row : rs) {
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
            CourseAvaregeTxt.setText(String.format("%.2f", average));
        } else {
            	System.out.println("No grades");
        }
    }
    
    private void setBarChart() {
    	CourseBarChart.setTitle("Course's grades");
		XYChart.Series<String,Number> dataSeries = new XYChart.Series<>();
		for(Double obj : examAvg_ExamName.keySet()) {
			dataSeries.getData().add(new XYChart.Data<>(examAvg_ExamName.get(obj), obj));
		}
		CourseBarChart.getData().add(dataSeries);
    }
    
    private void setMedian() {
        Collections.sort(gradesArr);
        int length = gradesArr.size();
        if (length % 2 == 0) {
            int middleIndex1 = length / 2 - 1;
            int middleIndex2 = length / 2;
            CourseMedianTxt.setText(String.valueOf((gradesArr.get(middleIndex1) + gradesArr.get(middleIndex2)) / 2.0));
        } else {
            int middleIndex = length / 2;
            CourseMedianTxt.setText(String.valueOf(gradesArr.get(middleIndex)));
        }
        
    }

	public void start(Stage primaryStage) {
		try {
	        Parent root =  FXMLLoader.load(getClass().getResource("/guiHod/ViewStatisticsByCourse.fxml"));
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
	private String getid() {
		return CourseNumberTxt.getText();
	}
	
	
}
