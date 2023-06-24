package common;

import java.util.ArrayList;
import java.util.HashMap;

import entities.QuestionForExam;
import javafx.collections.ObservableList;

public interface CreateNewExamManagerIF {
	public void addAllSelectedToArray(ObservableList<QuestionForExam> selectedItems);
	public ArrayList<Object> getSelected();
	public void initializeSelected();
	public ArrayList<HashMap<String, Object>> insertExamToDB(String code, String duration, String lecNotes, String studNotes, String name, String subject);
	public ArrayList<HashMap<String, Object>> insertQuestionsToDB(Integer examId);
	public ArrayList<HashMap<String, Object>> updateExamBank(HashMap<String, Object> bank, Integer examId);
	public ArrayList<HashMap<String, Object>> getExamBankQuery();
	public ObservableList<QuestionForExam> getSelectedItemsFromTable();
	public void setTxtSubject(String subject);
	public String getTxtSubject();
	public void setTxtName(String name);
	public String getTxtName();
	public void setStudNotesText(String studNotes);
	public String getStudNotesText();
	public void setLecNotesTxt(String lecNotes);
	public String getLecNotesTxt();
	public void setCodeTxt(String code);
	public String getCodeTxt();
	public void setDurationTxt(String duration);
	public String getDurationTxt();
	public void loadQuestionsTable();
}
