package entities;

import java.util.HashMap; 

//this class represents an Exam Bank/Collection.
public class ExamBank {
	
	private Integer bankId;
	private Integer lecturerId;
	private HashMap<String,Integer> exams;
	
	
	public ExamBank(Integer bankId, Integer lecturerId, HashMap<String, Integer> exams) {
		this.bankId = bankId;
		this.lecturerId = lecturerId;
		this.exams = exams;
	}


	public Integer getBankId() {
		return bankId;
	}


	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}


	public Integer getLecturerId() {
		return lecturerId;
	}


	public void setLecturerId(Integer lecturerId) {
		this.lecturerId = lecturerId;
	}


	public HashMap<String, Integer> getExams() {
		return exams;
	}


	public void setExams(HashMap<String, Integer> exams) {
		this.exams = exams;
	}


	@Override
	public String toString() {
		return "ExamBank [bank=" + bankId + ", lecturerId=" + lecturerId + "]";
	}
	
}
