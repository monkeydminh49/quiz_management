package com.e01.quiz_management.menu;

import com.e01.quiz_management.App;
import com.e01.quiz_management.data.ShareAppData;
import com.e01.quiz_management.model.Test;
import com.e01.quiz_management.test_form.SharedData;
import com.e01.quiz_management.util.RequestAPI;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;
import java.util.ResourceBundle;

public class MenuView implements Initializable {
    @FXML
    Label greetingName;
    @FXML
    Button joinButton;
    @FXML
    private Label joinMessage;
    @FXML
    private TextField testCodeTextField;
    @FXML
    private Rectangle container;
    @FXML
    private ImageView warningIcon;

//    @FXML
//    private void initialize() {
//        greetingName.setText("Hello, " + ShareAppData.getInstance().getUser().getName());
//        joinButton.setOnAction(actionEvent -> {
//            getTest();
//        });
//    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        greetingName.setText("Hello, " + ShareAppData.getInstance().getUser().getName());
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
                    App.setRoot("layout_test_form");
                }else{
                    long current_millis = LocalDateTime.now().atZone(ZoneId.of("Asia/Ho_Chi_Minh")).toInstant().toEpochMilli();
                    long start_millis = test.getStartTime().atZone(ZoneId.of("Asia/Ho_Chi_Minh")).toInstant().toEpochMilli();
                    long duration = test.getDuration() * 60 * 1000;
                    if (current_millis < start_millis) {
                        joinMessage.setText("Test has not started yet");
                        joinMessage.setStyle("-fx-text-fill: red");
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
                joinMessage.setText("Invalid test code");
                warningIcon.setOpacity(1);
                joinMessage.setOpacity(1);
            }
        }
    }

    public void switchToJoinTest(ActionEvent e) throws IOException {
        try {
            App.setRoot("jointest");
        } catch (Exception ie) {
            System.out.println(ie);
        }
    }

    public void switchToHistory(ActionEvent e) throws IOException {
        try {
            App.setRoot("list_test");
        } catch (Exception ie) {
            System.out.println(ie);
        }
    }

    public void switchToManagement(ActionEvent e) throws IOException {
        try {
            App.setRoot("layout_list_test");
        } catch (Exception ie) {
            System.out.println(ie);
        }
    }

    // FOR UI only
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
}
