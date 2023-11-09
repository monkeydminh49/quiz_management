package com.e01.quiz_management.ui.test_create;

import com.e01.quiz_management.model.FillQuestion;
import com.e01.quiz_management.model.MultipleChoice;
import com.e01.quiz_management.model.Question;

import java.util.List;

public class QuestionDataShared {
    private static QuestionDataShared instance;

    private QuestionDataShared() {
    }

    public static QuestionDataShared getInstance() {
        if (instance == null) {
            instance = new QuestionDataShared();
        }
        return instance;
    }

    private Integer index = 0;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    private List<Question> questions;

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    void addMultipleChoiceQuestion(MultipleChoice multipleChoice) {
        questions.add(multipleChoice);
    }

    void addFillQuestion(FillQuestion fillQuestion) {
        questions.add(fillQuestion);
    }

    void updateQuestion(Question question, int index) {
        questions.set(index, question);
    }

    void addQuestion(List<MultipleChoice> multipleChoices) {
        questions.addAll(multipleChoices);
    }
}