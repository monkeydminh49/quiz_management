package com.e01.quiz_management.Entities;

import java.time.Duration;

import java.util.concurrent.CountDownLatch;

public class Quiz {
    // Properties
    private Long quizId;
    private String title;
    private Duration duration;

    public Quiz(String title,long quizId,Duration duration ) {
        this.quizId= quizId;
        this.title = title;
        this.duration=duration;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public Long getQuizId() {
        return quizId;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return this.title;
    }

    // Other Methods

}