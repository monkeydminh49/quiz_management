package com.e01.quiz_management.ui.menu;

import com.e01.quiz_management.App;
import com.e01.quiz_management.data.ShareAppData;
import com.e01.quiz_management.model.Test;
import com.e01.quiz_management.ui.test_form.SharedData;
import com.e01.quiz_management.util.ETestStatus;
import com.e01.quiz_management.util.RequestAPI;
import com.e01.quiz_management.util.WebSocketConnect;
import com.e01.quiz_management.util.WebSocketConnectHandler;
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

import java.io.IOException;
import java.net.URL;
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

    public void onMousePressed(){
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(this.joinButton);
        translateTransition.setDuration(Duration.millis(65));
        translateTransition.setByY(5);
        translateTransition.play();
    }

    public void onMouseRelease(){
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(this.joinButton);
        translateTransition.setDuration(Duration.millis(65));
        translateTransition.setByY(-5);
        translateTransition.play();
    }

    public void getTest(){
        String testCode = testCodeTextField.getText();
        if (testCode.isBlank()){
            joinMessage.setText("Blank test code found");
            container.setStroke(Color.web("EC0B43"));
            warningIcon.setOpacity(1);
            joinMessage.setOpacity(1);
        }else{
            try {
                Test test = RequestAPI.getInstance().getTestByCode(testCode);
                ShareAppData.getInstance().setTest(test);
                SharedData.getInstance().setIsReview(false);
                if (test.getStartTime() == null) {
                    joinTest(test);
                }else{
                    ETestStatus testStatus = test.getStatus();
                    if (testStatus == ETestStatus.NOT_STARTED) {
                        joinMessage.setText("Test has not started yet");
                        warningIcon.setOpacity(1);
                        joinMessage.setOpacity(1);
                    } else if (testStatus == ETestStatus.ENDED) {
                        joinMessage.setText("Test has ended");
                        warningIcon.setOpacity(1);
                        joinMessage.setOpacity(1);
                    } else {
                       joinTest(test);
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

    private void joinTest(Test test) throws IOException {
        App.setRoot("layout_test_form");
        WebSocketConnect.getInstance().joinTest(test.getId());
    }
}
