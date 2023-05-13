package logic;

public class Question {
	private int id;
	private String course;
	private String lecturer;
	private String question;
	private int number;
	
	public Question(int id,String course,String lecturer,String question,int number) {
		this.id=id;
		this.course=course;
		this.lecturer=lecturer;
		this.question=question;
		this.number=number;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getLecturer() {
		return lecturer;
	}

	public void setLecturer(String lecturer) {
		this.lecturer = lecturer;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
	
	public String toString() {
		return String.format("%s %s %s %s %s\n",id,course,lecturer,question,number);
	}
}
