package com.e01.quiz_management.ui.menu;

import com.e01.quiz_management.App;
import com.e01.quiz_management.data.ShareAppData;
import com.e01.quiz_management.util.RequestAPI;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MenuView implements Initializable {
    @FXML
    Label greetingName;
    @FXML
    private Pane content;
    @FXML
    private Button menuButton;
    @FXML
    private Button historyButton;
    @FXML
    private Button testManagementButton;
    @FXML
    private Button logoutButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        greetingName.setText("Hello, " + ShareAppData.getInstance().getUser().getName());
        content.getChildren().clear();
        int tabNo = ShareAppData.getInstance().getTabNo();
        if (tabNo == 0) {
            try {
                switchToJoinTest(null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (tabNo == 1) {
            try {
                switchToHistory(null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (tabNo == 2) {
            try {
                switchToManagement(null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        logoutButton.setOnAction(actionEvent -> logOut());
    }

    public void switchToJoinTest(ActionEvent e) throws IOException {
        ShareAppData.getInstance().setTabNo(0);
        content.getChildren().clear();
        menuButton.setStyle("-fx-background-color: #DFA98F");
        historyButton.setStyle("-fx-background-color: #E9C8BC");
        testManagementButton.setStyle("-fx-background-color: #E9C8BC");
        content.getChildren().add(FXMLLoader.load(Objects.requireNonNull(App.class.getResource("jointest.fxml"))));
    }

    public void switchToHistory(ActionEvent e) throws IOException {
        ShareAppData.getInstance().setTabNo(1);
        content.getChildren().clear();
        historyButton.setStyle("-fx-background-color: #DFA98F");
        menuButton.setStyle("-fx-background-color: #E9C8BC");
        testManagementButton.setStyle("-fx-background-color: #E9C8BC");
        content.getChildren().add(FXMLLoader.load(Objects.requireNonNull(App.class.getResource("list_test.fxml"))));
    }

    public void switchToManagement(ActionEvent e) throws IOException {
        ShareAppData.getInstance().setTabNo(2);
        content.getChildren().clear();
        testManagementButton.setStyle("-fx-background-color: #DFA98F");
        historyButton.setStyle("-fx-background-color: #E9C8BC");
        menuButton.setStyle("-fx-background-color: #E9C8BC");
        content.getChildren().add(FXMLLoader.load(Objects.requireNonNull(App.class.getResource("layout_list_test.fxml"))));
    }

    public void logOut(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logging out");
        alert.setHeaderText("Log out");
        alert.setContentText("Are you sure?");
        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");
        alert.showAndWait().ifPresent(type -> {
            if (type == buttonTypeYes) {
                System.out.println("hha");
                try {
                    App.setRoot("login");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }});
    }
}
