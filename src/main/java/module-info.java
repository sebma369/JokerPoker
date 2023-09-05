module com.example.jokerpoker {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires commons.dbutils;

    opens com.example.jokerpoker to javafx.fxml;
    exports com.example.jokerpoker;
    exports com.example.jokerpoker.dao;
}