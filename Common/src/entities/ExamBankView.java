package entities;
/**
 * Represents a ExamBankView entity.
 */
public class ExamBankView {
	
	private Integer examId;
	private String firstName;
	private String lastName;
	private String subject;
	private String courses;
	private String examName;


	/**
	 * Constructs an ExamBankView object with the specified parameters.
	 *
	 * @param examId     the ID of the exam
	 * @param firstName  the first name of the examiner
	 * @param lastName   the last name of the examiner
	 * @param subject    the subject of the exam
	 * @param courses    the courses related to the exam
	 * @param examName   the name of the exam
	 */
	public ExamBankView(Integer examId, String firstName, String lastName, String subject, String courses, String examName) {
		super();
		this.examId = examId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.subject = subject;
		this.courses = courses;
		this.examName = examName;
	}
	
	/**
	 * Returns the name of the exam.
	 *
	 * @return the exam name
	 */	
	public String getExamName() {
		return examName;
	}

	/**
	 * Sets the name of the exam.
	 *
	 * @param examName the exam name to set
	 */
	public void setExamName(String examName) {
		this.examName = examName;
	}
	/**
	 * Returns the ID of the exam.
	 *
	 * @return the exam ID
	 */
	public Integer getExamId() {
		return examId;
	}
	/**
	 * Sets the ID of the exam.
	 *
	 * @param examId the exam ID to set
	 */
	public void setExamId(Integer examId) {
		this.examId = examId;
	}
	/**
	 * Returns the first name of the examiner.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * Sets the first name of the examiner.
	 *
	 * @param firstName the first name to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * Returns the last name of the examiner.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * Sets the last name of the examiner.
	 *
	 * @param lastName the last name to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * Returns the subject of the exam.
	 *
	 * @return the exam subject
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * Sets the subject of the exam.
	 *
	 * @param subject the exam subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * Returns the courses related to the exam.
	 *
	 * @return the courses
	 */
	public String getCourses() {
		return courses;
	}
	/**
	 * Sets the courses related to the exam.
	 *
	 * @param courses the courses to set
	 */
	public void setCourses(String courses) {
		this.courses = courses;
	}
}
