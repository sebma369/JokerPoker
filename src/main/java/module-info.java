module com.example.jokerpoker {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires commons.dbutils;
    requires java.desktop;

    opens com.example.jokerpoker to javafx.fxml;
    exports com.example.jokerpoker;
    exports com.example.jokerpoker.dao;

    //opens com.example.jokerpoker.Client to javafx.fxml;
}