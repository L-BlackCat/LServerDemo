package org.example.netty.tcp.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class MyMessageDecoder extends ReplayingDecoder<Void> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyMessageDecoder decode 被调用");
        int length = in.readInt();
        byte[] bytes = new byte[length];
        in.readBytes(bytes);


        //封装成 MessageProtocol 对象，放入 out， 传递下一个handler业务处理
        MessageProtocol protocol = new MessageProtocol();
        protocol.setLen(length);
        protocol.setContent(bytes);
        out.add(protocol);
    }
}
