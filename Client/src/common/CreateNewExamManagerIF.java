package common;

import java.util.ArrayList;
import java.util.HashMap;

import entities.QuestionForExam;
import javafx.collections.ObservableList;

public interface CreateNewExamManagerIF {
	public String getSubject();
	public String getCode();
	public String getDuration();
	public String getLecNotes();
	public String getStudNotes();
	public String getName();
	public void addAllSelectedToArray(ObservableList<QuestionForExam> selectedItems);
	public ArrayList<Object> getSelected();
	public void initializeSelected();
	public ArrayList<HashMap<String, Object>> insertExamToDB(String code, String duration, String lecNotes, String studNotes, String name, String subject);
	public ArrayList<HashMap<String, Object>> insertQuestionsToDB(Integer examId);
	public ArrayList<HashMap<String, Object>> updateExamBank(HashMap<String, Object> bank, Integer examId);
	public ArrayList<HashMap<String, Object>> getExamBankQuery();


}
