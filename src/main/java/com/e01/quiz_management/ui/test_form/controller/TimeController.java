package com.e01.quiz_management.ui.test_form.controller;

import com.e01.quiz_management.App;
import com.e01.quiz_management.model.Test;
import com.e01.quiz_management.model.TestHistory;
import com.e01.quiz_management.ui.test_form.SharedData;
import com.e01.quiz_management.ui.test_form.controller.QuestionController;
import com.e01.quiz_management.util.RequestAPI;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.time.LocalDateTime;

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
        TestHistory testHistory = sharedData.getTestHistory();
        Test test = sharedData.getTest();
        Integer score = questionController.getCal();
        if (testHistory == null) {
            testHistory = new TestHistory();
            testHistory.setTestId(test.getId());
            testHistory.setScore(score);
            testHistory.setSubmitTime(LocalDateTime.now());
            sharedData.setTestHistory(testHistory);
            RequestAPI.getInstance().postSubmitTestScore(testHistory.getTestId(), testHistory.getScore(), testHistory.getSubmitTime());
        } else {
            testHistory.setScore(score);
        }
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