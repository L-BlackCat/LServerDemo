package org.example.nio.chat_room.final_demo;


import java.net.InetSocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

public class NioChatServer {

    private int PORT = 1314;
    private ServerSocketChannel serverChannel;
    private InetSocketAddress inetSocketAddress;
    private Selector selector;

    private SelectionKey serverKey;

    private Vector<String> nameVector = new Vector<>();

    public NioChatServer() throws IOException {
        serverChannel = ServerSocketChannel.open();
        inetSocketAddress = new InetSocketAddress(PORT);
        serverChannel.bind(inetSocketAddress);
        // selector只能与非阻塞通道一起使用,将ServerSocketChannel设置为非阻塞
        serverChannel.configureBlocking(false);

        selector = Selector.open();
        serverKey = serverChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    ///######################################################################
    //  监听客户端请求 -   将请求发送给另外的客户端
    //#######################################################################
    public void listen(){
        try{
            while(true){

                int count = selector.select();
                if(count > 0){
                    Set<SelectionKey> selectKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectKeys.iterator();
                    while (iterator.hasNext()){
                        SelectionKey key = iterator.next();

                        //  链接就绪
                        if(key.isAcceptable()){
                            iterator.remove();
                            SocketChannel client = serverChannel.accept();
                            if(client == null){
                                continue;
                            }
                            client.configureBlocking(false);
                            client.register(selector,SelectionKey.OP_READ);
                            System.out.println(client.getRemoteAddress() + " 连接...");
                        }

                        //  读取就绪
                        if(key.isReadable()){
                            readData(key);
                        }

                        //  读就绪
                        if(key.isWritable()){
                            //  LFM 其他用户上线，聊天室内的通道会发送空字符串数据（目前修了三次）
                            writeData(key);
                        }
                    }

                }else {
                    System.out.println("等待中...");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void readData(SelectionKey key){
        SocketChannel client = null;
        try{
            client = (SocketChannel) key.channel();
            StringBuilder msgBuilder = new StringBuilder();

            ByteBuffer byteBuffer = ByteBuffer.allocate(256);
            byteBuffer.clear();
            int readLen;
            if((readLen = client.read(byteBuffer)) > 0){
                byteBuffer.flip();
                msgBuilder.append(new String(byteBuffer.array(),0,readLen));
            }
            String msg = msgBuilder.toString();

            if(msg.contains("login_")){
                //  登录界面，记录玩家姓名到列表中，并将列表写入到key的attachment中，进行客户端界面的展示
                String uname = msg.substring(6);
                nameVector.add(uname);
                System.out.println(uname + " 上线...");
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

                while (iterator.hasNext()) {
                    SelectionKey targetKey = iterator.next();
                    if(targetKey != serverKey){
                        targetKey.attach(nameVector);
                        //  设置selector监控IO操作的bit mask
                        targetKey.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
                    }
                }
            }else if(msg.contains("exit_")){
                //  退出界面，将目标key从set中进行移除，并告知客户端通道断开连接
                String uname = msg.substring(5);
                nameVector.remove(uname);
                key.attach("close");
                key.interestOps(SelectionKey.OP_WRITE);
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey targetKey = iterator.next();
                    if(targetKey != serverKey && targetKey != key){
                        targetKey.attach(nameVector);
                        targetKey.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
                    }
                }
                System.out.println(uname + " 离线...");

            }else{
                //获取客户端发送数据，并将数据发送给其他的客户端，更新key中的attachment中的用户列表
                String uname = msg.substring(0, msg.indexOf("^"));
                String context = msg.substring(msg.indexOf("^") + 1);
                System.out.println("[" + getDate() + "] (" + uname + "):" + context);
                context = uname + " " + getDate() +"\n" + context + "\n";
                Iterator<SelectionKey> iterator = this.selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey targetKey = iterator.next();
                    targetKey.attach(context);
                    targetKey.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
                }
            }

        } catch (IOException e) {
            try {
                System.out.println(client.getRemoteAddress() + " 离线了...");
                //取消注册
                key.cancel();
            } catch (IOException ex) {
                System.out.println("get data from channel is err :" + e.getMessage());
            }
        }
    }

    private void writeData(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        Object obj = key.attachment();
        key.attach("");
        if(obj.toString().equals("close")){
            key.cancel();
            //关闭通道
            channel.socket().close();
            channel.close();
            System.out.println("close...");
            return;
        }else{
            channel.write(ByteBuffer.wrap(obj.toString().getBytes()));
            System.out.println(channel.getRemoteAddress() + ":" + obj);
        }

        key.interestOps(SelectionKey.OP_READ);
    }


    public String getDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return sdf.format(new Date());
    }

    public static void main(String[] args) throws IOException {
        NioChatServer chatServer = new NioChatServer();
        chatServer.listen();
    }

}
