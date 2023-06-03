package entities;

import java.util.HashMap;

/**
 * Represents a question entity.
 */
public class Question {
    private int questionID;
    private String details;
    private String rightAnswer;
    private String questionBank;
    private String questionNum;
    private String qComposer;
    private String subject;
    private String[] courses;
    private String bank;
    private HashMap<String,String> answers;

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
    public Question(int questionID, String details, String rightAnswer, String questionBank, String questionNum,
                    String qComposer, String subject, String[] courses, HashMap<String, String> answers, String bank) {
        this.questionID = questionID;
        this.details = details;
        this.rightAnswer = rightAnswer;
        this.questionBank = questionBank;
        this.questionNum = questionNum;
        this.qComposer = qComposer;
        this.subject = subject;
        this.courses = courses;
        this.answers = answers;
        this.bank = bank;
    }

    /**
     * Returns the bank associated with the question.
     *
     * @return the question bank
     */
    public String getBank() {
        return bank;
    }

    /**
     * Sets the bank associated with the question.
     *
     * @param bank the question bank to set
     */
    public void setBank(String bank) {
        this.bank = bank;
    }

    /**
     * Returns the ID of the question.
     *
     * @return the question ID
     */
    public int getQuestionID() {
        return questionID;
    }

    /**
     * Sets the ID of the question.
     *
     * @param questionID the question ID to set
     */
    public void setQuestionID(int questionID) {
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
    public String getQuestionBank() {
        return questionBank;
    }

    /**
     * Sets the question bank associated with the question.
     *
     * @param questionBank the question bank to set
     */
    public void setQuestionBank(String questionBank) {
        this.questionBank = questionBank;
    }

    /**
     * Returns the question number.
     *
     * @return the question number
     */
    public String getQuestionNum() {
        return questionNum;
    }

    /**
     * Sets the question number.
     *
     * @param questionNum the question number to set
     */
    public void setQuestionNum(String questionNum) {
        this.questionNum = questionNum;
    }

    /**
     * Returns the composer of the question.
     *
     * @return the question composer
     */
    public String getqComposer() {
        return qComposer;
    }

    /**
     * Sets the composer of the question.
     *
     * @param qComposer the question composer to set
     */
    public void setqComposer(String qComposer) {
        this.qComposer = qComposer;
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
    public String[] getCourses() {
        return courses;
    }

    /**
     * Sets the courses related to the question.
     *
     * @param courses the question courses to set
     */
    public void setCourses(String[] courses) {
        this.courses = courses;
    }

    /**
     * Returns the possible answers for the question.
     *
     * @return the question answers
     */
    public HashMap<String, String> getAnswers() {
        return answers;
    }

    /**
     * Sets the possible answers for the question.
     *
     * @param answers the question answers to set
     */
    public void setAnswers(HashMap<String, String> answers) {
        this.answers = answers;
    }

    /**
     * Returns a string representation of the Question object.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "Question [questionID=" + questionID + ", course=" + courses + ", subject=" + subject +
                ", ecomposer=" + qComposer + ", questionNum=" + questionNum + ", bank=" + bank + "]";
    }
}

