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
	public void createExam(String code, String duration, String lecNotes,
			String studNotes, String name, String subject);
}
