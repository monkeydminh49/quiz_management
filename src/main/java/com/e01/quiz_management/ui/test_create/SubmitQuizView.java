package com.e01.quiz_management.ui.test_create;

import com.e01.quiz_management.App;
import com.e01.quiz_management.data.ShareAppData;
import com.e01.quiz_management.model.Test;
import com.e01.quiz_management.util.BaseResponse;
import com.e01.quiz_management.util.RequestAPI;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class SubmitQuizView implements Initializable {

    @FXML
    private TextField quizName;

    @FXML
    private TextField quizDuration;

    @FXML
    private ToggleButton quizType;

    @FXML
    private TextField practice;

    @FXML
    private TextField contest;

    @FXML
    private Button saveBtn;

    @FXML
    private Spinner<Integer> quizHour;

    @FXML
    private Spinner<Integer> quizMinute;

    @FXML
    private DatePicker quizDate;

    @FXML
    private Button cancelBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        quizHour.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));
        quizMinute.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
        quizHour.setEditable(true);
        quizMinute.setEditable(true);
        quizDate.setValue(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        quizType.setSelected(true);
        practice.setVisible(true);
        contest.setVisible(false);
        practice.setEditable(false);
        contest.setEditable(false);
        quizHour.setVisible(false);
        quizMinute.setVisible(false);
        quizDate.setVisible(false);
        quizType.selectedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (t1) {
                practice.setVisible(true);
                contest.setVisible(false);
                quizHour.setVisible(false);
                quizMinute.setVisible(false);
                quizDate.setVisible(false);
            } else {
                practice.setVisible(false);
                contest.setVisible(true);
                quizHour.setVisible(true);
                quizMinute.setVisible(true);
                quizDate.setVisible(true);
            }
        });
        saveBtn.setOnAction(actionEvent -> {
                    if (quizName.getText().isEmpty() || quizDuration.getText().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Error");
                        alert.setHeaderText("Error");
                        alert.setContentText("Please fill in all the fields");
                        alert.showAndWait();
                        return;
                    }
                    Boolean isEdit = ShareAppData.getInstance().getIsEdit();
                    Test quiz = ShareAppData.getInstance().getTest();
                    quiz.setTitle(quizName.getText());
                    quiz.setDuration(Integer.parseInt(quizDuration.getText()));
                    quiz.setStartTime(quizDate.getValue().atTime(quizHour.getValue(), quizMinute.getValue()));
                    if (practice.isVisible()) {
                        quiz.setStartTime(null);
                    } else {
                        LocalDateTime now = LocalDateTime.now();
                        if (quiz.getStartTime().isBefore(now)) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Error");
                            alert.setHeaderText("Error");
                            alert.setContentText("Please choose a time in the future");
                            alert.showAndWait();
                            return;
                        }
                    }
                    if (isEdit) {
                        System.out.println(quiz);
                        RequestAPI.getInstance().putUpdateTestById(quiz.getId(), quiz);
                        try {
                            ShareAppData.getInstance().updateTest(quiz);
                        } catch (Exception e) {
                            //popup notification if there is no test created
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Error");
                            alert.setHeaderText("Error");
                            alert.setContentText("There is no test updated");
                            alert.showAndWait();
                        }
                    } else {
                        quiz.setQuestions(QuestionDataShared.getInstance().getQuestions());
                        BaseResponse response1 = RequestAPI.getInstance().postCreateTest(quiz);
                        try {
                            Test test = RequestAPI.getInstance().getBaseResponseBodyObject(response1, Test.class);
                            ShareAppData.getInstance().addTest(test);
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
                    try {
                        App.setRoot("menu");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ShareAppData.getInstance().clearTest();
                }
        );
        cancelBtn.setOnAction(actionEvent -> {
            try {
                App.setRoot("menu");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        cancelBtn.setOnMousePressed(mouseEvent -> onMousePressed(cancelBtn));
        cancelBtn.setOnMouseReleased(mouseEvent -> onMouseRelease(cancelBtn));
        saveBtn.setOnMousePressed(mouseEvent -> onMousePressed(saveBtn));
        saveBtn.setOnMouseReleased(mouseEvent -> onMouseRelease(saveBtn));
    }

    // For UI
    public void onMouseRelease(Button btn) {
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(btn);
        translateTransition.setDuration(Duration.millis(65));
        translateTransition.setByY(-5);
        translateTransition.play();
    }

    public void onMousePressed(Button btn) {
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(btn);
        translateTransition.setDuration(Duration.millis(65));
        translateTransition.setByY(5);
        translateTransition.play();
    }
}