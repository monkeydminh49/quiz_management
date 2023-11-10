package com.e01.quiz_management.ui.test_create;

import com.e01.quiz_management.App;
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

public class AddQuestionView {

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
    private Button cancelButton;

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

    private boolean isEdit = false;

    @FXML
    private void initialize() {
        ToggleGroup group = new ToggleGroup();
        answer1RadioButton.setToggleGroup(group);
        answer2RadioButton.setToggleGroup(group);
        answer3RadioButton.setToggleGroup(group);
        answer4RadioButton.setToggleGroup(group);

        ObservableList<String> typeList = FXCollections.observableArrayList(EQuestionType.MULTIPLE_CHOICE.toString(), EQuestionType.FILL_IN_BLANK.toString());
        typeComboBox.setItems(typeList);
        typeComboBox.setValue(EQuestionType.MULTIPLE_CHOICE.toString());
        setMultipleChoiceQuestion();

        Integer index = QuestionDataShared.getInstance().getIndex();
        if (index != null) {
            Question question = QuestionDataShared.getInstance().getQuestions().get(index);
            questionTextField.setText(question.getQuestion());
            if (question.getType().equals(EQuestionType.MULTIPLE_CHOICE)) {
                setMultipleChoiceQuestion();
                MultipleChoice multipleChoice = new MultipleChoice(question);
                ans1TextField.setText(multipleChoice.getChoices().get(0).getContent());
                ans2TextField.setText(multipleChoice.getChoices().get(1).getContent());
                ans3TextField.setText(multipleChoice.getChoices().get(2).getContent());
                ans4TextField.setText(multipleChoice.getChoices().get(3).getContent());
                answer1RadioButton.setSelected(multipleChoice.getChoices().get(0).getCorrect());
                answer2RadioButton.setSelected(multipleChoice.getChoices().get(1).getCorrect());
                answer3RadioButton.setSelected(multipleChoice.getChoices().get(2).getCorrect());
                answer4RadioButton.setSelected(multipleChoice.getChoices().get(3).getCorrect());
            } else {
                setFillQuestion();
                FillQuestion fillQuestion = new FillQuestion(question);
                ans1TextField.setText(fillQuestion.getChoices().get(0).getContent());
            }
            isEdit = true;
        } else {
            isEdit = false;
        }

        cancelButton.setOnAction(event -> {
            try {
                App.setRoot("addQuiz");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        saveButton.setOnAction(event -> {
            if (isEdit) {
                Question question = QuestionDataShared.getInstance().getQuestions().get(index);
                if (question.getType().equals(EQuestionType.MULTIPLE_CHOICE)) {
                    MultipleChoice multipleChoice = getMultipleChoiceQuestion();
                    QuestionDataShared.getInstance().updateQuestion(multipleChoice, index);
                } else {
                    FillQuestion fillQuestion = getFillQuestion();
                    QuestionDataShared.getInstance().updateQuestion(fillQuestion, index);
                }
            } else {
                if (typeComboBox.getValue().equals(EQuestionType.MULTIPLE_CHOICE.toString())) {
                    if (questionTextField.getText().isEmpty() || ans1TextField.getText().isEmpty() || ans2TextField.getText().isEmpty() || ans3TextField.getText().isEmpty() || ans4TextField.getText().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Error");
                        alert.setHeaderText("Error");
                        alert.setContentText("Please fill all fields");
                        alert.showAndWait();
                        return;
                    } else if (!answer1RadioButton.isSelected() && !answer2RadioButton.isSelected() && !answer3RadioButton.isSelected() && !answer4RadioButton.isSelected()) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Error");
                        alert.setHeaderText("Error");
                        alert.setContentText("Please choose the correct answer");
                        alert.showAndWait();
                        return;
                    } else {
                        MultipleChoice multipleChoice = getMultipleChoiceQuestion();
                        QuestionDataShared.getInstance().addMultipleChoiceQuestion(multipleChoice);
                    }
                } else {
                    if (questionTextField.getText().isEmpty() || ans1TextField.getText().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Error");
                        alert.setHeaderText("Error");
                        alert.setContentText("Please fill all fields");
                        alert.showAndWait();
                        return;
                    } else {
                        FillQuestion fillQuestion = getFillQuestion();
                        QuestionDataShared.getInstance().addFillQuestion(fillQuestion);
                    }
                }
            }
            try {
                App.setRoot("addQuiz");
            } catch (IOException e) {
                e.printStackTrace();
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
            if (typeComboBox.getValue().equals(EQuestionType.MULTIPLE_CHOICE.toString())) {
                setMultipleChoiceQuestion();
            } else {
                setFillQuestion();
            }
        });

    }

    private MultipleChoice getMultipleChoiceQuestion() {
        MultipleChoice multipleChoice = new MultipleChoice();
        multipleChoice.setQuestion(questionTextField.getText());
        multipleChoice.setChoices(List.of(
                new Choice(ans1TextField.getText(), answer1RadioButton.isSelected()),
                new Choice(ans2TextField.getText(), answer2RadioButton.isSelected()),
                new Choice(ans3TextField.getText(), answer3RadioButton.isSelected()),
                new Choice(ans4TextField.getText(), answer4RadioButton.isSelected())));
        return multipleChoice;
    }

    private FillQuestion getFillQuestion() {
        FillQuestion fillQuestion = new FillQuestion();
        fillQuestion.setQuestion(questionTextField.getText());
        fillQuestion.setChoices(List.of(
                new Choice(ans1TextField.getText(), true),
                new Choice("", false),
                new Choice("", false),
                new Choice("", false)));
        return fillQuestion;
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

    // For UI
    public void back_onMousePressed() {
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(this.cancelButton);
        translateTransition.setDuration(Duration.millis(65));
        translateTransition.setByY(5);
        translateTransition.play();
    }

    public void back_onMouseRelease() {
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(this.cancelButton);
        translateTransition.setDuration(Duration.millis(65));
        translateTransition.setByY(-5);
        translateTransition.play();
    }

    public void save_onMousePressed() {
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(this.saveButton);
        translateTransition.setDuration(Duration.millis(65));
        translateTransition.setByY(5);
        translateTransition.play();
    }

    public void save_onMouseRelease() {
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(this.saveButton);
        translateTransition.setDuration(Duration.millis(65));
        translateTransition.setByY(-5);
        translateTransition.play();
    }
}