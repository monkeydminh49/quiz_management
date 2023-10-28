package com.e01.quiz_management.test_form;

import com.e01.quiz_management.model.Question;

import java.util.List;

public class SharedData {
    private static SharedData instance;
    private Long score = null;
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

    public Long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }
    public void clearScore() {
        this.score = null;
    }
}
