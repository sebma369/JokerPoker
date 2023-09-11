package com.example.jokerpoker;
//gameWindow

import javafx.application.Platform;
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

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
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
    GridPane front; //按钮区域
    @FXML
    Pane prevPlayerShowPane;
    @FXML
    Pane nextPlayerShowPane;

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
    Font font;
    Font font2;
    private String player1_id;
    private String player2_id;

    private int player1_deckNum;
    private int player2_deckNum;
    int x, y, width, height;
    public Boolean isOutCards;

    public String serverMessage;
    Image[] images;
    Image[] smallImages;
    private int last_cardNum;
    private int last_haveCards = 0;
    Label[] labels = new Label[20];

    String qiangdizhu = "-1";
    Button bujiao = new Button();
    Button one = new Button();
    Button two = new Button();
    Button three = new Button();


    private final Object lock = new Object();

    public void setPlayer(Player player){
        this.player = player;
    }


    public void init(){
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
        player1_num = new Label(); //玩家手牌数
        player2_num = new Label();
        poker_backImage = new Image(getClass().getResource("img/poker/poker_back.png").toExternalForm());
        poker_back = new ImageView(poker_backImage);
        player1_num.toFront();
        player2_num.toFront();
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
        buchu.setGraphic(buchu_btnView);
        chupai.setGraphic(chupai_false_btn);
        chupai.setOnAction(e->{
            if(!card.isEmpty()) {
                front.getChildren().remove(buchu);
                front.getChildren().remove(chupai);
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
                front.getChildren().remove(chupai);
                front.getChildren().remove(buchu);
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
        images[0] = new Image(GameController.class.getResource("img/poker/A.jpg").toExternalForm());
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


        for (int i = 0; i < 15; i++) {
            Image fxImage = images[i];

            // 创建缩小的图像
            double scaledWidth = fxImage.getWidth() * 3 / 4;
            double scaledHeight = fxImage.getHeight() * 3 / 4;

            Image smallImage = new Image(fxImage.getUrl(), scaledWidth, scaledHeight, true, true);
            smallImages[i] = smallImage;
        }

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
    public void addLord(String s){
        System.out.println(s);
        String[] str = s.split("");
        Platform.runLater(()->{
            if (str[0].equals(player1_id)) {
                    prevPlayerRole.setText("地主");
                    this.player1_deckNum = 20;
                    this.player2_deckNum = 17;
            }
            if (str[0].equals(player2_id)) {
                nextPlayerRole.setText("地主");
                this.player1_deckNum = 17;
                this.player2_deckNum = 20;
            }
            addIcon(dipai_1, str[1], this.smallImages);
            addIcon(dipai_2, str[2], this.smallImages);
            addIcon(dipai_3, str[3], this.smallImages);
            player1_num.setText(player1_deckNum + "张");
            player2_num.setText(player2_deckNum + "张");
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
            addIcon(dipai_1, str[0], this.smallImages);
            addIcon(dipai_2, str[1], this.smallImages);
            addIcon(dipai_3, str[2], this.smallImages);
            playerRole.setText("地主");
            player1_num.setText(player1_deckNum + "张");
            player2_num.setText(player2_deckNum + "张");
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

        label.setLayoutX(width / 2 - lordWin.getFitWidth() / 2);
        label.setLayoutY(height / 2 - lordWin.getFitHeight() / 2);
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

        label.setLayoutX(width / 2 - lordLose.getFitWidth() / 2);
        label.setLayoutY(height / 2 - lordLose.getFitHeight() / 2);
        Platform.runLater(()-> {
        layeredPane.getChildren().add(label);});

        //layeredPane.setLayer(label, 290);
        //JAVAFX不能设置层级
        sleep(3000);
    }

    public void showFarmerWin() throws InterruptedException {
        Image farmerWinImage = new Image(getClass().getResource("img/farmer_win.png").toExternalForm());
        ImageView farmerWin = new ImageView(farmerWinImage);
        Label label = new Label("", farmerWin);

        label.setLayoutX(width / 2 - farmerWin.getFitWidth() / 2);
        label.setLayoutY(height / 2 - farmerWin.getFitHeight() / 2);
        Platform.runLater(()-> {
        layeredPane.getChildren().add(label);});

        //layeredPane.setLayer(label, 290);
        //JAVAFX不能设置层级
        sleep(3000);
    }

    public void showFarmerLose() throws InterruptedException {
        Image farmerLoseImage = new Image(getClass().getResource("img/farmer_lose.png").toExternalForm());
        ImageView farmerLose = new ImageView(farmerLoseImage);
        Label label = new Label("", farmerLose);

        label.setLayoutX(width / 2 - farmerLose.getFitWidth() / 2);
        label.setLayoutY(height / 2 - farmerLose.getFitHeight() / 2);
        Platform.runLater(()-> {
        layeredPane.getChildren().add(label);});

        //layeredPane.setLayer(label, 290);
        //JAVAFX不能设置层级
        sleep(3000);
    }

    public void printCards() {
        Platform.runLater(()->{
            for (int i = 0; i < last_haveCards; i++) {
                layeredPane.getChildren().remove(labels[i]);
            }
        });
        int num = player.getDeck().size();
        System.out.println(player.getDeck());
        this.last_haveCards = player.getDeck().size();
        for (int i = 0; i < 20; i++) {
            Label l = new Label();
            labels[i] = l;
        }
        double w = images[0].getWidth();
        double h = images[0].getHeight();
        double total_w = (num * w + 2 * w) / 3;
        for(int i = 0; i < num; i++) {
            addIcon(labels[i], player.getDeck().get(i), images);
            labels[i].setOpacity(1.0);
            labels[i].setLayoutX(width / 2 - total_w / 2 + i * w / 3);
            labels[i].setLayoutY(20);
            labels[i].setPrefHeight(h + 30);
            int finalI = i;
            Platform.runLater(()->{
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
            });
            
            }
    }
    public StringBuilder stringBuilder = new StringBuilder();
    public void outCards() {
        Platform.runLater(()-> {
            chupai.setDisable(true);
            this.front.add(chupai, 0, 0);
            this.front.add(buchu, 1, 0);
        });
//        t1 = new Thread(() -> {
//            try {
//                System.out.println("wait开始");
//                synchronized (lock) {
//                    lock.wait();
//                }
//                System.out.println("wait结束");
//                this.isOutCards = false;
//                System.out.println("asdasqwew");
//                stringBuilder  = new StringBuilder();
//                for (Integer i : this.card) {
//                    stringBuilder.append(player.getDeck().get(i));
//                }
//
//                card.clear();
//
//                // 在事件分发线程上执行界面操作
//                Platform.runLater(() -> {
//                    for(Label label: labels){
//                        layeredPane.getChildren().remove(label);
//                    }
//                });
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//        t1.start();
    }
    Label[] playedCards = new Label[20];
    public void printPlayedCards(String s) throws InterruptedException{
        String[] str = s.split("");
        if (str.length == 1) {
            pass(s);
            return;
        }
        Platform.runLater(()->{
        for (int i = 0; i < this.last_cardNum; i++) {
            playerShowPane.getChildren().remove(playedCards[i]);
        }});
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
        for (int i = 0; i < 20; i++) {
            playedCards[i] = new Label();
        }
        for (int i = 0; i < num; i++) {
            addIcon(playedCards[i], cards.get(i), images);
            playedCards[i].setLayoutX(width / 2 - total_w / 2 + i * w / 3);
            playedCards[i].setLayoutY(0);
            playedCards[i].setPrefHeight(h + 30);
//            if (p.equals("m")) {
//                playedCards[i].setLayoutX(width / 2 - total_w / 2 + i * w / 3);
//                playedCards[i].setLayoutY(height / 2 - 65);
//                playedCards[i].setPrefHeight(h + 30);
//            }
//            else if (p.equals(player1_id)) {
//                playedCards[i].setLayoutX(bias + i * w / 3);
//                playedCards[i].setLayoutY(height / 2 - 65);
//                playedCards[i].setPrefHeight(h + 30);
//                player1_deckNum -= 1;
//                player1_num.setText(player1_deckNum + "张");
//            } else {
//                playedCards[i].setLayoutX(width - bias - total_w + i * w / 3);
//                playedCards[i].setLayoutY(height / 2 - 65);
//                playedCards[i].setPrefHeight(h + 30);
//                player2_deckNum -= 1;
//                player2_num.setText(player2_deckNum + "张");
//            }
            int inti = i;
            Platform.runLater(()->{
            playerShowPane.getChildren().add(playedCards[inti]);});
        }
    }

    public void pass(String s) throws InterruptedException{
        Image passImage = new Image(getClass().getResource("img/pass.png").toExternalForm());//过的图标
        ImageView pass = new ImageView(passImage);
        Label l = new Label();
        l.setGraphic(pass);
        Platform.runLater(()-> {
                    playerShowPane.getChildren().add(l);
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            playerShowPane.getChildren().remove(l);
        });
//        if (s.equals(player1_id)) {
//            player1_num.setGraphic(pass);
//            sleep(1000);
//            player1_num.setGraphic(null);
//        } else if (s.equals(player2_id)) {
//            player2_num.setGraphic(pass);
//            sleep(1000);
//            player2_num.setGraphic(null);
//        }
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
}
