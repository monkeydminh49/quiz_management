package com.e01.quiz_management.list_test;

import com.e01.quiz_management.App;
import com.e01.quiz_management.data.ShareAppData;
import com.e01.quiz_management.model.Test;
import com.e01.quiz_management.util.RequestAPI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

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
    @FXML
    Button backButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateTable();
        createTestButton.setOnAction(actionEvent -> {
            try {
                ShareAppData.getInstance().setTest(new Test());
                App.setRoot("addQuiz");
                ShareAppData.getInstance().setIsEdit(false);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        backButton.setOnAction(actionEvent -> {
            try {
                App.setRoot("menu");
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
                                App.setRoot("QuizInfo");
                                ShareAppData.getInstance().setTest(data);
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
                            tableView.getItems().remove(data);
                            RequestAPI.getInstance().deleteTest(data.getId());
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

    private void updateTable() {
        List<Test> tests = new ArrayList<>();
        tests = RequestAPI.getInstance().getAllUserTests();
        ObservableList<Test> observableArrayList =
                FXCollections.observableArrayList(tests);
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
}
