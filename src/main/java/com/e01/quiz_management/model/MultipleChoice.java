package com.e01.quiz_management.model;

import com.e01.quiz_management.util.EQuestionType;

import java.util.List;

public class MultipleChoice extends Question {

    public MultipleChoice() {
        this.setType(EQuestionType.MULTIPLE_CHOICE);
    }

    public Integer getScore() {
        Choice mAns = getmAns();
        if (mAns == null) {
            return 0;
        }
        for (Choice choice : getChoices()) {
            if (choice.getId().equals(mAns.getId())) {
                return 10;
            }
        }
        return 0;
    }

}
