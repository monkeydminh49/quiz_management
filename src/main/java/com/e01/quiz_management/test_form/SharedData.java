package com.e01.quiz_management.test_form;

import com.e01.quiz_management.model.Question;

import java.util.List;

public class SharedData {
    private static SharedData instance;
    private Double score = 0.0;
    private Boolean isReview = false;
    private List<Question> questions;

    private SharedData() {

    }

    public static SharedData getInstance() {
        if (instance == null) {
            instance = new SharedData();
        }
        return instance;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Boolean getIsReview() {
        return isReview;
    }

    public void setIsReview(Boolean isReview) {
        this.isReview = isReview;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
