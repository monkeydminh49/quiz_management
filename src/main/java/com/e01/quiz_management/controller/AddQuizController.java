package com.e01.quiz_management.controller;

import com.e01.quiz_management.App;
import com.e01.quiz_management.model.Choice;
import com.e01.quiz_management.model.Question;
import com.e01.quiz_management.model.Test;
import com.e01.quiz_management.util.BaseResponse;
import com.e01.quiz_management.util.RequestAPI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddQuizController implements Initializable {
    @FXML
    public ToggleButton practice;
    @FXML
    public ToggleButton kiemtra;
    @FXML
    private DatePicker selectedDate;
    @FXML
    private ComboBox<Integer> selectedHour=new ComboBox<>();
    @FXML
    private ComboBox<Integer> selectedMin = new ComboBox<>();
    @FXML
    private TextField  QuizName;
    @FXML
    private TextField QuizLength;
    private static int indexOfQuestion=0;
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
    private final Question question =new Question();
    private final Choice choice1=new Choice();
    private final Choice choice2=new Choice();
    private final Choice choice3=new Choice();
    private final Choice choice4=new Choice();
    private  static final ArrayList<Question> listQuestion = new ArrayList<>();
    private final ArrayList<Choice> listAnswer = new ArrayList<>();
    private static final Test quiz = new Test();
    private LocalDateTime startedTime;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //set hour and minute for choice box
        for (int i = 0; i < 24; i++) {
            selectedHour.getItems().add(i);
        }
        for (int i = 0; i < 60; i++) {
            selectedMin.getItems().add(i);
        }
    }
    public void convertToTime(){
        Integer hours = selectedHour.getValue();
        Integer minutes = selectedMin.getValue();
        startedTime=LocalDateTime.of(selectedDate.getValue().getYear(),selectedDate.getValue().getMonth(),selectedDate.getValue().getDayOfMonth(), hours, minutes);
        quiz.setStartTime(startedTime);
    }
    public void change() {
        //change button color when click
        if (practice.isSelected()) {
            quiz.setStartTime(null);
        }
    }
    @FXML
    public void createQuiz(ActionEvent event) {
        try{
            convertToTime();
            quiz.setStartTime(startedTime);
            String quizName = String.valueOf(this.QuizName.getText());
            long duration = Long.parseLong(this.QuizLength.getText());
            quiz.setTitle(quizName);
            quiz.setDuration(duration);
            // move to scene add question
            Parent root0= FXMLLoader.load(Objects.requireNonNull(App.class.getResource("AddQuestion.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root0);
            stage.setScene(scene);
            stage.show();
        } catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Please enter quiz name and duration");
            alert.showAndWait();
        }
    }

    @FXML
    public void addNextQuestion(ActionEvent event) {
        try {
            save();
            Parent root1 = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("AddQuestion.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root1);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            //popup if user does not save question
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Please save question before add next question");
            alert.showAndWait();
        }
    }

    private void save() throws IOException, InterruptedException {
        if (!questionContent.getText().isEmpty() && !option1.getText().isEmpty() && !option2.getText().isEmpty() && !option3.getText().isEmpty() && !option4.getText().isEmpty()) {
            //save 1 time for 1 question
            question.setQuestion(questionContent.getText());
            choice1.setContent(option1.getText());
            choice2.setContent(option2.getText());
            choice3.setContent(option3.getText());
            choice4.setContent(option4.getText());
            if (answer1.isSelected()) {
                choice1.setCorrect(true);
                choice2.setCorrect(false);
                choice3.setCorrect(false);
                choice4.setCorrect(false);
            }
            if (answer2.isSelected()) {
                choice2.setCorrect(true);
                choice1.setCorrect(false);
                choice3.setCorrect(false);
                choice4.setCorrect(false);
            }
            if (answer3.isSelected()) {
                choice3.setCorrect(true);
                choice1.setCorrect(false);
                choice2.setCorrect(false);
                choice4.setCorrect(false);
            }
            if (answer4.isSelected()) {
                choice4.setCorrect(true);
                choice1.setCorrect(false);
                choice2.setCorrect(false);
                choice3.setCorrect(false);
            }
            listAnswer.add(choice1);
            listAnswer.add(choice2);
            listAnswer.add(choice3);
            listAnswer.add(choice4);
            question.setChoices(listAnswer);
            System.out.println(question.getChoices().get(0).getCorrect());
            listQuestion.add(question);
            quiz.setQuestions(listQuestion);
            indexOfQuestion++;
            //remove duplicate question
            for (int i = 0; i < quiz.getQuestions().size(); i++) {
                for (int j = i + 1; j < quiz.getQuestions().size(); j++) {
                    if (quiz.getQuestions().get(i).getQuestion().equals(quiz.getQuestions().get(j).getQuestion())
                            && quiz.getQuestions().get(i).getChoices().get(0).getContent().equals(quiz.getQuestions().get(j).getChoices().get(0).getContent())
                            && quiz.getQuestions().get(i).getChoices().get(1).getContent().equals(quiz.getQuestions().get(j).getChoices().get(1).getContent())
                            && quiz.getQuestions().get(i).getChoices().get(2).getContent().equals(quiz.getQuestions().get(j).getChoices().get(2).getContent())
                            && quiz.getQuestions().get(i).getChoices().get(3).getContent().equals(quiz.getQuestions().get(j).getChoices().get(3).getContent())){
                        quiz.getQuestions().remove(j);
                        j--;
                        indexOfQuestion--;
                    }
                }
            }
        }else{
            //popup notification if there is insufficient information
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Please enter question and answer");
            alert.showAndWait();
        }
    }

    @FXML
    private void submitQuiz(ActionEvent event) throws IOException, InterruptedException {
            save();
            Parent root2 = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("QuizInfo.fxml")));
            Stage stage1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene1 = new Scene(root2);
            stage1.setScene(scene1);
            stage1.show();
            TextField quiztitle = (TextField) scene1.lookup("#quiztitle");
            TextField quizlength = (TextField) scene1.lookup("#quizlength");
            TextField noquestion = (TextField) scene1.lookup("#noquestion");
            quiztitle.setText(quiz.getTitle());
            quizlength.setText(String.valueOf(quiz.getDuration()));
            noquestion.setText(String.valueOf(quiz.getQuestions().size()));
    }

    @FXML
    private void previous(ActionEvent event) throws IOException {
        if (indexOfQuestion > 0) {
            //move to previous screen using indexOfQuestion
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
            indexOfQuestion--;
            questionContent.setText(quiz.getQuestions().get(indexOfQuestion).getQuestion());
            option1.setText(quiz.getQuestions().get(indexOfQuestion).getChoices().get(0).getContent());
            option2.setText(quiz.getQuestions().get(indexOfQuestion).getChoices().get(1).getContent());
            option3.setText(quiz.getQuestions().get(indexOfQuestion).getChoices().get(2).getContent());
            option4.setText(quiz.getQuestions().get(indexOfQuestion).getChoices().get(3).getContent());
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
            else {
                //popup notification if there is no previous question
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("There is no previous question");
                alert.showAndWait();
            }

    }
    @FXML
    private void next(ActionEvent event) throws IOException {
        if(indexOfQuestion+1<=quiz.getQuestions().size()){
            Parent root1 = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("AddQuestion.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root1);
            stage.setScene(scene);
            stage.show();
            TextArea  questionContent = (TextArea ) scene.lookup("#questionContent");
            TextField option1 = (TextField) scene.lookup("#option1");
            TextField option2 = (TextField) scene.lookup("#option2");
            TextField option3 = (TextField) scene.lookup("#option3");
            TextField option4 = (TextField) scene.lookup("#option4");
            RadioButton answer1 = (RadioButton) scene.lookup("#answer1");
            RadioButton answer2 = (RadioButton) scene.lookup("#answer2");
            RadioButton answer3 = (RadioButton) scene.lookup("#answer3");
            RadioButton answer4 = (RadioButton) scene.lookup("#answer4");
            //move to next question using indexOfQuestion
            indexOfQuestion++;
            questionContent.setText(quiz.getQuestions().get(indexOfQuestion).getQuestion());
            option1.setText(quiz.getQuestions().get(indexOfQuestion).getChoices().get(0).getContent());
            option2.setText(quiz.getQuestions().get(indexOfQuestion).getChoices().get(1).getContent());
            option3.setText(quiz.getQuestions().get(indexOfQuestion).getChoices().get(2).getContent());
            option4.setText(quiz.getQuestions().get(indexOfQuestion).getChoices().get(3).getContent());
            //fill up radio button
            if(quiz.getQuestions().get(indexOfQuestion).getChoices().get(0).getCorrect()){
                answer1.setSelected(true);
            }
            if(quiz.getQuestions().get(indexOfQuestion).getChoices().get(1).getCorrect()){
                answer2.setSelected(true);
            }
            if(quiz.getQuestions().get(indexOfQuestion).getChoices().get(2).getCorrect()){
                answer3.setSelected(true);
            }
            if(quiz.getQuestions().get(indexOfQuestion).getChoices().get(3).getCorrect()){
                answer4.setSelected(true);
            }
        }
        else {
                try{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error");
                    alert.setContentText("There is no next question");
                    alert.showAndWait();
                }catch (Exception e){
                    System.out.println(e);
                }
        }
    }
    @FXML
    public void completeAndSend() {
        try {
            change();
            //send test to api
            BaseResponse response1 = RequestAPI.getInstance().postCreateTest(quiz);
            Test test = RequestAPI.getInstance().getBaseResponseBodyObject(response1, Test.class);
            //popup dialog to show test id on button complete and send
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Test ID");
            alert.setHeaderText("Test ID");
            alert.setContentText("Your test Code is: " + test.getCode());
            alert.showAndWait();
        } catch (Exception e) {
            //popup notification if there is no test created
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("There is no test created");
            alert.showAndWait();
        }
    }

}
