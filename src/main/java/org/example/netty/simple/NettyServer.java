package org.example.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
    public static void main(String[] args) {
        //  服务端的一个启动辅助类。通过给它设置一系列参数来绑定端口启动服务。
        ServerBootstrap bootstrap = new ServerBootstrap();

        //  服务器线程模型外观类，作用:不停地检测IO事件、处理IO事件、执行任务。不断重复这三个事件
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();    //  默认为核心cpu数目 * 2

        try{
            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(1314)
                    .option(ChannelOption.SO_BACKLOG, 128)  //初始化服务器可连接队列大小，这里是128
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    // handler表示在服务端启动过程中，需要经过哪些流程
                    // 这里SimpleServerHandler最终的顶层接口为ChannelHandler，是Netty的一大核心概念，表示数据流经过的处理器，可以理解为流水线上的每一道关卡。
                    .handler(new SimpleServerHandler())  //handler对应bossGroup,childHandler对应workerGroup
                    //创建一个通道初始化对象,用于设置一系列的handler来处理每个连接的数据，也就是上面所说的，老板接到一个活之后，告诉每个工人这个活的固定步骤
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            System.out.println("客户socketChannel hashCode=" + socketChannel.hashCode());
                            socketChannel.pipeline().addLast(new NettyServerHandler()); //给通道增加一个处理handler
                        }
                    }); //  给我们的workerGroup的EventLoop对饮的管道设置处理器

            /*
            * bind()
            * 通过反射创建一个NioServerSocketChannel对象，并且在创建过程中创建一系列核心组件：
            *   Channel
            *   ChannelConfig
            *   ChannelId
            *   unsafe
            *   pipeline
            *   ChannelHandler
            * */
            //  绑定端口，同步等待，真正的启动过程   服务器启动完成，才会进入下一行代码
            ChannelFuture cf = bootstrap.bind().sync();
            /*
            * netty中的所有I/O操作都是异步的，不能立刻得知消息是否被正确处理。具体的实现就是通过Future和ChannelFuture对他们注册一个监听，当操作执行成功或失败时监听会自动触发注册事件
            * */

            //给cf 注册监听器，监控我们关心的事件
            cf.addListener(future -> {
                if(cf.isSuccess()){
                    System.out.println("监听端口 1314 成功");
                }else {
                    System.out.println("监听端口 1314 失败");
                }
            });

            //  等待服务器关闭端口绑定，这里的作用是让服务器不会退出
            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //  关闭两组时间循环，关闭之后，main方法就结束了
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }
}
