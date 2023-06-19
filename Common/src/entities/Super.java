package entities;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a Super entity.
 */

public class Super extends User{
	private Lecturer lecturer;
	private Hod hod;
	private HashMap<String,ArrayList<Integer>> coursesIdHM = new HashMap<>();
	/**
	 * Constructs a Super User object with the specified user details, courses ID HashMap, and department.
	 *
	 * @param userHM        the HashMap containing user details
	 * @param coursesIdHM   the HashMap containing courses IDs
	 * @param department    the department of the user
	 */
	public Super(HashMap<String, Object> userHM, HashMap<String, ArrayList<Integer>> coursesIdHM, Integer department) {
	    super(userHM);
	    lecturer = new Lecturer(userHM, coursesIdHM, department);
	    hod = new Hod(userHM, department);
	}

	/**
	 * Returns the Lecturer associated with the Super User.
	 *
	 * @return the Lecturer object
	 */
	public Lecturer getLecturer() {
	    return lecturer;
	}

	/**
	 * Sets the Lecturer associated with the Super User.
	 *
	 * @param lecturer the Lecturer to set
	 */
	public void setLecturer(Lecturer lecturer) {
	    this.lecturer = lecturer;
	}

	/**
	 * Returns the Hod associated with the Super User.
	 *
	 * @return the Hod object
	 */
	public Hod getHod() {
	    return hod;
	}

	/**
	 * Sets the Hod associated with the Super User.
	 *
	 * @param hod the Hod to set
	 */
	public void setHod(Hod hod) {
	    this.hod = hod;
	}
}