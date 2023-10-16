package com.e01.quiz_management;

import com.e01.quiz_management.model.Test;
import com.e01.quiz_management.util.RequestAPI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        RequestAPI.getInstance().postLogin("admin@gmail.com", "123456");
        RequestAPI.getInstance().getHello();
        Test newTest = new Test();

        newTest.setTitle("New test from client updated");
        RequestAPI.getInstance().postUpdateTestById(13L, newTest);
        Test test = RequestAPI.getInstance().getUserTestById(13L);
        System.out.println(test.getTitle());

        List<Test> tests = RequestAPI.getInstance().getAllUserTests();
        tests.forEach(t -> System.out.println(t.getTitle()));
        launch();
    }

}
