package org.example.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class SelectorExample {
    public static void main(String[] args) {
//        try{
//            ServerSocketChannel serverSocket = ServerSocketChannel.open();
//            InetSocketAddress address = new InetSocketAddress("localhost", 1314);
//            serverSocket.bind(address);
//            System.out.println("Selector is open for making connection: " + serverSocket.isOpen());
//
//
//            // 将其设为non-blocking状态，这样才能进行异步IO操作
//            //  channel和selector一起使用时，channel必须处于非阻塞模式下，FileChannel无法切换成非阻塞模式，所以不能与selector一起用
//            serverSocket.configureBlocking(false);
//
//            //Selector这个类通过select()函数，给应用程序提供了一个可以同时监控多个IO channel的方法
//            Selector selector = Selector.open();
//
//            int ops = serverSocket.validOps();
//            //将ServerSocketChannel注册到Selector上，返回对应的SelectionKey
//            //LFH   仅注册了一个通道,在下方在进行一个通道的注册
//            serverSocket.register(selector, ops,null);
//            for (;;){
////                System.out.println("Waiting for the select operation...");
//                int noOfKeys = selector.select();
////                System.out.println("The Number of selected keys are: " + noOfKeys);
//
//
//                Set<SelectionKey> selectionKeys = selector.selectedKeys();
//                Iterator<SelectionKey> iterator = selectionKeys.iterator();
//                while (iterator.hasNext()) {
//                    SelectionKey key = iterator.next();
//                    if(key.isAcceptable()){
//                        // The connection was accepted by a ServerSocketChannel.
//                        SocketChannel client = serverSocket.accept();
//                        client.configureBlocking(false);
//                        client.register(selector,SelectionKey.OP_READ);
//                        System.out.println("The new connection is accepted from the client: " + client);
//                    }else if(key.isReadable()){
//                        // The channel is ready for reading
//                        ByteBuffer buffer = ByteBuffer.allocate(1024);
//                        SocketChannel client = (SocketChannel) key.channel();
//                        int ret = client.read(buffer);
//                        if(ret != -1){
//                            String msg = buffer.asCharBuffer().toString();
//                            System.out.println(msg);
//                        }
//                    }else if(key.isWritable()){
//                        //  The channel is ready for writing
//                        System.out.println("success to write");
//                    }
//                    iterator.remove();
//                }
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        try{

            // Get the selector
            Selector selector = Selector.open();
            System.out.println("Selector is open for making connection: " + selector.isOpen());
            // Get the server socket channel and register using selector
            ServerSocketChannel SS = ServerSocketChannel.open();
            InetSocketAddress hostAddress = new InetSocketAddress("localhost", 8080);
            SS.bind(hostAddress);
            SS.configureBlocking(false);
            int ops = SS.validOps();
            SelectionKey selectKy = SS.register(selector, ops, null);
            for (;;) {
                System.out.println("Waiting for the select operation...");
                int noOfKeys = selector.select();
                System.out.println("The Number of selected keys are: " + noOfKeys);
                Set selectedKeys = selector.selectedKeys();
                Iterator itr = selectedKeys.iterator();
                while (itr.hasNext()) {
                    SelectionKey ky = (SelectionKey) itr.next();
                    if (ky.isAcceptable()) {
                        // The new client connection is accepted
                        SocketChannel client = SS.accept();
                        client.configureBlocking(false);
                        // The new connection is added to a selector
                        client.register(selector, SelectionKey.OP_READ);
                        System.out.println("The new connection is accepted from the client: " + client);
                    } else if (ky.isReadable()) {
                        // Data is read from the client
                        try (SocketChannel client = (SocketChannel) ky.channel()) {
                            ByteBuffer buffer = ByteBuffer.allocate(256);
                            client.read(buffer);

                            String output = new String(buffer.array()).trim();
                            System.out.println("Message read from client: \n" + output);
                            System.out.println("The Client messages are complete; close the session.");
                        }

                    }
                    itr.remove();
                } // end of while loop
            } // end of for loop
        }catch (Exception ee){
            ee.printStackTrace();
        }



    }
}
