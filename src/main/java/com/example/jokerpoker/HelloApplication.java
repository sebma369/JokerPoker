package com.example.jokerpoker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
//        Image image = new Image(getClass().getResource("bg/bgbg.jpg").toExternalForm(),400,600,false,true);
//        BackgroundImage backgroundImage = new BackgroundImage(image,
//                BackgroundRepeat.NO_REPEAT,
//                BackgroundRepeat.NO_REPEAT,
//                BackgroundPosition.DEFAULT,
//                null);
//        AnchorPane anchorPane = (AnchorPane) scene.getRoot();
//        anchorPane.setBackground(new Background(backgroundImage));
//        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}