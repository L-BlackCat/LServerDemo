package org.example.netty.tcp.protocoltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

public class MyProtocolClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    int count;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
            String mes = "今天有点饿，晚上去吃火锅";

            byte[] bytes = mes.getBytes(StandardCharsets.UTF_8);
            int len = bytes.length;

            MessageProtocol messageProtocol = new MessageProtocol();
            messageProtocol.setLen(len);
            messageProtocol.setContent(bytes);

            //  客户端和服务器端进行交互的二进制数据载体为ByteBuf，ByteBuf通过连接的内存管理器创建，字节数据填充到ByteBuf才能写到对端
            ctx.writeAndFlush(messageProtocol);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        int len = msg.getLen();
        byte[] bytes = msg.getContent();

        String message = new String(bytes, StandardCharsets.UTF_8);
        System.out.println("客户端接收到消息如下");
        System.out.println("长度=" + len);
        System.out.println("内容=" + message);

        System.out.println("客户端接受消息数量=" + (++this.count));
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常消息=" + cause.getMessage());
        ctx.close();
    }
}
