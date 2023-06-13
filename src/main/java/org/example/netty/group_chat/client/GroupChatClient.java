package org.example.netty.group_chat.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.example.netty.group_chat.bean.LoginRequestPacket;
import org.example.netty.group_chat.bean.MessageRequestPacket;
import org.example.netty.group_chat.cli_main.CliNettyChatHallFrame;
import org.example.netty.group_chat.cli_main.CliNettyChatLoginFrame;
import org.example.netty.group_chat.cli_main.CliNettyChatMainFrame;
import org.example.netty.group_chat.codec.GroupChatMessageDecode;
import org.example.netty.group_chat.codec.GroupChatMessageEncode;
import org.example.netty.group_chat.codec.GroupChatSpliter;
import org.example.netty.group_chat.engine.ClientProtocolID;
import org.example.netty.group_chat.engine.ClientProtocolMgr;

import java.util.Scanner;

public class GroupChatClient {
    public static final int MAX_RETRY = 5;
    public static boolean isRun = true;

    public static CliNettyChatMainFrame mainFrame;

    public static CliNettyChatLoginFrame loginFrame;

    public static CliNettyChatHallFrame hallFrame;


    public static void main(String[] args) throws InterruptedException {
        ClientProtocolMgr.Instance.onServerStart();
        loginFrame = new CliNettyChatLoginFrame();
        mainFrame = new CliNettyChatMainFrame();
        hallFrame = new CliNettyChatHallFrame();

        loginFrame.setVisible(true);
    }

    public static void start(String name) throws InterruptedException {
          NioEventLoopGroup loopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(loopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
//                            pipeline.addLast(new GroupChatSpliter());
                            pipeline.addLast("decoder", new GroupChatMessageDecode());
                            pipeline.addLast("encoder", new GroupChatMessageEncode());

                            pipeline.addLast(new GroupChatClientHandler());
                        }
                    });
            ChannelFuture cf = bootstrap.connect("localhost", 1314).sync();
            cf.addListener(future -> {
                if (future.isSuccess()) {
                    Channel channel = cf.channel();
                    System.out.println("客户端连接成功");
                    login(channel,name);
                    mainFrame.show(channel);
                } else {
                    System.out.println("客户单连接失败，重新连接");
//                    connect(bootstrap,"localhost",1314,5);
                }
            });

            Channel channel = cf.channel();
            System.out.println("--------------" + channel.remoteAddress() + "-------------");

            cf.channel().closeFuture().sync();
            System.out.println("结束");

        } finally {
            loopGroup.shutdownGracefully();
            isRun = false;
        }
    }

    public static void login(Channel channel,String name){
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setRequestId(ClientProtocolID.Chat_Login_Request.getId());
        loginRequestPacket.getMap().put("name", name);
        loginRequestPacket.setName(name);

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
