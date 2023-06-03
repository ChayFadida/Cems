package logic;

public class Question {
	private int id;
	private String course;
	private String lecturer;
	private String question;
	private int number;
	
	/**
	 *question constructor
	 *@param id,course,lecturer,question,number
	 *
	 * */
	public Question(int id,String course,String lecturer,String question,int number) {
		this.id=id;
		this.course=course;
		this.lecturer=lecturer;
		this.question=question;
		this.number=number;
	}
	
	/**
	 *number getter
	 *@return return the number of question
	 * */
	public int getNumber() {
		return number;
	}
	/**
	 *number setter
	 *@param number
	 * */
	public void setNumber(int number) {
		this.number = number;
	}
	
	/**
	 *id getter
	 *@return the id number
	 * */
	public int getId() {
		return id;
	}
	
	/**
	 *id setter
	 *@param id
	 * */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 *course getter
	 *@return String of the course
	 * */
	public String getCourse() {
		return course;
	}
	
	/**
	 *course setter
	 *@param String course
	 * */
	public void setCourse(String course) {
		this.course = course;
	}
	
	/**
	 *lecturer getter
	 *@return lecturer
	 * */
	public String getLecturer() {
		return lecturer;
	}
	
	/**
	 *lecturer setter
	 *@param lecturer
	 * */
	public void setLecturer(String lecturer) {
		this.lecturer = lecturer;
	}
	
	/**
	 *question getter
	 *@return string of the question
	 * */
	public String getQuestion() {
		return question;
	}
	
	/**
	 *question setter
	 *@param String question
	 * */
	public void setQuestion(String question) {
		this.question = question;
	}
	
	/**
	 *@return string
	 * */
	public String toString() {
		return String.format("%s %s %s %s %s\n",id,course,lecturer,question,number);
	}
}
