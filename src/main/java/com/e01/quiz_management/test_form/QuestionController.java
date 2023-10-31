package com.e01.quiz_management.test_form;

import com.e01.quiz_management.model.*;
import com.e01.quiz_management.util.EQuestionType;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class QuestionController {

    private Test test;
    private static List<Question> questions;
    private static int currentQuestionIndex;
    private final List<Integer> notAnsweredQuestions = new ArrayList<>();

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public int getQuestionSize() {
        return questions.size();
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public QuestionController() {
        questions = new ArrayList<>();
        currentQuestionIndex = 0;
    }

    public void setQuestions(List<Question> questions) {
        QuestionController.questions = questions;
        currentQuestionIndex = 0;
        for (int i = 0; i < questions.size(); i++) {
            notAnsweredQuestions.add(i);
        }
    }


    public QuestionController(Test test) {
        this.test = test;
        questions = test.getQuestions();
        currentQuestionIndex = 0;
        for (int i = 0; i < questions.size(); i++) {
            notAnsweredQuestions.add(i);
        }
    }

    public static Question getCurrentQuestion() {
        return questions.get(currentQuestionIndex);
    }

    public void nextQuestion() {
        if (currentQuestionIndex < questions.size() - 1) {
            currentQuestionIndex++;
        }
    }

    public void previousQuestion() {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--;
        }
    }

    public void updateNotAnsweredQuestions(int index) {
        notAnsweredQuestions.remove(Integer.valueOf(index));
        notAnsweredQuestions.forEach(System.out::println);
    }

    public void showQuestion(TextField questionTextField, List<RadioButton> answerRadioButtons, TextField answerTextField, Button saveButton) {
        Question question = getCurrentQuestion();
        questionTextField.setText(question.getQuestion());
        if (question.getType().equals(EQuestionType.MULTIPLE_CHOICE)) {
            answerTextField.setVisible(false);
            saveButton.setVisible(false);
            answerRadioButtons.forEach(radioButton -> {
                radioButton.setVisible(true);
                radioButton.setDisable(false);
            });
            MultipleChoice multipleChoice = new MultipleChoice(question);
            multipleChoice.showQuestion(questionTextField, answerRadioButtons);
        } else {
            answerTextField.setVisible(true);
            saveButton.setVisible(true);
            answerRadioButtons.forEach(radioButton -> {
                radioButton.setVisible(false);
                radioButton.setDisable(true);
            });
            FillQuestion fillQuestion = new FillQuestion(question);
            fillQuestion.showQuestion(questionTextField, answerTextField);
        }
    }

    public void showResult(TextField questionTextField, List<RadioButton> answerRadioButtons, TextField answerTextField) {
        Question question = getCurrentQuestion();
        questionTextField.setText(question.getQuestion());
        if (question.getType().equals(EQuestionType.MULTIPLE_CHOICE)) {
            answerTextField.setVisible(false);
            answerRadioButtons.forEach(radioButton -> {
                radioButton.setVisible(true);
                radioButton.setDisable(true);
            });
            MultipleChoice multipleChoice = new MultipleChoice(question);
            multipleChoice.showAnswer(questionTextField, answerRadioButtons);
        } else {
            answerTextField.setVisible(true);
            answerRadioButtons.forEach(radioButton -> {
                radioButton.setVisible(false);
                radioButton.setDisable(true);
            });
            FillQuestion fillQuestion = new FillQuestion(question);
            fillQuestion.showAnswer(questionTextField, answerTextField);
        }
    }

    public Integer goToNextUnansweredQuestion(TextField questionTextField, List<RadioButton> answerRadioButtons, TextField ansTextField, Button saveButton, int index) {
        for (int i = index + 1; i < questions.size(); i++) {
            if (notAnsweredQuestions.contains(i)) {
                currentQuestionIndex = i;
                showQuestion(questionTextField, answerRadioButtons, ansTextField, saveButton);
                return i;
            }
        }
        currentQuestionIndex = notAnsweredQuestions.get(0);
        showQuestion(questionTextField, answerRadioButtons, ansTextField, saveButton);
        return currentQuestionIndex;
    }

    public Integer getNotAnsweredQuestionSize() {
        return notAnsweredQuestions.size();
    }

    public void setCurrentQuestionIndex(int index) {
        currentQuestionIndex = index;
    }

    public Integer getCal() {
        Integer cal = 0;
        for (Question question : questions) {
            cal += question.getScore();
        }
        return cal * 100 / questions.size();
    }
}
