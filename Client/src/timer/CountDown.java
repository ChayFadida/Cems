package timer;

import java.util.*;
/*
 * represents a countdown on a curtain amount of time 
 * and manages the given left time on real time.
 */
public final class CountDown{
    private CountDownObserver observer;
    private TimeMode mode;
    private Timer timer;
    private int secondsRemaining;
    private boolean isRunning;
    /**
	 * constructor for CountDown.
	 * @param TimeMode mode, CountDownObserver observer.
	 */
    public CountDown(TimeMode mode, CountDownObserver observer) {
        this.observer = Objects.requireNonNull(observer);
        this.mode = mode;
        secondsRemaining = mode.getSeconds();
        isRunning = false;
    }
    /**
	 * method for managing the starting of a CountDown.
	 * @param non.
	 */
    public void start() {
        if (!isRunning && secondsRemaining != 0) {
            isRunning = true;
            timer = new Timer();
            startCountDown();
        }
    }
    /**
	 * method for starting of a CountDown.
	 * @param non.
	 */
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
    /**
	 * method for executing the end of a CountDown
	 * when the time to count is up.
	 * @param non.
	 */
    private void timeIsUp() {
        isRunning = false;
        timer.cancel();
        observer.timeIsUp();
    }
    /**
	 * method for stopping current running CountDown
	 * when requested to stop.
	 * @param non.
	 */
    public void stop() {
        if (isRunning) {
            isRunning = false;
            timer.cancel();
        }
    }
    /**
	 * method for resseting a CountDown
	 * @param non.
	 */
    public void reset() {
        secondsRemaining = mode.getSeconds();
        observer.update(secondsRemaining);
    }
    /**
	 * method for returning current mode
	 * @param non.
	 */
    public TimeMode getMode() {
        return mode;
    }
    /**
	 * method for setting current mode
	 * @param TimeMode newMode.
	 */
    public void setMode(TimeMode newMode) {
        stop();
        mode = newMode;
        reset();
    }
    /**
	 * method for returning current CountDownObserver interface
	 * @param non.
	 */
    public CountDownObserver getObserver() {
        return observer;
    }
    /**
	 * method for setting current CountDownObserver interface
	 * @param CountDownObserver newObserver.
	 */
    public void setObserver(CountDownObserver newObserver) {
        observer = newObserver;
    }
    /**
	 * method for getting current remaining seconds
	 * @param non.
	 */
    public int getSecondsRemaining() {
        return secondsRemaining;
    }
    /**
	 * method for getting state "running" of countdown
	 * @param non.
	 */
    public boolean isRunning() {
        return isRunning;
    }
    /**
	 * method for setting current remaining time to 0.
	 * @param non.
	 */
    public boolean isTimeUp() {
        return secondsRemaining == 0;
    }
    /**
	 * method for extending current ramaining time with new time.
	 * @param int newTime.
	 */
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
