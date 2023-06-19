package timer;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

/*
 * Observers CountDown instance
 * Takes care of displaying progress
 * through Label and ProgressBar
 */
public class Clock implements CountDownObserver, ExamSessionIF {
    private final TimerController controller;
    private final Label lblHour,lblMin,lblSec;
    private final ProgressBar progressBar;
    private TimeMode mode;

    public Clock(TimerController controller,
                         Label lblHour,
                         Label lblMin,
                         Label lblSec,
                         ProgressBar progressBar,
                         TimeMode mode) {
        this.controller = controller;
        this.lblHour = lblHour;
        this.lblMin = lblMin;
        this.lblSec = lblSec;
        this.progressBar = progressBar;
        setMode(mode);
    }
    
    /**
	 * method for updating the remaining time on the timer.
	 * @param int seconds.
	 */
    @Override
    public void update(int seconds) {
        Platform.runLater(() -> {
            lblHour.setText(hoursToString(seconds));
            lblMin.setText(minutesToString(seconds));
            lblSec.setText(secondsToString(seconds));
            updateProgressBar(seconds);
        });
    }
    
    /**
	 * method for finishing the timer that is running.
	 * @param non
	 */
    @Override
    public void timeIsUp() {
        Platform.runLater(controller::timeIsUp);
    }
    
    /**
	 * method for converting int seconds to propper string of seconds.
	 * @param   int seconds.
	 */
    private String secondsToString(int seconds) {
        seconds = seconds %3600 % 60; //remove hours and mins
        return String.format("%02d", seconds);
    }
    
    /**
	 * method for converting int seconds to propper string of minutes.
	 * @param   int seconds.
	 */
    private String minutesToString(int seconds) {
        seconds = seconds % 3600; //remove hours
        int minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format("%02d", minutes);
    }
    
    /**
	 * method for converting int seconds to propper string of hours.
	 * @param   int seconds.
	 */
    private String hoursToString(int seconds) {
        int hours = seconds / 3600;
        return String.format("%02d", hours);
    }
    
    /**
	 * method for updating the progressBar.
	 * @param   int seconds.
	 */
    private void updateProgressBar(int seconds) {
        progressBar.setProgress((float) seconds / mode.getSeconds());
    }
    
    /**
	 * method for setting up the mode and extracting data from it
	 * @param   TimeMode mode.
	 */
    public void setMode(TimeMode mode) {
        this.mode = mode;
        lblSec.setText(secondsToString(mode.getSeconds()));
        lblMin.setText(secondsToString(mode.getMinutes()));
        lblHour.setText(secondsToString(mode.getHours()));
        progressBar.setProgress(1);
    }
    
    /**
	 * method for getting current proggressBar.
	 * @param  non.
	 */
	public ProgressBar getProgressBar() {
		return progressBar;
	}
	
	/**
	 * method blocking a running timer for exam.
	 * @param  non.
	 */
	@Override
	public void blockExam() {
		Platform.runLater(controller::blockExam);
	}
	
	/**
	 * method for extending duration of current running exam.
	 * @param  int newTime.
	 */
	@Override
	public void extendExam(int newTime) {
		this.controller.countdown.extendExam(newTime);
	}
}
