package com.e01.quiz_management.controller;

import com.e01.quiz_management.data.ShareAppData;
import com.e01.quiz_management.model.Test;
import com.e01.quiz_management.util.BaseResponse;
import com.e01.quiz_management.util.RequestAPI;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

import java.net.URL;
import java.util.ResourceBundle;

public class SubmitQuizController implements Initializable {
    @FXML
    private TextField quiztitle;

    @FXML
    private TextField quizlength;

    @FXML
    private TextField noquestion;

    @FXML
    public ToggleButton practice;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        quiztitle.setText(ShareAppData.getInstance().getTest().getTitle());
        quizlength.setText(String.valueOf(ShareAppData.getInstance().getTest().getDuration()) + " minutes");
        noquestion.setText(ShareAppData.getInstance().getTest().getQuestions().size() + "");
        if (practice.isSelected()) {
            practice.setText("Practice");
        } else {
            practice.setText("Test");
        }
    }

    @FXML
    public void completeAndSend() {
        try {
            Test quiz = ShareAppData.getInstance().getTest();
            if (practice.isSelected()) {
                quiz.setStartTime(null);
            }
            BaseResponse response1 = RequestAPI.getInstance().postCreateTest(quiz);
            Test test = RequestAPI.getInstance().getBaseResponseBodyObject(response1, Test.class);
            //popup dialog to show test id on button complete and send
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Test ID");
            alert.setHeaderText("Test ID");
            alert.setContentText("Your test Code is: " + test.getCode());
            alert.showAndWait();
        } catch (Exception e) {
            //popup notification if there is no test created
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("There is no test created");
            alert.showAndWait();
        }
    }

}
