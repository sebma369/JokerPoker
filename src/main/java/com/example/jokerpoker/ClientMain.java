package com.example.jokerpoker;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientMain extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Player player = new Player();
        while(true){
            player.playGame();
        }
    }
}
