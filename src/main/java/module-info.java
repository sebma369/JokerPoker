module com.example.jokerpoker {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.jokerpoker to javafx.fxml;
    exports com.example.jokerpoker;
}