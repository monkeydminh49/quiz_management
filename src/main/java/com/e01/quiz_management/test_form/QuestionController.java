package com.e01.quiz_management.test_form;

import com.e01.quiz_management.model.Choice;
import com.e01.quiz_management.model.Question;
import com.e01.quiz_management.model.Test;
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

    public void showQuestion(TextField questionTextField, List<RadioButton> answerRadioButtons) {
        Question question = getCurrentQuestion();
        questionTextField.setText(question.getQuestion());
        for (int i = 0; i < question.getChoices().size(); i++) {
            answerRadioButtons.get(i).setText(question.getChoices().get(i).getContent());
            answerRadioButtons.get(i).setSelected(false);
        }
        if (question.getmAns() != null) {
            for (int i = 0; i < question.getChoices().size(); i++) {
                int ansId = question.getmAns().getId().intValue();
                if (question.getChoices().get(i).getId().intValue() == ansId) {
                    answerRadioButtons.get(i).setSelected(true);
                }
            }
        }
    }

    public void showResult(TextField questionTextField, List<RadioButton> answerRadioButtons) {
        Question question = getCurrentQuestion();
        questionTextField.setText(question.getQuestion());
        Choice mAns = question.getmAns();
        for (int i = 0; i < question.getChoices().size(); i++) {
            answerRadioButtons.get(i).setText(question.getChoices().get(i).getContent());
            answerRadioButtons.get(i).setStyle("-fx-text-fill: black");
            if (question.getChoices().get(i).getCorrect()) {
                answerRadioButtons.get(i).setStyle("-fx-text-fill: green");
            }
            answerRadioButtons.get(i).setDisable(true);
            answerRadioButtons.get(i).setSelected(false);
        }
        if (mAns != null) {
            System.out.println("mAns: " + mAns.getContent());
            for (int i = 0; i < question.getChoices().size(); i++) {
                int ansId = mAns.getId().intValue();
                if (question.getChoices().get(i).getId().intValue() == ansId) {
                    answerRadioButtons.get(i).setSelected(true);
                    if (question.getChoices().get(i).getCorrect()) {
                        answerRadioButtons.get(i).setStyle("-fx-text-fill: green");
                    } else {
                        answerRadioButtons.get(i).setStyle("-fx-text-fill: red");
                    }
                }
            }
        }
    }

    public Integer goToNextUnansweredQuestion(TextField questionTextField, List<RadioButton> answerRadioButtons, int index) {
        System.out.println("currentQuestionIndex: " + notAnsweredQuestions.size());
        for (int i = index + 1; i < questions.size(); i++) {
            if (notAnsweredQuestions.contains(i)) {
                currentQuestionIndex = i;
                showQuestion(questionTextField, answerRadioButtons);
                return i;
            }
        }
        currentQuestionIndex = notAnsweredQuestions.get(0);
        showQuestion(questionTextField, answerRadioButtons);
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
            if (question.getmAns() != null) {
                if (question.getmAns().getCorrect()) {
                    score++;
                }
            }
        }
        return score * 100 / questions.size();
    }

}
