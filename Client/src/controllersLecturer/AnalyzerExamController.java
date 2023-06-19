package controllersLecturer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import entities.Exam;
import java.text.DecimalFormat;
import abstractControllers.AbstractController;
import client.ConnectionServer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
/**
 * Controller class for the lecturer.
 * By choosing an exam the lecturer can get the statistic report about the chosen exam.
 * Extends AbstractController
 *
 */
public class AnalyzerExamController extends AbstractController{
	
	private ArrayList<Integer> gradesArr = new ArrayList<>();
	private HashMap<Integer, String> examGrade_StudentId = new HashMap<>();
	CategoryAxis xAxis = new CategoryAxis();
    NumberAxis yAxis = new NumberAxis();
	
	private Exam exam;
	
	/**
	 * get the chosen exam
	 * @return current exam
	 */
    public Exam getExam() {
		return exam;
	}

    /**
     * sets the exam
     * @param exam to set
     */
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
    
    /**
     * By activate this event the program will close current window.
     * @param event Action event 
     */
    @FXML
    void Close(ActionEvent event) {
        Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
    
    /**
     * By activate this event the program will minimze the current window.
     * @param event Action event.
     */
    @FXML
    void Minimize(ActionEvent event) {
    	Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    
    /**
     * calculate and sets the median in the ExamMedianTxt text box.
     */
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
    
    /**
     * Calculate and sets the average in the ExamAvaregeTxt
     * @param GradesResultSet result set from the DB.
     */
    private void setAvg(ArrayList<HashMap<String, Object>> GradesResultSet) {
    	double total = 0;
        int count = 0;
        for (HashMap<String, Object> row : GradesResultSet) {
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
    
    /**
     * Loads and activate all other methods.
     * @param GradesResultSet result set from the DB.
     */
    private void loadStats(ArrayList<HashMap<String, Object>> GradesResultSet) {
        if (GradesResultSet.isEmpty()) {
        	System.out.println("could not load grades.");
            return;
        }
        ExamBarChart.getData().clear();
        setAvg(GradesResultSet);
        setMedian();
        setBarChart();
        gradesArr.clear();
        examGrade_StudentId.clear();
    }
    
    /**
     * Sets the bar chart with the relevant data.
     */
    private void setBarChart() {
    	ExamBarChart.setTitle("Student's grades");
		XYChart.Series<String,Number> dataSeries = new XYChart.Series<>();
		for(Integer obj : examGrade_StudentId.keySet()) {
			dataSeries.getData().add(new XYChart.Data<>(examGrade_StudentId.get(obj), obj));
		}
		ExamBarChart.getData().add(dataSeries);
    }
    
    /**
     * Retrieves the statistics for the exam
     */
    void showStats() {
    	String ExamId = ""+exam.getExamId();
		HashMap<String,ArrayList<String>> msg = new HashMap<>();
		ArrayList<String> user = new ArrayList<>();
		user.add("Lecturer");
		msg.put("client", user);
		ArrayList<String> query = new ArrayList<>();
		query.add("getInfoForExamStats");
		msg.put("task",query);
		ArrayList<String> parameter = new ArrayList<>();
		parameter.add(ExamId);
		msg.put("param", parameter);
		sendMsgToServer(msg);
		try {
			this.loadStats(ConnectionServer.rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Loads the statistics of the exam 
     * @param e the exam to load.
     */
	void LoadExamStats(Exam e) {
		ExamNumberTxt.setText(e.getExamId() + "");
		ExamNumberTxt.setEditable(false);
		ExamAvaregeTxt.setText("");
		ExamMedianTxt.setText("");
		showStats();
    }
	
}