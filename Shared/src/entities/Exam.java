package entities;

import java.util.HashMap;

//this class represents an Exam object.
public class Exam {
	//private variables.
	private int examID;
	private String course;
	private String subject;
	private int duration;
	private ExamType type;
	private String lecturerNote;
	private String studentNote;
	private String ecomposer;
	private String code;
	private String examNum;
	private String bank;
	private HashMap<String,Integer> studentInfo;
	private ExamStatus status;
	
	/**
	 *Exam constructor
	 *@param examID,course,subject,duration,type,lecturerNote,studentNote,ecomposer,code,exanNum,bank,studentInfo,status.
	 *
	 * */
	public Exam(String course, String subject, int duration, ExamType type, String lecturerNote, String studentNote,
			String ecomposer, String code, String examNum, String bank, HashMap<String,Integer> studentInfo, ExamStatus status,
			int examID) {
		this.course = course;
		this.subject = subject;
		this.duration = duration;
		this.type = type;
		this.lecturerNote = lecturerNote;
		this.studentNote = studentNote;
		this.ecomposer = ecomposer;
		this.code = code;
		this.examNum = examNum;
		this.bank = bank;
		this.studentInfo = studentInfo;
		this.status = status;
		this.examID = examID;
	}
	
	/**
	 *examID getter
	 *@return return the examID of exam
	 * */
	public int getExamID() {
		return examID;
	}
	/**
	 *examID setter
	 *@param examID
	 * */
	public void setExamID(int examID) {
		this.examID = examID;
	}
	/**
	 *course getter
	 *@return return the course of exam
	 * */
	public String getCourse() {
		return course;
	}
	/**
	 *course setter
	 *@param course
	 * */
	public void setCourse(String course) {
		this.course = course;
	}
	/**
	 *subject getter
	 *@return return the subject of exam
	 * */
	public String getSubject() {
		return subject;
	}
	/**
	 *subject setter
	 *@param subject
	 * */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 *duration getter
	 *@return return the duration of exam
	 * */
	public int getDuration() {
		return duration;
	}
	/**
	 *duration setter
	 *@param duration
	 * */
	public void setDuration(int duration) {
		this.duration = duration;
	}
	/**
	 *type getter
	 *@return return the type of exam
	 * */
	public ExamType getType() {
		return type;
	}
	/**
	 *type setter
	 *@param type
	 * */
	public void setType(ExamType type) {
		this.type = type;
	}
	/**
	 *lecturerNote getter
	 *@return return the lecturerNote of exam
	 * */
	public String getLecturerNote() {
		return lecturerNote;
	}
	/**
	 *lecturerNote setter
	 *@param lecturerNote
	 * */
	public void setLecturerNote(String lecturerNote) {
		this.lecturerNote = lecturerNote;
	}
	/**
	 *studentNote getter
	 *@return return the studentNote of exam
	 * */
	public String getStudentNote() {
		return studentNote;
	}
	/**
	 *studentNote setter
	 *@param studentNote
	 * */
	public void setStudentNote(String studentNote) {
		this.studentNote = studentNote;
	}
	/**
	 *ecomposer getter
	 *@return return the ecomposer of exam
	 * */
	public String getEcomposer() {
		return ecomposer;
	}
	/**
	 *ecomposer setter
	 *@param ecomposer
	 * */
	public void setEcomposer(String ecomposer) {
		this.ecomposer = ecomposer;
	}
	/**
	 *code getter
	 *@return return the code of exam
	 * */
	public String getCode() {
		return code;
	}
	/**
	 *code setter
	 *@param code
	 * */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 *examNum getter
	 *@return return the examNum of exam
	 * */
	public String getExamNum() {
		return examNum;
	}
	/**
	 *examNum setter
	 *@param examNum
	 * */
	public void setExamNum(String examNum) {
		this.examNum = examNum;
	}
	/**
	 *bank getter
	 *@return return the bank of exam
	 * */
	public String getBank() {
		return bank;
	}
	/**
	 *bank setter
	 *@param bank
	 * */
	public void setBank(String bank) {
		this.bank = bank;
	}
	/**
	 *studentInfo getter
	 *@return return the student information
	 * */
	public HashMap<String,Integer> getStudentInfo() {
		return studentInfo;
	}
	/**
	 *studentInfo setter
	 *@param studentInfo
	 * */
	public void setStudentInfo(HashMap<String,Integer> studentInfo) {
		this.studentInfo = studentInfo;
	}
	/**
	 *status getter
	 *@return return the status of an exam
	 * */
	public ExamStatus getStatus() {
		return status;
	}
	/**
	 *status setter
	 *@param status
	 * */
	public void setStatus(ExamStatus status) {
		this.status = status;
	}
	/**
	 *Exam String getter
	 *@return return a formated string of an exam
	 * */
	@Override
	public String toString() {
		return "Exam [examID=" + examID + ", course=" + course + ", subject=" + subject + ", duration=" + duration
				+ ", type=" + type + ", ecomposer=" + ecomposer + ", examNum=" + examNum + ", bank=" + bank
				+ ", status=" + status + "]";
	}
}
