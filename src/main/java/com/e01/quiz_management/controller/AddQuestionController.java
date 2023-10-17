package com.e01.quiz_management.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class AddQuestionController {

        @FXML
        private TextArea question;
        @FXML
        private TextField option1;
        @FXML
        private TextField option2;
        @FXML
        private TextField option3;
        @FXML
        private TextField option4;
        @FXML
        private RadioButton option1radio;
        @FXML
        private RadioButton option2radio;
        @FXML
        private RadioButton option3radio;
        @FXML
        private RadioButton option4radio;
        @FXML
        private Button addNextQuestion;
        @FXML
        private Button submitQuiz;

    // them cau hoi
    @FXML
    public void addNextQuestion(ActionEvent event) throws IOException {
//        addQuestions();
        Parent root1 = FXMLLoader.load(getClass().getResource("AddQuestion.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root1);
        stage.setScene(scene);
        stage.show();
    }

    //submit
    @FXML
    private void submitQuiz(ActionEvent event) throws IOException {



        Parent root1 = FXMLLoader.load(getClass().getResource("QuizInfo.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root1);
        stage.setScene(scene);
        stage.show();
    }
}



