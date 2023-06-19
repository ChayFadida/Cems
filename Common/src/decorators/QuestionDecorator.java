package decorators;

import java.util.HashMap;

import interfaces.QuestionIF;
import javafx.scene.control.TextField;

/**
 * Represents an QuestionDecorator abstract class that describes a question.
 */
public abstract class QuestionDecorator implements QuestionIF{
	public QuestionIF questionDecorator;

	/**
	 * Constructs a QuestionDecorator with the specified decorated question.
	 *
	 * @param questionDecorator the decorated question
	 */
	public QuestionDecorator(QuestionIF questionDecorator) {
	    this.questionDecorator = questionDecorator;
	}

	/**
	 * Returns the notes of the decorated question.
	 *
	 * @return the notes
	 */
	public String getNotes() {
	    return questionDecorator.getNotes();
	}

	/**
	 * Sets the notes of the decorated question.
	 *
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
	    questionDecorator.setNotes(notes);
	}

	/**
	 * Returns the answers HashMap of the decorated question.
	 *
	 * @return the answers HashMap
	 */
	public HashMap<String, String> getAnswersHM() {
	    return questionDecorator.getAnswersHM();
	}

	/**
	 * Sets the answers HashMap of the decorated question.
	 *
	 * @param answersHM the answers HashMap to set
	 */
	public void setAnswersHM(HashMap<String, String> answersHM) {
	    questionDecorator.setAnswersHM(answersHM);
	}

	/**
	 * Sets the answers of the decorated question.
	 *
	 * @param answers the answers to set
	 */
	public void setAnswers(String answers) {
	    questionDecorator.setAnswers(answers);
	}

	/**
	 * Returns the answers of the decorated question.
	 *
	 * @return the answers
	 */
	public String getAnswers() {
	    return questionDecorator.getAnswers();
	}

	/**
	 * Returns the question ID of the decorated question.
	 *
	 * @return the question ID
	 */
	public Integer getQuestionID() {
	    return questionDecorator.getQuestionID();
	}

	/**
	 * Sets the question ID of the decorated question.
	 *
	 * @param questionID the question ID to set
	 */
	public void setQuestionID(Integer questionID) {
	    questionDecorator.setQuestionID(questionID);
	}

	/**
	 * Returns the details of the decorated question.
	 *
	 * @return the details
	 */
	public String getDetails() {
	    return questionDecorator.getDetails();
	}

	/**
	 * Sets the details of the decorated question.
	 *
	 * @param details the details to set
	 */
	public void setDetails(String details) {
	    questionDecorator.setDetails(details);
	}

	/**
	 * Returns the right answer of the decorated question.
	 *
	 * @return the right answer
	 */
	public String getRightAnswer() {
	    return questionDecorator.getRightAnswer();
	}

	/**
	 * Returns the question bank ID of the decorated question.
	 *
	 * @return the question bank ID
	 */
	public Integer getQuestionBank() {
	    return questionDecorator.getQuestionBank();
	}

	/**
	 * Returns the subject of the decorated question.
	 *
	 * @return the subject
	 */
	public String getSubject() {
	    return questionDecorator.getSubject();
	}

	/**
	 * Sets the subject of the decorated question.
	 *
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
	    questionDecorator.setSubject(subject);
	}

	/**
	 * Returns the composer of the decorated question.
	 *
	 * @return the composer
	 */
	public String getComposer() {
	    return questionDecorator.getComposer();
	}

	/**
	 * Sets the courses of the decorated question.
	 *
	 * @param composer the courses to set
	 */
	public void setCourses(String composer) {
	    questionDecorator.setCourses(composer);
	}

	/**
	 * Sets the right answer of the decorated question.
	 *
	 * @param rightAnswer the right answer to set
	 */
	public void setRightAnswer(String rightAnswer) {
	    questionDecorator.setRightAnswer(rightAnswer);
	}

	/**
	 * Sets the question bank ID of the decorated question.
	 *
	 * @param questionBank the question bank ID to set
	 */
	public void setQuestionBank(Integer questionBank) {
	    questionDecorator.setQuestionBank(questionBank);
	}
}