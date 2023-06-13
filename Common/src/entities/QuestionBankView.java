package entities;

public class QuestionBankView  {
	private String details;
    private String subject;
    private String lastName;
    private String firstName;
    private String courses;
	
    public QuestionBankView(String details, String subject, String lastName, String firstName, String courses) {
		this.details = details;
		this.subject = subject;
		this.lastName = lastName;
		this.firstName = firstName;
		this.courses = courses;
		
	}
	public String getCourses() {
		return courses;
	}
	public void setCourses(String courses) {
		this.courses = courses;
	}
	public String getQuestionID() {
		return courses;
	}
	public void setQuestionID(String courses) {
		this.courses = courses;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

    
}
