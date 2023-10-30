package com.e01.quiz_management.model;

import com.e01.quiz_management.util.EQuestionType;

import java.util.List;

public class Question {
    private Long id;
    private Long testId;
    private String question;
    private List<Choice> choices;
    private Choice mAns = null;
    private EQuestionType type;

    public Question() {
    }

    public Question(Long id, Long testId, String question, List<Choice> choices) {
        this.id = id;
        this.testId = testId;
        this.question = question;
        this.choices = choices;
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

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public void addChoice(Choice choice) {
        this.choices.add(choice);
    }

    public void addAllChoices(List<Choice> choices) {
        this.choices.addAll(choices);
    }

    public void setmAns(Choice mAns) {
        System.out.println("Set answer");
        this.mAns = mAns;
    }

    public Choice getmAns() {
        return mAns;
    }

    public EQuestionType getType() {
        return type;
    }

    public void setType(EQuestionType type) {
        this.type = type;
    }
}
