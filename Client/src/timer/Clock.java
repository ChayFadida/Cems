package timer;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

/*
 * Observers CountDown instance
 * Takes care of displaying progress
 * through Label and ProgressBar
 */
public class Clock implements CountDownObserver {
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

    @Override
    public void update(int seconds) {
        Platform.runLater(() -> {
            lblHour.setText(hoursToString(seconds));
            lblMin.setText(minutesToString(seconds));
            lblSec.setText(secondsToString(seconds));
            updateProgressBar(seconds);
        });
    }

    @Override
    public void timeIsUp() {
        Platform.runLater(controller::timeIsUp);
    }

    private String secondsToString(int seconds) {
        seconds = seconds %3600 % 60; //remove hours and mins
        return String.format("%02d", seconds);
    }
    
    private String minutesToString(int seconds) {
        seconds = seconds % 3600; //remove hours
        int minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format("%02d", minutes);
    }
    
    private String hoursToString(int seconds) {
        int hours = seconds / 3600;
        return String.format("%02d", hours);
    }

    private void updateProgressBar(int seconds) {
        progressBar.setProgress((float) seconds / mode.getSeconds());
    }

    public void setMode(TimeMode mode) {
        this.mode = mode;
        lblSec.setText(secondsToString(mode.getSeconds()));
        lblMin.setText(secondsToString(mode.getMinutes()));
        lblHour.setText(secondsToString(mode.getHours()));
        progressBar.setProgress(1);
    }
}
