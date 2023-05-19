package nio.chat_room.self_demo;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NioServer {
    private int PORT = 1314;
    private ServerSocketChannel serverChannel;
    private InetSocketAddress inetSocketAddress;
    private Selector selector;


    public NioServer() throws IOException {
        serverChannel = ServerSocketChannel.open();
        inetSocketAddress = new InetSocketAddress(PORT);
        serverChannel.bind(inetSocketAddress);
        // selector只能与非阻塞通道一起使用,将ServerSocketChannel设置为非阻塞
        serverChannel.configureBlocking(false);

        selector = Selector.open();
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
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
                            SocketChannel client = serverChannel.accept();
                            client.configureBlocking(false);
                            client.register(selector,SelectionKey.OP_READ);
                            System.out.println(client.getRemoteAddress() + " 连接...");
                        }

                        //  读取就绪
                        if(key.isReadable()){
                            getDataFromChannel(key);
                        }
                        iterator.remove();
                    }

                }else {
                    System.out.println("等待中...");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void getDataFromChannel(SelectionKey key){
        SocketChannel client = null;
        try{
            client = (SocketChannel) key.channel();

            ByteBuffer byteBuffer = ByteBuffer.allocate(256);
            int readLen = client.read(byteBuffer);
            if(readLen > 0){
                String msg = new String(byteBuffer.array());
                System.out.println("客户端数据：\n" + msg.trim());
                //  将数据发送给其他的客户端
                sendDataToOtherClients(msg,client);
            }

        } catch (IOException e) {
            try {
                System.out.println(client.getRemoteAddress() + " 离线了...");
                //取消注册
                key.cancel();
            } catch (IOException ex) {
                System.out.println("get data from channel is err :" + e.getMessage());
            }
        }finally {
//            try {
//                //  关闭了通道
//                if(client != null){
//                    client.close();
//                }
//            } catch (IOException e) {
//                System.out.println("client close is err，" + e.getMessage() );
//            }
        }
    }

    private void sendDataToOtherClients(String msg,SocketChannel self) throws IOException {
        System.out.println("服务器转发消息中...");
        System.out.println("服务器转发数据给客户端线程: " + Thread.currentThread().getName());
        ///######################################################################
        //  LFH 在一个selector中，有3个SelectorKey的集合
        //      1.key set代表了所有注册在这个Selector上的channel，这个集合可以通过keys()方法拿到。
        //      2.Selected-key set代表了所有通过select()方法监测到可以进行IO操作的channel，这个集合可以通过selectedKeys()拿到。
        //      3.Cancelled-key set代表了已经cancel了注册关系的channel，在下一个select()操作中，这些channel对应的SelectionKey会从key set和cancelled-key set中移走。这个集合无法直接访问。
        //#######################################################################
        Set<SelectionKey> selectionKeys = this.selector.keys();
        for (SelectionKey key : selectionKeys) {
            Channel targetChannel = key.channel();
            if(targetChannel instanceof SocketChannel && targetChannel != self){
                ByteBuffer byteBuff = ByteBuffer.wrap(msg.getBytes());
                SocketChannel dest = (SocketChannel) targetChannel;
                dest.write(byteBuff);
                System.out.println("server send:"+byteBuff);
            }
        }
    }


    public static void main(String[] args) throws IOException {
        NioServer nioServer = new NioServer();
        nioServer.listen();
    }
}
