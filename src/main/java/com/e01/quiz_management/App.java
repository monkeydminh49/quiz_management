package com.e01.quiz_management;

import com.e01.quiz_management.data.ShareAppData;
import com.e01.quiz_management.list_test.ListTestView;
import com.e01.quiz_management.model.Test;
import com.e01.quiz_management.util.RequestAPI;
import com.e01.quiz_management.util.Response;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
//        RequestAPI.getInstance().postLogin("admin@gmail.com", "123456");
//        Task<List<Test>> task = new Task<List<Test>>() {
//            @Override
//            protected List<Test> call() throws Exception {
//                return RequestAPI.getInstance().getAllUserTests();
//            }
//        };
//
//        task.setOnSucceeded(event -> {
//            Response<List<Test>> data = new Response.Success<List<Test>>(task.getValue());
//            ShareAppData.getInstance().setListTestResponse(data);
//            ListTestView.getInstance().updateTable();
//        });
//        new Thread(task).start();
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
        RequestAPI.getInstance().postLogin("admin@gmail.com", "123456");
        System.out.println(RequestAPI.getInstance().deleteTest(1L));
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