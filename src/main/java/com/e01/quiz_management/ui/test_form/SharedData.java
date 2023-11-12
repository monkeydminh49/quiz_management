package com.e01.quiz_management.ui.test_form;

import com.e01.quiz_management.model.Test;
import com.e01.quiz_management.model.TestHistory;

public class SharedData {
    private static SharedData instance;
    private TestHistory testHistory = null;
    private Boolean isReview = false;
    private Test test;

    private SharedData() {

    }

    public static SharedData getInstance() {
        if (instance == null) {
            instance = new SharedData();
        }
        return instance;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Boolean getIsReview() {
        return isReview;
    }

    public void setIsReview(Boolean isReview) {
        this.isReview = isReview;
    }

    public TestHistory getTestHistory() {
        return testHistory;
    }

    public void setTestHistory(TestHistory testHistory) {
        this.testHistory = testHistory;
    }

    public void clearTestHistory() {
        this.testHistory = null;
    }
}
