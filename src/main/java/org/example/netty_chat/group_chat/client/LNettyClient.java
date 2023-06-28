package org.example.netty_chat.group_chat.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.example.netty_chat.group_chat.bean.MessageRequestPacket;
import org.example.netty_chat.group_chat.bean.RequestPacket;
import org.example.netty_chat.group_chat.cli_main.CliNettyChatHallFrame;
import org.example.netty_chat.group_chat.cli_main.CliNettyChatLoginFrame;
import org.example.netty_chat.group_chat.cli_main.CliNettyChatMainFrame;
import org.example.netty_chat.group_chat.codec.GroupChatSpliter;
import org.example.netty_chat.group_chat.codec.PacketCodeCHandler;
import org.example.netty_chat.group_chat.engine.ClientProtocolID;
import org.example.netty_chat.group_chat.engine.ClientProtocolMgr;
import org.example.netty_chat.group_chat.engine.entity.Session;
import org.example.netty_chat.group_chat.logger.Debug;
import org.example.netty_chat.group_chat.server.IMIdleStateHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class LNettyClient {
    public static final int MAX_RETRY = 5;
    public static boolean isRun = true;
    private String userName;

    private int port;
    private String host;
    private Bootstrap bootstrap;

    private NioEventLoopGroup loopGroup;

    public LNettyClient(String host, int port) {
        this.port = port;
        this.host = host;
    }

    public static CliNettyChatMainFrame mainFrame;

    public static CliNettyChatLoginFrame loginFrame = new CliNettyChatLoginFrame();

    public static CliNettyChatHallFrame hallFrame = new CliNettyChatHallFrame();

    public static Map<String,CliNettyChatMainFrame> mainFrameMap = new HashMap<>();

    public static CliNettyChatMainFrame createMainFrame(String groupName, List<Session> sessionList){
        if(mainFrameMap.containsKey(groupName)){
            return mainFrameMap.get(groupName);
        }
        CliNettyChatMainFrame mainFrame = new CliNettyChatMainFrame(groupName);
        mainFrame.updateModel(sessionList);
        mainFrameMap.put(groupName,mainFrame);
        mainFrame.setGroupName(groupName);
        return mainFrame;
    }

    public static CliNettyChatMainFrame getMainFrame(String groupName){
        return mainFrameMap.get(groupName);
    }

    public static void shutdownMainFrame(String groupName){
        CliNettyChatMainFrame mainFrame = mainFrameMap.remove(groupName);
        mainFrame.getFrame().setVisible(false);
    }

    public static void main(String[] args) throws InterruptedException {
        ClientProtocolMgr.Instance.onServerStart();
        loginFrame = new CliNettyChatLoginFrame();
        mainFrame = new CliNettyChatMainFrame();
        hallFrame = new CliNettyChatHallFrame();

        loginFrame.setVisible(true);
    }

    public static void start(String name) throws InterruptedException {
        LNettyClient client = new LNettyClient("localhost", 1314);
        client.userName = name;
        client.connect();
    }

    public void connect(){
        LNettyClient client = this;
        loopGroup = new NioEventLoopGroup();
        try {
            bootstrap = new Bootstrap();
            bootstrap.group(loopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new IMIdleStateHandler(10,0,0));
                            pipeline.addLast(new GroupChatSpliter());
                            pipeline.addLast(PacketCodeCHandler.instance);
                            pipeline.addLast(new IMClientHandler(client));
                        }
                    });
            ChannelFuture cf = bootstrap.connect(client.host,client.port).sync();
            cf.addListener(future -> {
                if (future.isSuccess()) {
                    Channel channel = cf.channel();
                    Debug.info("客户端连接成功");
                    login(channel,userName);
                } else {
                    Throwable cause = future.cause();
                    Debug.err("客户端连接失败 " + cause.getMessage());
                }
            });

            Channel channel = cf.channel();
            System.out.println("--------------" + channel.remoteAddress() + "-------------");

            cf.channel().closeFuture().sync();
            System.out.println("结束");

        } catch (InterruptedException e) {
            Debug.err("启动失败",e);
        } finally {
            loopGroup.shutdownGracefully();
            isRun = false;
        }
    }

    public static void login(Channel channel,String name){
        RequestPacket loginRequestPacket = new RequestPacket();
        loginRequestPacket.setRequestId(ClientProtocolID.Chat_Login_Request.getId());
        loginRequestPacket.getMap().put("name", name);

        channel.writeAndFlush(loginRequestPacket);
        channel.attr(IAttributes.NAME).set(name);
    }

    public static void startConsoleThread(Channel channel){
        new Thread(() -> {

            Scanner scanner = new Scanner(System.in);
            while(!Thread.interrupted() && isRun){
                if(ChannelAttrUtil.Instance.hasLogin(channel)){

                    String msg = scanner.nextLine().trim();
                    MessageRequestPacket packet = new MessageRequestPacket();
                    packet.setMsg(msg);
                    packet.setRequestId(ClientProtocolID.Chat_Message_Request.getId());
                    channel.writeAndFlush(packet);
                }
            }
//            while(!Thread.interrupted() && isRun){
//
//                if(ChannelAttrUtil.Instance.hasLogin(channel)){
//                    String msg = "你好，欢迎关注我的微信公众号，《闪电侠的博客》!";
//                    for (int i = 0; i < 1000; i++) {
//                        String newMsg = msg + i;
//                        MessageRequestPacket packet = new MessageRequestPacket();
//                        packet.setMsg(newMsg);
//                        packet.setRequestId(ClientProtocolID.Chat_Message_Request.getId());
//                        channel.writeAndFlush(packet);
//                    }
//                    isRun = false;
//                }
//            }
        }).start();

    }

//    public static void connect(Bootstrap bootstrap,String host,int port,int retry) throws InterruptedException {
//
//        ChannelFuture cf = bootstrap.connect(host, port).sync();
//        cf.addListener(future -> {
//            if (future.isSuccess()) {
//                System.out.println("客户端连接成功");
//            }
//            else if(retry == 0){
//                System.out.println("重连次数已用完，放弃连接");
//            } else  {
//                //第几次重连
//                int order = (MAX_RETRY - retry) + 1;
//                int delay = 1 << order;
//                Debug.err(new Date() + "： 连接失败，第" + order + "次重连……");
//                bootstrap.config().group().schedule(() -> connect(bootstrap,host,port,retry - 1),delay, TimeUnit.SECONDS);
//            }
//
//
//        });
//    }
}
