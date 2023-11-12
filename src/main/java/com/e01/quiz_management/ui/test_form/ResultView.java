package com.e01.quiz_management.ui.test_form;

import com.e01.quiz_management.App;
import com.e01.quiz_management.data.ShareAppData;
import com.e01.quiz_management.model.Test;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

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
    private Rectangle redoRectangle;

    @FXML
    private void initialize() {
        try {
            resultTextField.setText(" ");
            SharedData sharedData = SharedData.getInstance();
            Test test = ShareAppData.getInstance().getTest();
            if (test.getStartTime() != null) {
                redoButton.setDisable(true);
                redoRectangle.setVisible(false);
            }
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
                    test.resetTest();
                    sharedData.setTest(test);
                    App.setRoot("layout_test_form");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        exitButton.setOnMouseReleased(event -> onMouseRelease(exitButton));
        exitButton.setOnMousePressed(event -> onMousePressed(exitButton));
        reviewButton.setOnMouseReleased(event -> onMouseRelease(reviewButton));
        reviewButton.setOnMousePressed(event -> onMousePressed(reviewButton));
        redoButton.setOnMouseReleased(event -> onMouseRelease(redoButton));
        redoButton.setOnMousePressed(event -> onMousePressed(redoButton));
    }
    // For UI
    public void onMouseRelease(Button btn) {
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(btn);
        translateTransition.setDuration(Duration.millis(65));
        translateTransition.setByY(-5);
        translateTransition.play();
    }

    public void onMousePressed(Button btn) {
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(btn);
        translateTransition.setDuration(Duration.millis(65));
        translateTransition.setByY(5);
        translateTransition.play();
    }
}
