package org.example.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class SelectorExample {
    public static void main(String[] args) {
        try{
            ServerSocketChannel serverSocket = ServerSocketChannel.open();
            InetSocketAddress address = new InetSocketAddress("localhost", 1314);
            serverSocket.bind(address);
            // 将其设为non-blocking状态，这样才能进行异步IO操作
            serverSocket.configureBlocking(false);

            //Selector这个类通过select()函数，给应用程序提供了一个可以同时监控多个IO channel的方法
            Selector selector = Selector.open();

            int ops = serverSocket.validOps();
            //将ServerSocketChannel注册到Selector上，返回对应的SelectionKey
            SelectionKey selectKey = serverSocket.register(selector, ops);

            System.out.println("selector.select():" + selector.select());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
