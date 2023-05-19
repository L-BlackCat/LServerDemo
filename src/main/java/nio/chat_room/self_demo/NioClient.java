package nio.chat_room.self_demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

public class NioClient {
    private SocketChannel clientChannel;
    private int port = 1314;
    private Selector selector;
    private String name;


    public NioClient() throws IOException {
        clientChannel = SocketChannel.open();
        clientChannel.connect(new InetSocketAddress("localhost",port));
        clientChannel.configureBlocking(false);

        selector = Selector.open();
        clientChannel.register(selector, SelectionKey.OP_READ);
        name = clientChannel.getLocalAddress().toString().substring(1);
        System.out.println(name + " is ok...");
    }

    public void readInfo(){
        try{
            //  从哪里进行读，不能自己读自己->某个远程网络发送过来的数据
            int count = selector.select();
            if(count > 0){
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while(iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    if (key.isReadable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(256);
                        channel.read(byteBuffer);
                        byteBuffer.flip();
                        System.out.println("client read:" + byteBuffer);
                        String msg = new String(byteBuffer.array()).trim();
                        if(!msg.isEmpty()){
                            System.out.println(msg.trim());
                        }
                    }
                    iterator.remove();  //删除当前的selectionKey, 防止重复操作
                }
            }else {
                System.out.println("没有可以用的通道...");
            }
        } catch (IOException e) {
            System.out.println("readInfo err: " + e.getMessage());
        }
    }

    public void sendInfo(String msg){
        String info = name + "说： " + msg;
        try{
            clientChannel.write(ByteBuffer.wrap(info.getBytes()));
        } catch (IOException e) {
            System.out.println("sendInfo err: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {
        NioClient nioClient = new NioClient();
        new Thread(()->{
            while(true){
                nioClient.readInfo();
                try{
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    System.out.println("sleep err:" + e.getMessage());
                }
            }
        }).start();

        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNextLine()){
            String msg = scanner.nextLine();
            nioClient.sendInfo(msg);
        }
    }
}
