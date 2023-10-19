package com.e01.quiz_management.test_form;

import javafx.scene.control.TextField;

public class TimeController {
    private int time;

    public TimeController(int time) {
        this.time = time;
    }

    public void startTest(TextField timeTextField) {
        Thread thread = new Thread(() -> {
            try {
                while (time > 0) {
                    timeTextField.setText(showTime());
                    time--;
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    public void stopTest() {
        time = 0;
    }

    public String showTime() {
        int minutes = time / 60;
        int seconds = time % 60;
        String minutesString = minutes < 10 ? "0" + minutes : minutes + "";
        String secondsString = seconds < 10 ? "0" + seconds : seconds + "";
        return minutesString + ":" + secondsString;
    }
}
