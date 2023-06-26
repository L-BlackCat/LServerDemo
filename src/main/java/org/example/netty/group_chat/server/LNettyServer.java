package org.example.netty.group_chat.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.example.netty.group_chat.codec.PacketCodeCHandler;
import org.example.netty.group_chat.codec.GroupChatSpliter;
import org.example.netty.group_chat.engine.ClientProtocolMgr;
import org.example.netty.group_chat.logger.Debug;
import org.example.netty.simple.SimpleServerHandler;

public class LNettyServer {
    public static void init(){
        ClientProtocolMgr.Instance.onServerStart();
    }

    /**
     * Netty的线程模型是事件驱动型，这个线程要做的事情就是
     *  1.不停的检测IO事件
     *  2.处理IO事件
     *  3.执行任务
     * 不停重复这三个步骤
     */
    public void start(){
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();  //比作老板，从外面接活,用来接受新的连接，然后将新的连接交给workerGroup来处理
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(2);   //比作员工，负责干活，用来处理连接的事件。
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        try{
            serverBootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new SimpleServerHandler())
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            /**
                             * 设置一系列的handler，用来处理每个连接的数据
                             * 老板从外面街接到一个活之后，告诉每个工人活的固定步骤
                             */
                            //  获取pipeline
                            ChannelPipeline pipeline = ch.pipeline();

                            pipeline.addLast(PacketCodeCHandler.instance);
                            pipeline.addLast(new GroupChatSpliter());
                            //  增加心跳检测机制
                            pipeline.addLast(new IMIdleStateHandler(10,8,0));
                            pipeline.addLast(new IMServerHandler());
                        }
                    });

            /**
             *  bind(port)函数
             *  创建JDK底层channel，创建对应的Config对象，设置channel为非阻塞模式。
             *  生创建Server对应的Channel，创建各大组件，成的组件：
             *      channel
             *      channelId
             *      unsafe
             *      channelPipeline
             *      channelConfig
             *          用来保存channel的Attr和Option
             *      *channelHandler(并非自bind(port)中创建)
             *  初始化Server对应的channel,init(channel):
             *      设置服务器的channel的option和attr
             *      设置客户端channel的option和attr
             *      配置服务器启动逻辑（这时候并没有启动）
             *          添加用户自定义的处理逻辑到服务端启动流程
             *          server的channel添加ServerBootstrapAcceptor接入器，接受新情求，并触发addHandler、register等事件。
             *  config().group().register(channel)
             *      绑定事件循环器
             *      调用JDK底层注册selector，并将NioServerSocketChannel当成attachment绑定到Selector上
             *
             *      调用invokeHandlerAddedIfNeeded(),控制台打印：handlerAdded
             *      调用pipeline.fireChannelRegistered()之后，控制台输出： handlerAdded    channelRegistered
             *
             *  doBind0()
             *      调用JDK底层绑定端口和地址，触发服务器active事件，控制台打印：handlerActive,当active事件被触发时，才真正做服务器端口绑定。
             *
             */
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
            //  关闭两组事件循环
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }


    public static void main(String[] args) {
        init();
        new LNettyServer().start();
    }
}
