package org.example.netty.tcp.tcp_zhanbao_and_chaibao;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class MyClient {
    public static void main(String[] args) {
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
                            ch.pipeline().addLast(new MyClientHandler());    //加入自己的处理器
                        }
                    });

            System.out.println("客户端Ok");

            ChannelFuture cf = bootstrap.connect("localhost", 1314).sync();
//            cf.channel().write("hello world!");
//            cf.channel().write("this is netty!");
//            cf.channel().write("I want to do something !");
//            cf.channel().write("HAHAHAHAHH!");
//            cf.channel().write("wawawawaw");
//            cf.channel().flush();

            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
