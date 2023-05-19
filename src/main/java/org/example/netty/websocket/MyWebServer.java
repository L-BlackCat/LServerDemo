package org.example.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class MyWebServer {
    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(2);    //  默认为核心cpu数目 * 2

        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)  //初始化服务器可连接队列大小，这里是128
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();

                            //  因为基于http协议，使用http的编码和解码器
                            pipeline.addLast(new HttpServerCodec());
                            //  以块方式写，添加ChunkedWriteHandler处理器
                            pipeline.addLast(new ChunkedWriteHandler());

                            //  http数据在传输过程中是分段，这就是为什么当浏览器发送大量数据时，就会发出多次http请求，httpObjectAggregator就是可以将多个段聚合
                            pipeline.addLast(new HttpObjectAggregator(8192));

                            //webSocketServerProtocolHandler核心功能是将http协议升级为ws协议，保持长链接
                            pipeline.addLast(new WebSocketServerProtocolHandler("/hello2"));


                            //  自定义handler，处理业务逻辑
                            /*
                            *  对应websocket ，它的数据是以 帧(frame) 形式传递
                            *  WebSocketFrame有六个子类
                            * */
                            pipeline.addLast(new MyTextWenSocketFrameHandler());
                        }
                    });

            ChannelFuture cf = serverBootstrap.bind(1314).sync();

            cf.addListener(future -> {
                if(cf.isSuccess()){
                    System.out.println("监听端口 1314 成功");
                }else {
                    System.out.println("监听端口 1314 失败");
                }
            });

            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
