package entities;

import java.util.HashMap; 

//this class represents an Exam Bank/Collection.
public class ExamBank {
	
	private String name;
	private int lecturerId;
	HashMap<String,Integer> exams;
	
	/**
	 *ExamBank constructor
	 *@param name,lecturerId.
	 *
	 * */
	public ExamBank(String name, int lecturerId) {
		this.name = name;
		this.lecturerId = lecturerId;
	}
	/**
	 *ExamBank constructor
	 *@param name,lecturerId,exams.
	 *
	 * */
	public ExamBank(String name, int lecturerId, HashMap<String, Integer> exams) {
		this.name = name;
		this.lecturerId = lecturerId;
		this.exams = exams;
	}
	/**
	 *name getter
	 *@return return the name of examBank
	 * */
	public String getName() {
		return name;
	}
	/**
	 *name setter
	 *@param name
	 * */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 *lecturerId getter
	 *@return return the lecturerId of examBank
	 * */
	public int getLecturerId() {
		return lecturerId;
	}
	/**
	 *lecturerId setter
	 *@param lecturerId
	 * */
	public void setLecturerId(int lecturerId) {
		this.lecturerId = lecturerId;
	}
	/**
	 *exams getter
	 *@return return the exams of examBank
	 * */
	public HashMap<String, Integer> getExams() {
		return exams;
	}
	/**
	 *exams setter
	 *@param exams hashmap
	 * */
	public void setExams(HashMap<String, Integer> exams) {
		this.exams = exams;
	}
	/**
	 *Exam String getter
	 *@return return a formated string of an examBank
	 * */
	@Override
	public String toString() {
		return "ExamBank [name=" + name + ", lecturerId=" + lecturerId + "]";
	}
	
}
