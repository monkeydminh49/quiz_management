package com.e01.quiz_management.test_form;

import com.e01.quiz_management.model.Choice;
import com.e01.quiz_management.model.MultipleChoice;
import com.e01.quiz_management.model.Question;
import com.e01.quiz_management.model.Test;
import com.e01.quiz_management.util.EQuestionType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class QuestionController {

    private Test test;
    private List<Question> questions;
    private int currentQuestionIndex;
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
        this.questions = new ArrayList<>();
        this.currentQuestionIndex = 0;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
        this.currentQuestionIndex = 0;
        for (int i = 0; i < questions.size(); i++) {
            notAnsweredQuestions.add(i);
        }
    }


    public QuestionController(Test test) {
        this.test = test;
        this.questions = test.getQuestions();
        this.currentQuestionIndex = 0;
        for (int i = 0; i < questions.size(); i++) {
            notAnsweredQuestions.add(i);
        }
    }

    public Question getCurrentQuestion() {
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

    public void showQuestion(TextField questionTextField, List<RadioButton> answerRadioButtons, TextField answerTextField) {
        Question question = getCurrentQuestion();
        questionTextField.setText(question.getQuestion());
        if (question.getType().equals(EQuestionType.MULTIPLE_CHOICE)) {
            answerTextField.setVisible(false);
            List<Choice> choices = question.getChoices();
            for (int i = 0; i < choices.size(); i++) {
                answerRadioButtons.get(i).setText(choices.get(i).getContent());
                answerRadioButtons.get(i).setDisable(false);
                answerRadioButtons.get(i).setSelected(false);
                answerRadioButtons.get(i).setStyle("-fx-text-fill: black");
            }
        } else {
            answerTextField.setVisible(true);
            answerTextField.setText("");
            answerRadioButtons.forEach(radioButton -> {
                radioButton.setVisible(false);
                radioButton.setDisable(true);
            });
        }
    }

    public void showResult(TextField questionTextField, List<RadioButton> answerRadioButtons, TextField answerTextField) {
        Question question = getCurrentQuestion();
        questionTextField.setText(question.getQuestion());
        Choice mAns = question.getmAns();
        if (question.getType().equals(EQuestionType.MULTIPLE_CHOICE)) {
            answerTextField.setVisible(false);
            List<Choice> choices = question.getChoices();
            for (int i = 0; i < choices.size(); i++) {
                answerRadioButtons.get(i).setText(choices.get(i).getContent());
                answerRadioButtons.get(i).setDisable(true);
                if (choices.get(i).getId().equals(mAns.getId())) {
                    answerRadioButtons.get(i).setStyle("-fx-text-fill: green");
                } else {
                    answerRadioButtons.get(i).setStyle("-fx-text-fill: red");
                }
            }
        } else {
            answerTextField.setVisible(true);
            answerTextField.setText(mAns.getContent());
            answerRadioButtons.forEach(radioButton -> {
                radioButton.setVisible(false);
                radioButton.setDisable(true);
            });
        }
    }

    public Integer goToNextUnansweredQuestion(TextField questionTextField, List<RadioButton> answerRadioButtons, TextField ansTextField, int index) {
        System.out.println("currentQuestionIndex: " + notAnsweredQuestions.size());
        for (int i = index + 1; i < questions.size(); i++) {
            if (notAnsweredQuestions.contains(i)) {
                currentQuestionIndex = i;
                showQuestion(questionTextField, answerRadioButtons, ansTextField);
                return i;
            }
        }
        currentQuestionIndex = notAnsweredQuestions.get(0);
        showQuestion(questionTextField, answerRadioButtons, ansTextField);
        return currentQuestionIndex;
    }

    public Integer getNotAnsweredQuestionSize() {
        return notAnsweredQuestions.size();
    }

    public void setCurrentQuestionIndex(int index) {
        this.currentQuestionIndex = index;
    }

    public Integer getCal() {
        int score = 0;
        for (Question question : questions) {
            if (question.getScore() != null) {
                score += question.getScore();
            }
        }
        return score * 100 / questions.size();
    }
}
