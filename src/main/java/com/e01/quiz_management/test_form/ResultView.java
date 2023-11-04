package com.e01.quiz_management.test_form;

import com.e01.quiz_management.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ResultView {

    @FXML
    TextField resultTextField;
    @FXML
    Button exitButton;
    @FXML
    Button reviewButton;
    @FXML
    Button redoButton;

    @FXML
    private void initialize() {
        try {
            resultTextField.setText(" ");
            SharedData sharedData = SharedData.getInstance();
            resultTextField.setText(String.valueOf(sharedData.getTestHistory().getScore()));
            exitButton.requestFocus();

            exitButton.setOnAction(event -> {
                try {
                    App.setRoot("menu");
                    sharedData.clearTestHistory();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            reviewButton.setOnAction(event -> {
                try {
                    sharedData.setIsReview(true);
                    App.setRoot("layout_test_form");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            redoButton.setOnAction(event -> {
                try {
                    sharedData.setIsReview(false);
                    App.setRoot("layout_test_form");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
