package com.e01.quiz_management.model;

import com.e01.quiz_management.util.EQuestionType;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.util.List;

public class Question {
    private Long id;
    private Long testId;
    private String question;
    private EQuestionType type;
    private List<Choice> choices;
    private Choice mAns = null;

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

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setmAns(Choice mAns) {
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


    public Integer getScore() {
//        if (this.type.equals(EQuestionType.MULTIPLE_CHOICE)) {
//            return getMultipleChoice().getScore();
//        } else {
//            return getFillQuestion().getScore();
//        }
        return 0;
    }
}
