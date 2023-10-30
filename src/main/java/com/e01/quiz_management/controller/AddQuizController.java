package com.e01.quiz_management.controller;

import com.e01.quiz_management.App;
import com.e01.quiz_management.data.ShareAppData;
import com.e01.quiz_management.model.Choice;
import com.e01.quiz_management.model.Question;
import com.e01.quiz_management.model.Test;
import com.e01.quiz_management.util.BaseResponse;
import com.e01.quiz_management.util.RequestAPI;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

public class AddQuizController implements Initializable {
    @FXML
    private DatePicker selectedDate;
    @FXML
    private ComboBox<Integer> selectedHour = new ComboBox<>();
    @FXML
    private ComboBox<Integer> selectedMin = new ComboBox<>();
    @FXML
    private TextField QuizName;
    @FXML
    private TextField QuizLength;
    private static int indexOfQuestion = 0;
    @FXML
    private Label signupMessage;
    @FXML
    private TextArea questionContent;
    @FXML
    private TextField option1;
    @FXML
    private TextField option2;
    @FXML
    private TextField option3;
    @FXML
    private TextField option4;
    @FXML
    private RadioButton answer1;
    @FXML
    private RadioButton answer2;
    @FXML
    private RadioButton answer3;
    @FXML
    private RadioButton answer4;
    @FXML
    private Button importButton;
    private static Test quiz;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //set hour and minute for choice box
        for (int i = 0; i < 24; i++) {
            selectedHour.getItems().add(i);
        }
        for (int i = 0; i < 60; i++) {
            selectedMin.getItems().add(i);
        }

        if (importButton != null) {
            importButton.setOnAction(actionEvent -> {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");
                File file = fileChooser.showOpenDialog(importButton.getScene().getWindow());
                if (file != null) {
                    try {
                        List<Question> questions = UploadController.getInstance().createQuestionsFromFile(file);
                        quiz.addAllQuestion(questions);
                        indexOfQuestion = quiz.getQuestions().size();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setHeaderText("Success");
                        alert.setContentText("Import successfully");
                        alert.showAndWait();
                    } catch (Exception e) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Error");
                        alert.setHeaderText("Error");
                        alert.setContentText("Import failed");
                        alert.showAndWait();
                    }
                }
            });
        }
        quiz = ShareAppData.getInstance().getTest();
        if (quiz != null) {
            try {
                if (quiz.getStartTime() != null) {
                    selectedDate.setValue(quiz.getStartTime().toLocalDate());
                    selectedHour.setValue(quiz.getStartTime().getHour());
                    selectedMin.setValue(quiz.getStartTime().getMinute());
                }
                QuizName.setText(quiz.getTitle());
                QuizLength.setText(String.valueOf(quiz.getDuration()));
                indexOfQuestion = quiz.getQuestions().size();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void convertToTime() {
        Integer hours = selectedHour.getValue();
        Integer minutes = selectedMin.getValue();
        LocalDateTime startedTime = LocalDateTime.of(selectedDate.getValue().getYear(), selectedDate.getValue().getMonth(), selectedDate.getValue().getDayOfMonth(), hours, minutes);
        quiz.setStartTime(startedTime);
    }

    @FXML
    public void createQuiz(ActionEvent event) {
        convertToTime();
        String quizName = QuizName.getText();
        long duration = Long.parseLong(QuizLength.getText());
        quiz.setTitle(quizName);
        quiz.setDuration(duration);
        ShareAppData.getInstance().setTest(quiz);
        try {
            App.setRoot("addQuestion");
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Please enter quiz name and duration");
            alert.showAndWait();
        }
    }

    @FXML
    public void addNextQuestion(ActionEvent event) {
        indexOfQuestion = quiz.getQuestions().size();
        save();
    }

    private void save() {
        if (!questionContent.getText().isEmpty() && !option1.getText().isEmpty() && !option2.getText().isEmpty() && !option3.getText().isEmpty() && !option4.getText().isEmpty()) {
            Question question = new Question();
            question.setQuestion(questionContent.getText());
            List<Choice> choices = List.of(
                    new Choice(option1.getText(), answer1.isSelected()),
                    new Choice(option2.getText(), answer2.isSelected()),
                    new Choice(option3.getText(), answer3.isSelected()),
                    new Choice(option4.getText(), answer4.isSelected())
            );
//            thêm câu hỏi vào quiz
            question.setChoices();
            boolean isExist = false;
            for (Question q : quiz.getQuestions()) {
                if (q.getQuestion().equals(question.getQuestion())) {
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                quiz.addQuestion(question);
            }
//            reset ui
            indexOfQuestion++;
            questionContent.clear();
            option1.clear();
            option2.clear();
            option3.clear();
            option4.clear();
            answer1.setSelected(false);
            answer2.setSelected(false);
            answer3.setSelected(false);
            answer4.setSelected(false);
            System.out.println("current question: " + indexOfQuestion);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Please enter question and answer");
            alert.showAndWait();
        }
    }

    @FXML
    private void submitQuiz(ActionEvent event) throws IOException, InterruptedException {
        if (!questionContent.getText().isEmpty() &&
                !option1.getText().isEmpty() &&
                !option2.getText().isEmpty() &&
                !option3.getText().isEmpty() &&
                !option4.getText().isEmpty()) {
            save();
            ShareAppData.getInstance().setTest(quiz);
            App.setRoot("QuizInfo");
        } else {
            //popup notification if there is insufficient information
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Please enter question and answer");
            alert.showAndWait();
        }
    }


    @FXML
    private void previous(ActionEvent event) throws IOException {
        indexOfQuestion--;
        if (indexOfQuestion >= 0) {
            getAndSetQuestion(questionContent, option1, option2, option3, option4, answer1, answer2, answer4, answer3);
        } else {
            //popup notification if there is no previous question
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("There is no previous question");
            alert.showAndWait();
        }
        System.out.println("current question: " + indexOfQuestion);
    }

    private void getAndSetQuestion(TextArea questionContent, TextField option1, TextField option2, TextField
            option3, TextField option4, RadioButton answer1, RadioButton answer2, RadioButton answer4, RadioButton answer3) {
        questionContent.setText(quiz.getQuestions().get(indexOfQuestion).getQuestion());
        option1.setText(quiz.getQuestions().get(indexOfQuestion).getChoices().get(0).getContent());
        option2.setText(quiz.getQuestions().get(indexOfQuestion).getChoices().get(1).getContent());
        option3.setText(quiz.getQuestions().get(indexOfQuestion).getChoices().get(2).getContent());
        option4.setText(quiz.getQuestions().get(indexOfQuestion).getChoices().get(3).getContent());
        answer1.setSelected(false);
        answer2.setSelected(false);
        answer3.setSelected(false);
        answer4.setSelected(false);
        if (quiz.getQuestions().get(indexOfQuestion).getChoices().get(0).getCorrect()) {
            answer1.setSelected(true);
        }
        if (quiz.getQuestions().get(indexOfQuestion).getChoices().get(1).getCorrect()) {
            answer2.setSelected(true);
        }
        if (quiz.getQuestions().get(indexOfQuestion).getChoices().get(3).getCorrect()) {
            answer4.setSelected(true);
        }
        if (quiz.getQuestions().get(indexOfQuestion).getChoices().get(2).getCorrect()) {
            answer3.setSelected(true);
        }
    }

    @FXML
    private void next(ActionEvent event) throws IOException {
        indexOfQuestion++;
        if (indexOfQuestion < quiz.getQuestions().size()) {//move to next question using indexOfQuestion
            getAndSetQuestion(questionContent, option1, option2, option3, option4, answer1, answer2, answer4, answer3);
        } else {
            try {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("There is no next question");
                alert.showAndWait();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        System.out.println("current question: " + indexOfQuestion);
    }

    @FXML
    public void backToFirst(ActionEvent event) throws IOException {
        //back to 1st question
        indexOfQuestion = 0;
        Parent root1 = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("AddQuestion.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root1);
        stage.setScene(scene);
        stage.show();
        TextArea questionContent = (TextArea) scene.lookup("#questionContent");
        TextField option1 = (TextField) scene.lookup("#option1");
        TextField option2 = (TextField) scene.lookup("#option2");
        TextField option3 = (TextField) scene.lookup("#option3");
        TextField option4 = (TextField) scene.lookup("#option4");
        RadioButton answer1 = (RadioButton) scene.lookup("#answer1");
        RadioButton answer2 = (RadioButton) scene.lookup("#answer2");
        RadioButton answer3 = (RadioButton) scene.lookup("#answer3");
        RadioButton answer4 = (RadioButton) scene.lookup("#answer4");
        getAndSetQuestion(questionContent, option1, option2, option3, option4, answer1, answer2, answer4, answer3);
    }
}
