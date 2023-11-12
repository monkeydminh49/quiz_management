package com.e01.quiz_management.ui.test_form;

import com.e01.quiz_management.App;
import com.e01.quiz_management.data.ShareAppData;
import com.e01.quiz_management.model.*;
import com.e01.quiz_management.ui.test_form.controller.QuestionController;
import com.e01.quiz_management.ui.test_form.controller.TimeController;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestFormView {
    @FXML
    private TextField questionTextField;
    @FXML
    private RadioButton answer1RadioButton;
    @FXML
    private RadioButton answer2RadioButton;
    @FXML
    private RadioButton answer3RadioButton;
    @FXML
    private RadioButton answer4RadioButton;
    @FXML
    private TextField timeTextField;
    @FXML
    private Button submitButton;
    @FXML
    private Button nextButton;
    @FXML
    private Button previousButton;
    @FXML
    private Button checkButton;
    @FXML
    private Button xButton;
    @FXML
    private TextField ansTextField;
    @FXML
    private Button saveButton;
    @FXML
    private Rectangle rectangle;
    private int index = -1;

    public TestFormView() {
    }

    @FXML
    private void initialize() {
        xButton.setVisible(false);
        ansTextField.setText("");
        answer1RadioButton.setSelected(false);
        answer2RadioButton.setSelected(false);
        answer3RadioButton.setSelected(false);
        answer4RadioButton.setSelected(false);
        try {
            SharedData sharedData = SharedData.getInstance();
            if (sharedData.getIsReview()) {
                startReview();
            } else {
                startTest();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        submitButton.setOnMouseReleased(event -> onMouseRelease(submitButton));
        submitButton.setOnMousePressed(event -> onMousePressed(submitButton));
        checkButton.setOnMouseReleased(event -> onMouseRelease(checkButton));
        checkButton.setOnMousePressed(event -> onMousePressed(checkButton));
    }

    private void startTest() throws Exception {
        List<RadioButton> buttons = new ArrayList<>();
        buttons.add(answer1RadioButton);
        buttons.add(answer2RadioButton);
        buttons.add(answer3RadioButton);
        buttons.add(answer4RadioButton);
        timeTextField.setVisible(true);
        submitButton.setVisible(true);
        rectangle.setVisible(true);
        checkButton.setVisible(true);
        xButton.setVisible(false);
        Test test = ShareAppData.getInstance().getTest();
        System.out.println(test);
        SharedData sharedData = SharedData.getInstance();
        sharedData.setTest(test);
        QuestionController questionController = new QuestionController(test);
        TimeController timeController = new TimeController();
        if (test.getStartTime() == null) {
            timeTextField.setText("Practice Test");
        } else {
            long current_millis = LocalDateTime.now().atZone(java.time.ZoneId.of("Asia/Ho_Chi_Minh")).toInstant().toEpochMilli();
            long start_millis = test.getStartTime().atZone(java.time.ZoneId.of("Asia/Ho_Chi_Minh")).toInstant().toEpochMilli();
            long duration = test.getDuration() * 60 * 1000;
            long remaining = duration - (current_millis - start_millis);
            if (remaining > 0) {
                timeController.setTime((int) (remaining / 1000));
            } else {
                timeController.setTime(0);
            }
            timeController.startTest(timeTextField, questionController);
        }
        questionController.showQuestion(questionTextField, buttons, ansTextField, saveButton);
        previousButton.setDisable(questionController.getCurrentQuestionIndex() == 0);
        setOnClick(questionController, buttons, timeController, saveButton);
    }

    private void startReview() throws Exception {
        List<RadioButton> buttons = new ArrayList<>();
        buttons.add(answer1RadioButton);
        buttons.add(answer2RadioButton);
        buttons.add(answer3RadioButton);
        buttons.add(answer4RadioButton);
        timeTextField.setVisible(false);
        submitButton.setVisible(false);
        rectangle.setVisible(false);
        checkButton.setVisible(false);
        xButton.setVisible(true);
        SharedData sharedData = SharedData.getInstance();
        QuestionController questionController = new QuestionController(sharedData.getTest());
        saveButton.setVisible(false);
        questionController.showResult(questionTextField, buttons, ansTextField);
        previousButton.setDisable(questionController.getCurrentQuestionIndex() == 0);
        setOnClick(questionController, buttons, null, saveButton);
    }

    private void setOnClick(QuestionController questionController, List<RadioButton> buttons, TimeController timeController, Button saveButton) {
        checkButton.setOnAction(event -> {
            if (questionController.getNotAnsweredQuestionSize() > 0) {
                index = questionController.goToNextUnansweredQuestion(questionTextField, buttons, ansTextField, saveButton, index);
            }
            if (questionController.getNotAnsweredQuestionSize() == 0) {
                checkButton.setVisible(false);
            }
            previousButton.setDisable(questionController.getCurrentQuestionIndex() == 0);
            nextButton.setDisable(questionController.getCurrentQuestionIndex() == questionController.getQuestionSize() - 1);
        });

        nextButton.setOnAction(event -> {
            if (submitButton.isVisible()) {
                questionController.nextQuestion();
                actionS(questionController, questionTextField, buttons, saveButton);
            } else {
                questionController.nextQuestion();
                actionR(questionController, questionTextField, buttons);
            }
        });

        previousButton.setOnAction(event -> {
            if (submitButton.isVisible()) {
                questionController.previousQuestion();
                actionS(questionController, questionTextField, buttons, saveButton);
            } else {
                questionController.previousQuestion();
                actionR(questionController, questionTextField, buttons);
            }
        });
        answer1RadioButton.setOnAction(event -> selectAnswer(answer1RadioButton, questionController, buttons));
        answer2RadioButton.setOnAction(event -> selectAnswer(answer2RadioButton, questionController, buttons));
        answer3RadioButton.setOnAction(event -> selectAnswer(answer3RadioButton, questionController, buttons));
        answer4RadioButton.setOnAction(event -> selectAnswer(answer4RadioButton, questionController, buttons));
        ansTextField.setOnAction(event -> fillAnswer(questionController, buttons));
        saveButton.setOnAction(event -> fillAnswer(questionController, buttons));
        submitButton.setOnAction(event ->
                timeController.stopTest(questionController)
        );
        xButton.setOnAction(event -> {
            try {
                App.setRoot("layout_result");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void actionS(QuestionController questionController, TextField questionTextField, List<RadioButton> buttons, Button saveButton) {
        questionController.showQuestion(questionTextField, buttons, ansTextField, saveButton);
        previousButton.setDisable(questionController.getCurrentQuestionIndex() == 0);
        nextButton.setDisable(questionController.getCurrentQuestionIndex() == questionController.getQuestionSize() - 1);
    }

    private void actionR(QuestionController questionController, TextField questionTextField, List<RadioButton> buttons) {
        saveButton.setVisible(false);
        questionController.showResult(questionTextField, buttons, ansTextField);
        previousButton.setDisable(questionController.getCurrentQuestionIndex() == 0);
        nextButton.setDisable(questionController.getCurrentQuestionIndex() == questionController.getQuestionSize() - 1);
    }

    private void selectAnswer(RadioButton button, QuestionController questionController, List<RadioButton> buttons) {
        button.setSelected(true);
        for (RadioButton b : buttons) {
            if (b != button) {
                b.setSelected(false);
            }
        }
        questionController.updateNotAnsweredQuestions(questionController.getCurrentQuestionIndex());
        Question question = QuestionController.getCurrentQuestion();
        Choice mChoice = question.getChoices().get(buttons.indexOf(button));
        QuestionController.getCurrentQuestion().setmAns(mChoice);
    }

    private void fillAnswer(QuestionController questionController, List<RadioButton> buttons) {
        Question question = QuestionController.getCurrentQuestion();
        Choice answer = new Choice();
        answer.setContent(ansTextField.getText());
        question.setmAns(answer);
        questionController.updateNotAnsweredQuestions(questionController.getCurrentQuestionIndex());
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
