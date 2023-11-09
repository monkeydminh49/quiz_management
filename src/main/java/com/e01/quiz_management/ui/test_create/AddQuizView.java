package com.e01.quiz_management.ui.test_create;

import com.e01.quiz_management.App;
import com.e01.quiz_management.model.*;
import com.e01.quiz_management.ui.test_create.controller.UploadController;
import com.e01.quiz_management.util.EQuestionType;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class AddQuizView implements Initializable {

    @FXML
    private TextArea questionTextField;

    @FXML
    private RadioButton answer1RadioButton;

    @FXML
    private RadioButton answer2RadioButton;

    @FXML
    private RadioButton answer3RadioButton;

    @FXML
    private RadioButton answer4RadioButton;

    @FXML
    private Button saveButton;

    @FXML
    private TextField ansTextField;

    @FXML
    private Button addQuestionButton;

    @FXML
    private Button nextButton;

    @FXML
    private Button previousButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button importButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ansTextField.setVisible(false);
        answer1RadioButton.setVisible(false);
        answer2RadioButton.setVisible(false);
        answer3RadioButton.setVisible(false);
        answer4RadioButton.setVisible(false);
        questionTextField.setVisible(false);

        AtomicInteger currentQuestionIndex = new AtomicInteger();
        currentQuestionIndex.set(0);

        List<Question> questions = QuestionDataShared.getInstance().getQuestions();

        if (questions != null) {
            if (!questions.isEmpty()) {
                Question question = questions.get(currentQuestionIndex.get());
                setQuestion(question);
            }
        }

        ansTextField.setEditable(false);
        questionTextField.setEditable(false);
        answer1RadioButton.setDisable(true);
        answer2RadioButton.setDisable(true);
        answer3RadioButton.setDisable(true);
        answer4RadioButton.setDisable(true);

        addQuestionButton.setOnAction(event -> {
            try {
                App.setRoot("addQuestion");
                QuestionDataShared.getInstance().setIndex(null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        saveButton.setOnAction(event -> {
            try {
                if (QuestionDataShared.getInstance().getQuestions().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error");
                    alert.setContentText("Please add at least one question");
                    alert.showAndWait();
                    return;
                }
                App.setRoot("QuizInfo");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        nextButton.setOnAction(event -> {
            List<Question> finalQuestions = QuestionDataShared.getInstance().getQuestions();
            if (currentQuestionIndex.get() < finalQuestions.size() - 1) {
                currentQuestionIndex.getAndIncrement();
                Question question = finalQuestions.get(currentQuestionIndex.get());
                setQuestion(question);
            }
        });

        previousButton.setOnAction(event -> {
            List<Question> finalQuestions = QuestionDataShared.getInstance().getQuestions();
            if (currentQuestionIndex.get() > 0) {
                currentQuestionIndex.getAndDecrement();
                Question question = finalQuestions.get(currentQuestionIndex.get());
                setQuestion(question);
            }
        });

        editButton.setOnAction(event -> {
            try {
                App.setRoot("addQuestion");
                QuestionDataShared.getInstance().setIndex(currentQuestionIndex.get());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        deleteButton.setOnAction(event -> {
            List<Question> finalQuestions = QuestionDataShared.getInstance().getQuestions();
            finalQuestions.remove(currentQuestionIndex.get());
            if (currentQuestionIndex.get() > 0) {
                currentQuestionIndex.getAndDecrement();
            }
            if (!finalQuestions.isEmpty()) {
                Question question = finalQuestions.get(currentQuestionIndex.get());
                setQuestion(question);
            } else {
                ansTextField.setVisible(false);
                answer1RadioButton.setVisible(false);
                answer2RadioButton.setVisible(false);
                answer3RadioButton.setVisible(false);
                answer4RadioButton.setVisible(false);
                questionTextField.setVisible(false);
            }
        });

        importButton.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            File file = fileChooser.showOpenDialog(importButton.getScene().getWindow());
            if (file != null) {
                try {
                    List<MultipleChoice> questionsFromFile = UploadController.getInstance().createQuestionsFromFile(file);
                    QuestionDataShared.getInstance().addQuestion(List.copyOf(questionsFromFile));
                    Question question = QuestionDataShared.getInstance().getQuestions().get(0);
                    setQuestion(question);
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error");
                    alert.setContentText("Import failed");
                    alert.showAndWait();
                }
            }
        });
        addQuestionButton.setOnMousePressed(mouseEvent -> onMousePressed(addQuestionButton));
        addQuestionButton.setOnMouseReleased(mouseEvent -> onMouseRelease(addQuestionButton));
        saveButton.setOnMousePressed(mouseEvent -> onMousePressed(saveButton));
        saveButton.setOnMouseReleased(mouseEvent -> onMouseRelease(saveButton));
        editButton.setOnMousePressed(mouseEvent -> onMousePressed(editButton));
        editButton.setOnMouseReleased(mouseEvent -> onMouseRelease(editButton));
        deleteButton.setOnMousePressed(mouseEvent -> onMousePressed(deleteButton));
        deleteButton.setOnMouseReleased(mouseEvent -> onMouseRelease(deleteButton));
        importButton.setOnMousePressed(mouseEvent -> onMousePressed(importButton));
        importButton.setOnMouseReleased(mouseEvent -> onMouseRelease(importButton));

    }

    private void setQuestion(Question question) {
        questionTextField.setVisible(true);
        questionTextField.setText(question.getQuestion());
        ansTextField.setVisible(false);
        answer1RadioButton.setVisible(false);
        answer2RadioButton.setVisible(false);
        answer3RadioButton.setVisible(false);
        answer4RadioButton.setVisible(false);
        if (question.getType().equals(EQuestionType.MULTIPLE_CHOICE)) {
            ansTextField.setVisible(false);
            for (int i = 0; i < question.getChoices().size(); i++) {
                Choice choice = question.getChoices().get(i);
                if (i == 0) {
                    answer1RadioButton.setVisible(true);
                    answer1RadioButton.setText(choice.getContent());
                    answer1RadioButton.setSelected(choice.getCorrect());
                } else if (i == 1) {
                    answer2RadioButton.setVisible(true);
                    answer2RadioButton.setText(choice.getContent());
                    answer2RadioButton.setSelected(choice.getCorrect());
                } else if (i == 2) {
                    answer3RadioButton.setVisible(true);
                    answer3RadioButton.setText(choice.getContent());
                    answer3RadioButton.setSelected(choice.getCorrect());
                } else if (i == 3) {
                    answer4RadioButton.setVisible(true);
                    answer4RadioButton.setText(choice.getContent());
                    answer4RadioButton.setSelected(choice.getCorrect());
                }
            }
        } else {
            ansTextField.setVisible(true);
            try {
                ansTextField.setText(question.getChoices().get(0).getContent());
            } catch (Exception e) {
                ansTextField.setText("");
            }
        }
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