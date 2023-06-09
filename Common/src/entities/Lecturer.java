package entities;

import java.util.ArrayList;
import java.util.HashMap;

public class Lecturer extends User{
	private ArrayList<Integer> coursesId = new ArrayList<>();
	
	public Lecturer(HashMap<String,Object> userHM, ArrayList<Integer> coursesId) {
		super(userHM);
		this.coursesId=coursesId;
	}

	public ArrayList<Integer> getCoursesId() {
		return coursesId;
	}

	public void setCoursesId(ArrayList<Integer> coursesId) {
		this.coursesId = coursesId;
	}
	
	


}
