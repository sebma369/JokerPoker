package com.example.jokerpoker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;

import static java.lang.Thread.sleep;

public class ClientMain extends Application {
    public static HelloApplication helloApplication = new HelloApplication();
    Stage stage = new Stage();
    @Override
    public void start(Stage stage) throws Exception {
        helloApplication.start(stage);


    }
}
