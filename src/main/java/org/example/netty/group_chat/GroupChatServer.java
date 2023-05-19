package org.example.netty.group_chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class GroupChatServer {


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

                            pipeline.addLast(new StringEncoder());
                            pipeline.addLast(new StringDecoder());
                            //  增加心跳检测机制
                            pipeline.addLast(new IdleStateHandler(7000,7000,20, TimeUnit.SECONDS));
                            pipeline.addLast(new GroupChatServerHandler());
                        }
                    });

            ChannelFuture cf = serverBootstrap.bind(1314).sync();
            cf.addListener(future -> {
                if(future.isSuccess()){
                    System.out.println("连接端口 1314 成功");
                }else {
                    System.out.println("连接端口 1314 失败");
                }
            });

            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            System.out.println("服务器启动失败");
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }


    public static void main(String[] args) {
        new GroupChatServer().start();
    }
}
