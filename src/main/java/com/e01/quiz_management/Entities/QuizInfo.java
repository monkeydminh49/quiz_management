package com.e01.quiz_management.Entities;

import java.sql.Time;

public class QuizInfo {
    private String QuizTitle;
    private Long QuizId;
    private Time Duration;
    private Integer NumberOfQues;

    public Time getDuration() {
        return Duration;
    }

    public void setDuration(Time duration) {
        Duration = duration;
    }

    public String getQuizTitle() {
        return QuizTitle;
    }

    public void setQuizTitle(String quizTitle) {
        QuizTitle = quizTitle;
    }

    public Long getQuizId() {
        return QuizId;
    }

    public void setQuizId(Long quizId) {
        QuizId = quizId;
    }

    public Integer getNumberOfQues() {
        return NumberOfQues;
    }

    public void setNumberOfQues(Integer numberOfQues) {
        NumberOfQues = numberOfQues;
    }
}
