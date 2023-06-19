package timer;

/**
 * Represents an ExamSessionIF interface that describes an exam functions.
 */

public interface ExamSessionIF {
	public void blockExam();
	public void extendExam(int newTime);
}