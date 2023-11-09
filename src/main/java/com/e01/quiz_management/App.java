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
//        Question ques = new Question();
//        ques.setQuestion("Question 1");
//        ques.setType(EQuestionType.MULTIPLE_CHOICE);
//        List<Choice> choices = new ArrayList<>();
//        choices.add(new Choice("Choice 1", false));
//        choices.add(new Choice("Choice 2", false));
//        choices.add(new Choice("Choice 4", false));
//        choices.add(new Choice("Choice 5", true));
//        ques.setChoices(choices);
//        ques.setmAns(null);
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(new JavaTimeModule());
//        try {
//            String payload = mapper.writeValueAsString(ques);
//            System.out.println(payload);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//        RequestAPI.getInstance().postLogin("admin@gmail.com", "123456");
//        Test quiz = new Test();
//        quiz.setTitle("Test from client");
//        quiz.setDuration(60);
//        quiz.setUserId(1L);
//        quiz.setQuestions(new ArrayList<>());
//        quiz.setStartTime(null);
//        BaseResponse response1 = RequestAPI.getInstance().postCreateTest(quiz);
//        Test test = RequestAPI.getInstance().getBaseResponseBodyObject(response1, Test.class);
//        System.out.println(RequestAPI.getInstance().deleteTest(1L));
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