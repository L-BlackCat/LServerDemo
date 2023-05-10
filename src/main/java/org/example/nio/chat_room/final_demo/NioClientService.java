package org.example.nio.chat_room.final_demo;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NioClientService {

    private SocketChannel clientChannel;
    private int port = 1314;
    private String name;


    public NioClientService(String name) throws IOException {
        clientChannel = SocketChannel.open();
        clientChannel.connect(new InetSocketAddress("localhost",port));
        clientChannel.configureBlocking(false);
        this.name = name;
    }

    public String readInfo(){
        String content = null;
        try{
            //  从哪里进行读，不能自己读自己->某个远程网络发送过来的数据
            int count;
            ByteBuffer buffer = ByteBuffer.allocate(256);
            StringBuilder msgBuilder = new StringBuilder();
            while((count = clientChannel.read(buffer)) > 0){
                msgBuilder.append(new String(buffer.array(),0,count));
            }
            /*
            * 数据：
            * 1.玩家列表
            * 2.玩家发送消息
            * 3.close字符（告知关闭通道）;
            * */
            if(msgBuilder.length() > 0){
                content = msgBuilder.toString();
                if(content.contains("close")){
                    content = null;
                    clientChannel.socket().close();
                    clientChannel.close();
                }
            }
        } catch (IOException e) {
            System.out.println("readInfo err: " + e.getMessage());
        }
        return content;
    }

    public void sendInfo(String msg){
        try{
            while(!clientChannel.finishConnect()){

            }
            clientChannel.write(ByteBuffer.wrap(msg.getBytes()));
        } catch (IOException e) {
            System.out.println("sendInfo err: " + e.getMessage());
        }
    }

    public void exit(){
        String info = "exit_" + name;
        try{
            clientChannel.write(ByteBuffer.wrap(info.getBytes()));
        } catch (IOException e) {
            System.out.println("sendInfo err: " + e.getMessage());
        }
    }

    public String getName() {
        return name;
    }
}
