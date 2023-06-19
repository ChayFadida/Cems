package entities;

import java.io.Serializable;

import javafx.scene.control.TextField;

@SuppressWarnings("serial")
public class QuestionForExam extends Question implements Serializable{
    private transient TextField score;
    @SuppressWarnings("unused")
	private transient TextField textField;

	public QuestionForExam(Question question,String score) {
		super(question.getQuestionID(), question.getDetails(), question.getRightAnswer(),
				question.getQuestionBank(), question.getSubject(), 
				question.getAnswers(), question.getNotes(),question.getCourses());
		this.score = new TextField(score);
	}
	
	public TextField getScore() {
		return score;
	}
	public void setScore(TextField score) {
		this.score = score;
	}
	

}

