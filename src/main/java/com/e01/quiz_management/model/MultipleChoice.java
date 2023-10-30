package com.e01.quiz_management.model;

import com.e01.quiz_management.util.EQuestionType;

import java.util.List;

public class MultipleChoice extends Question {
    List<Choice> choices;

    public MultipleChoice() {
        super();
        super.setType(EQuestionType.MULTIPLE_CHOICE);
    }

    public MultipleChoice(Long id, Long testId, String question, List<Choice> choices) {
        super(id, testId, question);
        super.setType(EQuestionType.MULTIPLE_CHOICE);
        this.choices = choices;
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

    public Integer getScore() {
        Choice mAns = getmAns();
        if (mAns == null) {
            return 0;
        }
        for (Choice choice : choices) {
            if (choice.getId().equals(mAns.getId())) {
                return 10;
            }
        }
        return 0;
    }

}
