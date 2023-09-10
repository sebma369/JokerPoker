package com.example.jokerpoker;
//gameWindow

import Server.Player;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Thread.sleep;

public class GameController {
    Player player;

    ArrayList<Integer> card = new ArrayList<>();

    @FXML
    Pane layeredPane; //放手牌

    @FXML
    Pane front; //按钮区域

    //先不设定抢点机制
    Button chupai;
    Button buchu;

    Image bg;
    Image GG;
    Label player1;
    Label player2;
    Image GG_lord1Image;
    ImageView GG_lord1;
    Image GG_lord2Image;
    ImageView GG_lord2;
    Image chupai_btnImage;
    ImageView chupai_btn;
    Image chupai_false_btnImage;
    ImageView chupai_false_btn;
    Image buchu_btn;
    //imageView那些之后再搞
    Label player1_num;
    Label player2_num;
    Image poker_backImage;
    ImageView poker_back;
    @FXML
    Pane dipai;
    Label dipai_1;
    Label dipai_2;
    Label dipai_3;
    Pane btn_group2;
    Font font;
    Font font2;
    private String player1_id;
    private String player2_id;

    private int player1_deckNum;
    private int player2_deckNum;
    int x, y, width, height;
    private Boolean isOutCards;

    public String serverMessage;
    Image[] images;
    ImageView[] smallImages;
    private int last_cardNum;
    private int last_haveCards;
    Label[] labels = new Label[20];


    public void initialize(){

    }


    public GameController(int x, int y, Player player){
        chupai = new Button();
        buchu = new Button();

        bg = new Image(getClass().getResource("Client/img/GG.png").toExternalForm()); //后面再改图片

        GG = new Image(getClass().getResource("Client/img/GG.png").toExternalForm());
        ImageView GGView = new ImageView(GG);
        Label player1 = new Label("", GGView);
        Label player2 = new Label("", GGView);
        GG_lord1Image = new Image(getClass().getResource("Client/img/GG.png").toExternalForm());
        ImageView GG_lord1 = new ImageView(GG_lord1Image);
        GG_lord2Image = new Image(getClass().getResource("Client/img/GG.png").toExternalForm());
        ImageView GG_lord2 = new ImageView(GG_lord2Image);
        chupai_btnImage = new Image(getClass().getResource("Client/img/GG.png").toExternalForm());
        ImageView chupai_btn = new ImageView(chupai_btnImage);
        chupai_false_btnImage = new Image(getClass().getResource("Client/img/GG.png").toExternalForm());
        chupai_false_btn = new ImageView(chupai_false_btnImage);
        buchu_btn = new Image(getClass().getResource("Client/img/GG.png").toExternalForm());
        ImageView buchu_btnView = new ImageView(buchu_btn);
        player1_num = new Label(); //玩家手牌数
        player2_num = new Label();
        poker_backImage = new Image(getClass().getResource("Client/img/GG.png").toExternalForm());
        poker_back = new ImageView(poker_backImage);
        font = new Font("宋体", Font.BOLD, 20);
        font2 = new Font("宋体", Font.BOLD, 16);
        player1_id = "-1";
        player2_id = "-1";
        x = 450;
        y = 210;
        width = 650;
        height = 450;
        isOutCards = false;
        images = new Image[15];//卡牌图片
        smallImages = new Image[15];//小图片
        last_cardNum = 0;//出牌区上次的卡牌数
        last_haveCards = 0;//手牌区上次手牌数量


        this.player = player;
        this.x = x;
        this.y = y;
        buchu.setGraphic(buchu_btnView);
        chupai.setGraphic(chupai_false_btn);
        chupai.setOnAction(e->{
            isOutCards = true;
            front.getChildren().remove(btn_group2);
        });

        buchu.setOnAction(e -> {
            if (CompareCard.getInstance().getPlayer().equals("m")) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setContentText("必须出牌");
                alert.showAndWait();
            } else {
                for (Integer i : card) {
                    labels[i].setTranslateY(labels[i].getTranslateY() + 20);
                }
                card.clear();
                isOutCards = true;
                front.getChildren().remove(btn_group2);
            }
        });
        btn_group2.setStyle("-fx-background-color: transparent;");
        images[0] = new Image(getClass().getResource().toExternalForm());
        images[1] = new Image(getClass().getResource().toExternalForm());
        images[2] = new Image(getClass().getResource().toExternalForm());
        images[3] = new Image(getClass().getResource().toExternalForm());
        images[4] = new Image(getClass().getResource().toExternalForm());
        images[5] = new Image(getClass().getResource().toExternalForm());
        images[6] = new Image(getClass().getResource().toExternalForm());
        images[7] = new Image(getClass().getResource().toExternalForm());
        images[8] = new Image(getClass().getResource().toExternalForm());
        images[9] = new Image(getClass().getResource().toExternalForm());
        images[10] = new Image(getClass().getResource().toExternalForm());
        images[11] = new Image(getClass().getResource().toExternalForm());
        images[12] = new Image(getClass().getResource().toExternalForm());
        images[13] = new Image(getClass().getResource().toExternalForm());
        images[14] = new Image(getClass().getResource().toExternalForm());

        ImageView[] smallImages = new ImageView[15];

        for (int i = 0; i < 15; i++) {
            Image fxImage = images[i];

            // 创建缩小的图像
            double scaledWidth = fxImage.getWidth() * 3 / 4;
            double scaledHeight = fxImage.getHeight() * 3 / 4;

            Image smallImage = new Image(fxImage.getUrl(), scaledWidth, scaledHeight, true, true);
            ImageView imageView = new ImageView(smallImage);

            smallImages[i] = imageView;
        }
        // 创建 GridPane 布局
        GridPane gridPane = new GridPane();

// 设置网格布局的行和列约束
        RowConstraints rowConstraints = new RowConstraints();
        ColumnConstraints columnConstraints = new ColumnConstraints();

        gridPane.getRowConstraints().add(rowConstraints);
        for (int i = 0; i < 11; i++) {
            gridPane.getColumnConstraints().add(columnConstraints);
        }

// 将 GridPane 添加到 Pane 中
        dipai.getChildren().add(gridPane);
        dipai.getChildren().add(dipai_1);
        dipai.getChildren().add(dipai_2);
        dipai.getChildren().add(dipai_3);
        for(int i = 0; i < 4; i++){
            dipai.getChildren().add(new Label());
        }
        dipai_1.setGraphic(poker_back);
        dipai_2.setGraphic(poker_back);
        dipai_3.setGraphic(poker_back);
        dipai.setStyle("-fx-background-color: transparent;");
    }
    public void showGame(){

    }
    //加载地主和底牌图标
    public void addLord(String s){
        String[] str = s.split("");
        addIcon(dipai_1, str[1], this.smallImages);
        addIcon(dipai_2, str[2], this.smallImages);
        addIcon(dipai_3, str[3], this.smallImages);
        if (str[0].equals(player1_id)) {
            player1.setGraphic(GG_lord1);
            this.player1_deckNum = 20;
            this.player2_deckNum = 17;
        }
        if (str[0].equals(player2_id)) {
            player2.setGraphic(GG_lord2);
            this.player1_deckNum = 17;
            this.player2_deckNum = 20;
        }
        player1_num.setText(player1_deckNum + "张");
        player2_num.setText(player2_deckNum + "张");

    }

    public void addSelf(String s) {
        Image lordImage = new Image();
        ImageView lord = new ImageView(lordImage);
        Label l = new Label("", lord);
        //设置底牌
        String[] str = s.split("");
        addIcon(dipai_1, str[0], this.smallImages);
        addIcon(dipai_2, str[1], this.smallImages);
        addIcon(dipai_3, str[2], this.smallImages);
        l.setLayoutX(0);
        l.setLayoutY(l.getLayoutY() - l.getHeight() - 50);

        l.setStyle("-fx-background-color: transparent;");
        layeredPane.getChildren().add(l);
        layeredPane.toFront();

        player1_deckNum = 17;
        player2_deckNum = 17;
        player1_num.setText(player1_deckNum + "张");
        player2_num.setText(player2_deckNum + "张");
    }

    //更新记录玩家编号
    public void refresh(String dianshu) {
        String[] str = dianshu.split("");
        if (this.player1_id.equals("-1")) {
            this.player1_id = str[0];
            if (str[1].equals("0"))
                player1_num.setText("不叫");
            else if (str[1].equals("1"))
                player1_num.setText("1分");
            else if (str[1].equals("2"))
                player1_num.setText("2分");
            else
                player1_num.setText("3分");
            return;
        }
        this.player2_id = str[0];
        if (str[1].equals("0"))
            player2_num.setText("不叫");
        else if (str[1].equals("1"))
            player2_num.setText("1分");
        else if (str[1].equals("2"))
            player2_num.setText("2分");
        else
            player2_num.setText("3分");
    }

    public void showLordWin() throws InterruptedException {
        Image lordWinImage = new Image();
        ImageView lordWin = new ImageView(lordWinImage);
        Label label = new Label("", lordWin);

        label.setLayoutX(width / 2 - lordWin.getFitWidth() / 2);
        label.setLayoutY(height / 2 - lordWin.getFitHeight() / 2);
        layeredPane.getChildren().add(label);

        //layeredPane.setLayer(label, 290);
        //JAVAFX不能设置层级
        sleep(3000);
    }

    public void showLordLose() throws InterruptedException {
        Image lordLoseImage = new Image();
        ImageView lordLose = new ImageView(lordLoseImage);
        Label label = new Label("", lordLose);

        label.setLayoutX(width / 2 - lordLose.getFitWidth() / 2);
        label.setLayoutY(height / 2 - lordLose.getFitHeight() / 2);
        layeredPane.getChildren().add(label);

        //layeredPane.setLayer(label, 290);
        //JAVAFX不能设置层级
        sleep(3000);
    }

    public void showFarmerWin() throws InterruptedException {
        Image farmerWinImage = new Image();
        ImageView farmerWin = new ImageView(farmerWinImage);
        Label label = new Label("", farmerWin);

        label.setLayoutX(width / 2 - farmerWin.getFitWidth() / 2);
        label.setLayoutY(height / 2 - farmerWin.getFitHeight() / 2);
        layeredPane.getChildren().add(label);

        //layeredPane.setLayer(label, 290);
        //JAVAFX不能设置层级
        sleep(3000);
    }

    public void showFarmerLose() throws InterruptedException {
        Image farmerLoseImage = new Image();
        ImageView farmerLose = new ImageView(farmerLoseImage);
        Label label = new Label("", farmerLose);

        label.setLayoutX(width / 2 - farmerLose.getFitWidth() / 2);
        label.setLayoutY(height / 2 - farmerLose.getFitHeight() / 2);
        layeredPane.getChildren().add(label);

        //layeredPane.setLayer(label, 290);
        //JAVAFX不能设置层级
        sleep(3000);
    }

    public String outCards() throws InterruptedException{
        chupai.setDisable(true);
        this.front.getChildren().add(btn_group2);
        BorderPane borderPane = new BorderPane();
        // 添加btn_group2到BorderPane的底部（南部）
        borderPane.setBottom(btn_group2);
        while (!this.isOutCards) {
            sleep(10);
        }
        this.isOutCards = false;
        StringBuilder stringBuilder = new StringBuilder();
        if (this.card == null)
            return "";
        for (Integer i : this.card) {
            stringBuilder.append(player.getDeck().get(i));
        }
        card.clear();
        for(Label label: labels){
            layeredPane.getChildren().remove(label);
        }
        return stringBuilder.toString();
    }

    public void printCards() {
        for (int i = 0; i < last_haveCards; i++) {
            layeredPane.getChildren().remove(labels[i]);
        }
        this.last_haveCards = player.getDeck().size();
        for (int i = 0; i < 20; i++) {
            Label l = new Label();
            labels[i] = l;
        }
        double w = images[0].getWidth();
        double h = images[0].getHeight();
        int num = player.getDeck().size();
        double total_w = (num * w + 2 * w) / 3;
        int floor = 250;
        for(int i = 0; i < num; i++) {
            addIcon(labels[i], player.getDeck().get(i), this.images);
            labels[i].setLayoutX(width / 2 - total_w / 2 + i * w / 3);
            labels[i].setLayoutY(height / 2 + 70);
            labels[i].setPrefHeight(h + 30);
            layeredPane.getChildren().add(labels[i]);
            int finalI = i;
            labels[i].setOnMouseClicked(event -> {
                if (!card.contains(finalI)) {
                    card.add(finalI);
                    labels[finalI].setTranslateY(labels[finalI].getTranslateY() - 20);
                } else {
                    card.remove(finalI);
                    labels[finalI].setTranslateY(labels[finalI].getTranslateY() + 20);
                }
                CompareCard compareCard = CompareCard.getInstance();
                StringBuilder stringBuilder = new StringBuilder();
                if (card.size() == 0) {
                    chupai.setGraphic(chupai_false_btn);
                    chupai.setDisable(false);
                } else {
                    for (Integer in : card) {
                        String s = player.getDeck().get(in);
                        stringBuilder.append(s);
                    }
                    if (compareCard.compare(stringBuilder.toString())) {
                        chupai.setGraphic(chupai_btn);
                        chupai.setDisable(true);
                    } else {
                        chupai.setGraphic(chupai_false_btn);
                        chupai.setDisable(false);
                    }
                }
            });


            }
        }
    Label[] playedCards = new Label[20];
    public void printPlayedCards(String s) throws InterruptedException{
        String[] str = s.split("");
        if (str.length == 1) {
            pass(s);
            return;
        }
        for (int i = 0; i < this.last_cardNum; i++) {
            layeredPane.getChildren().remove(playedCards[i]);
        }
        String p = str[0];
        List<String> list = Arrays.asList(s.substring(1, str.length).split(""));
        ArrayList<String> cards = new ArrayList<>(list);
        int bias = 130;
        double w = images[0].getWidth() * 3 / 4;
        double h = images[0].getWidth() * 3 / 4;
        int num = cards.size();
        System.out.println(num);//debug
        this.last_cardNum = num;
        double total_w = (num * w + 2 * w) / 3;
        int floor = 270;
        for (int i = 0; i < 20; i++) {
            playedCards[i] = new Label();
        }
        for (int i = 0; i < num; i++) {
            addIcon(playedCards[i], cards.get(i), this.smallImages);
            if (p.equals("m")) {
                playedCards[i].setLayoutX(width / 2 - total_w / 2 + i * w / 3);
                playedCards[i].setLayoutY(height / 2 - 65);
                playedCards[i].setPrefHeight(h + 30);
            }
            else if (p.equals(player1_id)) {
                playedCards[i].setLayoutX(bias + i * w / 3);
                playedCards[i].setLayoutY(height / 2 - 65);
                playedCards[i].setPrefHeight(h + 30);
                player1_deckNum -= 1;
                player1_num.setText(player1_deckNum + "张");
            } else {
                playedCards[i].setLayoutX(width - bias - total_w + i * w / 3);
                playedCards[i].setLayoutY(height / 2 - 65);
                playedCards[i].setPrefHeight(h + 30);
                player2_deckNum -= 1;
                player2_num.setText(player2_deckNum + "张");
            }
            layeredPane.getChildren().add(playedCards[i]);
            playedCards[i].toFront();
        }

    }

    public void pass(String s) throws InterruptedException{
        Image passImage = new Image("");//过的图标
        ImageView pass = new ImageView(passImage);
        if (s.equals(player1_id)) {
            player1_num.setGraphic(pass);
            sleep(1000);
            player1_num.setGraphic(null);
        } else if (s.equals(player2_id)) {
            player2_num.setGraphic(pass);
            sleep(1000);
            player2_num.setGraphic(null);
        }
    }

    public void addIcon(Label label, String card, ImageView[] images){
        if (card.equals("A"))
            label.setGraphic(images[0]);
        else if (card.equals("2"))
            label.setGraphic(images[1]);
        else if (card.equals("3"))
            label.setGraphic(images[2]);
        else if (card.equals("4"))
            label.setGraphic(images[3]);
        else if (card.equals("5"))
            label.setGraphic(images[4]);
        else if (card.equals("6"))
            label.setGraphic(images[5]);
        else if (card.equals("7"))
            label.setGraphic(images[6]);
        else if (card.equals("8"))
            label.setGraphic(images[7]);
        else if (card.equals("9"))
            label.setGraphic(images[8]);
        else if (card.equals("X"))
            label.setGraphic(images[9]);
        else if (card.equals("J"))
            label.setGraphic(images[10]);
        else if (card.equals("Q"))
            label.setGraphic(images[11]);
        else if (card.equals("K"))
            label.setGraphic(images[12]);
        else if (card.equals("w"))
            label.setGraphic(images[13]);
        else
            label.setGraphic(images[14]);
    }

    public void close(){
        Stage stage = (Stage) Platform.runLater(() -> {}).getScene().getWindow();

        // 隐藏窗口
        stage.hide();
    }

    public int[] getLocate(Stage stage) {

    }

}