package entities;

public class ExamBankView {
	
	private Integer examId;
	private String firstName;
	private String lastName;
	private String subject;
	private String courses;
	private String examName;



	public ExamBankView(Integer examId, String firstName, String lastName, String subject, String courses, String examName) {
		super();
		this.examId = examId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.subject = subject;
		this.courses = courses;
		this.examName = examName;
	}
	
	
	public String getExamName() {
		return examName;
	}


	public void setExamName(String examName) {
		this.examName = examName;
	}
	
	public Integer getExamId() {
		return examId;
	}

	public void setExamId(Integer examId) {
		this.examId = examId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getCourses() {
		return courses;
	}

	public void setCourses(String courses) {
		this.courses = courses;
	}
}
