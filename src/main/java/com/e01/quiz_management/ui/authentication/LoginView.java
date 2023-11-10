package com.e01.quiz_management.ui.authentication;

import com.e01.quiz_management.App;
import com.e01.quiz_management.data.ShareAppData;
import com.e01.quiz_management.model.TestHistory;
import com.e01.quiz_management.ui.list_test.ListTestView;
import com.e01.quiz_management.model.Test;
import com.e01.quiz_management.model.User;
import com.e01.quiz_management.util.BaseResponse;
import com.e01.quiz_management.util.RequestAPI;
import com.e01.quiz_management.util.Response;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;

public class LoginView {
    @FXML
    private Label loginMessage;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;

    public void login(ActionEvent e) throws IOException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        if (username.isBlank() || password.isBlank()) {
            loginMessage.setText("Blank username or password found");
            loginMessage.setStyle("-fx-text-fill: red");
        } else {
            BaseResponse response = RequestAPI.getInstance().postLogin(username, password);
            System.out.println(response.getBody());
            if (response.getCode() == 1) {
                User user = RequestAPI.getInstance().getBaseResponseBodyObject(response, User.class);
                ShareAppData.getInstance().setUser(user);
                Task<List<Test>> task = new Task<>() {
                    @Override
                    protected List<Test> call() throws Exception {
                        System.out.println("call");
                        return RequestAPI.getInstance().getAllUserTests();
                    }
                };

                task.setOnSucceeded(event -> {
                    System.out.println("success");
                    Response<List<Test>> data = new Response.Success<List<Test>>(task.getValue());
                    ShareAppData.getInstance().setListTestResponse(data);
                    ShareAppData.getInstance().setTests(((Response.Success<List<Test>>) ShareAppData.getInstance().getListTestResponse()).getData());
                    ListTestView.getInstance().updateTable();
                });
                new Thread(task).start();

                App.setRoot("menu");
            } else {
                loginMessage.setText("Incorrect username or password");
                loginMessage.setStyle("-fx-text-fill: red");
            }
        }
    }

    public void switchToSignUp(ActionEvent e) throws IOException {
        try {
            App.setRoot("signup");
        } catch (Exception ie) {
            System.out.println(ie);
        }
    }
}
