package org.example.netty.group_chat.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import org.example.netty.group_chat.bean.Packet;
import org.example.netty.group_chat.bean.PacketData;
import org.example.netty.group_chat.logger.Debug;

import java.util.List;

public class PacketCodeCHandler extends MessageToMessageCodec<ByteBuf, PacketData> {
    public static final PacketCodeCHandler instance = new PacketCodeCHandler();
    @Override
    protected void encode(ChannelHandlerContext ctx, PacketData pack, List<Object> out) throws Exception {
        Debug.debug("开始进行编码----");
        ByteBuf buf = PacketCodeC.Instance.encode(pack);
        out.add(buf);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        try{
            Debug.debug(ctx.channel().remoteAddress() + " 开始进行了解码");
            Packet packetData = PacketCodeC.Instance.decode(msg);
            if(packetData == null){
                return;
            }

            out.add(packetData);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
