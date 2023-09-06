package com.example.jokerpoker.Server;

import java.io.IOException;

public class ServerMain {
    public static void main(String args[]) throws IOException {
        DatagramSocketServer server = new DatagramSocketServer();
        server.startServer(); //启动服务器
    }
}
