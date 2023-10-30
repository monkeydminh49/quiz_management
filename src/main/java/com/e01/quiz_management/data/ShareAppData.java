package com.e01.quiz_management.data;

import com.e01.quiz_management.model.Test;
import com.e01.quiz_management.model.User;
import com.e01.quiz_management.util.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShareAppData {
    private static ShareAppData instance;
    private Test test;
    private User user;
    private Boolean isEdit = false;

    private Response<List<Test>> listTestResponse = (Response<List<Test>>) Response.Loading;

    public Response<List<Test>> getListTestResponse() {
        return listTestResponse;
    }

    public void setListTestResponse(Response<List<Test>> listTestResponse) {
        this.listTestResponse = listTestResponse;
    }
    private List<Test> tests;

    public List<Test> getTests() {
        return tests;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }

    private ShareAppData() {
        tests = new ArrayList<>();
    }

    public static ShareAppData getInstance() {
        if (instance == null) {
            instance = new ShareAppData();
        }
        return instance;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Boolean getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(Boolean isEdit) {
        this.isEdit = isEdit;
    }

    public void clearTest() {
        this.test = null;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void addTest(Test test) {
        this.tests.add(test);
    }

    public void updateTest(Test test) {
        for (int i = 0; i < tests.size(); i++) {
            if (Objects.equals(tests.get(i).getId(), test.getId())) {
                tests.set(i, test);
                break;
            }
        }
    }
}
