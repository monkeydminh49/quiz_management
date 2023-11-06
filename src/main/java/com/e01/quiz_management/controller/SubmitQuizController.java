package com.e01.quiz_management.controller;

import com.e01.quiz_management.App;
import com.e01.quiz_management.data.ShareAppData;
import com.e01.quiz_management.list_test.ListTestView;
import com.e01.quiz_management.model.Test;
import com.e01.quiz_management.util.BaseResponse;
import com.e01.quiz_management.util.RequestAPI;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class SubmitQuizController implements Initializable {

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        quizHour.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));
        quizMinute.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
        quizDate.setValue(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        quizType.setSelected(true);
        practice.setVisible(true);
        contest.setVisible(false);
        practice.setEditable(false);
        contest.setEditable(false);
        quizType.selectedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (t1) {
                practice.setVisible(true);
                contest.setVisible(false);
            } else {
                practice.setVisible(false);
                contest.setVisible(true);
            }
        });
        saveBtn.setOnAction(actionEvent -> {
                    Boolean isEdit = ShareAppData.getInstance().getIsEdit();
                    Test quiz = ShareAppData.getInstance().getTest();
                    quiz.setTitle(quizName.getText());
                    quiz.setDuration(Integer.parseInt(quizDuration.getText()));
                    quiz.setStartTime(quizDate.getValue().atTime(quizHour.getValue(), quizMinute.getValue()));
                    if (quizType.isSelected()) {
                        quiz.setStartTime(null);
                    }
                    if (isEdit) {
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
                        BaseResponse response1 = RequestAPI.getInstance().postCreateTest(quiz);
                        try {
                            Test test = RequestAPI.getInstance().getBaseResponseBodyObject(response1, Test.class);
                            String payload = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(test);
                            System.out.println(payload);
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
                    ShareAppData.getInstance().clearTest();
                    try {
                        App.setRoot("layout_list_test", ListTestView.getInstance());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );
    }
}
