package org.example.netty.group_chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

public class GroupChatClient {


    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup loopGroup = new NioEventLoopGroup();
        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(loopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("decoder",new StringDecoder());
                            pipeline.addLast("encoder",new StringEncoder());

                            pipeline.addLast(new GroupChatClientHandler());
                        }
                    });
            ChannelFuture cf = bootstrap.connect("localhost", 1314);
            cf.addListener(future -> {
                if(future.isSuccess()){
                    System.out.println("客户端连接成功");
                }else {
                    System.out.println("客户单连接失败，重新连接");
//                    bootstrap.connect("localhost",1314);
                }
            });

            Channel channel = cf.channel();
            System.out.println("--------------" + channel.remoteAddress() + "-------------");
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()){
                channel.writeAndFlush(scanner.nextLine());
            }

            cf.channel().closeFuture().sync();

        }finally {
            loopGroup.shutdownGracefully();
        }

    }
}
