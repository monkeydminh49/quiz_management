package com.e01.quiz_management.model;

import com.e01.quiz_management.util.EQuestionType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.util.List;

public class MultipleChoice extends Question {
    public MultipleChoice() {
        this.setType(EQuestionType.MULTIPLE_CHOICE);
    }

    public MultipleChoice(Question question) {
        this.setId(question.getId());
        this.setTestId(question.getTestId());
        this.setQuestion(question.getQuestion());
        this.setType(EQuestionType.MULTIPLE_CHOICE);
        this.setChoices(question.getChoices());
        this.setmAns(question.getmAns());
    }

    public Integer getScore() {
        Choice mAns = getmAns();
        if (mAns == null) {
            return 0;
        }
        System.out.println(mAns.getContent() + " " + getChoices().get(0).getContent());
        if (mAns.getCorrect()) {
            return 1;
        }
        return 0;
    }

    public void showQuestion(TextField question, List<RadioButton> buttons) {
        question.setText(getQuestion());
        List<Choice> choices = getChoices();
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setText(choices.get(i).getContent());
            buttons.get(i).setSelected(false);
        }
        if (getmAns() != null) {
            for (int i = 0; i < buttons.size(); i++) {
                if (choices.get(i).getId().equals(getmAns().getId())) {
                    buttons.get(i).setSelected(true);
                }
            }
        }
    }

    public void showAnswer(TextField question, List<RadioButton> buttons) {
        Choice mAns = getmAns();
        question.setText(getQuestion());
        List<Choice> choices = getChoices();
        if (mAns == null) {
            for (int i = 0; i < buttons.size(); i++) {
                buttons.get(i).setSelected(false);
                buttons.get(i).setText(choices.get(i).getContent());
                if (choices.get(i).getCorrect()) {
                    buttons.get(i).setStyle("-fx-text-fill: green");
                }
            }
        } else {
            for (int i = 0; i < buttons.size(); i++) {
                buttons.get(i).setText(choices.get(i).getContent());
                buttons.get(i).setSelected(false);
                if (choices.get(i).getId().equals(mAns.getId())) {
                    buttons.get(i).setSelected(true);
                    if (choices.get(i).getCorrect()) {
                        buttons.get(i).setStyle("-fx-text-fill: green");
                    } else {
                        buttons.get(i).setStyle("-fx-text-fill: red");
                    }
                } else {
                    if (choices.get(i).getCorrect()) {
                        buttons.get(i).setStyle("-fx-text-fill: green");
                    } else {
                        buttons.get(i).setStyle("-fx-text-fill: black");
                    }
                }
            }
        }
    }

}
