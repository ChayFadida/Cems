package timer;

public class TimeMode {

    private int seconds;

    TimeMode(int minutes) {
        seconds = minutes * 60;
    }

    public int getSeconds() {
        return seconds;
    }

    public int getMinutes() {
        return seconds / 60;
    }
    
    public int getHours() {
        return seconds / 3600;
    }

    public void setMinutes(int minutes) {
        seconds = minutes * 60;
    }
    public void setHours(int minutes) {
        seconds = minutes * 3600;
    }
}
