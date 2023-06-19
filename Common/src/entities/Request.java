package entities;

/**
 * Represents a Request entity.
 */
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

    
    /**
     * Constructs a Request object with the specified parameters.
     *
     * @param requestId    the ID of the request
     * @param examId       the ID of the exam
     * @param lecturerId   the ID of the lecturer
     * @param courseId     the ID of the course
     * @param subject      the subject of the request
     * @param oldDuration  the old duration of the exam
     * @param newDuration  the new duration of the exam
     * @param status       the status of the request
     * @param reasons      the reasons for the request
     */
    public Request(int requestId, int examId, int lecturerId, int courseId, String subject, int oldDuration,
            int newDuration, String status, String reasons) {
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

    /**
     * Returns the reasons for the request.
     *
     * @return the reasons
     */
    public String getReasons() {
        return reasons;
    }

    /**
     * Sets the reasons for the request.
     *
     * @param reasons the reasons to set
     */
    public void setReasons(String reasons) {
        this.reasons = reasons;
    }

    /**
     * Returns the ID of the request.
     *
     * @return the request ID
     */
    public int getRequestId() {
        return requestId;
    }

    /**
     * Sets the ID of the request.
     *
     * @param requestId the request ID to set
     */
    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    /**
     * Returns the ID of the exam.
     *
     * @return the exam ID
     */
    public int getExamId() {
        return examId;
    }

    /**
     * Sets the ID of the exam.
     *
     * @param examId the exam ID to set
     */
    public void setExamId(int examId) {
        this.examId = examId;
    }

    /**
     * Returns the ID of the lecturer.
     *
     * @return the lecturer ID
     */
    public int getLecturerId() {
        return lecturerId;
    }

    /**
     * Sets the ID of the lecturer.
     *
     * @param lecturerId the lecturer ID to set
     */
    public void setLecturerId(int lecturerId) {
        this.lecturerId = lecturerId;
    }

    /**
     * Returns the ID of the course.
     *
     * @return the course ID
     */
    public int getCourseId() {
        return courseId;
    }

    /**
     * Sets the ID of the course.
     *
     * @param courseId the course ID to set
     */
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    /**
     * Returns the subject of the request.
     *
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets the subject of the request.
     *
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Returns the old duration of the exam.
     *
     * @return the old duration
     */
    public int getOldDuration() {
        return oldDuration;
    }

    /**
     * Sets the old duration of the exam.
     *
     * @param oldDuration the old duration to set
     */
    public void setOldDuration(int oldDuration) {
        this.oldDuration = oldDuration;
    }

    /**
     * Returns the new duration of the exam.
     *
     * @return the new duration
     */
    public int getNewDuration() {
        return newDuration;
    }

    /**
     * Sets the new duration of the exam.
     *
     * @param newDuration the new duration to set
     */
    public void setNewDuration(int newDuration) {
        this.newDuration = newDuration;
    }

    /**
     * Returns the status of the request.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the request.
     *
     * @param status the status to set
     */
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