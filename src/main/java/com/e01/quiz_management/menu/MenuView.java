package com.e01.quiz_management.menu;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MenuView {
    @FXML
    Label greetingName;
    public void greeting(String s){
        this.greetingName.setText("Hello " + s);
    }
}
