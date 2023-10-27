package com.e01.quiz_management.test_form;

import com.e01.quiz_management.App;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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
            resultTextField.setText(sharedData.getScore() + "");
            Platform.runLater(() -> {
                exitButton.requestFocus();
            });

            exitButton.setOnAction(event -> {
                System.exit(0);
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
