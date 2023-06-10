package entities;

import java.util.HashMap;

public class Hod extends User{
	private String department;
	
	public Hod(HashMap<String,Object> userHM, String department) {
		super(userHM);
		this.department=department;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	
}

