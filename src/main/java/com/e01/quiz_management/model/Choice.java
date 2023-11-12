package com.e01.quiz_management.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Choice {
    private Long id;
    private String content;
    @JsonProperty("isCorrect")
    private Boolean isCorrect;
    private Long questionId;

    public Choice() {
        this.isCorrect = false;
    }

    public Choice(Long id, String content, Boolean isCorrect, Long questionId) {
        this.id = id;
        this.content = content;
        this.isCorrect = isCorrect;
        this.questionId = questionId;
    }

    public Choice(String content, Boolean isCorrect) {
        this.content = content;
        this.isCorrect = isCorrect;
    }

    public Choice(Choice choice, String text, boolean selected) {
        this.content = text;
        this.isCorrect = selected;
        this.questionId = choice.getQuestionId();
        this.id = choice.getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Boolean getCorrect() {
        return this.isCorrect;
    }

    public void setCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    @Override
    public String toString() {
        return "Choice{" + "id=" + id + ", content='" + content + '\'' + ", isCorrect=" + isCorrect + ", questionId=" + questionId + "}\n";
    }
}