package timer;

import java.util.*;

public final class CountDown {
    private static CountDownObserver observer;
    private static TimeMode mode;
    private static Timer timer;
    private static int secondsRemaining;
    private static boolean isRunning;

    public CountDown(TimeMode mode, CountDownObserver observer) {
        CountDown.observer = Objects.requireNonNull(observer);
        CountDown.mode = mode;
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

    private static void timeIsUp() {
        isRunning = false;
        timer.cancel();
        observer.timeIsUp();
    }

    public static void stop() {
        if (isRunning) {
            isRunning = false;
            timer.cancel();
        }
    }

    public static void reset() {
        secondsRemaining = mode.getSeconds();
        observer.update(secondsRemaining);
    }

    public static TimeMode getMode() {
        return mode;
    }

    public static void setMode(TimeMode newMode) {
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
    
    public static void blockExam() {
        isRunning = false;
        timer.cancel();
        observer.blockExam();
    }
    
    public static void extendExam(TimeMode newTime) {
    	int currMins = getMode().getMinutes();
    	int newMins = newTime.getMinutes();
    	if(newMins-currMins>0) {
    		TimeMode remainingTime = new TimeMode(newMins-currMins);
    		setMode(remainingTime);
    	}
    	else
    		timeIsUp();
    }
}
