package entities;

import java.util.HashMap;

public class Student extends User{
	private String department;
	
	public Student(HashMap<String,Object> userHM,String department) {
		super(userHM);
		this.department = department;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}	

}
