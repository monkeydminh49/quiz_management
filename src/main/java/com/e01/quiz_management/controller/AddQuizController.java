package com.e01.quiz_management.controller;

import com.e01.quiz_management.App;
import com.e01.quiz_management.model.Test;
import com.e01.quiz_management.util.BaseResponse;
import com.e01.quiz_management.util.RequestAPI;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class AddQuizController {

    @FXML
    private TextField  QuizName;
    @FXML
    private TextField QuizLength;
    @FXML
    private Button createQuiz;
    private String quizName;
    private Long duration;
    private Test quiz;
    public void addQuizName() {

        quizName= String.valueOf(this.QuizName.getText());
    }
    public void addDuration() {
        duration =Long.parseLong(this.QuizLength.getText());
    }
    public AddQuizController() {
    }
    public void createQuiz(ActionEvent event) throws IOException {
        addDuration();
        addQuizName();
        quiz = new Test();
        quiz.setTitle(quizName);
        quiz.setDuration(duration);
        System.out.println("name of the quiz is : "+ quiz.getTitle());
        System.out.println("the length of the quiz is : " + quiz.getDuration());
        if (quizName != null && !quizName.trim().isEmpty() && duration != null) {
            Parent root1 = FXMLLoader.load(App.class.getResource("AddQuestion.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root1);
            stage.setScene(scene);
            stage.show();
        }
        sendRes();

    }

    public void sendRes(){
        BaseResponse response1 = RequestAPI.getInstance().postCreateTest(quiz);
        Test test = RequestAPI.getInstance().getBaseResponseBodyObject(response1, Test.class);
        System.out.println(test.getTitle());
        System.out.println(test.getDuration());

        System.out.println(response1.getMessage());
    }
}
