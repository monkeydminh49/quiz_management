module com.e01.quiz_management {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires org.apache.poi.poi;
    requires spring.messaging;
//    requires slf4j.api;
    requires spring.websocket;
    requires org.apache.logging.log4j;

    opens com.e01.quiz_management.model to javafx.fxml, com.fasterxml.jackson.databind;
    opens com.e01.quiz_management.websocket to com.fasterxml.jackson.databind;
    opens com.e01.quiz_management.ui.test_create to javafx.fxml;
    opens com.e01.quiz_management to javafx.fxml;
    exports com.e01.quiz_management;
    exports com.e01.quiz_management.util;
    exports com.e01.quiz_management.model;
    exports com.e01.quiz_management.ui.authentication;
    exports com.e01.quiz_management.ui.test_form;
    exports com.e01.quiz_management.ui.test_create;
    opens com.e01.quiz_management.ui.authentication to javafx.fxml;
    opens com.e01.quiz_management.ui.test_form to javafx.fxml;
    exports com.e01.quiz_management.ui.menu;
    opens com.e01.quiz_management.ui.menu to javafx.fxml;
    exports com.e01.quiz_management.ui.list_test;
    opens com.e01.quiz_management.ui.list_test to javafx.fxml;
    exports com.e01.quiz_management.ui.test_form.controller;
    opens com.e01.quiz_management.ui.test_form.controller to javafx.fxml;
    exports com.e01.quiz_management.ui.test_create.controller;
    opens com.e01.quiz_management.ui.test_create.controller to javafx.fxml;
}
