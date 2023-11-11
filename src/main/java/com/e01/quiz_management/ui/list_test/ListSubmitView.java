package com.e01.quiz_management.ui.list_test;

import com.e01.quiz_management.App;
import com.e01.quiz_management.data.ShareAppData;
import com.e01.quiz_management.model.Test;
import com.e01.quiz_management.model.TestHistory;
import com.e01.quiz_management.util.ETestStatus;
import com.e01.quiz_management.util.RequestAPI;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class    ListSubmitView implements Initializable {

    @FXML
    private TableView<TestHistory> myTable;
    @FXML
    private TableColumn<TestHistory, String> orderColumn;
    @FXML
    private TableColumn<TestHistory, String> titleColumn;
    @FXML
    private TableColumn<TestHistory, String> codeColumn;
    @FXML
    private TableColumn<TestHistory, Integer> scoreColumn;
    @FXML
    private TableColumn<TestHistory, String> submitTimeColumn;
    @FXML
    private TextArea testDescription;
    @FXML
    private Button backButton;

    private Test currentTest;
    private List<TestHistory> testHistories;

    private static ListSubmitView instance;
    public static ListSubmitView getInstance(){
        if (instance == null){
            instance = new ListSubmitView();
        }
        return instance;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initData();
        updateData();
        initComponent();
    }

    private void initComponent(){
        backButton.setOnAction(actionEvent -> {
            try {
                ShareAppData.getInstance().setTest(null);
                App.setRoot("menu");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        backButton.setOnAction(actionEvent -> {
            try {
                ShareAppData.getInstance().setTest(null);
                App.setRoot("menu");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        backButton.setOnMousePressed(event -> {
            TranslateTransition translateTransition = new TranslateTransition();
            translateTransition.setNode(this.backButton);
            translateTransition.setDuration(Duration.millis(65));
            translateTransition.setByY(5);
            translateTransition.play();
        });
        backButton.setOnMouseReleased(event -> {
            TranslateTransition translateTransition = new TranslateTransition();
            translateTransition.setNode(this.backButton);
            translateTransition.setDuration(Duration.millis(65));
            translateTransition.setByY(-5);
            translateTransition.play();
        });
    }

    private void initData(){
        testHistories = new ArrayList<>();
        currentTest = ShareAppData.getInstance().getTestLive();
        testHistories = RequestAPI.getInstance().getTestHistoriesByTestId(currentTest.getId());
        orderColumn.setCellFactory(column -> {
            return new TableCell<TestHistory, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : getIndex() + 1 + "");
                }
            };
        });
        titleColumn.setCellValueFactory(new PropertyValueFactory<TestHistory, String>("title"));
        codeColumn.setCellValueFactory(new PropertyValueFactory<TestHistory, String>("candidateName"));
        submitTimeColumn.setCellValueFactory(cellData -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
            if (cellData.getValue().getSubmitTime() != null) {
                String time = cellData.getValue().getSubmitTime().format(formatter);

                return new SimpleStringProperty(time);
            } else {
                return new SimpleStringProperty("<no data>");
            }
        });
        scoreColumn.setCellValueFactory(new PropertyValueFactory<TestHistory, Integer>("score"));
    }

    public void updateData(){
        updateTestDescription();
        ObservableList<TestHistory> observableArrayList =
                FXCollections.observableArrayList(testHistories);

        myTable.setItems(observableArrayList);
    }
    public void updateTestDescription(){
        testDescription.setText(ShareAppData.getInstance().getTestLive().getTestDescription() + "\nNumber of submissions: " + testHistories.size());
        if (currentTest.getStatus() == ETestStatus.HAPPENING){
            testDescription.setText(testDescription.getText() + "\nNumber of live participant: " + ShareAppData.getInstance().getTestLive().getNumberOfLiveParticipant() );
        }
    }

    public List<TestHistory> getTestHistories() {
        return testHistories;
    }
}

