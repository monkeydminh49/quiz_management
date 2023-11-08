package com.e01.quiz_management.controller;

import com.e01.quiz_management.App;
import com.e01.quiz_management.data.ShareAppData;
import com.e01.quiz_management.model.Choice;
import com.e01.quiz_management.model.FillQuestion;
import com.e01.quiz_management.model.MultipleChoice;
import com.e01.quiz_management.model.Question;
import com.e01.quiz_management.util.EQuestionType;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;

public class AddQuestionController {

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
    private Button backButton;

    @FXML
    private ComboBox<String> typeComboBox;
    @FXML
    private TextField ans1TextField;
    @FXML
    private TextField ans2TextField;
    @FXML
    private TextField ans3TextField;
    @FXML
    private TextField ans4TextField;

    @FXML
    private void initialize() {
        ObservableList<String> typeList = FXCollections.observableArrayList("Multiple Choice", "Fill in the Blank");
        typeComboBox.setItems(typeList);
        typeComboBox.setValue("Multiple Choice");
        setMultipleChoiceQuestion();
        backButton.setOnAction(actionEvent -> {
            try {
                ShareAppData.getInstance().setTest(null);
                App.setRoot("addQuiz");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        saveButton.setOnAction(event -> {
            String question = questionTextField.getText();
            if (typeComboBox.getValue().equals("Multiple Choice")) {
                if (questionTextField.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error");
                    alert.setContentText("Please fill the question");
                    alert.showAndWait();
                } else if (ans1TextField.getText().isEmpty() || ans2TextField.getText().isEmpty() || ans3TextField.getText().isEmpty() || ans4TextField.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error");
                    alert.setContentText("Please fill all the answers");
                    alert.showAndWait();
                } else if (!answer1RadioButton.isSelected() && !answer2RadioButton.isSelected() && !answer3RadioButton.isSelected() && !answer4RadioButton.isSelected()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error");
                    alert.setContentText("Please choose the correct answer");
                    alert.showAndWait();
                } else {
                    MultipleChoice multipleChoice = getMultipleChoice(question);
                    if (multipleChoice != null) {
                        try {
                            QuestionDataShared.getInstance().addMultipleChoiceQuestion(multipleChoice);
                            App.setRoot("addQuiz");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                if (questionTextField.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error");
                    alert.setContentText("Please fill the question");
                    alert.showAndWait();
                } else if (ans1TextField.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error");
                    alert.setContentText("Please fill the answer");
                    alert.showAndWait();
                } else {

                    FillQuestion fillQuestion = new FillQuestion();
                    fillQuestion.setQuestion(question);
                    fillQuestion.setChoices(List.of(new Choice(ans1TextField.getText(), true)));
                    try {
                        QuestionDataShared.getInstance().addFillQuestion(fillQuestion);
                        App.setRoot("addQuiz");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        typeComboBox.setOnAction(event -> {
            questionTextField.setText("");
            ans1TextField.setText("");
            ans2TextField.setText("");
            ans3TextField.setText("");
            ans4TextField.setText("");
            answer1RadioButton.setSelected(false);
            answer2RadioButton.setSelected(false);
            answer3RadioButton.setSelected(false);
            answer4RadioButton.setSelected(false);
            if (typeComboBox.getValue().equals("Multiple Choice")) {
                setMultipleChoiceQuestion();
            } else {
                setFillQuestion();
            }
        });
    }

    private MultipleChoice getMultipleChoice(String question) {
        MultipleChoice multipleChoice = new MultipleChoice();
        multipleChoice.setQuestion(question);
        multipleChoice.setChoices(List.of(
                new Choice(ans1TextField.getText(), answer1RadioButton.isSelected()),
                new Choice(ans2TextField.getText(), answer2RadioButton.isSelected()),
                new Choice(ans3TextField.getText(), answer3RadioButton.isSelected()),
                new Choice(ans4TextField.getText(), answer4RadioButton.isSelected())
        ));
        return multipleChoice;
    }

    private void setMultipleChoiceQuestion() {
        answer1RadioButton.setVisible(true);
        answer2RadioButton.setVisible(true);
        answer3RadioButton.setVisible(true);
        answer4RadioButton.setVisible(true);
        ans1TextField.setVisible(true);
        ans2TextField.setVisible(true);
        ans3TextField.setVisible(true);
        ans4TextField.setVisible(true);
    }

    private void setFillQuestion() {
        answer1RadioButton.setVisible(false);
        answer2RadioButton.setVisible(false);
        answer3RadioButton.setVisible(false);
        answer4RadioButton.setVisible(false);
        ans1TextField.setVisible(true);
        ans2TextField.setVisible(false);
        ans3TextField.setVisible(false);
        ans4TextField.setVisible(false);
    }

//    For UI
    public void back_onMousePressed(){
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(this.backButton);
        translateTransition.setDuration(Duration.millis(65));
        translateTransition.setByY(5);
        translateTransition.play();
    }

    public void back_onMouseRelease(){
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(this.backButton);
        translateTransition.setDuration(Duration.millis(65));
        translateTransition.setByY(-5);
        translateTransition.play();
    }

    public void save_onMousePressed(){
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(this.saveButton);
        translateTransition.setDuration(Duration.millis(65));
        translateTransition.setByY(5);
        translateTransition.play();
    }

    public void save_onMouseRelease(){
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(this.saveButton);
        translateTransition.setDuration(Duration.millis(65));
        translateTransition.setByY(-5);
        translateTransition.play();
    }
}
