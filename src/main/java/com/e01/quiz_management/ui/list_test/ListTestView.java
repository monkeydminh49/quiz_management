package com.e01.quiz_management.ui.list_test;

import com.e01.quiz_management.App;
import com.e01.quiz_management.ui.test_create.QuestionDataShared;
import com.e01.quiz_management.data.ShareAppData;
import com.e01.quiz_management.model.Test;
import com.e01.quiz_management.util.RequestAPI;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ListTestView implements Initializable {
    @FXML
    TableView<Test> tableView;
    @FXML
    TableColumn<Test, String> orderColumn;
    @FXML
    TableColumn<Test, String> codeColumn;
    @FXML
    TableColumn<Test, String> titleColumn;
    @FXML
    TableColumn<Test, LocalDateTime> startColumn;
    @FXML
    TableColumn<Test, Long> durationColumn;
    @FXML
    TableColumn<Test, Void> actionColumn;
    @FXML
    Button createTestButton;
    private static ListTestView instance;

    public ListTestView() {
        orderColumn = new TableColumn<>();
        codeColumn = new TableColumn<>();
        titleColumn = new TableColumn<>();
        startColumn = new TableColumn<>();
        durationColumn = new TableColumn<>();
        actionColumn = new TableColumn<>();
        tableView = new TableView<>();
        createTestButton = new Button();

    }

    public static ListTestView getInstance() {
        if (instance == null) {
            instance = new ListTestView();
        }
        return instance;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateTable();
        createTestButton.setOnAction(actionEvent -> {
            try {
                QuestionDataShared.getInstance().setQuestions(new ArrayList<>());
                App.setRoot("addQuiz");
                ShareAppData.getInstance().setIsEdit(false);
                ShareAppData.getInstance().setTest(new Test());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void addButtonToTable() {
        TableColumn<Test, Void> colBtn = new TableColumn<>("Button Column");
        Callback<TableColumn<Test, Void>, TableCell<Test, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Test, Void> call(final TableColumn<Test, Void> param) {
                final TableCell<Test, Void> cell = new TableCell<>() {
                    private final Button btn = new Button("Edit");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Test data = getTableView().getItems().get(getIndex());
                            try {
                                App.setRoot("addQuiz");
                                ShareAppData.getInstance().setTest(data);
                                QuestionDataShared.getInstance().setQuestions(data.getQuestions());
                                ShareAppData.getInstance().setIsEdit(true);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    }

                    private final Button btn2 = new Button("Delete");

                    {
                        btn2.setOnAction((ActionEvent event) -> {
                            Test data = getTableView().getItems().get(getIndex());
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Delete Test");
                            alert.setHeaderText("Delete Test");
                            alert.setContentText("Are you sure?");
                            ButtonType buttonTypeYes = new ButtonType("Yes");
                            ButtonType buttonTypeNo = new ButtonType("No");
                            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
                            alert.showAndWait().ifPresent(type -> {
                                if (type == buttonTypeYes) {
                                    tableView.getItems().remove(data);
                                    Task<Boolean> task = new Task<>() {
                                        @Override
                                        protected Boolean call() throws Exception {
                                            return RequestAPI.getInstance().deleteTest(data.getId());
                                        }
                                    };

                                    task.setOnSucceeded(event1 -> {
                                        if (task.getValue()) {
                                            ShareAppData.getInstance().getTests().remove(data);
                                        } else {
                                            updateTable();
                                            Alert alert1 = new Alert(Alert.AlertType.ERROR);
                                            alert1.setTitle("Delete Test");
                                            alert1.setHeaderText("Delete Test");
                                            alert1.setContentText("Delete Test failed!");
                                            alert1.showAndWait();
                                        }
                                    });
                                    new Thread(task).start();
                                }
                            });
                        });
                    }

                    private final Button btn3 = new Button("Detail");

                    {
                        btn3.setOnAction((ActionEvent event) -> {
                            Test data = getTableView().getItems().get(getIndex());
                            try {
                                App.setRoot("layout_list_submit");
                                ShareAppData.getInstance().setTest(data);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    }

                    private final HBox pane = new HBox(btn, btn2, btn3);

                    {
                        pane.setSpacing(10);
                        pane.setStyle("-fx-alignment: CENTER");
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);

                        } else {
                            setGraphic(pane);
                            setText(null);
                        }
                    }


                };
                return cell;
            }
        };
        colBtn.setCellFactory(cellFactory);
        actionColumn.setCellFactory(cellFactory);
    }

    public void updateTable() {
//        Response<List<Test>> listTestResponse = ShareAppData.getInstance().getListTestResponse();
        List<Test> tests = new ArrayList<>();
        tests = ShareAppData.getInstance().getTests();
        System.out.println("update");
        ObservableList<Test> observableArrayList = FXCollections.observableArrayList(tests);
        orderColumn.setCellFactory(column -> {
            return new TableCell<Test, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : getIndex() + 1 + "");
                }
            };
        });
//        neu data null thi ghi la <empty>
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        tableView.setItems(observableArrayList);
        addButtonToTable();
    }

    public void onMousePressed() {
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(this.createTestButton);
        translateTransition.setDuration(Duration.millis(65));
        translateTransition.setByY(5);
        translateTransition.play();
    }

    public void onMouseRelease() {
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(this.createTestButton);
        translateTransition.setDuration(Duration.millis(65));
        translateTransition.setByY(-5);
        translateTransition.play();
    }
}
