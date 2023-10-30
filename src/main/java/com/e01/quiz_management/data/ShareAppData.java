package com.e01.quiz_management.data;

import com.e01.quiz_management.model.Test;
import com.e01.quiz_management.model.User;

public class ShareAppData {
    private static ShareAppData instance;
    private Test test = null;
    private User user;

    private Boolean isEdit = false;

    private ShareAppData() {
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
}
