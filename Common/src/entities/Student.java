package entities;

import java.util.HashMap;

import timer.ExamSessionIF;

/**
 * Represents a Student entity.
 */
@SuppressWarnings("serial")
public class Student extends User{
	
	private Integer department;
	private ExamSessionIF examSession = null; 
	/**
	 * Constructs a Student object with the specified user details and department.
	 *
	 * @param userHM     the HashMap containing user details
	 * @param department the department of the student
	 */
	public Student(HashMap<String,Object> userHM,Integer department) {
		super(userHM);
		this.department = department;
	}
	/**
	 * Returns the department of the student.
	 *
	 * @return the department
	 */
	public Integer getDepartment() {
		return department;
	}
	/**
	 * Sets the department of the student.
	 *
	 * @param department the department to set
	 */
	public void setDepartment(Integer department) {
		this.department = department;
	}
	/**
	 * Returns the Exam SessionIF of the student.
	 *
	 * @return the examSession
	 */
	public ExamSessionIF getExamSession() {
		return examSession;
	}
	/**
	 * Sets the Exam SessionIF of the student.
	 *
	 * @param examSession the ExamSessionIF to set
	 */
	public void setExamSession(ExamSessionIF examSession) {
		this.examSession = examSession;
	}	
}
