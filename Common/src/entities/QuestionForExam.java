package entities;

import java.io.Serializable;

import javafx.scene.control.TextField;

/**
 * Represents a QuestionForExam entity.
 */
@SuppressWarnings("serial")
public class QuestionForExam extends Question implements Serializable{
	private String strScore;
    private transient TextField score ;
    @SuppressWarnings("unused")
	private transient TextField textField;

/**
     * Constructs a QuestionForExam object based on a Question object and a score.
     *
     * @param question the original question
     * @param score    the score for the question
     */
	public QuestionForExam(Question question,String score) {
		super(question.getQuestionID(), question.getDetails(), question.getRightAnswer(),
				question.getQuestionBank(), question.getSubject(), 
				question.getAnswers(), question.getNotes(),question.getCourses());
		this.strScore = score;
	}
    /**
     * Returns the score for the question.
     *
     * @return the score field
     */
	public TextField getScore() {
		return score;
	}
    /**
     * Sets the score for the question.
     *
     * @param score the score field to set
     */
	public void setScore(TextField score) {
		this.score = score;
	}
	
	public String getStrScore() {
		return strScore;
	}
	public void setStrScore(String strScore) {
		this.strScore = strScore;
	}
	
}

