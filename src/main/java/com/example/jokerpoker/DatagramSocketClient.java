package com.example.jokerpoker;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class DatagramSocketClient {
    public void startClient() throws IOException {
        //1.创建客户端对象
        DatagramSocket socketClient = new DatagramSocket();

        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println("请发送数据：");
            String msg = scan.nextLine();
            if(msg.equals("exit")){
                System.out.println("退出成功！");
                socketClient.close();
                break;
            }
            //2.创建数据包封装要发出去的数据
            byte[] bytes = msg.getBytes();
            DatagramPacket packetClient = new DatagramPacket(bytes, bytes.length,
                    InetAddress.getLocalHost(), 12345); //目标服务器为本机

            //3.将数据包的数据发送出去
            socketClient.send(packetClient);
        }

    }
}
