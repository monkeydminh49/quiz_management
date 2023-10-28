package com.e01.quiz_management.test_form;

import com.e01.quiz_management.App;
import com.e01.quiz_management.util.RequestAPI;
import javafx.scene.control.TextField;

import java.io.IOException;

public class TimeController {
    private int time;

    public TimeController(int time) {
        this.time = time;
    }

    public TimeController() {
        this.time = 0;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void startTest(TextField timeTextField, QuestionController questionController) {
        Thread thread = new Thread(() -> {
            try {
                while (time > 0) {
                    timeTextField.setText(showTime());
                    time--;
                    Thread.sleep(1000);
                }
                stopTest(questionController);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    public void stopTest(QuestionController questionController) {
        this.time = 0;
        SharedData sharedData = SharedData.getInstance();
        Long score = sharedData.getScore();
        if (score == null) {
            RequestAPI requestAPI = RequestAPI.getInstance();
            try {
                requestAPI.postScore(questionController.getCal());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        sharedData.setScore(questionController.getCal());
        try {
            App.setRoot("layout_result");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String showTime() {
        int minutes = time / 60;
        int seconds = time % 60;
        String minutesString = minutes < 10 ? "0" + minutes : minutes + "";
        String secondsString = seconds < 10 ? "0" + seconds : seconds + "";
        return minutesString + ":" + secondsString;
    }

}