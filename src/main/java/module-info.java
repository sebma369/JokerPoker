module com.example.jokerpoker {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.jokerpoker to javafx.fxml;
    exports com.example.jokerpoker;
}