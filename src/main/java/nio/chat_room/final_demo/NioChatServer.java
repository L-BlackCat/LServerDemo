package nio.chat_room.final_demo;


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
        /*
        * 优点：模型简单，没有多线程、进程通信、竞争的问题
        * 缺点：
        *   性能问题，发挥不了多核cpu的性能。handler在处理一个链接的时候，整个进程无法处理其他的事件，当事件足够多的时候，导致性能瓶颈
        *   可靠性问题，线程意外终止，或者进入死循环，会导致整个通信模块不可用
        *
        * 解决方式：
        *   1.单selector多线程，使用线程池，read到数据之后，线程池创建线程用来对数据进行处理，返回结果,将结果write回客户端
        *       优点：发挥多核cpu的性能
        *       缺点：
        *           单线程用来处理所有事件的监听和响应，当处理事件足够多的时候（高并发场景下），会导致性能瓶颈
        *   2.主从Reactor多线程
        *       一个主Reactor线程用来处理连接事件，一个或多个从Reactor线程用来绑定请求socket的事件监听和响应,使用线程池，read到数据之后，线程池创建线程用来对数据进行处理，返回结果,将结果write回客户端
        *       优点：
        *           主Reactor和从Reactor职责明确，主Reactor只需要接受新连接，从Reactor完成后续业务操作
        *           主Reactor和从Reactor数据交互简单，Reactor字需要将连接传给从Reactor，从Reactor无需返回参数
        *           发挥多核cpu’性能，大大提高性能上限
        *       缺点：
        *           编程复杂
        *
        *
        * 5.7.1 3 种模式用生活案例来理解
        *   单 Reactor 单线程，前台接待员和服务员是同一个人，全程为顾客服务
        *   单 Reactor 多线程，1 个前台接待员，多个服务员，接待员只负责接待
        *   主从 Reactor 多线程，多个前台接待员，多个服务生
        *
        * */
    }

}
