package org.example.netty.group_chat.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.example.netty.group_chat.codec.GroupChatMessageDecode;
import org.example.netty.group_chat.codec.GroupChatMessageEncode;
import org.example.netty.group_chat.engine.ClientProtocolMgr;
import org.example.netty.group_chat.logger.Debug;

import java.util.concurrent.TimeUnit;

public class GroupChatServer {
    public static void init(){
        ClientProtocolMgr.Instance.onServerStart();
    }


    public void start(){
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(2);
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        try{
            serverBootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //  获取pipeline
                            ChannelPipeline pipeline = ch.pipeline();

                            pipeline.addLast(new GroupChatMessageDecode());
                            //  增加心跳检测机制
                            pipeline.addLast(new IdleStateHandler(7000,7000,100, TimeUnit.SECONDS));
//                            pipeline.addLast(new GroupChatServerHandler());
                            pipeline.addLast(new GroupChatServerDistributorHandler());
                            pipeline.addLast(new GroupChatMessageEncode());
                        }
                    });

            ChannelFuture cf = serverBootstrap.bind(1314).sync();
            cf.addListener(future -> {
                if(future.isSuccess()){
                    Debug.info("连接端口 1314 成功");
                }else {
                    Debug.warn("连接端口 1314 失败");
                }
            });

            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            Debug.warn("服务器启动失败");
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }


    public static void main(String[] args) {
        init();
        new GroupChatServer().start();
    }
}
