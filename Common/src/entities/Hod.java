package entities;

import java.util.HashMap;

public class Hod extends User{
	private Integer department;
	
	public Hod(HashMap<String,Object> userHM, Integer department) {
		super(userHM);
		this.department=department;
	}

	public Integer getDepartment() {
		return department;
	}

	public void setDepartment(Integer department) {
		this.department = department;
	}
	
	
}

