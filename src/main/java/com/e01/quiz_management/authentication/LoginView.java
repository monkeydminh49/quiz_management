package com.e01.quiz_management.authentication;

import com.e01.quiz_management.util.BaseResponse;
import com.e01.quiz_management.util.RequestAPI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.io.IOException;

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
        if (username.isBlank() || password.isBlank()){
            loginMessage.setText("Blank username or password found");
            loginMessage.setStyle("-fx-text-fill: red");
        }else{
            BaseResponse response = RequestAPI.getInstance().postLogin(username, password);
            if (response.getCode() == 1){
                loginMessage.setText("Successfully login!");
                loginMessage.setStyle("-fx-text-fill: green");
            }else{
                loginMessage.setText("Incorrect username or password");
                loginMessage.setStyle("-fx-text-fill: red");
            }
        }
    }
}
