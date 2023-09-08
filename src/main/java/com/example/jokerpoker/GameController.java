package com.example.jokerpoker;
//gameWindow

import Server.Player;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameController {
    Player player;

    ArrayList<Integer> card = new ArrayList<>();

    @FXML
    Pane layeredPane; //放手牌

    @FXML
    Pane front; //按钮区域

    ImageIcon _bujiao = new ImageIcon("Client/img/0.png");
    ImageIcon _one = new ImageIcon("Client/img/1.png");
    ImageIcon _two = new ImageIcon("Client/img/2.png");
    ImageIcon _three = new ImageIcon("Client/img/3.png");

    ImageIcon bg = new ImageIcon("Client/img/desk.jpg"); //后面再改图片

    Button chupai = new Button();
    Button buchu = new Button();
    ImageIcon GG = new ImageIcon("Client/img/GG.png");
    ImageIcon GG_lord1 = new ImageIcon("Client/img/GG_lord1.png");
    ImageIcon GG_lord2 = new ImageIcon("Client/img/GG_lord2.png");
    ImageIcon chupai_btn = new ImageIcon("Client/img/chupai.png");
    ImageIcon chupai_false_btn = new ImageIcon("Client/img/chupai_false.png");
    ImageIcon buchu_btn = new ImageIcon("Client/img/buchu.png");


    
    public String serverMessage;
    
    public void printCards() {

    }

    public String returnDianshu(String serverMessage) {
        return "1";
    }

    public void refresh(String serverMessage) {
    }

    public void close() {
    }

    public void addSelf(String toString) {
    }

    public void addLord(String serverMessage) {
    }

    public String outCards() {

    }

    public void printPlayedCards(String s) {
    }
}
