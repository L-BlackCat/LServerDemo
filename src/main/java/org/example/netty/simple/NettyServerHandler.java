package org.example.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        System.out.println("服务器读取线程：" + Thread.currentThread().getName() + " channel =" + ctx.channel());
//        System.out.println("server ctx =" + ctx);
//        System.out.println("看看channel 和 pipeline的关系");
//        Channel channel = ctx.channel();
//        ChannelPipeline pipeline = ctx.pipeline();  //本质上是一个双向链表，出站入站
//
//        //  ByteBuf是Netty提供的，不是NIO的ByteBuff
//        ByteBuf buf = (ByteBuf) msg;
//        System.out.println("客户端发送消息：" + buf.toString(CharsetUtil.UTF_8));
//        System.out.println("客户端地址：" + channel.remoteAddress());



        //Netty任务队列中的Task有三种典型使用场景

        //1.用户自定义普通任务
        ctx.channel().eventLoop().execute(() -> {
            try{
                Thread.sleep(5000);
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello, welcome to Netty Server",CharsetUtil.UTF_8));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        });


        //2.用户自定义定时任务
        ctx.channel().eventLoop().schedule(() -> {

            try{
                Thread.sleep(5000);
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello, welcome to Netty Server",CharsetUtil.UTF_8));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },5, TimeUnit.SECONDS);

        //3.非当前Reactor线程调用Channel的各种方法，例如：聊天推送系统，一个用户向聊天室中的其他用户发送消息，服务器根据标识，向对应的channel发送消息

    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //  将数据写入缓存，并刷新
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello, welcome to Netty Server",CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //  处理异常，一般用来关闭通道
        ctx.close();
    }
}
