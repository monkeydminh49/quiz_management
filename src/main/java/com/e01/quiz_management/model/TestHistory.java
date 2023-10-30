package com.e01.quiz_management.model;

import java.time.LocalDateTime;

public class TestHistory {
    private Long id;
    private Long testId;
    private String code;
    private String title;
    private Long candidateId;
    private String candidateName;
    private Long duration;
    private Integer score = null;
    private LocalDateTime submitTime;
    private LocalDateTime startTime;

    public TestHistory() {
    }

    public TestHistory(Long id, Long testId, String code, String title, Long candidateId, String candidateName, Long duration, int score, LocalDateTime startTime, LocalDateTime submitTime) {
        this.id = id;
        this.code = code;
        this.testId = testId;
        this.title = title;
        this.candidateId = candidateId;
        this.duration = duration;
        this.score = score;
        this.submitTime = submitTime;
        this.startTime = startTime;
        this.candidateName = candidateName;
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

    public Long getCandidateId() {
        return candidateId;
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

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
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

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }
}
