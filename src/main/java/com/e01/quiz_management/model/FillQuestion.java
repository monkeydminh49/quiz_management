package com.e01.quiz_management.model;

import com.e01.quiz_management.util.EQuestionType;
import javafx.scene.control.TextField;

import java.util.Objects;

public class FillQuestion extends Question {

    public FillQuestion() {
        this.setType(EQuestionType.FILL_IN_BLANK);
    }

    public void showQuestion(TextField questionTextField, TextField ansTextField) {
        questionTextField.setText(getQuestion());
        ansTextField.setText("");
    }

    public void showAnswer(TextField question, TextField ansTextField) {
        Choice mAns = getmAns();
        question.setText(getQuestion());
        if (mAns == null) {
            ansTextField.setText("");
        } else {
            ansTextField.setText(mAns.getContent());
            if (Objects.equals(mAns.getContent(), getChoices().get(0).getContent())) {
                ansTextField.setStyle("-fx-text-fill: green");
            } else {
                ansTextField.setStyle("-fx-text-fill: red");
            }
        }
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
