package com.example.jokerpoker;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Player {
    private DataInputStream in;//输入流
    private DataOutputStream out;//输出流
    private Socket socket = new Socket();//客户端的套接字
    private ArrayList<String> deck = new ArrayList<>();//自己的牌
    private String serverMessage;//服务器消息
    private boolean isLord;//自己是不是地主
    private String whoIsLord;//谁是地主
    private String state;//准备状态
    private boolean isInGame;//判断是否从游戏里出来
    private String roomInfo;//房间内玩家准备情况
    private String roomNum;//大厅里房间人数情况
    private String selectRoom;//玩家房间选择

    public GameController gameController;

    public Player() {
        try {
//            String ip = "106.13.40.227";//服务器ip
            String ip = "127.0.0.1";//本地ip
            InetSocketAddress socketAddress = new InetSocketAddress(ip, 8888);
            this.socket.connect(socketAddress);
            System.out.println("接入成功");//前端可以忽视
            this.in = new DataInputStream(this.socket.getInputStream());
            this.out = new DataOutputStream(this.socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("断开连接");//前端可以忽视
        }
        this.isInGame = false;
        this.selectRoom = "-1";
    }

    public void playGame() throws IOException, InterruptedException {
        do {
            this.isInGame = true;//游戏局内,设为true,游戏单局结束后直接到准备阶段
            //获取手牌
            dealCards();
        } while (!qiangdizhu());//抢地主环节
        //开始出牌
        while (true) {
            serverMessage = in.readUTF();
            if (serverMessage.equals("请你出牌")) {
                System.out.println("请你出牌");//前端提示玩家出牌
                String str;
                //出牌
                str = gameController.outCards();
                out.writeUTF(str);//str表示出的牌,
                String[] s = str.split("");
                for (String s1 : s) {
                    deck.remove(s1);
                }
                gameController.printCards();
                System.out.println("你出了" + str + "牌");//前端显示玩家出的牌
                gameController.printPlayedCards("m" + str);//显示出牌
                CompareCard.getInstance().setCompareCard("m" + str);//出牌记录下来
                System.out.println("你现在的牌为" + deck.toString());
            } else if (serverMessage.equals("游戏结束")) {//服务器返回结束信号
                out.writeUTF("1");
                serverMessage = message();
                if (serverMessage.equals(whoIsLord)) {
                    if (isLord) {
                        System.out.println("地主赢了");//前端
                        gameWindow.showLordWin();
                    } else {
                        System.out.println("农民输了");
                        gameWindow.showFarmerLose();
                    }
                } else {
                    if (isLord) {
                        System.out.println("地主输了");
                        gameWindow.showLordLose();
                    } else {
                        System.out.println("农民赢了");
                        gameWindow.showFarmerWin();
                    }
                }
                gameWindow.close();
                break;
            } else {
                CompareCard.getInstance().setCompareCard(serverMessage);//记录其他玩家出牌
                gameWindow.printPlayedCards(serverMessage);
                System.out.println(serverMessage);//前端处理其他玩家出牌,第一个为玩家序号,后面是玩家出的牌
                out.writeUTF("1");
            }
        }
    }



    public void dealCards() throws IOException {
        String str = message();//服务器传过来的开始游戏信息
        System.out.println(str);
        List<String> result = Arrays.asList(message().split(""));//message里是服务器传过来的手牌信息
        deck = new ArrayList<>(result);//手牌
        //排序
        //deck.sort();
        //获取自己的牌
        System.out.println("你的牌为\n" + deck.toString());//前端显示手牌
    }

    public String message() throws IOException {
        String str = this.in.readUTF();
        this.out.writeUTF("message");
        return str;
    }

    public boolean qiangdizhu() throws IOException, InterruptedException {

        System.out.println("抢地主");//前端显示抢地主
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Player.class.getResource("game-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        gameController = fxmlLoader.getController();
        gameController.printCards();
        int bujiao = 0;
        for (int i = 0; i < 3; i++) {
            serverMessage = message();
            if (serverMessage.equals("抢地主")) {
                serverMessage = in.readUTF();
                String s = gameController.returnDianshu(serverMessage);
                //前端改成按钮传入,需要设置阻塞,建议在前端方法里写入while死循环,选择了才返回,选择抢地主的点数
                if (s.equals("0")) {//后面的通过scanner方法读入的都需要阻塞,都建议使用while死循环
                    bujiao++;
                }
                out.writeUTF(s);
                System.out.println("你抢了" + s);//
            } else {
                gameController.refresh(serverMessage);//显示其他玩家抢的点数
                if (serverMessage.charAt(1) == '0') {
                    bujiao++;
                }
            }
        }
        if (bujiao == 3) {//三人都不叫,返回false ,重新发牌
            gameController.close();
            return false;
        }
        serverMessage = message();
        whoIsLord = serverMessage;//储存地主信息
        if (serverMessage.equals("you")) {
            System.out.println("你是地主");//前端显示自己为地主
            isLord = true;
            //添加地主三张牌
            String c;
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < 3; i++) {
                c = message();
                deck.add(c);
                s.append(c);
            }
            gameController.addSelf(s.toString());
            //前端地主显示三张地主牌传入builder.toString
        } else {
            System.out.println(serverMessage);//显示地主,加上地主的三张牌 例2AAA,前端显示
            isLord = false;
            //服务器发送地主的三张牌
            //界面显示地主牌
            whoIsLord = serverMessage.charAt(0) + "";
            gameController.addLord(serverMessage);//前端更新
        }
        //sort
        gameController.printCards();//显示手牌
        System.out.println("你的手牌为" + deck.toString());//显示自己的手牌
        return true;
    }
}