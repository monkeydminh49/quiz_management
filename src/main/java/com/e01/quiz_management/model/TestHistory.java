package com.e01.quiz_management.model;

import java.time.LocalDateTime;

public class TestHistory {
    private Long id;
    private String code;
    private String title;
    private Long userId;
    private Long duration;
    private int score;
    private LocalDateTime submitTime;
    private LocalDateTime startTime;
    public TestHistory() {
    }

    public TestHistory(Long id, String code, String title, Long userId, Long duration, int score, LocalDateTime startTime, LocalDateTime submitTime) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.userId = userId;
        this.duration = duration;
        this.score = score;
        this.submitTime = submitTime;
        this.startTime = startTime;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getDuration() {
        return duration;
    }

    public int getScore() {
        return score;
    }

    public LocalDateTime getSubmitTime() {
        return submitTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setSubmitTime(LocalDateTime submitTime) {
        this.submitTime = submitTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
}
