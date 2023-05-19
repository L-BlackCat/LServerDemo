package org.example.netty.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {
    public static void main(String[] args) throws Exception {
        NioEventLoopGroup group = new NioEventLoopGroup();

        try{

            //  创建客户端启动对象
            Bootstrap bootstrap = new Bootstrap();
            //  设置相关参数
            bootstrap.group(group)  //设置线程组
                    .channel(NioSocketChannel.class)    //设置客户端通道的实现类（用于反射创建对象）
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyClientHandler());    //加入自己的处理器
                        }
                    });

            System.out.println("客户端Ok");

            ChannelFuture cf = bootstrap.connect("localhost", 1314).sync();
            cf.addListener(future -> {
                if (future.isSuccess()) {
                    System.out.println("客户端连接成功 [host](127.0.0.1) [port](1314)");
                }else {
                    System.out.println("客户端连接失败 [host](127.0.0.1) [port](1314) 重连");
//                    bootstrap.connect("localhost",1314);
                }
            });

            cf.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully();
        }
    }
}
