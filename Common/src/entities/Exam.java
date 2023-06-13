package entities;

import java.util.HashMap;

//this class represents an Exam object.
public class Exam {
	//private variables.
	private Integer examId;
	private Integer courseId;
	private String subject;
	private Integer duration;
	private String lecturerNote;
	private String studentNote;
	private Integer composerId;
	private String code;
	private String examNum;
	private Integer bankId;
	private boolean isLocked;
	
	public Exam(Integer examId, Integer courseId, String subject, Integer duration, String lecturerNote,
			String studentNote, Integer composerId, String code, String examNum, Integer bankId, boolean isLocked) {
		super();
		this.examId = examId;
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

	public Integer getExamId() {
		return examId;
	}

	public void setExamId(Integer examId) {
		this.examId = examId;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getLecturerNote() {
		return lecturerNote;
	}

	public void setLecturerNote(String lecturerNote) {
		this.lecturerNote = lecturerNote;
	}

	public String getStudentNote() {
		return studentNote;
	}

	public void setStudentNote(String studentNote) {
		this.studentNote = studentNote;
	}

	public Integer getComposerId() {
		return composerId;
	}

	public void setComposerId(Integer composerId) {
		this.composerId = composerId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getExamNum() {
		return examNum;
	}

	public void setExamNum(String examNum) {
		this.examNum = examNum;
	}

	public Integer getBankId() {
		return bankId;
	}

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}

	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

	@Override
	public String toString() {
		return "Exam [examID=" + examId + ", course=" + courseId + ", subject=" + subject + ", duration=" + duration
				+ ", ecomposer=" + composerId + ", examNum=" + examNum + ", bank=" + bankId+ "]";
	}
}