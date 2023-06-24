package entities;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a Lecturer entity.
 */
@SuppressWarnings("serial")
public class Lecturer extends User{
	private ArrayList<Integer> coursesId = new ArrayList<>();
	private HashMap<String,ArrayList<Integer>> coursesIdHM = new HashMap<>();
	private Integer departmentId;
	/**
	 * Constructs a Lecturer object with the specified parameters.
	 *
	 * @param userHM         the user details as a HashMap
	 * @param coursesIdHM    the courses IDs mapped by their names
	 * @param departmentId   the ID of the department
	 */
	public Lecturer(HashMap<String,Object> userHM, HashMap<String,ArrayList<Integer>> coursesIdHM,Integer departmentId) {
		super(userHM);
		this.coursesIdHM=coursesIdHM;
		this.departmentId=departmentId;
	}
	/**
	 * Returns the list of course IDs associated with the lecturer.
	 *
	 * @return the course IDs
	 */
	public ArrayList<Integer> getCoursesId() {
		return coursesId;
	}
	/**
	 * Sets the list of course IDs associated with the lecturer.
	 *
	 * @param coursesId the course IDs to set
	 */
	public void setCoursesId(ArrayList<Integer> coursesId) {
		this.coursesId = coursesId;
	}
	/**
	 * Returns the course IDs mapped by their names.
	 *
	 * @return the course IDs mapped by names
	 */
	public HashMap<String, ArrayList<Integer>> getCoursesIdHM() {
		return coursesIdHM;
	}
	/**
	 * Sets the course IDs mapped by their names.
	 *
	 * @param coursesIdHM the course IDs mapped by names to set
	 */
	public void setCoursesIdHM(HashMap<String, ArrayList<Integer>> coursesIdHM) {
		this.coursesIdHM = coursesIdHM;
	}
	/**
	 * Returns the ID of the department.
	 *
	 * @return the department ID
	 */
	public Integer getDepartmentId() {
		return departmentId;
	}
	/**
	 * Sets the ID of the department.
	 *
	 * @param departmentId the department ID to set
	 */
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	@Override
	public boolean equals(Object obj) {
		Lecturer lecturer1 = (Lecturer)obj;
		if(super.equals(lecturer1) && lecturer1.getDepartmentId() == this.departmentId && 
				lecturer1.getCoursesId().equals(this.coursesId)) {
			return true;
		}
	return false;
	}
}
