package entities;

import javafx.scene.control.TextField;

public class QuestionForExam extends Question{
	private TextField score;
	public QuestionForExam(Question question,String score) {
		super(question.getQuestionID(), question.getDetails(), question.getRightAnswer(),
				question.getQuestionBank(), question.getSubject(), 
				question.getNotes(), question.getAnswers(), question.getCourses());
		this.score = new TextField(score);
	}
	
	public TextField getScore() {
		return score;
	}
	public void setScore(TextField score) {
		this.score = score;
	}
	
}

