package com.e01.quiz_management.Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.File;
import java.net.URI;



import static java.util.Objects.*;

public class QuizApplication extends Application{
    public static void main(String[] args ){
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root1 = FXMLLoader.load(getClass().getResource("/Style/taddQuestion.fxml"));
        Stage stage = new Stage();
        Scene scene =new Scene(root1);
        stage.setScene(scene);
        stage.show();
    }
}
