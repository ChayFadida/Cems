package entities;

public class ExamResult {
	private int examId ,courseId;
	private float grade;
	private String examName,status,subject;
	public ExamResult(int examId, int courseId, float grade, String examName, String status, String subject) {
		this.examId = examId;
		this.courseId = courseId;
		this.grade = grade;
		this.examName = examName;
		this.status = status;
		this.subject = subject;
	}
	public int getExamId() {
		return examId;
	}
	public void setExamId(int examId) {
		this.examId = examId;
	}
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public float getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	@Override
	public String toString() {
		return "ExamResult [examId=" + examId + ", courseId=" + courseId + ", grade=" + grade + ", examName=" + examName
				+ ", status=" + status + ", subject=" + subject + "]";
	}
	
}
