package controllersHod;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.ResourceBundle;

import abstractControllers.AbstractController;
import client.ConnectionServer;
import entities.ExamBankView;
import entities.ExamResults;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.lang.Math;
import javafx.scene.chart.XYChart;

public class HODviewStatisticsByStudentController extends AbstractController implements Initializable {
	private ArrayList<ExamResults> erArr ;
	private ArrayList<Integer> gradesArr = new ArrayList<>();
	private HashMap<Integer, String> examId_ExamName = new HashMap<>();
	CategoryAxis xAxis = new CategoryAxis();
    NumberAxis yAxis = new NumberAxis();
	
    @FXML
    private Button ApplyTemp;

    @FXML
    private Text ExamMedianTxt;

    @FXML
    private TextField StudentAvaregeTxt;

    @FXML
    private BarChart<String, Number> StudentBarChart = new BarChart<>(xAxis,yAxis);

    @FXML
    private TextField StudentIDTxt;

    @FXML
    private TextField StudentMedianTxt;
    
    @FXML
    private TextField StudentNameTxt;

    @FXML
    private NumberAxis colGrade;

    @FXML
    private Button exitBtn;

    @FXML
    private Button minimizeBtn;

    @FXML
    private CategoryAxis rowExamID;
    
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
		arr1.add("getStudentDoneExamsGradeByID");
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
    


    private void loadAverage(ArrayList<HashMap<String, Object>> rs) {
        if (rs.isEmpty()) {
        	System.out.println("rs is niill");
            // Handle the case when the result set is empty
            // You can display an error message or perform any other necessary action
            return;
        }
        StudentBarChart.getData().clear();
        setAvg(rs);
        setName(rs.get(0));
        setMedian();
        setBarChart();
        gradesArr.clear();
        examId_ExamName.clear();
    }
    
    private void setBarChart() {
    	StudentBarChart.setTitle("Student's grades");
		XYChart.Series<String,Number> dataSeries = new XYChart.Series<>();
		for(Integer obj : examId_ExamName.keySet()) {
			dataSeries.getData().add(new XYChart.Data<>(examId_ExamName.get(obj), obj));
		}
		StudentBarChart.getData().add(dataSeries);
    }
    
    private void setAvg(ArrayList<HashMap<String, Object>> rs) {
    	double total = 0;
        int count = 0;
        for (HashMap<String, Object> row : rs) {
            // Assuming the grade is stored in a key called "grade"
            if (row.containsKey("grade")) {
                Object gradeObj = row.get("grade");
                if (gradeObj instanceof Integer) {
                	Integer grade = (Integer) gradeObj;
                	examId_ExamName.put(grade,(String)row.get("examName"));
                	gradesArr.add(grade);
                    total += grade;
                    count++;
                }
            }
        }
        if (count > 0) {
            double average = total / count;
            StudentAvaregeTxt.setText(String.format("%.2f", average));
        } else {
            // Handle the case when no grades were found in the result set
            // You can display an error message or perform any other necessary action
        }
    }
    
    private void setName(HashMap<String, Object> rs) {
    	StudentNameTxt.setText(rs.get("firstName") + " " + rs.get("lastName"));
    }
    
    private void setMedian() {
            // Sort the grades array in ascending order
            Collections.sort(gradesArr);

            int length = gradesArr.size();
            if (length % 2 == 0) {
                // If the array length is even, average the two middle elements
                int middleIndex1 = length / 2 - 1;
                int middleIndex2 = length / 2;
                StudentMedianTxt.setText(String.valueOf((gradesArr.get(middleIndex1) + gradesArr.get(middleIndex2)) / 2.0));
              //  return (gradesArr.get(middleIndex1) + gradesArr.get(middleIndex2)) / 2.0;
            } else {
                // If the array length is odd, return the middle element
                int middleIndex = length / 2;
                StudentMedianTxt.setText(String.valueOf(gradesArr.get(middleIndex)));
              //  return gradesArr.get(middleIndex);
            }
            
        }
    


	@Override
	public void initialize(URL location, ResourceBundle resourceBundle) {
		StudentAvaregeTxt.setText("");
		StudentNameTxt.setText("");
		StudentMedianTxt.setText("");
		/*StudentBarChart.setTitle("Student's grades");
		XYChart.Series<String,Number> dataSeries = new XYChart.Series<>();
		dataSeries.getData().add(new XYChart.Data<>("hello", 20));
		StudentBarChart.getData().add(dataSeries);*/
	}

    
	public void start(Stage primaryStage) {
		try {
	        Parent root =  FXMLLoader.load(getClass().getResource("/guiHod/ViewStatisticsByStudent.fxml"));
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
		return StudentIDTxt.getText();
	}


	
	
	
	
	
	
	
	
}

