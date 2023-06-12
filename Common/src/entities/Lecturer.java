package entities;

import java.util.ArrayList;
import java.util.HashMap;

public class Lecturer extends User{
	private ArrayList<Integer> coursesId = new ArrayList<>();
	private HashMap<String,Object> coursesIdHM = new HashMap<>();
	public Lecturer(HashMap<String,Object> userHM, HashMap<String,Object> coursesIdHM) {
		super(userHM);
		this.coursesIdHM=coursesIdHM;
	}

	public ArrayList<Integer> getCoursesId() {
		return coursesId;
	}

	public void setCoursesId(ArrayList<Integer> coursesId) {
		this.coursesId = coursesId;
	}

	public HashMap<String, Object> getCoursesIdHM() {
		return coursesIdHM;
	}

	public void setCoursesIdHM(HashMap<String, Object> coursesIdHM) {
		this.coursesIdHM = coursesIdHM;
	}
}
