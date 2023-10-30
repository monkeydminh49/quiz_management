package com.e01.quiz_management.model;

import com.e01.quiz_management.util.EQuestionType;

import java.util.Objects;

public class FillQuestion extends Question{
    Choice choices;

    public FillQuestion() {
        super();
        super.setType(EQuestionType.FILL_IN_BLANK);
    }

    public FillQuestion(Long id, Long testId, String question, Choice choice) {
        super(id, testId, question);
        super.setType(EQuestionType.FILL_IN_BLANK);
        this.choices= choice;
    }

    public Choice getmAns() {
        return choices;
    }

    public Choice getChoices() {
        return choices;
    }

    public void setChoice(Choice choice) {
        this.choices = choice;
    }

    public Integer getScore() {
        Choice mAns = getmAns();
        if (mAns == null) {
            return 0;
        }
        if (Objects.equals(choices.getContent(), mAns.getContent())) {
            return 10;
        }
        return 0;
    }

}
