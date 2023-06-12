package decorators;

import java.util.HashMap;

import interfaces.QuestionIF;
import javafx.scene.control.TextField;

public abstract class QuestionDecorator implements QuestionIF{
	public QuestionIF questionDecorator;

	public QuestionDecorator(QuestionIF questionDecorator) {
		this.questionDecorator=questionDecorator;
	}
	
	public String getNotes() {
		return questionDecorator.getNotes();
	}
	public void setNotes(String notes) {
		questionDecorator.setNotes(notes);
	}
	public HashMap<String, String> getAnswersHM() {
		return questionDecorator.getAnswersHM();
	}
	public void setAnswersHM(HashMap<String, String> answersHM) {
		questionDecorator.setAnswersHM(answersHM);
	}
	public void setAnswers(String answers) {
		questionDecorator.setAnswers(answers);
	}
	public String getAnswers() {
		return questionDecorator.getAnswers();
	}
	public Integer getQuestionID() {
		return questionDecorator.getQuestionID();
	}
	public void setQuestionID(Integer questionID) {
		questionDecorator.setQuestionID(questionID);
	}
	public String getDetails() {
		return questionDecorator.getDetails();
	}
	public void setDetails(String details) {
		questionDecorator.setDetails(details);
	}
	public String getRightAnswer() {
		return questionDecorator.getRightAnswer();
	}
	public Integer getQuestionBank() {
		return questionDecorator.getQuestionBank();
	}
	public String getSubject() {
		return questionDecorator.getSubject();
	}
	public void setSubject(String subject) {
		questionDecorator.setSubject(subject);
	}
	public String getComposer() {
		return questionDecorator.getComposer();
	}
	public void setCourses(String composer) {
		questionDecorator.setCourses(composer);
	}
	public void setRightAnswer(String rightAnswer) {
		questionDecorator.setRightAnswer(rightAnswer);
	}
	public void setQuestionBank(Integer questionBank) {
		questionDecorator.setQuestionBank(questionBank);
	}
	
}
