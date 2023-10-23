package com.e01.quiz_management;

import com.e01.quiz_management.util.RequestAPI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login"), 640, 480);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        RequestAPI.getInstance().postLogin("admin@gmail.com", "123456");
//        RequestAPI.getInstance().getHello();
//        Test newTest = new Test();
//
//        newTest.setTitle("New test from client updated");
//        // Update test request
//        BaseResponse response =  RequestAPI.getInstance().postUpdateTestById(13L, newTest);
//        System.out.println(response.getMessage());
//
//        // Mapping from response.getBody() to specific object
//        Test test = RequestAPI.getInstance().getBaseResponseBodyObject(response, Test.class);
//        System.out.println(test.getTitle());
//
//        // Get user's test by id request
//        Test test2 = RequestAPI.getInstance().getUserTestById(13L);
//        System.out.println(test2.getTitle());
//
//        // Get all user's tests request
//        List<Test> tests = RequestAPI.getInstance().getAllUserTests();
//        tests.forEach(t -> System.out.println(t.getTitle()));
//
        launch();
    }

}
