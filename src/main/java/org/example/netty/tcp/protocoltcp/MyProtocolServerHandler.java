package org.example.netty.tcp.protocoltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class MyProtocolServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    int count;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        //接受到输出并处理
        int len = msg.getLen();
        byte[] context = msg.getContent();

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("服务器接受消息如下：");
        System.out.println("长度=" + len);
        System.out.println("内容=" + new String(context, StandardCharsets.UTF_8));

        System.out.println("服务器接受消息数量=" + (++this.count));

        //  回复消息
        String responseMsg = UUID.randomUUID().toString();
        MessageProtocol responseProtocol = new MessageProtocol();
        int responseLen = responseMsg.getBytes(StandardCharsets.UTF_8).length;
        responseProtocol.setLen(responseLen);
        responseProtocol.setContent(responseMsg.getBytes(StandardCharsets.UTF_8));
        ctx.writeAndFlush(responseProtocol);
    }
}
