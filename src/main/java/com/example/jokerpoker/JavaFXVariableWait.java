package com.example.jokerpoker;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class JavaFXVariableWait extends Application {

    private boolean variableChanged = false;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Variable Wait Example");

        Button startButton = new Button("Start");
        Button continueButton = new Button("Continue");

        startButton.setOnAction(event -> {
            // 启动一个新线程来等待变量的改变
            new Thread(() -> {
                synchronized (this) {
                    try {
                        while (!variableChanged) {
                            // 等待变量改变
                            wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // 在JavaFX主线程中更新UI
                Platform.runLater(() -> {
                    continueButton.setDisable(false);
                });
            }).start();
        });

        continueButton.setDisable(true);
        continueButton.setOnAction(event -> {
            // 点击"Continue"按钮后改变变量值，通知等待线程继续执行
            synchronized (this) {
                variableChanged = true;
                notify();
            }
        });

        StackPane root = new StackPane();
        root.getChildren().addAll(startButton, continueButton);
        primaryStage.setScene(new Scene(root, 300, 200));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
