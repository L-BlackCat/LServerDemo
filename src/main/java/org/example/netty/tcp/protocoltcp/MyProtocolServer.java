package org.example.netty.tcp.protocoltcp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class MyProtocolServer {
    public static void main(String[] args) {

        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1); //监听端口，接受新连接的线程组
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(2);    //处理每一个连接的数据读写的线程组

        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup)    //  1.线程模型（两大线程组）
                    .channel(NioServerSocketChannel.class)  //  2.指定I/O模型
                    .option(ChannelOption.SO_BACKLOG, 128)  //初始化服务器可连接队列大小，这里是128，给服务器端channel设置tcp参数
                    .childOption(ChannelOption.SO_KEEPALIVE, true)  //给每个连接设置tcp参数
                    .childHandler(new ChannelInitializer<SocketChannel>() {  //  3.childHandler()处理新连接的读写处理逻辑   handler()方法用于指定服务器端启动过程中的一些逻辑
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {

                            ChannelPipeline pipeline = socketChannel.pipeline();

                            pipeline.addLast(new MyMessageDecoder());
                            pipeline.addLast(new MyMessageEncoder());
                            pipeline.addLast(new MyProtocolServerHandler());
                        }
                    });

            //  4.绑定端口1314启动服务器
            ChannelFuture cf = serverBootstrap.bind(1314).sync();



            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
