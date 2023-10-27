package com.e01.quiz_management.menu;

import com.e01.quiz_management.model.Test;
import com.e01.quiz_management.util.BaseResponse;
import com.e01.quiz_management.util.RequestAPI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class JoinTestView {
    @FXML
    private Button joinButton;
    @FXML
    private Label joinMessage;
    @FXML
    private TextField testCodeTextField;

    public void getTest(){
        String testCode = testCodeTextField.getText();
        if (testCode.isBlank()){
            joinMessage.setText("Blank test code found");
            joinMessage.setTextFill(Color.RED);
        }else{
            try {
                Test test = RequestAPI.getInstance().getTestByCode(testCode);
                if (test.getStartTime() == null){
                    joinMessage.setText("This is a practice test");
                    joinMessage.setStyle("-fx-text-fill: green");
                }else{
                    long current_millis = LocalDateTime.now().atZone(ZoneId.of("Asia/Ho_Chi_Minh")).toInstant().toEpochMilli();
                    long start_millis = test.getStartTime().atZone(ZoneId.of("Asia/Ho_Chi_Minh")).toInstant().toEpochMilli();
                    long duration = test.getDuration() * 60 * 1000;
                    if (current_millis < start_millis) {
                        joinMessage.setText("Test has not started yet");
                        joinMessage.setStyle("-fx-text-fill: red");
                    } else if (current_millis > start_millis + duration) {
                        joinMessage.setText("Test has ended");
                        joinMessage.setStyle("-fx-text-fill: red");
                    } else {
                        joinMessage.setText("Test is available");
                        joinMessage.setStyle("-fx-text-fill: green");
                    }
                }
            } catch (Exception e) {
                joinMessage.setText("Invalid test code");
                joinMessage.setStyle("-fx-text-fill: red");
            }
        }
    }
}
