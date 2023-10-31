package com.e01.quiz_management.authentication;

import com.e01.quiz_management.App;
import com.e01.quiz_management.data.ShareAppData;
import com.e01.quiz_management.menu.MenuView;
import com.e01.quiz_management.model.User;
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
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

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
            System.out.println(response.getBody());
            if (response.getCode() == 1){
                User user = RequestAPI.getInstance().getBaseResponseBodyObject(response, User.class);
                ShareAppData.getInstance().setUser(user);
                App.setRoot("menu");
            }else{
                loginMessage.setText("Incorrect username or password");
                loginMessage.setStyle("-fx-text-fill: red");
            }
        }
    }

    public void switchToSignUp(ActionEvent e) throws IOException {
        try{
            App.setRoot("signup");
        }catch(Exception ie){
            System.out.println(ie);
        }
    }

    public void username_field_onMouseEntered(){
//        usernameTextField.setStyle("-fx-background-color: #FAFAFA; -fx-border-color: #999999; -fx-border-radius: 5");
    }

    public void username_field_onMouseExited(){
//        usernameTextField.setStyle("-fx-background-color: #FAFAFA; -fx-border-color: #D4D4D4; -fx-border-radius: 5");
    }

    public void password_field_onMouseEntered(){
//        passwordTextField.setStyle("-fx-background-color: #FAFAFA; -fx-border-color: #999999; -fx-border-radius: 5");
    }

    public void password_field_onMouseExited(){
//        passwordTextField.setStyle("-fx-background-color: #FAFAFA; -fx-border-color: #D4D4D4; -fx-border-radius: 5");
    }

    public void username_field_onFocused(){
//        usernameTextField.setStyle("-fx-background-color: #FAFAFA; -fx-border-color: #F68E01; -fx-border-radius: 5");
    }

    public void password_field_onFocused(){
//        passwordTextField.setStyle("-fx-background-color: #FAFAFA; -fx-border-color: #F68E01; -fx-border-radius: 5");
    }
}
