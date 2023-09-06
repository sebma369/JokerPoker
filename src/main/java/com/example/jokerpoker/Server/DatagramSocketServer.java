package com.example.jokerpoker.Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class DatagramSocketServer {
    public void startServer() throws IOException {
        System.out.println("服务器启动！");
        //1.创建服务端对象
        DatagramSocket socketServer = new DatagramSocket(12345);

        while (true) {
            //2.创建数据包对象，用于接收数据
            byte[] buffer = new byte[1024 * 64];
            DatagramPacket packetServer = new DatagramPacket(buffer, buffer.length);

            //3.开始使用数据包接收客户端发送过来的数据
            socketServer.receive(packetServer);
            int len = packetServer.getLength();
            String str = new String (buffer, 0, len);
            System.out.println(str);
            System.out.println("----------------------------------");
        }
    }
}
