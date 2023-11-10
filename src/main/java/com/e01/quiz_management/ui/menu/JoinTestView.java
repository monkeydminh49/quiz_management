package com.e01.quiz_management.ui.menu;

import com.e01.quiz_management.App;
import com.e01.quiz_management.data.ShareAppData;
import com.e01.quiz_management.model.Test;
import com.e01.quiz_management.ui.test_form.SharedData;
import com.e01.quiz_management.util.RequestAPI;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ResourceBundle;

public class JoinTestView implements Initializable {
    @FXML
    private Button joinButton;
    @FXML
    private Label joinMessage;
    @FXML
    private TextField testCodeTextField;
    @FXML
    private ImageView warningIcon;
    @FXML
    private Rectangle container;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        warningIcon.setOpacity(0);
        joinMessage.setOpacity(0);
        joinButton.setDisable(false);
        // When this input field is typed
        testCodeTextField.setOnKeyTyped(keyEvent -> {
            warningIcon.setOpacity(0);
            joinMessage.setOpacity(0);
            container.setStroke(Color.web("000000"));
        });

        // When this button is clicked
        joinButton.setOnAction(actionEvent -> {
            getTest();
        });


    }

    public void onMousePressed() {
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(this.joinButton);
        translateTransition.setDuration(Duration.millis(65));
        translateTransition.setByY(5);
        translateTransition.play();
    }

    public void onMouseRelease() {
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(this.joinButton);
        translateTransition.setDuration(Duration.millis(65));
        translateTransition.setByY(-5);
        translateTransition.play();
        joinButton.getParent().setMouseTransparent(true);

        translateTransition.setOnFinished(actionEvent -> {
            joinButton.getParent().setMouseTransparent(false);
        });
    }

    public void getTest() {
        String testCode = testCodeTextField.getText();
        if (testCode.isBlank()) {
            joinMessage.setText("Blank test code found");
            container.setStroke(Color.web("EC0B43"));
            warningIcon.setOpacity(1);
            joinMessage.setOpacity(1);
        } else {
            try {
                Test test = RequestAPI.getInstance().getTestByCode(testCode);
                ShareAppData.getInstance().setTest(test);
                SharedData.getInstance().setIsReview(false);
                if (test.getStartTime() == null) {
                    App.setRoot("layout_test_form");
                } else {
                    long current_millis = LocalDateTime.now().atZone(ZoneId.of("Asia/Ho_Chi_Minh")).toInstant().toEpochMilli();
                    long start_millis = test.getStartTime().atZone(ZoneId.of("Asia/Ho_Chi_Minh")).toInstant().toEpochMilli();
                    long duration = test.getDuration() * 60 * 1000;
                    if (current_millis < start_millis) {
                        joinMessage.setText("Test has not started yet");
                        warningIcon.setOpacity(1);
                        joinMessage.setOpacity(1);
                    } else if (current_millis > start_millis + duration) {
                        joinMessage.setText("Test has ended");
                        warningIcon.setOpacity(1);
                        joinMessage.setOpacity(1);
                    } else {
                        App.setRoot("layout_test_form");
                    }
                    container.setStroke(Color.web("EC0B43"));
                }
            } catch (Exception e) {
                warningIcon.setOpacity(1);
                joinMessage.setOpacity(1);
                joinMessage.setText("Invalid test code");
            }
        }
    }
}
