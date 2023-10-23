package com.e01.quiz_management.controller;

import com.e01.quiz_management.App;
import com.e01.quiz_management.model.Choice;
import com.e01.quiz_management.model.Question;
import com.e01.quiz_management.model.Test;
import com.e01.quiz_management.util.BaseResponse;
import com.e01.quiz_management.util.RequestAPI;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Objects;

public class AddQuizController {

    @FXML
    private TextField  QuizName;
    @FXML
    private TextField QuizLength;
    @FXML
    private Button createQuiz;
    private String quizName;
    private Long duration;
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
    private Button addNextQuestion;
    @FXML
    private Button submitQuiz;
    private static Question question;
    private Choice choice1,choice2,choice3,choice4;
    private  static final ArrayList<Question> listQuestion = new ArrayList<>();
    private ArrayList<Choice> listAnswer = new ArrayList<>();
//    private  static final ArrayList<Choice> listAnswer = new ArrayList<>();
    private static final Test quiz = new Test();

    public void addQuizName() {
        quizName= String.valueOf(this.QuizName.getText());
    }
    public void addDuration() {
        duration =Long.parseLong(this.QuizLength.getText());
    }
    @FXML
    public void createQuiz(ActionEvent event) throws IOException {
        addQuizName();
        addDuration();
        quiz.setTitle(quizName);
        quiz.setDuration(duration);
        // move to scene add question
        Parent root0= FXMLLoader.load(Objects.requireNonNull(App.class.getResource("AddQuestion.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root0);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void addNextQuestion(ActionEvent event) throws IOException {
        // create question
        question = new Question();
        listAnswer= new ArrayList<>();
        question.setQuestion(questionContent.getText());
        // create answer
        choice1 = new Choice();
        choice2 = new Choice();
        choice3 = new Choice();
        choice4 = new Choice();
        choice1.setContent(option1.getText());
        choice2.setContent(option2.getText());
        choice3.setContent(option3.getText());
        choice4.setContent(option4.getText());
        if (answer1.isSelected()) {
            choice1.setCorrect(true);
//            choice2.setCorrect(false);
//            choice3.setCorrect(false);
//            choice4.setCorrect(false);

        }
        if (answer2.isSelected()) {
            choice2.setCorrect(true);
//            choice1.setCorrect(false);
//            choice3.setCorrect(false);
//            choice4.setCorrect(false);
        }
        if (answer3.isSelected()) {
            choice3.setCorrect(true);
//            choice1.setCorrect(false);
//            choice2.setCorrect(false);
//            choice4.setCorrect(false);
        }
        if (answer4.isSelected()) {
            choice4.setCorrect(true);
//            choice1.setCorrect(false);
//            choice2.setCorrect(false);
//            choice3.setCorrect(false);

        }
        // add answer to list answer
        listAnswer.add(choice1);
        listAnswer.add(choice2);
        listAnswer.add(choice3);
        listAnswer.add(choice4);
        // add list answer to question
        question.setChoices(listAnswer);
        // add question to list question
        listQuestion.add(question);
        // add list question to quiz
        quiz.setQuestions(listQuestion);
        // move to scene add question
        Parent root1= FXMLLoader.load(Objects.requireNonNull(App.class.getResource("AddQuestion.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root1);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void deleteAll(ActionEvent event){
        option1.clear();
        option2.clear();
        option3.clear();
        option4.clear();
        questionContent.clear();
        answer1.setSelected(false);
        answer2.setSelected(false);
        answer3.setSelected(false);
        answer4.setSelected(false);
    }

    @FXML
    private void submitQuiz(ActionEvent event) throws IOException {
        // add final question
        question = new Question();
        question.setQuestion(questionContent.getText());
        // create answer
        choice1 = new Choice();
        choice2 = new Choice();
        choice3 = new Choice();
        choice4 = new Choice();
        choice1.setContent(option1.getText());
        choice2.setContent(option2.getText());
        choice3.setContent(option3.getText());
        choice4.setContent(option4.getText());
        if (answer1.isSelected()) {
            choice1.setCorrect(true);
//            choice2.setCorrect(false);
//            choice3.setCorrect(false);
//            choice4.setCorrect(false);

        }
        if (answer2.isSelected()) {
            choice2.setCorrect(true);
//            choice1.setCorrect(false);
//            choice3.setCorrect(false);
//            choice4.setCorrect(false);
        }
        if (answer3.isSelected()) {
            choice3.setCorrect(true);
//            choice1.setCorrect(false);
//            choice2.setCorrect(false);
//            choice4.setCorrect(false);
        }
        if (answer4.isSelected()) {
            choice4.setCorrect(true);
//            choice1.setCorrect(false);
//            choice2.setCorrect(false);
//            choice3.setCorrect(false);

        }
        // add answer to list answer
        listAnswer.add(choice1);
        listAnswer.add(choice2);
        listAnswer.add(choice3);
        listAnswer.add(choice4);
        // add list answer to question
        question.setChoices(listAnswer);
        // add question to list question
        listQuestion.add(question);
        // add list question to quiz
        quiz.setQuestions(listQuestion);

        //remove duplicate question
        for (int i = 0; i < quiz.getQuestions().size(); i++) {
            for (int j = i + 1; j < quiz.getQuestions().size(); j++) {
                if (quiz.getQuestions().get(i).getQuestion().equals(quiz.getQuestions().get(j).getQuestion())) {
                    quiz.getQuestions().remove(j);
                    j--;
                }
            }
        }
        // move to scene quiz information
        Parent root2 = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("QuizInfo.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root2);
        stage.setScene(scene);
        stage.show();
        //display quiz information in scene quiz information
        TextField quiztitle = (TextField) scene.lookup("#quiztitle");
        TextField quizlength = (TextField) scene.lookup("#quizlength");
        TextField noquestion = (TextField) scene.lookup("#noquestion");
        quiztitle.setText(quiz.getTitle());
        quizlength.setText(String.valueOf(quiz.getDuration()));
        noquestion.setText(String.valueOf(quiz.getQuestions().size()));

    }

    @FXML
    private void backToprevious(ActionEvent event) throws IOException {
        // move to previous scene
        Parent root3= FXMLLoader.load(App.class.getResource("addQuestion.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root3);
        stage.setScene(scene);
        stage.show();
        // display question and answer in scene add question
        TextArea questionContent = (TextArea) scene.lookup("#questionContent");
        TextField option1 = (TextField) scene.lookup("#option1");
        TextField option2 = (TextField) scene.lookup("#option2");
        TextField option3 = (TextField) scene.lookup("#option3");
        TextField option4 = (TextField) scene.lookup("#option4");
        questionContent.setText(question.getQuestion());
        option1.setText(question.getChoices().get(0).getContent());
        option2.setText(question.getChoices().get(1).getContent());
        option3.setText(question.getChoices().get(2).getContent());
        option4.setText(question.getChoices().get(3).getContent());

    }

    @FXML
    public void completeAndSend(ActionEvent event) throws IOException{
        //send test to api
        BaseResponse response1 = RequestAPI.getInstance().postCreateTest(quiz);
        Test test = RequestAPI.getInstance().getBaseResponseBodyObject(response1, Test.class);
        //popup dialog to show test id on button complete and send
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Test ID");
        alert.setHeaderText("Test ID");
        alert.setContentText("Your test ID is: " + test.getId());
        alert.showAndWait();


        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(response1.getBody());
        System.out.println(json);
//        Test test = RequestAPI.getInstance().getBaseResponseBodyObject(response1, Test.class);
//        System.out.println(test.getTitle());
//        System.out.println(test.getDuration());
    }
}
