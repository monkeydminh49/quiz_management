package com.e01.quiz_management.controller;

import com.e01.quiz_management.model.Test;
import com.e01.quiz_management.util.RequestAPI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ListTestController implements Initializable {

    @FXML
    private TableView<Test> myTable;
    @FXML
    private TableColumn<Test, String> orderColumn;
    @FXML
    private TableColumn<Test, String> titleColumn;
    @FXML
    private TableColumn<Test, String> codeColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Test> tests = RequestAPI.getInstance().getAllUserTests();
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
        myTable.setItems(observableArrayList);
    }
}
