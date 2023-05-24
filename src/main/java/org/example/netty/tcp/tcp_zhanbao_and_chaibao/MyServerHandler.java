package org.example.netty.tcp.tcp_zhanbao_and_chaibao;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class MyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private int count;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {

        byte[] buffer = new byte[msg.readableBytes()];
        msg.readBytes(buffer);

        //将buffer转成字符串
        String message = new String(buffer, StandardCharsets.UTF_8);

        //粘包：客户端发送10个数据包，粘到了一起变成了一个大数据包，服务器收到一个大数据包
        //拆包：客户端发送10个数据包，6.5个包变成了一个包，3.5个包变成了另一个包，服务器收到了两个包
        System.out.println("服务器接收到数据 " + message);
        System.out.println("服务器接收到消息量=" + (++this.count));
        /*
        * 思路：解决服务器端每次读取数据长度的问题，就不会出现服务器多读或少读数据的问题，从而避免TCP粘包、拆包
        * 方式：约定好数据结构，按照规则读取指定字节数    自定义协议+编码解码器
        * */

        //服务器回送数据给客户端, 回送一个随机id ,
        ByteBuf responseByteBuf = Unpooled.copiedBuffer(UUID.randomUUID() + " ", StandardCharsets.UTF_8);
        ctx.writeAndFlush(responseByteBuf);

    }
}
