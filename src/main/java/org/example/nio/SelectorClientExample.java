package org.example.nio;

import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SelectorClientExample {
    public static void main(String[] args) {
        try{

            SocketChannel client = SocketChannel.open();

            client.connect(new InetSocketAddress("localhost",8080));

            System.out.println(client.getLocalAddress().toString().substring(1));

            ByteBuffer buffer = ByteBuffer.allocate(256);
            String msg = "The Client is sending messages to server...\n" +
                    "Time goes fast.\n" +
                    "What next?\n" +
                    "Bye Bye";
            buffer.put(msg.getBytes());
            buffer.flip();
            System.out.println(buffer);
            while(buffer.hasRemaining()){
                client.write(buffer);
            }

            System.out.println();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
