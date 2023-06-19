package entities;
/**
 * Represents a Course entity.
 */
public class Course {
	private Integer courseId;
	private String courseName;
	private Integer departmentId;
	/**
	 * Constructs a Course object with the specified courseId, courseName, and departmentId.
	 * 
	 * @param courseId      the ID of the course
	 * @param courseName    the name of the course
	 * @param departmentId  the ID of the department
	 */
	public Course(Integer courseId, String courseName, Integer departmentId) {
		this.courseId = courseId;
		this.courseName = courseName;
		this.departmentId=departmentId;

	}
	/**
	 * Retrieves the ID of the course.
	 * 
	 * @return the courseI d
	 */
	public Integer getCourseId() {
		return courseId;
	}
	/**
	 * Retrieves the name of the course.
	 * 
	 * @return the course Name
	 */
	public String getCourseName() {
		return courseName;
	}
	/**
	 * Sets the name of the course.
	 * 
	 * @param courseName the new name of the course
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	/**
	 * Retrieves a string representation of the course.
	 * 
	 * @return the course Name
	 */
	public String toString() {
		return courseName;
		
	}
	/**
	 * Retrieves the ID of the department.
	 * 
	 * @return the department Id
	 */
	public Integer getDepartmentId() {
		return departmentId;
	}
	/**
	 * Sets the ID of the department.
	 * 
	 * @param departmentId the new ID of the department
	 */
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	/**
	 * Sets the ID of the course.
	 * 
	 * @param courseId the new ID of the course
	 */
	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}
}
