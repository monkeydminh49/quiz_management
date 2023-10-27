package com.e01.quiz_management.list_test;

import com.e01.quiz_management.App;
import com.e01.quiz_management.data.ShareAppData;
import com.e01.quiz_management.model.Choice;
import com.e01.quiz_management.model.Question;
import com.e01.quiz_management.model.Test;
import com.e01.quiz_management.util.RequestAPI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        orderColumn.setPrefWidth(orderColumn.getMinWidth());
        codeColumn.setPrefWidth(codeColumn.getMinWidth());
        titleColumn.setPrefWidth(titleColumn.getMinWidth());
        startColumn.setPrefWidth(startColumn.getMinWidth());
        durationColumn.setPrefWidth(durationColumn.getMinWidth());
        updateTable();
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
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    }

                    private final Button btn2 = new Button("Delete");

                    {
                        btn2.setOnAction((ActionEvent event) -> {
                            Test data = getTableView().getItems().get(getIndex());
                            RequestAPI.getInstance().deleteTest(data.getId());
                            updateTable();
                        });
                    }

                    private final HBox pane = new HBox(btn, btn2);
                    {
                        pane.setSpacing(10);
                        pane.setAlignment(javafx.geometry.Pos.CENTER);
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
        titleColumn.setCellValueFactory(new PropertyValueFactory<Test, String>("title"));
        codeColumn.setCellValueFactory(new PropertyValueFactory<Test, String>("code"));
        startColumn.setCellValueFactory(new PropertyValueFactory<Test, LocalDateTime>("startTime"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<Test, Long>("duration"));
        tableView.setItems(observableArrayList);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        addButtonToTable();
    }
}
