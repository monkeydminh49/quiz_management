package com.e01.quiz_management.model;

import com.e01.quiz_management.util.EQuestionType;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.Objects;

public class Question {
    private Long id;
    private Long testId;
    private String question;
    private EQuestionType type;
    private List<Choice> choices;
    private Choice mAns = null;

    public Question() {
    }

    public Question(Long id, Long testId, String question) {
        this.id = id;
        this.testId = testId;
        this.question = question;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public EQuestionType getType() {
        return type;
    }

    public Integer getScore() {
        return 0;
    }

    public void setmAns(Choice mAns) {
        this.mAns = mAns;
    }

    public Choice getmAns() {
        return mAns;
    }

    protected void setType(EQuestionType eQuestionType) {
        this.type = eQuestionType;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public List<Choice> getChoices() {
        return choices;
    }
}
