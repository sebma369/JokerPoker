package com.example.jokerpoker;

import java.io.IOException;

public class ClientMain {
    public static void main(String args[]) throws IOException {
        DatagramSocketClient client = new DatagramSocketClient();
        client.startClient(); //启动客户端
    }
}
