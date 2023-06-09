package entities;

import java.util.HashMap;

/**
 * Represents a Hod entity.
 */
@SuppressWarnings("serial")
public class Hod extends User{
	private Integer departmentId;
	/**
	 * Constructs a Hod object with the specified parameters.
	 *
	 * @param userHM      the user details as a HashMap
	 * @param department  the ID of the department
	 */
	public Hod(HashMap<String,Object> userHM, Integer department) {
		super(userHM);
		this.departmentId=department;
	}
	/**
	 * Returns the ID of the department.
	 *
	 * @return the department ID
	 */
	public Integer getDepartment() {
		return departmentId;
	}
	/**
	 * Sets the ID of the department.
	 *
	 * @param department the department ID to set
	 */
	public void setDepartment(Integer department) {
		this.departmentId = department;
	}
	
	@Override
	public boolean equals(Object obj) {
		Hod hod1 = (Hod)obj;
		if(super.equals(hod1) && hod1.getDepartment().equals(this.departmentId) ) {
			return true;
		}
	return false;
	}
}

