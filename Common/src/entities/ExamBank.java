package entities;

import java.util.HashMap; 

/**
 * Represents a ExamBank entity.
 */
public class ExamBank {
	
	private Integer bankId;
	private Integer lecturerId;
	private HashMap<String,Integer> exams;
	
	/**
	 * Constructs an ExamBank object with the specified parameters.
	 *
	 * @param bankId      the ID of the exam bank
	 * @param lecturerId  the ID of the lecturer
	 * @param exams       the exams in the bank, mapped by their names
	 */
	public ExamBank(Integer bankId, Integer lecturerId, HashMap<String, Integer> exams) {
		this.bankId = bankId;
		this.lecturerId = lecturerId;
		this.exams = exams;
	}

	/**
	 * Returns the ID of the exam bank.
	 *
	 * @return the bank ID
	 */
	public Integer getBankId() {
		return bankId;
	}

	/**
	 * Sets the ID of the exam bank.
	 *
	 * @param bankId the bank ID to set
	 */
	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}

	/**
	 * Returns the ID of the lecturer.
	 *
	 * @return the lecturer ID
	 */
	public Integer getLecturerId() {
		return lecturerId;
	}

	/**
	 * Sets the ID of the lecturer.
	 *
	 * @param lecturerId the lecturer ID to set
	 */
	public void setLecturerId(Integer lecturerId) {
		this.lecturerId = lecturerId;
	}

	/**
	 * Returns the exams in the bank, mapped by their names.
	 *
	 * @return the exams in the bank
	 */
	public HashMap<String, Integer> getExams() {
		return exams;
	}

	/**
	 * Sets the exams in the bank, mapped by their names.
	 *
	 * @param exams the exams to set
	 */
	public void setExams(HashMap<String, Integer> exams) {
		this.exams = exams;
	}

	/**
	 * Returns a string representation of the ExamBank object.
	 *
	 * @return a string representation of the object
	 */
	@Override
	public String toString() {
		return "ExamBank [bank=" + bankId + ", lecturerId=" + lecturerId + "]";
	}
	
}
