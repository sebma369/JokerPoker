package com.example.jokerpoker;

import com.example.jokerpoker.dao.AccountDAO;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
    private TextField textUsername;

    @FXML
    private TextField textPassword;

    @FXML
    public void enroll(ActionEvent event) throws Exception {
        HelloApplication.enroll();
    }

    @FXML
    public void login(ActionEvent event) throws Exception {    //登录
        AccountDAO accountDAO = new AccountDAO();
        String user = textUsername.getText();
        String pass = textPassword.getText();
        if (accountDAO.checkUser(user,pass) == 1) {
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
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("警告");
            alert.setContentText("账号或密码错误");
            alert.setHeaderText("错误");
            alert.showAndWait();
        }
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
