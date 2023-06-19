package entities;

public class ExamResult {
	private int examId ,courseId,studentId;
	private Integer grade;
	private String examName,status,subject;
    /**
     * Constructs an ExamResult object with the specified properties.
     *
     * @param examId    the ID of the exam
     * @param courseId  the ID of the course
     * @param studentId the ID of the student
     * @param grade     the grade of the exam result
     * @param examName  the name of the exam
     * @param status    the status of the exam result
     * @param subject   the subject of the exam result
     */
	public ExamResult(int examId, int courseId,int studentId, Integer grade, String examName, String status, String subject) {
		this.examId = examId;
		this.courseId = courseId;
		this.studentId = studentId;
		this.grade = grade;
		this.examName = examName;
		this.status = status;
		this.subject = subject;
	}
    /**
     * Retrieves the student ID associated with the exam result.
     *
     * @return the student ID
     */
	public int getStudentId() {
		return studentId;
	}
	 /**
     * Sets the student ID for the exam result.
     *
     * @param studentId the student ID to set
     */
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	  /**
     * Retrieves the exam ID associated with the exam result.
     *
     * @return the exam ID
     */
	public int getExamId() {
		return examId;
	}
    /**
     * Sets the exam ID for the exam result.
     *
     * @param examId the exam ID to set
     */
	public void setExamId(int examId) {
		this.examId = examId;
	}
	 /**
     * Retrieves the course ID associated with the exam result.
     *
     * @return the course ID
     */
	public int getCourseId() {
		return courseId;
	}
	 /**
     * Sets the course ID for the exam result.
     *
     * @param courseId the course ID to set
     */
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	 /**
     * Retrieves the grade of the exam result.
     *
     * @return the grade
     */
	public Integer getGrade() {
		return grade;
	}
	/**
     * Sets the grade for the exam result.
     *
     * @param grade the grade to set
     */
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	/**
     * Retrieves the name of the exam.
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
     * Retrieves the status of the exam result.
     *
     * @return the status
     */
	public String getStatus() {
		return status;
	}
	/**
     * Sets the status for the exam result.
     *
     * @param status the status to set
     */
	public void setStatus(String status) {
		this.status = status;
	}
	  /**
     * Retrieves the subject of the exam result.
     *
     * @return the subject
     */
	public String getSubject() {
		return subject;
	}
	/**
     * Sets the subject for the exam result.
     *
     * @param subject the subject to set
     */
	public void setSubject(String subject) {
		this.subject = subject;
	}
    /**
     * Returns a string representation of the ExamResult object.
     *
     * @return a string representation of the object
     */
	@Override
	public String toString() {
		return "ExamResult [examId=" + examId + ", courseId=" + courseId + ", grade=" + grade + ", examName=" + examName
				+ ", status=" + status + ", subject=" + subject + "]";
	}
	
}
