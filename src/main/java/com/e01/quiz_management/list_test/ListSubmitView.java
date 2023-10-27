package com.e01.quiz_management.list_test;

import com.e01.quiz_management.data.ShareAppData;
import com.e01.quiz_management.model.TestHistory;
import com.e01.quiz_management.util.RequestAPI;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ListSubmitView implements Initializable {

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<TestHistory> tests = new ArrayList<>();
        tests = RequestAPI.getInstance().getTestHistoriesByTestId(ShareAppData.getInstance().getTest().getId());
        System.out.println(ShareAppData.getInstance().getTest().getId());
        testDescription.setText(ShareAppData.getInstance().getTest().getTestDescription() + "\nNumber of submissions: " + tests.size());
        ObservableList<TestHistory> observableArrayList =
                FXCollections.observableArrayList(tests);
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
}

