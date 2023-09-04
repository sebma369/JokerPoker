package com.example.jokerpoker;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private Label welcomeText;

    @FXML
    private Label labelTitle;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String fontPath=HelloController.class.getResource("fonts/spider.ttf").toExternalForm();
        Font spiderFont = Font.loadFont(fontPath,30);;
        this.labelTitle.setFont(spiderFont);
    }
}