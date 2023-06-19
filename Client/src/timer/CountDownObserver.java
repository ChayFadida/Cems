package timer;
/*
 *an interface thatll be implemented by a clock 
 */
public interface CountDownObserver {
	/**
	 * method for updating the remaining time on the timer.
	 * @param int seconds.
	 */
    public void update(int seconds);
    /**
	 * method for finishing the timer that is running.
	 * @param non
	 */
    public void timeIsUp();
}
