package entities;

//this class represents a duration request made for an exam by a lecturer and sent to hod.
public class Request {
	int requestId;
	int examId;
    int lecturerId;
    int courseId;
    String subject;
    int oldDuration;
    int newDuration;
    String status;
    String reasons;

    
	public Request(int requestId, int examId, int lecturerId,int courseId,String subject, int oldDuration, int newDuration,String status,String reasons) {
		this.requestId = requestId;
		this.examId = examId;
		this.lecturerId = lecturerId;
		this.courseId = courseId;
		this.subject = subject;
		this.oldDuration = oldDuration;
		this.newDuration = newDuration;
		this.status = status;
		this.reasons = reasons;
	}

	public String getReasons() {
		return reasons;
	}

	public void setReasons(String reasons) {
		this.reasons = reasons;
	}

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public int getLecturerId() {
		return lecturerId;
	}

	public void setLecturerId(int lecturerId) {
		this.lecturerId = lecturerId;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getOldDuration() {
		return oldDuration;
	}

	public void setOldDuration(int oldDuration) {
		this.oldDuration = oldDuration;
	}

	public int getNewDuration() {
		return newDuration;
	}

	public void setNewDuration(int newDuration) {
		this.newDuration = newDuration;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Request [requestId=" + requestId + ", examId=" + examId + ", lecturerId=" + lecturerId + ", courseId="
				+ courseId + ", subject=" + subject + ", oldDuration=" + oldDuration + ", newDuration=" + newDuration
				+ ", status=" + status + ", reasons=" + reasons + "]";
	}
}

