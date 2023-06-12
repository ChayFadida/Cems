package entities;

import java.util.HashMap;

public class Student extends User{
	private Integer department;
	
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

}
