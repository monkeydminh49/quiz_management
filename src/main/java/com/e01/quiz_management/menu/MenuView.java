package com.e01.quiz_management.menu;

import com.e01.quiz_management.App;
import com.e01.quiz_management.data.ShareAppData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MenuView {
    @FXML
    Label greetingName;

    @FXML
    private void initialize() {
        greetingName.setText("Hello, " + ShareAppData.getInstance().getUser().getName());
    }

    public void switchToJoinTest(ActionEvent e) throws IOException {
        try {
            App.setRoot("jointest");
        } catch (Exception ie) {
            System.out.println(ie);
        }
    }

    public void switchToHistory(ActionEvent e) throws IOException {
        try {
            App.setRoot("list_test");
        } catch (Exception ie) {
            System.out.println(ie);
        }
    }

    public void switchToManagement(ActionEvent e) throws IOException {
        try {
            App.setRoot("layout_list_test");
        } catch (Exception ie) {
            System.out.println(ie);
        }
    }
}
