package entities;

import java.util.HashMap;

public class Hod extends User{
	private Integer departmentId;
	
	public Hod(HashMap<String,Object> userHM, Integer department) {
		super(userHM);
		this.departmentId=department;
	}

	public Integer getDepartment() {
		return departmentId;
	}

	public void setDepartment(Integer department) {
		this.departmentId = department;
	}
	
	
}

