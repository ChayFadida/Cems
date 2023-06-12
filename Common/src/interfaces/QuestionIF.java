package interfaces;

import java.util.HashMap;

public interface QuestionIF {
	public String getNotes();

	public void setNotes(String notes);

	public HashMap<String, String> getAnswersHM();

	public void setAnswersHM(HashMap<String, String> answersHM);

	public void setAnswers(String answers);
	
	public String getAnswers();

    public Integer getQuestionID();

    public void setQuestionID(Integer questionID);

    public String getDetails();

    public void setDetails(String details);

    public String getRightAnswer();

    public void setRightAnswer(String rightAnswer);

    public Integer getQuestionBank();

    public void setQuestionBank(Integer questionBank);

    public String getSubject();

    public void setSubject(String subject);

    public String getComposer();

    public void setCourses(String composer);

    public String toString();
}
