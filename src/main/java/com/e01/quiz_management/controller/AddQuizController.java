package com.e01.quiz_management.controller;

import com.e01.quiz_management.App;
import com.e01.quiz_management.data.ShareAppData;
import com.e01.quiz_management.model.Choice;
import com.e01.quiz_management.model.Question;
import com.e01.quiz_management.model.Test;
import com.e01.quiz_management.util.BaseResponse;
import com.e01.quiz_management.util.EQuestionType;
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
    public ComboBox<String> typeOfQuestion =new ComboBox<>();
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
        //set value for typeOfQuestion choice box
        typeOfQuestion.getItems().add("Multiple choice");
        typeOfQuestion.getItems().add("Fill in the blank");
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
        if (typeOfQuestion != null) {
            typeOfQuestion.setOnAction(actionEvent -> {
                if (typeOfQuestion.getValue().equals("Multiple choice")) {
                    option3.setVisible(true);
                    option4.setVisible(true);
                    answer3.setVisible(true);
                    answer4.setVisible(true);
                    option2.setVisible(true);
                    answer2.setVisible(true);
                    answer1.setVisible(true);
                } else if (typeOfQuestion.getValue().equals("Fill in the blank")) {
                    option3.setVisible(false);
                    option4.setVisible(false);
                    answer3.setVisible(false);
                    answer4.setVisible(false);
                    option2.setVisible(false);
                    answer2.setVisible(false);
                    answer1.setVisible(false);
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
        //check if the question is multiple choice or fill in the blank
        if (typeOfQuestion.getValue().equals("Multiple choice")) {
            save();
        } else {
            saveFillInBlank();
        }

    }

    private void save() {
        if (!questionContent.getText().isEmpty() && !option1.getText().isEmpty() && !option2.getText().isEmpty() && !option3.getText().isEmpty() && !option4.getText().isEmpty()) {
            Question question = new Question();
            question.setType(EQuestionType.valueOf("MULTIPLE_CHOICE"));
            question.setQuestion(questionContent.getText());
            List<Choice> choices = List.of(
                    new Choice(option1.getText(), answer1.isSelected()),
                    new Choice(option2.getText(), answer2.isSelected()),
                    new Choice(option3.getText(), answer3.isSelected()),
                    new Choice(option4.getText(), answer4.isSelected())
            );
//            thêm câu hỏi vào quiz
            question.setChoices(choices);
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
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Please enter question and answer");
            alert.showAndWait();
        }
    }

    private void saveFillInBlank() {
        if (!questionContent.getText().isEmpty() && !option1.getText().isEmpty()) {
            Question question = new Question();
            question.setType(EQuestionType.valueOf("FILL_IN_BLANK"));
            question.setQuestion(questionContent.getText());
            List<Choice> choices = List.of(
                    new Choice(option1.getText(), true)
            );
//            thêm câu hỏi vào quiz
            question.setChoices(choices);
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
            answer1.setSelected(false);
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
        } else if (!questionContent.getText().isEmpty() && !option1.getText().isEmpty()) {
            saveFillInBlank();
            ShareAppData.getInstance().setTest(quiz);
            App.setRoot("QuizInfo");
        }
        else {
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
            if (quiz.getQuestions().get(indexOfQuestion).getType().equals(EQuestionType.valueOf("MULTIPLE_CHOICE"))) {
                getAndSetQuestion(questionContent, option1, option2, option3, option4, answer1, answer2, answer4, answer3);
            } else {
                //move to previous question using indexOfQuestion if previous question type is fill in the blank
                getAndSetQuestionOfFillInBlank(questionContent, option1);
            }
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
        answer1.setVisible(true);
        answer2.setVisible(true);
        answer3.setVisible(true);
        answer4.setVisible(true);
        option2.setVisible(true);
        option3.setVisible(true);
        option4.setVisible(true);
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

    private void getAndSetQuestionOfFillInBlank(TextArea questionContent, TextField option1) {
        //clear text field except questionContent and option1
        answer1.setVisible(false);
        answer2.setVisible(false);
        answer3.setVisible(false);
        answer4.setVisible(false);
        option2.setVisible(false);
        option3.setVisible(false);
        option4.setVisible(false);
        questionContent.setText(quiz.getQuestions().get(indexOfQuestion).getQuestion());
        option1.setText(quiz.getQuestions().get(indexOfQuestion).getChoices().get(0).getContent());
    }
    @FXML
    private void next(ActionEvent event) throws IOException {
        indexOfQuestion++;
        if (indexOfQuestion < quiz.getQuestions().size()) {
            if (quiz.getQuestions().get(indexOfQuestion).getType().equals(EQuestionType.valueOf("SINGLE_CHOICE"))) {
                getAndSetQuestion(questionContent, option1, option2, option3, option4, answer1, answer2, answer4, answer3);
            } else {
                //move to previous question using indexOfQuestion if previous question type is fill in the blank
                getAndSetQuestionOfFillInBlank(questionContent, option1);
            }
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
