package com.example.jokerpoker;

import com.example.jokerpoker.dao.AccountDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
    private TextField textUsername;

    @FXML
    private TextField textPassword;

    @FXML
    private TextField textCheckedPassword;

    @FXML
    public void submit(ActionEvent event) throws Exception {
        AccountDAO accountDAO = new AccountDAO();
        String user = textUsername.getText();
        String pass = textPassword.getText();
        String check = textCheckedPassword.getText();

        if (user == null || user.length() == 0 || pass == null || pass.length() == 0 || check == null || check.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("警告");
            alert.setContentText("账号或密码为空，请重新输入！");
            alert.setHeaderText("错误");
            alert.showAndWait();
        }
        else {
            if (pass.equals(check)) {
                if (accountDAO.checkUser(user, pass) == 1) {
                    //账号或密码已存在，请重新注册
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("警告");
                    alert.setContentText("账号或密码已存在，请重新注册！");
                    alert.setHeaderText("错误");
                    alert.showAndWait();
                } else {
                    accountDAO.saveUser(user, pass);
                    //注册成功
                    Stage stage = (Stage) btnLogin.getScene().getWindow();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("账号注册");
                    alert.setContentText("账号注册成功");
                    alert.setHeaderText("成功");
                    alert.showAndWait();
                    stage.show();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("警告");
                alert.setContentText("两次密码输入不一致！");
                alert.setHeaderText("错误");
                alert.showAndWait();
            }
        }


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
