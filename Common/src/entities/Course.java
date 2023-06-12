package entities;

public class Course {
	private Integer courseId;
	private String courseName;
	private Integer departmentId;
	public Course(Integer courseId, String courseName, Integer departmentId) {
		this.courseId = courseId;
		this.courseName = courseName;
		this.departmentId=departmentId;
	}
	public Integer getCourseId() {
		return courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String toString() {
		return courseName;
		
	}
	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}
}
