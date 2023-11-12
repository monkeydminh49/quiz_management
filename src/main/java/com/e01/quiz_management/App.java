package com.e01.quiz_management;

import com.e01.quiz_management.model.*;
//import com.e01.quiz_management.util.EQuestionType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
//        RequestAPI.getInstance().postLogin("admin@gmail.com", "123456");
        scene = new Scene(loadFXML("login"));
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        Platform.runLater(() -> {
            try {
                scene.setRoot(loadFXML(fxml));
                Stage stage = (Stage) scene.getWindow();
                stage.sizeToScene();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void setRoot(String fxml, Object o) throws IOException {
        Platform.runLater(() -> {
            try {
                scene.setRoot(loadFXML(fxml, o));
                Stage stage = (Stage) scene.getWindow();
                stage.sizeToScene();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    private static Parent loadFXML(String fxml, Object o) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        fxmlLoader.setController(o);
        return fxmlLoader.load();
    }

    public static Scene getScene() {
        return scene;
    }

    public static void main(String[] args) {
        launch();
    }
}