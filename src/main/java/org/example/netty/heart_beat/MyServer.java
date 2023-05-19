package org.example.netty.heart_beat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class MyServer {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(2);

        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            /*
                            * IdleStateHandle是netty提供的处理空闲状态的处理器，当一段时间没有触发读、写或读写执行，就会触发IdleStateEvent事件
                            * long readerIdleTime:  表示多长时间没有读，就会发送一个心跳检测包检测是否连接
                            * long writerIdleTime:  表示多长时间没有写，就会发送一个心跳检测包检测是否连接
                            * long allIdleTime: 表示多长事件没有读写，就会发送一个心跳检测包检测是否连接
                            *
                            * 当IdleStateEvent触发后，就会传递给通道的下一个handler,通过调用(触发)下一个handler的userEventTriggered ，在该方法中处理IdleStateEvent（读空闲，写空闲，读写空闲）
                            * */
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new IdleStateHandler(7000,7000,20, TimeUnit.SECONDS));
                            pipeline.addLast(new MyServerHandler());
                        }
                    });

            ChannelFuture cf = serverBootstrap.bind(1314).sync();
            cf.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
