package com.e01.quiz_management.model;

import com.e01.quiz_management.util.EQuestionType;

import java.util.List;
import java.util.Objects;

public class FillQuestion extends Question {

    public FillQuestion() {
        this.setType(EQuestionType.FILL_IN_BLANK);
    }

    public Integer getScore() {
        Choice mAns = getmAns();
        if (mAns == null) {
            return 0;
        }
        if (Objects.equals(mAns.getContent(), getChoices().get(0).getContent())) {
            return 1;
        }
        return 0;
    }

}
