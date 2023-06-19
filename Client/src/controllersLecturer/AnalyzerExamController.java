package controllersLecturer;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.ResourceBundle;
import entities.Exam;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AnalyzerExamController extends AbstractController{
	
	private ArrayList<Integer> gradesArr = new ArrayList<>();
	private HashMap<Integer, String> examGrade_StudentId = new HashMap<>();
	CategoryAxis xAxis = new CategoryAxis();
    NumberAxis yAxis = new NumberAxis();
	
	private Exam exam;
	
	
    public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}
	
    @FXML
    private CategoryAxis GradeRow;

    @FXML
    private NumberAxis StudentClm;

    @FXML
    private BarChart<String,Number> ExamBarChart = new BarChart<>(xAxis,yAxis);

    @FXML
    private TextField ExamAvaregeTxt;

    @FXML
    private TextField ExamMedianTxt;

    @FXML
    private TextField ExamNumberTxt;

    @FXML
    private ImageView backButton;
    
    @FXML
    private Button CloseBtn;

    @FXML
    private Button MinimizeBtn;
    
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
    
    private void setMedian() {
        Collections.sort(gradesArr);

        int length = gradesArr.size();
        if (length % 2 == 0) {
            int middleIndex1 = length / 2 - 1;
            int middleIndex2 = length / 2;
            double median = (gradesArr.get(middleIndex1) + gradesArr.get(middleIndex2)) / 2.0;

            DecimalFormat decimalFormat = new DecimalFormat("#.0");
            String formattedMedian = decimalFormat.format(median);
            ExamMedianTxt.setText(formattedMedian);
        } else {
            int middleIndex = length / 2;
            ExamMedianTxt.setText(String.valueOf(gradesArr.get(middleIndex)));
        }
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
                	examGrade_StudentId.put(grade, Integer.toString((Integer) row.get("studentId")));
                	gradesArr.add(grade);
                    total += grade;
                    count++;
                }
            }
        }
        if (count > 0) {
            double average = total / count;
            ExamAvaregeTxt.setText(String.format("%.1f", average));
        } else {
            System.out.println("no grades");
        }
    }
    
    private void loadAverage(ArrayList<HashMap<String, Object>> rs) {
        if (rs.isEmpty()) {
        	System.out.println("rs is null");
            return;
        }
        ExamBarChart.getData().clear();
        setAvg(rs);
        setMedian();
        setBarChart();
        gradesArr.clear();
        examGrade_StudentId.clear();
    }
    
    private void setBarChart() {
    	ExamBarChart.setTitle("Student's grades");
		XYChart.Series<String,Number> dataSeries = new XYChart.Series<>();
		for(Integer obj : examGrade_StudentId.keySet()) {
			dataSeries.getData().add(new XYChart.Data<>(examGrade_StudentId.get(obj), obj));
		}
		ExamBarChart.getData().add(dataSeries);
    }
    
    void showStats() {
    	String ExamId = ""+exam.getExamId();
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> arr = new ArrayList<>();
		arr.add("Lecturer");
		msg.put("client", arr);
		ArrayList<String> arr1 = new ArrayList<>();
		arr1.add("getInfoForExamStats");
		msg.put("task",arr1);
		ArrayList<String> arr2 = new ArrayList<>();
		arr2.add(ExamId);
		msg.put("param", arr2);
		sendMsgToServer(msg);
		try {
			this.loadAverage(ConnectionServer.rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
   

	
	void LoadExamStats(Exam e) {
		ExamNumberTxt.setText(e.getExamId() + "");
		ExamNumberTxt.setEditable(false);
		ExamAvaregeTxt.setText("");
		ExamMedianTxt.setText("");
		showStats();
    }
	
}