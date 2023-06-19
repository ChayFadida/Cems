package entities;

/**
 * Represents a QuestionForVirtualExam entity.
 */
@SuppressWarnings("serial")
public class QuestionForVirtualExam extends Question{
	private Integer score;
	private Integer selection=0;
	/**
	 * Constructs a QuestionForVirtualExam object with the specified parameters.
	 *
	 * @param questionID    the ID of the question
	 * @param details       the details of the question
	 * @param rightAnswer   the correct answer to the question
	 * @param questionBank  the ID of the question bank
	 * @param subject       the subject of the question
	 * @param answers       the possible answers to the question
	 * @param notes         the additional notes for the question
	 * @param courses       the courses related to the question
	 * @param score         the score for the question
	 */
	public QuestionForVirtualExam(Integer questionID, String details, String rightAnswer, Integer questionBank,
			String subject, String answers, String notes, String courses,Integer score) {
		super(questionID, details, rightAnswer, questionBank, subject, answers, notes, courses);
		this.score=score;
	}
	/**
	 * Returns the score for the question.
	 *
	 * @return the score
	 */
	public Integer getScore() {
		return score;
	}
	/**
	 * Sets the score for the question.
	 *
	 * @param score the score to set
	 */
	public void setScore(Integer score) {
		this.score = score;
	}
	/**
	 * Returns the selection for the question.
	 *
	 * @return the selection
	 */
	public Integer getSelection() {
		return selection;
	}
	/**
	 * Sets the selection for the question.
	 *
	 * @param selection the selection to set
	 */
	public void setSelection(Integer selection) {
		this.selection = selection;
	}
	
}
