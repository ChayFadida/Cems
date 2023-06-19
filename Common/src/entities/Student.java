package entities;

import java.util.HashMap;

import timer.ExamSessionIF;

@SuppressWarnings("serial")
public class Student extends User{
	private Integer department;
	private ExamSessionIF examSession = null; 
	
	public Student(HashMap<String,Object> userHM,Integer department) {
		super(userHM);
		this.department = department;
	}

	public Integer getDepartment() {
		return department;
	}

	public void setDepartment(Integer department) {
		this.department = department;
	}

	public ExamSessionIF getExamSession() {
		return examSession;
	}

	public void setExamSession(ExamSessionIF examSession) {
		this.examSession = examSession;
	}	
	
	
}
