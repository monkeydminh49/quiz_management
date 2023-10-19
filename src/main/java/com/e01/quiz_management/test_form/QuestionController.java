package com.e01.quiz_management.test_form;

import com.e01.quiz_management.model.Question;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class QuestionController {
    private List<Question> questions;
    private int currentQuestionIndex;
    private List<Integer> notAnsweredQuestions = new ArrayList<>();

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public int getQuestionSize() {
        return questions.size();
    }

    public QuestionController(List<Question> questions) {
        this.questions = questions;
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
}
