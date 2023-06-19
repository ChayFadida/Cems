package entities;

public class ExamResults {
	
	private Integer examId;
	private Integer studentId;
	private Integer grade;
	private Integer startTime; //not sure that its int(?)
	private Integer endTime; //not sure that its int(?)
	private Integer duration;
	private String notes;
	private boolean selected;
	private Integer pdfBytes; //for sure not an int
	private String status;
	
	public ExamResults(Integer examId, Integer studentId, Integer grade, Integer startTime, Integer endTime,
			Integer duration, String notes, boolean selected, Integer pdfBytes, String status) {
		super();
		this.examId = examId;
		this.studentId = studentId;
		this.grade = grade;
		this.startTime = startTime;
		this.endTime = endTime;
		this.duration = duration;
		this.notes = notes;
		this.selected = selected;
		this.pdfBytes = pdfBytes;
		this.status = status;
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
     * Retrieves the start time of the event.
     *
     * @return the start time
     */
	public Integer getStartTime() {
		return startTime;
	}
	
	   /**
     * Sets the start time for the event.
     *
     * @param startTime the start time to set
     */
	public void setStartTime(Integer startTime) {
		this.startTime = startTime;
	}
	
	/**
     * Retrieves the end time of the event.
     *
     * @return the end time
     */
	public Integer getEndTime() {
		return endTime;
	}
	
	/**
     * Sets the end time for the event.
     *
     * @param endTime the end time to set
     */
	public void setEndTime(Integer endTime) {
		this.endTime = endTime;
	}
	
	/**
     * Retrieves the duration of the event.
     *
     * @return the duration
     */
	public Integer getDuration() {
		return duration;
	}
	
	 /**
     * Sets the duration for the event.
     *
     * @param duration the duration to set
     */
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	
	 /**
     * Retrieves the notes associated with the event.
     *
     * @return the notes
     */
	public String getNotes() {
		return notes;
	}
	
	/**
     * Sets the notes for the event.
     *
     * @param notes the notes to set
     */
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	/**
     * Checks if the event is selected.
     *
     * @return true if selected, false otherwise
     */
	public boolean isSelected() {
		return selected;
	}
	
	
	 /**
     * Sets the selected state of the event.
     *
     * @param selected the selected state to set
     */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	 /**
     * Retrieves the size of the PDF associated with the event.
     *
     * @return the PDF size in bytes
     */
	public Integer getPdfBytes() {
		return pdfBytes;
	}
	
	 /**
     * Sets the size of the PDF for the event.
     *
     * @param pdfBytes the PDF size to set
     */
	public void setPdfBytes(Integer pdfBytes) {
		this.pdfBytes = pdfBytes;
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
	

	
	
	

}
