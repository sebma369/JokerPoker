package com.example.jokerpoker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;

import static java.awt.Desktop.getDesktop;

public class EnrollController {
    @FXML
    private Button btnLogin;

    @FXML
    private Button btnClose;

    @FXML
    private Button btnMin;

    @FXML
    public void submit(ActionEvent event) throws Exception {
        Stage stage = (Stage) btnLogin.getScene().getWindow();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void Team(ActionEvent event) throws Exception {
        getDesktop().browse(new URI("https://github.com/sebma369/JokerPoker"));
    }

    public void close(ActionEvent event) throws Exception {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    public void min(ActionEvent event) throws Exception {
        Stage stage = (Stage) btnMin.getScene().getWindow();
        stage.setIconified(true);
    }
}
