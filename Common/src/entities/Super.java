package entities;

import java.util.ArrayList;
import java.util.HashMap;

public class Super extends User{
	private Lecturer lecturer;
	private Hod hod;
	private HashMap<String,ArrayList<Integer>> coursesIdHM = new HashMap<>();
	public Super(HashMap<String, Object> userHM,HashMap<String,ArrayList<Integer>> coursesIdHM,Integer department) {
		super(userHM);
		lecturer = new Lecturer(userHM,coursesIdHM,department);
		hod = new Hod(userHM,department);
	}

	public Lecturer getLecturer() {
		return lecturer;
	}

	public void setLecturer(Lecturer lecturer) {
		this.lecturer = lecturer;
	}

	public Hod getHod() {
		return hod;
	}

	public void setHod(Hod hod) {
		this.hod = hod;
	}
	
	
}
