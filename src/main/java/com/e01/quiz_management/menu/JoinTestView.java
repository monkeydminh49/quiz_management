package com.e01.quiz_management.menu;

import com.e01.quiz_management.App;
import com.e01.quiz_management.data.ShareAppData;
import com.e01.quiz_management.model.Test;
import com.e01.quiz_management.test_form.SharedData;
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
    @FXML
    private Button backButton;

    @FXML
    public void initialize(){
        joinButton.setOnAction(actionEvent -> {
            getTest();
        });
        backButton.setOnAction(actionEvent -> {
            try {
                App.setRoot("menu");
                SharedData.getInstance().setIsReview(false);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void getTest(){
        String testCode = testCodeTextField.getText();
        if (testCode.isBlank()){
            joinMessage.setText("Blank test code found");
            joinMessage.setTextFill(Color.RED);
        }else{
            try {
                Test test = RequestAPI.getInstance().getTestByCode(testCode);
                ShareAppData.getInstance().setTest(test);
                if (test.getStartTime() == null){
                    joinMessage.setText("This is a practice test");
                    joinMessage.setStyle("-fx-text-fill: green");
                    App.setRoot("layout_test_form");
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
                        App.setRoot("layout_test_form");
                    }
                }
            } catch (Exception e) {
                joinMessage.setText("Invalid test code");
                joinMessage.setStyle("-fx-text-fill: red");
            }
        }
    }
}
