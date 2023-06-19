package timer;
/*
 * represents a managing tool for given amount of time
 * and manages the way it returns as needed per usage
 */
public class TimeMode {

    private int seconds;
    /**
	 * Constructor for TimeMode.
	 * @param int minutes.
	 */
    public TimeMode(int minutes) {
        seconds = minutes * 60;
    }
    /**
	 * method for returning given time as seconds.
	 * @param non.
	 */
    public int getSeconds() {
        return seconds;
    }
    /**
	 * method for returning given time as minutes.
	 * @param non.
	 */
    public int getMinutes() {
        return seconds / 60;
    }
    /**
	 * method for returning given time as hours.
	 * @param non.
	 */
    public int getHours() {
        return seconds / 3600;
    }
    /**
	 * method for setting current time as given minutes.
	 * @param int minutes.
	 */
    public void setMinutes(int minutes) {
        seconds = minutes * 60;
    }
    /**
	 * method for setting current time as given hours.
	 * @param int minutes.
	 */
    public void setHours(int minutes) {
        seconds = minutes * 3600;
    }
}
