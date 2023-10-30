package com.e01.quiz_management.controller;

import com.e01.quiz_management.App;
import com.e01.quiz_management.data.ShareAppData;
import com.e01.quiz_management.model.Test;
import com.e01.quiz_management.util.BaseResponse;
import com.e01.quiz_management.util.RequestAPI;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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

    @FXML
    public Button backToFirst;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        quiztitle.setText(ShareAppData.getInstance().getTest().getTitle());
        quizlength.setText(String.valueOf(ShareAppData.getInstance().getTest().getDuration()));
        noquestion.setText(String.valueOf(ShareAppData.getInstance().getTest().getQuestions().size()));
        practice.setText("Contest");
        practice.setOnAction(event -> {
            if (practice.isSelected()) {
                practice.setText("Practice");
                quizlength.setDisable(true);
            } else {
                practice.setText("Contest");
                quizlength.setDisable(false);
            }
        });

        backToFirst.setOnAction(actionEvent -> {
            try {
                App.setRoot("addQuiz");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    public void completeAndSend() {
        Boolean isEdit = ShareAppData.getInstance().getIsEdit();
        Test quiz = ShareAppData.getInstance().getTest();
        quiz.setTitle(quiztitle.getText());
        quiz.setDuration(Integer.parseInt(quizlength.getText()));
        if (practice.getText().equals("Practice")) {
            quiz.setStartTime(null);
        }
        if (isEdit) {
            try {
                RequestAPI.getInstance().putUpdateTestById(quiz.getId(), quiz);
            } catch (Exception e) {
                //popup notification if there is no test created
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("There is no test created");
                alert.showAndWait();
            }
        } else {
            try {
                BaseResponse response1 = RequestAPI.getInstance().postCreateTest(quiz);
                Test test = RequestAPI.getInstance().getBaseResponseBodyObject(response1, Test.class);
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
        ShareAppData.getInstance().clearTest();
        try {
            App.setRoot("layout_list_test");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
