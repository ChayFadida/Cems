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
	public Integer getExamId() {
		return examId;
	}
	public void setExamId(Integer examId) {
		this.examId = examId;
	}
	public Integer getStudentId() {
		return studentId;
	}
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	public Integer getStartTime() {
		return startTime;
	}
	public void setStartTime(Integer startTime) {
		this.startTime = startTime;
	}
	public Integer getEndTime() {
		return endTime;
	}
	public void setEndTime(Integer endTime) {
		this.endTime = endTime;
	}
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public Integer getPdfBytes() {
		return pdfBytes;
	}
	public void setPdfBytes(Integer pdfBytes) {
		this.pdfBytes = pdfBytes;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	
	
	

}
