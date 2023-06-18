package timer;

import java.util.*;

public final class CountDown{
    private CountDownObserver observer;
    private TimeMode mode;
    private Timer timer;
    private int secondsRemaining;
    private boolean isRunning;

    public CountDown(TimeMode mode, CountDownObserver observer) {
        this.observer = Objects.requireNonNull(observer);
        this.mode = mode;
        secondsRemaining = mode.getSeconds();
        isRunning = false;
    }

    public void start() {
        if (!isRunning && secondsRemaining != 0) {
            isRunning = true;
            timer = new Timer();
            startCountDown();
        }
    }

    private void startCountDown() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                secondsRemaining -= 1;
                observer.update(secondsRemaining);
                if (secondsRemaining == 0)
                    timeIsUp();
            }
        }, 1000, 1000);
    }

    private void timeIsUp() {
        isRunning = false;
        timer.cancel();
        observer.timeIsUp();
    }

    public void stop() {
        if (isRunning) {
            isRunning = false;
            timer.cancel();
        }
    }

    public void reset() {
        secondsRemaining = mode.getSeconds();
        observer.update(secondsRemaining);
    }

    public TimeMode getMode() {
        return mode;
    }

    public void setMode(TimeMode newMode) {
        stop();
        mode = newMode;
        reset();
    }

    public CountDownObserver getObserver() {
        return observer;
    }

    public void setObserver(CountDownObserver newObserver) {
        observer = newObserver;
    }

    public int getSecondsRemaining() {
        return secondsRemaining;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public boolean isTimeUp() {
        return secondsRemaining == 0;
    }

	public void extendExam(int newTime) {
		int currMins = getMode().getMinutes();
    	if((newTime-currMins+(secondsRemaining/60))>0) {
    		TimeMode remainingTime = new TimeMode(newTime-currMins + (secondsRemaining/60));
    		setMode(remainingTime);
    		start();
    	}
    	else
    		timeIsUp();
		
	}
}
