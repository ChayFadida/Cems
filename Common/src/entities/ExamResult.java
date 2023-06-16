package entities;

public class ExamResult {
	private int examId ,courseId,studentId;
	private Integer grade;
	private String examName,status,subject;
	public ExamResult(int examId, int courseId,int studentId, Integer grade, String examName, String status, String subject) {
		this.examId = examId;
		this.courseId = courseId;
		this.studentId = studentId;
		this.grade = grade;
		this.examName = examName;
		this.status = status;
		this.subject = subject;
	}

	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
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
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
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
