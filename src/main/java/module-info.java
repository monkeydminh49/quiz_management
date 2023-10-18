module com.e01.quiz_management {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;

    opens com.e01.quiz_management to javafx.fxml;
    exports com.e01.quiz_management;
    exports com.e01.quiz_management.util;
    exports com.e01.quiz_management.model;
    exports com.e01.quiz_management.authentication;
    opens com.e01.quiz_management.authentication to javafx.fxml;
}
