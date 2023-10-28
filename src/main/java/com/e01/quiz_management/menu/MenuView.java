package com.e01.quiz_management.menu;

import com.e01.quiz_management.App;
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
    public void greeting(String s){
        this.greetingName.setText("Hello " + s);
    }

    public void switchToJoinTest(ActionEvent e) throws IOException {
        Stage stage;
        Scene scene;
        Parent root;
        try{
            root = FXMLLoader.load((Objects.requireNonNull(App.class.getResource("jointest.fxml"))));
            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            scene = new Scene(root);
//            scene.getStylesheets().add(Objects.requireNonNull(App.class.getResource("app.css")).toExternalForm());
            stage.setScene(scene);
            stage.show();
        }catch(Exception ie){
            System.out.println(ie);
        }
    }
}
