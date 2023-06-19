package entities;

/**
 * Represents a Exam entity.
 */

//this class represents an Exam object.
public class Exam {
	// private variables.
	private Integer examId;
	private Integer courseId;
	private String subject;
	private String examName;
	private Integer duration;
	private String lecturerNote;
	private String studentNote;
	private Integer composerId;
	private String code;
	private String examNum;
	private Integer bankId;
	private Integer isLocked;
	
	/**
	 * Constructs an Exam object with the specified parameters.
	 *
	 * @param examId       the ID of the exam
	 * @param examName     the name of the exam
	 * @param courseId     the ID of the course
	 * @param subject      the subject of the exam
	 * @param duration     the duration of the exam
	 * @param lecturerNote the lecturer's note for the exam
	 * @param studentNote  the student's note for the exam
	 * @param composerId   the ID of the exam composer
	 * @param code         the code for the exam
	 * @param examNum      the exam number
	 * @param bankId       the ID of the bank
	 * @param isLocked     the lock status of the exam
	 */
	public Exam(Integer examId, String examName, Integer courseId, String subject, Integer duration,
	            String lecturerNote, String studentNote, Integer composerId, String code, String examNum,
	            Integer bankId, Integer isLocked) {
	    this.examId = examId;
	    this.examName = examName;
	    this.courseId = courseId;
	    this.subject = subject;
	    this.duration = duration;
	    this.lecturerNote = lecturerNote;
	    this.studentNote = studentNote;
	    this.composerId = composerId;
	    this.code = code;
	    this.examNum = examNum;
	    this.bankId = bankId;
	    this.isLocked = isLocked;
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
	 * Returns the ID of the course.
	 *
	 * @return the course ID
	 */
	public Integer getCourseId() {
	    return courseId;
	}

	/**
	 * Sets the ID of the course.
	 *
	 * @param courseId the course ID to set
	 */
	public void setCourseId(Integer courseId) {
	    this.courseId = courseId;
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
	 * Returns the duration of the exam.
	 *
	 * @return the exam duration
	 */
	public Integer getDuration() {
	    return duration;
	}

	/**
	 * Sets the duration of the exam.
	 *
	 * @param duration the exam duration to set
	 */
	public void setDuration(Integer duration) {
	    this.duration = duration;
	}

	/**
	 * Returns the lecturer's note for the exam.
	 *
	 * @return the lecturer's note
	 */
	public String getLecturerNote() {
	    return lecturerNote;
	}

	/**
	 * Sets the lecturer's note for the exam.
	 *
	 * @param lecturerNote the lecturer's note to set
	 */
	public void setLecturerNote(String lecturerNote) {
	    this.lecturerNote = lecturerNote;
	}

	/**
	 * Returns the student's note for the exam.
	 *
	 * @return the student's note
	 */
	public String getStudentNote() {
	    return studentNote;
	}

	/**
	 * Sets the student's note for the exam.
	 *
	 * @param studentNote the student's note to set
	 */
	public void setStudentNote(String studentNote) {
	    this.studentNote = studentNote;
	}

	/**
	 * Returns the ID of the exam composer.
	 *
	 * @return the composer ID
	 */
	public Integer getComposerId() {
	    return composerId;
	}

	/**
	 * Sets the ID of the exam composer.
	 *
	 * @param composerId the composer ID to set
	 */
	public void setComposerId(Integer composerId) {
	    this.composerId = composerId;
	}

	/**
	 * Returns the code for the exam.
	 *
	 * @return the exam code
	 */
	public String getCode() {
	    return code;
	}

	/**
	 * Sets the code for the exam.
	 *
	 * @param code the exam code to set
	 */
	public void setCode(String code) {
	    this.code = code;
	}

	/**
	 * Returns the exam number.
	 *
	 * @return the exam number
	 */
	public String getExamNum() {
	    return examNum;
	}

	/**
	 * Sets the exam number.
	 *
	 * @param examNum the exam number to set
	 */
	public void setExamNum(String examNum) {
	    this.examNum = examNum;
	}

	/**
	 * Returns the ID of the bank.
	 *
	 * @return the bank ID
	 */
	public Integer getBankId() {
	    return bankId;
	}

	/**
	 * Sets the ID of the bank.
	 *
	 * @param bankId the bank ID to set
	 */
	public void setBankId(Integer bankId) {
	    this.bankId = bankId;
	}

	/**
	 * Returns the lock status of the exam.
	 *
	 * @return {@code true} if the exam is locked, {@code false} otherwise
	 */
	public Integer isLocked() {
	    return isLocked;
	}

	/**
	 * Sets the lock status of the exam.
	 *
	 * @param isLocked the lock status to set
	 */
	public void setLocked(Integer isLocked) {
	    this.isLocked = isLocked;
	}

	/**
	 * Returns a string representation of the Exam object.
	 *
	 * @return a string representation of the object
	 */
	@Override
	public String toString() {
	    return "Exam [examID=" + examId + ", course=" + courseId + ", subject=" + subject + ", duration=" + duration
	            + ", ecomposer=" + composerId + ", examNum=" + examNum + ", bank=" + bankId + "]";
	}
}