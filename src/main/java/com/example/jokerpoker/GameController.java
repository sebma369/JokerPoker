package com.example.jokerpoker;
//gameWindow

import com.example.jokerpoker.dao.AccountDAO;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

import static java.lang.Thread.sleep;

public class GameController {
    public Player player;

    ArrayList<Integer> card = new ArrayList<>();
    @FXML
    Pane prevPlayerShowPane;

    @FXML
    Pane nextPlayerShowPane;

    @FXML
    Pane layeredPane; //放手牌

    @FXML
    ImageView Player1ImageView;
    @FXML
    ImageView Player2ImageView;
    @FXML
    ImageView PlayerImgView;

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
    ImageView buchu_btnView;
    //imageView那些之后再搞
    @FXML
    Label player1_num;
    @FXML
    Label player2_num;

    Image poker_backImage;
    ImageView poker_back;

    @FXML
    Pane playerShowPane;
    @FXML
    GridPane dipai;
    Label dipai_1;
    Label dipai_2;
    Label dipai_3;

    private String player1_id;
    private String player2_id;

    private int player1_deckNum;
    private int player2_deckNum;
    int x, y, width, height;
    public Boolean isOutCards;

    Image[] images;
    private int last_cardNum = 0;
    private int last_haveCards = 0;
    Label[] labels = new Label[20];

    String qiangdizhu = "-1";
    Button bujiao = new Button();
    Button one = new Button();
    Button two = new Button();
    Button three = new Button();
    ImageView readyView = new ImageView(new Image(getClass().getResource("img/ready.png").toExternalForm()));
    Button ready = new Button();
    ImageView unreadyView = new ImageView(new Image(getClass().getResource("img/unready.png").toExternalForm()));
    @FXML
    Pane readyPane;
    @FXML
    Label playerNickname;

    public void setPlayer(Player player){
        this.player = player;
    }
    public void refreshImage(String s){
            if(s.charAt(1)=='0'){
                System.out.println("Player1 isn't in room");
                Player1ImageView.setImage(new Image(getClass().getResource("bg/close.png").toExternalForm()));
            }
            else if(s.charAt(1)=='1') {
                Player1ImageView.setImage(new Image(getClass().getResource("img/GG.png").toExternalForm()));
                System.out.println("Player1 connected");
            }
            else if(s.charAt(1)=='2'){
                Player1ImageView.setImage(new Image(getClass().getResource("img/GG_ready1.png").toExternalForm()));
                System.out.println("Player1 is ready");
            }
            if(s.charAt(3)=='0'){
                Player1ImageView.setImage(new Image(getClass().getResource("bg/close.png").toExternalForm()));
            }
            else if(s.charAt(3)=='1'){
                Player2ImageView.setImage(new Image(getClass().getResource("img/GG.png").toExternalForm()));
                System.out.println("Player2 connected");
            }
            else if(s.charAt(3)=='2'){
                Player2ImageView.setImage(new Image(getClass().getResource("img/GG_ready2.png").toExternalForm()));
                System.out.println("Player2 is ready");
            }
    }


    public void init(){
        player1_num.getScene().getWindow().setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent arg0) {
                player.isInGame=false;
                player.state="quit";
                AccountDAO accountDAO = new AccountDAO();
                String user = player.getUsername();
                try {
                    accountDAO.updateOnline(user,0);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                System.exit(0);
            }
        });
        Platform.runLater(()->{
            playerNickname.setText(player.getUsername());
        });
        PlayerImgView.setImage(new Image(getClass().getResource("img/GG.png").toExternalForm()));
        ready.setLayoutX(100);
        ready.setLayoutY(20);
        ready.setGraphic(readyView);
        ready.setBackground(null);
        ready.setOnMouseClicked(mouseEvent -> {
                Platform.runLater(()->{
                    playerRole.setText("");
                    prevPlayerRole.setText("");
                    nextPlayerRole.setText("");
                    player1_num.setText("");
                    player2_num.setText("");
                    layeredPane.getChildren().clear();
                    playerShowPane.getChildren().clear();
                    prevPlayerShowPane.getChildren().clear();
                    nextPlayerShowPane.getChildren().clear();
                    dipai_1.setGraphic(new ImageView(poker_backImage));
                    dipai_2.setGraphic(new ImageView(poker_backImage));
                    dipai_3.setGraphic(new ImageView(poker_backImage));
                    card.clear();
                });
            if(player.state.equals("unready")){
                player.state="ready";
                ready.setGraphic(unreadyView);
                PlayerImgView.setImage(new Image(getClass().getResource("img/GG_ready.png").toExternalForm()));
            }
            else{
                player.state="unready";
                ready.setGraphic(readyView);
                PlayerImgView.setImage(new Image(getClass().getResource("img/GG.png").toExternalForm()));
            }
        });
        chupai = new Button();
        buchu = new Button();
        bg = new Image(getClass().getResource("img/desk.jpg").toExternalForm()); //后面再改图片
        GG = new Image(getClass().getResource("img/GG.png").toExternalForm());
        ImageView GGView = new ImageView(GG);
        player1 = new Label("", GGView);
        player2 = new Label("", GGView);
        GG_lord1Image = new Image(getClass().getResource("img/GG_lord1.png").toExternalForm());
        GG_lord1 = new ImageView(GG_lord1Image);
        GG_lord2Image = new Image(getClass().getResource("img/GG_lord2.png").toExternalForm());
         GG_lord2 = new ImageView(GG_lord2Image);
        chupai_btnImage = new Image(getClass().getResource("img/chupai.png").toExternalForm());
        chupai_btn = new ImageView(chupai_btnImage);
        chupai_false_btnImage = new Image(getClass().getResource("img/chupai_false.png").toExternalForm());
        chupai_false_btn = new ImageView(chupai_false_btnImage);
        buchu_btn = new Image(getClass().getResource("img/buchu.png").toExternalForm());
        buchu_btnView = new ImageView(buchu_btn);
        poker_backImage = new Image(getClass().getResource("img/poker/poker_back.png").toExternalForm());
        poker_back = new ImageView(poker_backImage);
        Image image0 = new Image(getClass().getResource("img/0.png").toExternalForm());
        Image image1 = new Image(getClass().getResource("img/1.png").toExternalForm());
        Image image2 = new Image(getClass().getResource("img/2.png").toExternalForm());
        Image image3 = new Image(getClass().getResource("img/3.png").toExternalForm());
        ImageView imageView0 = new ImageView(image0);
        ImageView imageView1 = new ImageView(image1);
        ImageView imageView2 = new ImageView(image2);
        ImageView imageView3 = new ImageView(image3);
        bujiao.setGraphic(imageView0);
        one.setGraphic(imageView1);
        two.setGraphic(imageView2);
        three.setGraphic(imageView3);
        bujiao.setLayoutY(100);
        one.setLayoutY(100);
        two.setLayoutY(100);
        three.setLayoutY(100);
        bujiao.setBackground(null);
        one.setBackground(null);
        two.setBackground(null);
        three.setBackground(null);

        bujiao.setOnMouseClicked(mouseEvent -> {
            this.qiangdizhu = "0";
        });
        one.setOnMouseClicked(mouseEvent -> {
            this.qiangdizhu = "1";
        });
        two.setOnMouseClicked(mouseEvent -> {
            this.qiangdizhu = "2";
        });
        three.setOnMouseClicked(mouseEvent -> {
            this.qiangdizhu = "3";
        });

        player1_id = "-1";
        player2_id = "-1";
        x = 450;
        y = 210;
        width = 650;
        height = 450;
        isOutCards = false;
        images = new Image[15];//卡牌图片
        buchu.setGraphic(buchu_btnView);
        chupai.setGraphic(chupai_false_btn);
        buchu.setBackground(null);
        chupai.setBackground(null);
        chupai.setLayoutY(100);
        buchu.setLayoutY(100);
        chupai.setLayoutX(100);
        buchu.setLayoutX(300);
        chupai.setOnAction(e->{
            if(!card.isEmpty()) {
                playerShowPane.getChildren().remove(buchu);
                playerShowPane.getChildren().remove(chupai);
                StringBuilder stringBuilder = new StringBuilder();
                for (Integer i : this.card) {
                    stringBuilder.append(player.getDeck().get(i));
                }
                String str=stringBuilder.toString();
                try {
                    player.out.writeUTF(str);//str表示出的牌
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    printPlayedCards("m"+str);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                System.out.println(str);
                String[] s = str.split("");
                for (String s1 : s) {
                    player.deck.remove(s1);
                }
                card.clear();
                for (Label label : labels) {
                    layeredPane.getChildren().remove(label);
                }
                printCards();
                CompareCard.getInstance().setCompareCard("m" + str);//出牌记录下来
            }
        });

        buchu.setOnAction(e -> {
            if (CompareCard.getInstance().getPlayer().equals("m")) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setContentText("必须出牌");
                alert.showAndWait();
            } else {
                playerShowPane.getChildren().remove(chupai);
                playerShowPane.getChildren().remove(buchu);
                for (Integer i : card) {
                    labels[i].setTranslateY(labels[i].getTranslateY() + 20);
                }
                String str = "";
                try {
                    player.out.writeUTF(str);//str表示出的牌
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                card.clear();
                for (Label label : labels) {
                    layeredPane.getChildren().remove(label);
                }
                printCards();
                CompareCard.getInstance().setCompareCard("m" + str);
            }
        });
        images[0] = new Image(getClass().getResource("img/poker/A.jpg").toExternalForm());
        images[1] = new Image(getClass().getResource("img/poker/2.jpg").toExternalForm());
        images[2] = new Image(getClass().getResource("img/poker/3.jpg").toExternalForm());
        images[3] = new Image(getClass().getResource("img/poker/4.jpg").toExternalForm());
        images[4] = new Image(getClass().getResource("img/poker/5.jpg").toExternalForm());
        images[5] = new Image(getClass().getResource("img/poker/6.jpg").toExternalForm());
        images[6] = new Image(getClass().getResource("img/poker/7.jpg").toExternalForm());
        images[7] = new Image(getClass().getResource("img/poker/8.jpg").toExternalForm());
        images[8] = new Image(getClass().getResource("img/poker/9.jpg").toExternalForm());
        images[9] = new Image(getClass().getResource("img/poker/10.jpg").toExternalForm());
        images[10] = new Image(getClass().getResource("img/poker/J.jpg").toExternalForm());
        images[11] = new Image(getClass().getResource("img/poker/Q.jpg").toExternalForm());
        images[12] = new Image(getClass().getResource("img/poker/K.jpg").toExternalForm());
        images[13] = new Image(getClass().getResource("img/poker/small.jpg").toExternalForm());
        images[14] = new Image(getClass().getResource("img/poker/big.jpg").toExternalForm());

// 设置网格布局的行和列约束
        dipai_1 = new Label();
        dipai_2 = new Label();
        dipai_3 = new Label();
        dipai.add(dipai_1, 0, 0);
        dipai.add(dipai_2, 1, 0);
        dipai.add(dipai_3, 2, 0);

        dipai_1.setGraphic(new ImageView(poker_backImage));
        dipai_2.setGraphic(new ImageView(poker_backImage));
        dipai_3.setGraphic(new ImageView(poker_backImage));
    }

    public void returnDianshu(String dianshu) throws InterruptedException {
        qiangdizhu = "-1";
        //notice.setText("请选择你的点数");
        String[] str = dianshu.split("");
        List<String> list = Arrays.asList(str);
        ArrayList<String> arr = new ArrayList<>(list);
        //bujiao.setContentAreaFilled(false);
        //bujiao.setBorder(null);
        //bujiao.setIcon(_bujiao);
        final int[] layx = {100};
        Platform.runLater(()->{
            bujiao.setLayoutX(layx[0]);
            layx[0] += 100;
            playerShowPane.getChildren().add(bujiao);
        });
        if (arr.contains("1")) {
            Platform.runLater(()->{
                one.setLayoutX(layx[0]);
                layx[0] += 100;
                playerShowPane.getChildren().add(one);
        });
        }
        if (arr.contains("2")) {
            Platform.runLater(()->{
                two.setLayoutX(layx[0]);
                layx[0] += 100 ;
                playerShowPane.getChildren().add(two);
            });
        }
        if (arr.contains("3")) {
            Platform.runLater(()->{
                three.setLayoutX(layx[0]);
                layx[0] += 100;
                playerShowPane.getChildren().add(three);
            });
        }
        while (qiangdizhu.equals("-1")){
            sleep(10);
        }
        Platform.runLater(()->{
            playerShowPane.getChildren().remove(bujiao);
            playerShowPane.getChildren().remove(one);
            playerShowPane.getChildren().remove(two);
            playerShowPane.getChildren().remove(three);
        });
    }
    @FXML
    Label prevPlayerRole;
    @FXML
    Label nextPlayerRole;
    //加载地主和底牌图标
    @FXML
    Label test;
    public void addLord(String s){
        System.out.println(s);
        String[] str = s.split("");
        Platform.runLater(()->{
            if (str[0].equals(player1_id)) {
                    prevPlayerRole.setText("地主");
                    nextPlayerRole.setText("农民");
                    playerRole.setText("农民");
                    this.player1_deckNum = 20;
                    this.player2_deckNum = 17;
            }
            if (str[0].equals(player2_id)) {
                nextPlayerRole.setText("地主");
                prevPlayerRole.setText("农民");
                playerRole.setText("农民");
                this.player1_deckNum = 17;
                this.player2_deckNum = 20;
            }
            addIcon(dipai_1, str[1], images);
            addIcon(dipai_2, str[2], images);
            addIcon(dipai_3, str[3], images);
            player1_num.setText(" "+player1_deckNum + "张");
            player2_num.setText(" "+player2_deckNum + "张");
            System.out.println(player1_num.getText());
        }
        );

    }
    @FXML
    Label playerRole;
    public void addSelf(String s) {
        System.out.println(s);
        Image lordImage = new Image(getClass().getResource("img/lord.png").toExternalForm());
        ImageView lord = new ImageView(lordImage);
        player1_deckNum = 17;
        player2_deckNum = 17;
        //设置底牌
        String[] str = s.split("");
        Platform.runLater(()->{
            addIcon(dipai_1, str[0], images);
            addIcon(dipai_2, str[1], images);
            addIcon(dipai_3, str[2], images);
            player1_num.setText(player1_deckNum + "张");
            player2_num.setText(player2_deckNum + "张");
            playerRole.setText("地主");
            prevPlayerRole.setText("农民");
            nextPlayerRole.setText("农民");
        }
        );
    }

    //更新记录玩家编号
    public void refresh(String dianshu) {
        Platform.runLater(()->{
            System.out.println("refresh");
            String[] str = dianshu.split("");
            if (this.player1_id.equals("-1")) {
                this.player1_id = str[0];
                if (str[1].equals("0"))
                    player1_num.setText("不叫");
                else if (str[1].equals("1"))
                    player1_num.setText("1分");
                else if (str[1].equals("2"))
                    player1_num.setText("2分");
                else {
                    player1_num.setText("3分");
                }
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
        });

    }

    public void showLordWin() throws InterruptedException {
        Image lordWinImage = new Image(getClass().getResource("img/lord_win.png").toExternalForm());
        ImageView lordWin = new ImageView(lordWinImage);
        Label label = new Label("", lordWin);


        Platform.runLater(()-> {
                    layeredPane.getChildren().add(label);
        });

        //layeredPane.setLayer(label, 290);
        //JAVAFX不能设置层级
        sleep(3000);
    }

    public void showLordLose() throws InterruptedException {
        Image lordLoseImage = new Image(getClass().getResource("img/lord_lose.png").toExternalForm());
        ImageView lordLose = new ImageView(lordLoseImage);
        Label label = new Label("", lordLose);

        Platform.runLater(()-> {
        layeredPane.getChildren().add(label);});


    }

    public void showFarmerWin() throws InterruptedException {
        Image farmerWinImage = new Image(getClass().getResource("img/farmer_win.png").toExternalForm());
        ImageView farmerWin = new ImageView(farmerWinImage);
        Label label = new Label("", farmerWin);

        Platform.runLater(()-> {
        layeredPane.getChildren().add(label);});

    }

    public void showFarmerLose() throws InterruptedException {
        Image farmerLoseImage = new Image(getClass().getResource("img/farmer_lose.png").toExternalForm());
        ImageView farmerLose = new ImageView(farmerLoseImage);
        Label label = new Label("", farmerLose);

        Platform.runLater(()-> {
        layeredPane.getChildren().add(label);});

    }

    public void printCards() {
        Platform.runLater(()->{
            System.out.println("last"+last_haveCards);
            for (int i = 0; i < last_haveCards; i++) {
                layeredPane.getChildren().remove(labels[i]);
            }
        int num = player.getDeck().size();
        this.last_haveCards = player.getDeck().size();
        for (int i = 0; i < 20; i++) {
            Label l = new Label();
            labels[i] = l;
        }
        System.out.println("flag");
        double w = images[0].getWidth();
        double h = images[0].getHeight();
        double total_w = (num * w + 2 * w) / 3;
        for(int i = 0; i < num; i++) {
            addIcon(labels[i], player.getDeck().get(i), images);
            labels[i].setOpacity(1.0);
            labels[i].setLayoutX(layeredPane.getWidth()/2 - total_w / 2 + i * w / 3);
            labels[i].setLayoutY(20);
            labels[i].setPrefHeight(h + 30);
            int finalI = i;
                layeredPane.getChildren().add(labels[finalI]);
                labels[finalI].setOnMouseClicked(event -> {
                    if (!card.contains(finalI)) {
                        card.add(finalI);
                        labels[finalI].setTranslateY(labels[finalI].getTranslateY() - 20);
                        System.out.println("card"+card);
                        System.out.println("deck"+player.getDeck());
                    } else {
                        for(int m = 0; m < card.size(); m++){
                            if (card.get(m).equals(finalI)){
                                card.remove(m);
                            }
                        }
                        labels[finalI].setTranslateY(labels[finalI].getTranslateY() + 20);
                    }
                    CompareCard compareCard = CompareCard.getInstance();
                    System.out.println(compareCard.player+compareCard.cards);
                    StringBuilder stringBuilder = new StringBuilder();
                    if (card.size() == 0) {
                        chupai.setGraphic(chupai_false_btn);
                        chupai.setDisable(true);
                    } else {
                        for (Integer in : card) {
                            System.out.println(in);
                            String s = player.getDeck().get(in);
                            stringBuilder.append(s);
                        }
                        if (compareCard.compare(stringBuilder.toString())) {
                            chupai.setGraphic(chupai_btn);
                            chupai.setDisable(false);
                        } else {
                            chupai.setGraphic(chupai_false_btn);
                            chupai.setDisable(true);
                        }
                    }
                });
            
            }
        });
    }
    public StringBuilder stringBuilder = new StringBuilder();
    public void outCards() {
        Platform.runLater(()-> {
            chupai.setDisable(true);
            playerShowPane.getChildren().add(chupai);
            playerShowPane.getChildren().add(buchu);
            System.out.println("out");
        });
    }
    Label[] playedCards = new Label[20];
    public void printPlayedCards(String s) throws InterruptedException{
        String[] str = s.split("");
        if (str.length == 1) {
            pass(s);
            return;
        }
        Platform.runLater(()->{
            playerShowPane.getChildren().clear();
            prevPlayerShowPane.getChildren().clear();
            nextPlayerShowPane.getChildren().clear();
            String p = str[0];
            List<String> list = Arrays.asList(s.substring(1, str.length).split(""));
            ArrayList<String> cards = new ArrayList<>(list);
            double w = images[0].getWidth() * 3 / 4;
            double h = images[0].getWidth() * 3 / 4;
            int num = cards.size();
            this.last_cardNum = num;
            for (int i = 0; i < 20; i++) {
                playedCards[i] = new Label();
            }
            for (int i = 0; i < num; i++) {
                addIcon(playedCards[i], cards.get(i), images);
                if (p.equals("m")) {
                    playedCards[i].setLayoutX(playerShowPane.getWidth()/2-(w/3)*(4+num)/2+i * w / 3);
                    playerShowPane.getChildren().add(playedCards[i]);
                    System.out.println(p+"showm");
                }
                else if (p.equals(player1_id)) {
                    playedCards[i].setLayoutX(i * w / 3);
                    player1_deckNum -= 1;
                    prevPlayerShowPane.getChildren().add(playedCards[i]);
                    player1_num.setText(player1_deckNum + "张");
                    System.out.println(p+"showplayer1");
                } else {
                    playedCards[i].setLayoutX(i * w / 3);
                    player2_deckNum -= 1;
                    nextPlayerShowPane.getChildren().add(playedCards[i]);
                    player2_num.setText(player2_deckNum + "张");
                    System.out.println(p+"showplayer2");
                }
            }
        });
    }

    public void pass(String s) throws InterruptedException{
        Image passImage = new Image(getClass().getResource("img/pass.png").toExternalForm());//过的图标
        ImageView pass = new ImageView(passImage);
        Label l = new Label();
        l.setGraphic(pass);
        String[] str = s.split("");
        Platform.runLater(()-> {
            if(str[0].equals("m")) {
                playerShowPane.getChildren().clear();
                playerShowPane.getChildren().add(l);
            }
            else if(str[0].equals(player1_id)){
                prevPlayerShowPane.getChildren().clear();
                prevPlayerShowPane.getChildren().add(l);
            }
            else {
                nextPlayerShowPane.getChildren().clear();
                nextPlayerShowPane.getChildren().add(l);
            }
        });

    }


    public void addIcon(Label label, String card, Image[] images){
        if (card.equals("A")){
            ImageView newImg = new ImageView(images[0]);
            label.setGraphic(newImg);}
        else if (card.equals("2")){
            ImageView newImg = new ImageView(images[1]);
            label.setGraphic(newImg);}
        else if (card.equals("3")){
            ImageView newImg = new ImageView(images[2]);
            label.setGraphic(newImg);}
        else if (card.equals("4")){
            ImageView newImg = new ImageView(images[3]);
            label.setGraphic(newImg);}
        else if (card.equals("5")){
            ImageView newImg = new ImageView(images[4]);
            label.setGraphic(newImg);}
        else if (card.equals("6")){
            ImageView newImg = new ImageView(images[5]);
            label.setGraphic(newImg);}
        else if (card.equals("7")){
            ImageView newImg = new ImageView(images[6]);
            label.setGraphic(newImg);}
        else if (card.equals("8")){
            ImageView newImg = new ImageView(images[7]);
            label.setGraphic(newImg);}
        else if (card.equals("9")){
            ImageView newImg = new ImageView(images[8]);
            label.setGraphic(newImg);}
        else if (card.equals("X")){
            ImageView newImg = new ImageView(images[9]);
            label.setGraphic(newImg);}
        else if (card.equals("J")){
            ImageView newImg = new ImageView(images[10]);
            label.setGraphic(newImg);}
        else if (card.equals("Q")){
            ImageView newImg = new ImageView(images[11]);
            label.setGraphic(newImg);}
        else if (card.equals("K")){
            ImageView newImg = new ImageView(images[12]);
            label.setGraphic(newImg);}
        else if (card.equals("w")){
            ImageView newImg = new ImageView(images[13]);
            label.setGraphic(newImg);}
        else{
            ImageView newImg = new ImageView(images[14]);
            label.setGraphic(newImg);}
    }

    public void clearReady() {
        Platform.runLater(()->{
            ready.setGraphic(readyView);
            readyPane.getChildren().remove(ready);
            PlayerImgView.setImage(new Image(getClass().getResource("img/GG.png").toExternalForm()));
            Player1ImageView.setImage(new Image(getClass().getResource("img/GG.png").toExternalForm()));
            Player2ImageView.setImage(new Image(getClass().getResource("img/GG.png").toExternalForm()));
        });
    }

    public void setReady() {
        Platform.runLater(()->{
            readyPane.getChildren().add(ready);
        });
    }
}
