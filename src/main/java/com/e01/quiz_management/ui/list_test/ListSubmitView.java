package com.e01.quiz_management.ui.list_test;

import com.e01.quiz_management.App;
import com.e01.quiz_management.data.ShareAppData;
import com.e01.quiz_management.model.Test;
import com.e01.quiz_management.model.TestHistory;
import com.e01.quiz_management.util.*;
import com.e01.quiz_management.websocket.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class    ListSubmitView  implements Initializable{

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
    @FXML
    private AnchorPane pane;

    private Test currentTest;
    private List<TestHistory> testHistories;

    private static ListSubmitView instance;
    public static ListSubmitView getInstance(){
        if (instance == null){
            instance = new ListSubmitView();
        }
        return instance;
    }

    public ListSubmitView(){
        testDescription = new TextArea();
        backButton = new Button();
        myTable = new TableView<>();
        orderColumn = new TableColumn<>();
        titleColumn = new TableColumn<>();
        codeColumn = new TableColumn<>();
        scoreColumn = new TableColumn<>();
        submitTimeColumn = new TableColumn<>();
        pane = new AnchorPane();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        testHistories = new ArrayList<>();
        currentTest = ShareAppData.getInstance().getTestLive();
        testHistories = RequestAPI.getInstance().getTestHistoriesByTestId(currentTest.getId());

        WebSocketConnect.getInstance().subscribeToTest(ShareAppData.getInstance().getTest().getId(), new WebSocketConnectHandler() {

            @Override
            public void onReceived(Object payload) {
                Message msg = (Message) payload;
                System.out.println("received message haha");
                if (msg.getType() == EMessageType.NUM_LIVE_PARTICIPANT){
                    List<Test> tests = ShareAppData.getInstance().getTests();
                    for (Test test : tests) {
                        if (test.getId().equals( ShareAppData.getInstance().getTest().getId())) {
                            test.setNumberOfLiveParticipant((Integer) msg.getData());
                            ShareAppData.getInstance().setTestLive(test);
                            break;
                        }
                    }
                    System.out.println("User " + msg.getFrom() + " join!");
                    updateTestDescription();
                    ListSubmitView.getInstance().newUserJoinEffect(msg.getFrom());
                } else if (msg.getType() == EMessageType.TEST_HISTORY) {
                    System.out.println("User " + msg.getFrom() + " submit!");
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.registerModule(new JavaTimeModule());
                    String data ="{}";
                    TestHistory testHistory = null;
                    try {
                        data = mapper.writeValueAsString(msg.getData());
                        System.out.println(data);
                        testHistory = mapper.readValue(data, TestHistory.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(testHistory.getCode());
                    testHistories.add(testHistory);
                    updateData();
                }

            }
        });

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

    public void newUserJoinEffect(String name){
        System.out.println("Effect");
        String[] colors = new String[] {"#FF0000", "#FF7F00", "#FFFF00", "#00FF00", "#0000FF", "#4B0082", "#9400D3"};
        Text text = new Text(name + " has joined the test!");
        text.getStyleClass().add("button-text");
        text.setX(50);
        text.setY(450);
        text.setStyle("-fx-fill: " + colors[(int) (Math.random() * colors.length)] + ";");


        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(text);
        translateTransition.setDuration(Duration.millis(3500));
        translateTransition.setByY(-100);
        translateTransition.play();

        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setNode(text);
        fadeTransition.setDuration(Duration.millis(3500));
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished(actionEvent -> {
            pane.getChildren().remove(text);
        });
        fadeTransition.play();
        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    // Update UI here.
                    pane.getChildren().add(text);

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("hello 123");

    }

    private void initData(){

    }

    public void updateData(){
        updateTestDescription();
        System.out.println( "Num test history: "+ testHistories.size());
        ObservableList<TestHistory> observableArrayList =
                FXCollections.observableArrayList(testHistories);

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

