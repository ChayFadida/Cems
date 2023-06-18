package timer;

public interface CountDownObserver {
    public void update(int seconds);
    public void timeIsUp();
    public void blockExam();
}
