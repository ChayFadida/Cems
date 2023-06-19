package entities;
/**
* Represents a view of a question bank.
*/
public class QuestionBankView  {
	private String details;
    private String subject;
    private String lastName;
    private String firstName;
    private String courses;
    /**
     * Constructs a QuestionBankView object with the specified parameters.
     *
     * @param details    the details of the question bank
     * @param subject    the subject of the question bank
     * @param lastName   the last name of the examiner
     * @param firstName  the first name of the examiner
     * @param courses    the courses related to the question bank
     */
    public QuestionBankView(String details, String subject, String lastName, String firstName, String courses) {
		this.details = details;
		this.subject = subject;
		this.lastName = lastName;
		this.firstName = firstName;
		this.courses = courses;
		
	}
    /**
     * Returns the courses related to the question bank.
     *
     * @return the courses
     */
	public String getCourses() {
		return courses;
	}
	/**
	 * Sets the courses related to the question bank.
	 *
	 * @param courses the courses to set
	 */
	public void setCourses(String courses) {
		this.courses = courses;
	}
	/**
	 * Returns the details of the question bank.
	 *
	 * @return the details
	 */
	public String getDetails() {
		return details;
	}
	/**
	 * Sets the details of the question bank.
	 *
	 * @param details the details to set
	 */
	public void setDetails(String details) {
		this.details = details;
	}
	/**
	 * Returns the subject of the question bank.
	 *
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * Sets the subject of the question bank.
	 *
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
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

    
}
