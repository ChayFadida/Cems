package timer;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class TimerExampleController {
	//int currSeconds;

    @FXML
    private Button btnStart;

    @FXML
    private ImageView clockImg;

    @FXML
    private ProgressBar clockProgressBar;

    @FXML
    private Label lblHour;

    @FXML
    private Label lblHours;

    @FXML
    private Label lblMin;

    @FXML
    private Label lblSec;
    
    @FXML
    private Text secTimer;
    private CountDown countdown;
    private Clock clock;

//    @FXML
//    public void initialize() {
//    	TimeMode timeMode = new TimeMode(1);//set TimeMode to 120 min - need to take it from DB
//        clock = new Clock(
//                this, lblHour,lblMin,lblSec, clockProgressBar, timeMode); 
//        countdown = new CountDown(timeMode, clock);
//        //initializeButtonToMode();
//    }

    public void toggleBtnClicked() {
        if (countdown.isRunning())
            stop();
        else
            activate();
    }

    private void stop() {
        countdown.stop();
        updateToggleBtn("RESUME");
    }

    private void updateToggleBtn(String text) {
        btnStart.setText(text);
    }

    private void activate() {
        if (countdown.isTimeUp())
            reset();
        start();
    }

    private void reset() {
        removeTimeIsUpStyles();
        countdown.reset();
    }

    private void removeTimeIsUpStyles() {
        //container.getStyleClass().remove("time-is-up-background");
        btnStart.getStyleClass().remove("time-is-up-color");
    }

    private void start() {
        countdown.start();
        updateToggleBtn("STOP");
    }
    //irrelevant
    public void modeBtnClicked(ActionEvent event) {
        Button button = (Button) event.getSource();
        //TimeMode mode = buttonToMode.get(button);
        //changeMode(mode);
        highlightModeButton(button);
    }
    
    //irrelevant
    private void changeMode(TimeMode mode) {
        countdown.setMode(mode);
        clock.setMode(mode);
        removeTimeIsUpStyles();
        start();
    }
    
    //irrelevant
    private void highlightModeButton(Button button) {
        //removeModeButtonHighlighting();
        button.getStyleClass().add("highlight-btn");
    }

    private void removeModeButtonHighlighting() {
//        pomodoroBtn.getStyleClass().remove("highlight-btn");
//        shortBreakBtn.getStyleClass().remove("highlight-btn");
//        longBreakBtn.getStyleClass().remove("highlight-btn");
    }

    public void timeIsUp() {
        addTimeIsUpStyles();
        //playSound();
        updateToggleBtn("RESET");
    }

    private void addTimeIsUpStyles() {
        //container.getStyleClass().add("time-is-up-background");
        btnStart.getStyleClass().add("time-is-up-color");
    }

//    private void playSound() {
//        Media sound = new Media(this.getClass().getResource("sound.wav").toString());
//        MediaPlayer player = new MediaPlayer(sound);
//        player.setVolume(Settings.volume);
//        player.play();
//    }

    public void settingsIconClicked() throws IOException {
        stop();
        //imerExampleApp.setRoot("settings");
    }
    @FXML
    void getStartBtn(ActionEvent event) {
    	if (countdown.isRunning())
            stop();
        else
            activate();
    }
    
} 


