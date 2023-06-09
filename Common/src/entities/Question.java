package entities;

import java.io.Serializable;
import java.util.HashMap;
import thirdPart.JsonHandler;

/**
 * Represents a question entity.
 */
@SuppressWarnings("serial")
public class Question implements Serializable{
    private Integer questionID;
    private String details;
    private String rightAnswer;
    private Integer questionBank;
    private String subject;
    private String answers;
    private String notes;
    private String courses;
    private HashMap<String,String> answersHM;
    private HashMap<String,String> coursesHM;

    /**
     * Constructs a Question object with the given parameters.
     *
     * @param questionID     the ID of the question
     * @param details        the details of the question
     * @param rightAnswer    the right answer to the question
     * @param questionBank   the question bank associated with the question
     * @param questionNum    the question number
     * @param qComposer      the composer of the question
     * @param subject        the subject of the question
     * @param courses        the courses related to the question
     * @param answers        the possible answers for the question
     * @param bank           the bank associated with the question
     */

    public Question(Integer questionID, String details, String rightAnswer, Integer questionBank, String subject,
			 String answers, String notes, String courses) {
		super();
		this.questionID = questionID;
		this.details = details;
		this.rightAnswer = rightAnswer;
		this.questionBank = questionBank;
		this.subject = subject;
		this.notes=notes;
		this.answers = answers;
		this.courses=courses;
		answersHM = new HashMap<>();
		coursesHM=new HashMap<>();
		answersHM = JsonHandler.convertJsonToHashMap(answers, String.class, String.class);
		//input answers data into hashmap needs to be implemented!
	}
    /**

    Returns the HashMap containing the courses.
    @return the courses HashMap
    */
    public HashMap<String, String> getCoursesHM() {
		return coursesHM;
	}
    /**

    Sets the HashMap containing the courses.
    @param coursesHM the courses HashMap to set
    */
	public void setCoursesHM(HashMap<String, String> coursesHM) {
		this.coursesHM = coursesHM;
	}
    /**

    Returns the notes.
    @return the notes
    */
	public String getNotes() {
		return notes;
	}

    /**
    *Sets the notes.
    @param notes the notes to set
    */
	public void setNotes(String notes) {
		this.notes = notes;
	}

    /**
    *Returns the HashMap containing the answers.
    *@return the answers HashMap
    */
	public HashMap<String, String> getAnswersHM() {
		return answersHM;
	}

	/**
    *Sets the HashMap containing the answers.
    *@param answersHM the answers HashMap to set
    */
	public void setAnswersHM(HashMap<String, String> answersHM) {
		this.answersHM = answersHM;
	}

    /**
    *Sets the answers.
    *@param answers the answers to set
    */
	public void setAnswers(String answers) {
		this.answers = answers;
	}
	public String getAnswers() {
		return answers;
	}

	/**
     * Returns the ID of the question.
     *
     * @return the question ID
     */
    public Integer getQuestionID() {
        return questionID;
    }

    /**
     * Sets the ID of the question.
     *
     * @param questionID the question ID to set
     */
    public void setQuestionID(Integer questionID) {
        this.questionID = questionID;
    }

    /**
     * Returns the details of the question.
     *
     * @return the question details
     */
    public String getDetails() {
        return details;
    }

    /**
     * Sets the details of the question.
     *
     * @param details the question details to set
     */
    public void setDetails(String details) {
        this.details = details;
    }

    /**
     * Returns the right answer to the question.
     *
     * @return the right answer
     */
    public String getRightAnswer() {
        return rightAnswer;
    }

    /**
     * Sets the right answer to the question.
     *
     * @param rightAnswer the right answer to set
     */
    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    /**
     * Returns the question bank associated with the question.
     *
     * @return the question bank
     */
    public Integer getQuestionBank() {
        return questionBank;
    }

    /**
     * Sets the question bank associated with the question.
     *
     * @param questionBank the question bank to set
     */
    public void setQuestionBank(Integer questionBank) {
        this.questionBank = questionBank;
    }

    /**
     * Returns the subject of the question.
     *
     * @return the question subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets the subject of the question.
     *
     * @param subject the question subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Returns the courses related to the question.
     *
     * @return the question courses
     */
    public String getCourses() {
        return courses;
    }

    /**
     * Sets the courses related to the question.
     *
     * @param courses the question courses to set
     */
    public void setCourses(String courses) {
        this.courses = courses;
    }
    
    /**
     * Returns a string representation of the Question object.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {

        return "Question [questionID=" + questionID  + ", subject=" + subject +
                  ", bank=" + questionBank + "]";
    }
}