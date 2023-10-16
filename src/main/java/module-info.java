module com.e01.quiz_management {
    requires javafx.controls;
    requires javafx.fxml;
    requires jfoenix;
    requires org.controlsfx.controls;
    requires java.sql;

    opens com.e01.quiz_management to javafx.fxml;
    exports com.e01.quiz_management;
}
