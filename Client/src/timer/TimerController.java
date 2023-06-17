package timer;

public class TimerController {
	public CountDown countdown;
    private Clock clock;
    private TimeMode timeMode;
    
    
    public void start(Clock clock,TimeMode timeMode) {
    	this.clock=clock;
    	this.timeMode=timeMode;
    	countdown = new CountDown(timeMode,clock);
    	activate();
    }
    
    public void toggleBtnClicked() {
        if (countdown.isRunning())
            stop();
        else
            activate();
    }

    private void stop() {
        countdown.stop();
    }

    private void activate() {
        if (countdown.isTimeUp())
            reset();
        start();
    }

    private void reset() {
        countdown.reset();
    }

    private void start() {
        countdown.start();
    }
    public void timeIsUp() {
    	//implement query for timeIdUp
    }
}
