package entities;

public class QuestionForVirtualExam extends Question{
	private Integer score;
	private Integer selection=1;
	public QuestionForVirtualExam(Integer questionID, String details, String rightAnswer, Integer questionBank,
			String subject, String answers, String notes, String courses,Integer score) {
		super(questionID, details, rightAnswer, questionBank, subject, answers, notes, courses);
		this.score=score;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getSelection() {
		return selection;
	}

	public void setSelection(Integer selection) {
		this.selection = selection;
	}
	
}
