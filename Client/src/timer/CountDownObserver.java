package timer;

public interface CountDownObserver {
    void update(int seconds);
    void timeIsUp();
}
