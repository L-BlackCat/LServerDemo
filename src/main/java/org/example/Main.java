package org.example;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;

public class Main {
    public static void main(String[] args) {
        try{
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress("localhost",1314));


        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Hello world!");
    }
}