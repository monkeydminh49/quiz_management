module com.e01.quiz_management {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires org.apache.poi.poi;

    opens com.e01.quiz_management.controller to javafx.fxml;
    opens com.e01.quiz_management to javafx.fxml;
    exports com.e01.quiz_management;
    exports com.e01.quiz_management.util;
    exports com.e01.quiz_management.model;
    exports com.e01.quiz_management.authentication;
    exports com.e01.quiz_management.test_form;
    exports com.e01.quiz_management.controller;
    opens com.e01.quiz_management.model to com.fasterxml.jackson.databind;
    opens com.e01.quiz_management.authentication to javafx.fxml;
    opens com.e01.quiz_management.test_form to javafx.fxml;
    exports com.e01.quiz_management.menu;
    opens com.e01.quiz_management.menu to javafx.fxml;
    exports com.e01.quiz_management.list_test;
    opens com.e01.quiz_management.list_test to javafx.fxml;
}
