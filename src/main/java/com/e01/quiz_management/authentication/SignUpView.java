package com.e01.quiz_management.authentication;

import com.e01.quiz_management.App;
import com.e01.quiz_management.util.BaseResponse;
import com.e01.quiz_management.util.RequestAPI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SignUpView {
    @FXML
    private Label signupMessage;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private PasswordField passwordTextField;

    public void signup(ActionEvent e) throws IOException{
        String username = usernameTextField.getText();
        String name = nameTextField.getText();
        String password = passwordTextField.getText();
        if (username.isBlank() || password.isBlank() || name.isBlank()){
            signupMessage.setText("Blank field found");
            signupMessage.setStyle("-fx-text-fill: red");
        }else{
            try {
                BaseResponse response = RequestAPI.getInstance().postRegister(name, username, password);
                System.out.println(response.getBody());
                if (response.getCode() == 1){
                    signupMessage.setText("Successfully sign up!");
                    signupMessage.setStyle("-fx-text-fill: green");
                }else{
                    signupMessage.setText("Username existed");
                    signupMessage.setStyle("-fx-text-fill: red");
                }
            } catch (Exception ie){
                System.out.println(ie);
            }
        }
    }
    public void switchToLogin(ActionEvent e) throws IOException {
        try{
            App.setRoot("login");
        }catch(Exception ie){
            System.out.println(ie);
        }
    }
}
