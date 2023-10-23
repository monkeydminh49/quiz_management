package com.e01.quiz_management.model;

import java.util.List;

public class Test {
    private Long id;
    private Long userId;
    private String title;
    private long duration;
    private List<Question> questions;

    public Test() {
    }

    public Test(Long id, Long userId, String title, long duration, List<Question> questions) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.duration = duration;
        this.questions = questions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public void addQuestion(Question question){
        this.questions.add(question);
    }

    public void addAllQuestion(List<Question> questions){
        this.questions.addAll(questions);
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}