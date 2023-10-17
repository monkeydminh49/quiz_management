package com.e01.quiz_management.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class QuizInformationController {
    private Stage stage;
    private Scene scene;
    private Parent root1;
    public void backToPrevious(ActionEvent event) throws IOException {

        root1= FXMLLoader.load(getClass().getResource("AddQuestion.fxml"));
        stage =(Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root1);
        stage.setScene(scene);
        stage.show();
    }

    public void submitQuiz(ActionEvent actionEvent) {
    }
}
