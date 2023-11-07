package com.e01.quiz_management.controller;

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

    private List<Question> questions;

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    void addMultipleChoiceQuestion(MultipleChoice multipleChoice) {
        questions.add(multipleChoice);
        System.out.println(questions);
    }

    void addFillQuestion(FillQuestion fillQuestion) {
        questions.add(fillQuestion);
        System.out.println(questions);
    }

}
