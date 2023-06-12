package entities;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class QuestionForExam extends Question{
	private TextField score;
	private CheckBox selection;
	public QuestionForExam(Question question,String score) {
		super(question.getQuestionID(), question.getDetails(), question.getRightAnswer(),
				question.getQuestionBank(), question.getSubject(), question.getComposer(),
				question.getNotes(), question.getAnswers());
		this.score = new TextField(score);
		selection= new CheckBox();
		selection.setIndeterminate(false);
	}
	
	public TextField getScore() {
		return score;
	}
	public void setScore(TextField score) {
		this.score = score;
	}

	public CheckBox getSelection() {
		return selection;
	}

	public void setSelection(Boolean selection) {
		this.selection.setIndeterminate(selection);
	}
	
}

