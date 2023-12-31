package com.e01.quiz_management.model;

import com.e01.quiz_management.util.EQuestionType;
import com.e01.quiz_management.util.ETestStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import javafx.beans.binding.BooleanExpression;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class Test {
    private Long id;
    private String code;
    private Long userId;
    private String title;
    private LocalDateTime startTime;
    private List<Question> questions;
    private long duration;

    private int numberOfLiveParticipant;

    public Test() {
        questions = new ArrayList<>();
//        this.numberOfLiveParticipant = 0;
    }

    public Test(Long id, String code, Long userId, String title, LocalDateTime startTime, List<Question> questions, long duration, int numberOfLiveParticipant) {
        this.id = id;
        this.code = code;
        this.userId = userId;
        this.title = title;
        this.startTime = startTime;
        this.duration = duration;
        this.numberOfLiveParticipant = numberOfLiveParticipant;
        this.questions = new ArrayList<>();
        questions.forEach(q -> {
            if (q.getType() == EQuestionType.MULTIPLE_CHOICE) {
                MultipleChoice multipleChoice = new MultipleChoice(q);
                this.questions.add(multipleChoice);
                System.out.println("multiple choice");
            } else {
                FillQuestion fillQuestion = new FillQuestion(q);
                this.questions.add(fillQuestion);
                System.out.println("fill question");
            }
        });
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public void addQuestion(Question question) {
        this.questions.add(question);
    }

    public void addAllQuestion(List<MultipleChoice> questions) {
        this.questions.addAll(questions);
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public String getTestDescription() {
        return "Test description:\n" +
                "Title: " + title + "\n" +
                "Code: " + code + "\n" +
                "Start time: " + startTime + "\n" +
                "Duration: " + duration + " minutes\n" +
                "Number of questions: " + questions.size();
    }

    public ETestStatus getStatus(){
        if (this.getStartTime() != null){
            long current_millis = LocalDateTime.now().atZone(ZoneId.of("Asia/Ho_Chi_Minh")).toInstant().toEpochMilli();
            long start_millis = this.getStartTime().atZone(ZoneId.of("Asia/Ho_Chi_Minh")).toInstant().toEpochMilli();
            long duration = this.getDuration() * 60 * 1000;
            if (current_millis < start_millis){
                return ETestStatus.NOT_STARTED;
            } else if (current_millis > start_millis + duration){
                return ETestStatus.ENDED;
            } else {
                return ETestStatus.HAPPENING;
            }
        } else {
            return ETestStatus.PRACTICE;
        }

    }
    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", startTime=" + startTime +
                ", questions=" + questions +
                ", duration=" + duration +
                "}\n";
    }

    public void resetTest() {
        this.questions.forEach(q -> {
            q.setmAns(null);
        });
    }

    public int getNumberOfLiveParticipant() {
        return numberOfLiveParticipant;
    }

    public void setNumberOfLiveParticipant(int numberOfLiveParticipant) {
        this.numberOfLiveParticipant = numberOfLiveParticipant;
    }
}