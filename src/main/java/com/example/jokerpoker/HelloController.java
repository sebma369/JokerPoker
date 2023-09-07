package com.example.jokerpoker;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class HelloController {
    @FXML
    private Button btnEnroll;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnClose;

    @FXML
    private Button btnMin;

    @FXML
    public void enroll(ActionEvent event) throws Exception {
            Stage stage = (Stage) btnEnroll.getScene().getWindow();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("enroll-view.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

    }

    @FXML
    public void login(ActionEvent event) throws Exception {
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("game-view.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
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
