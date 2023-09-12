package com.example.jokerpoker;

import com.example.jokerpoker.dao.AccountDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
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
    private PasswordField textPassword;

    public Player player;

    public void enroll(ActionEvent event) throws Exception {
        HelloApplication.enroll();
    }

    public void login(ActionEvent event) throws Exception {    //登录
        AccountDAO accountDAO = new AccountDAO();
        String user = textUsername.getText();
        String pass = textPassword.getText();
        if (accountDAO.checkUser(user,pass) == 1) {
            if (accountDAO.checkOnline(user)) {
                accountDAO.updateOnline(user, 1);
                HelloApplication.login = true;
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success!");
                alert.setContentText("登陆成功");
                alert.showAndWait();
                Stage stage = (Stage) btnClose.getScene().getWindow();
                stage.close();
                Player p = new Player();
                p.setUsername(user);
                p.playGame();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("警告");
                alert.setContentText("该账号已登录");
                alert.setHeaderText("错误");
                alert.showAndWait();
            }
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
