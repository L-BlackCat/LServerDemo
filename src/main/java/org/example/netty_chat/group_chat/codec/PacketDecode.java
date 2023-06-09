package org.example.netty_chat.group_chat.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import org.example.netty_chat.group_chat.bean.Packet;
import org.example.netty_chat.group_chat.logger.Debug;

import java.util.List;

public class PacketDecode extends ReplayingDecoder<Packet> {
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Debug.debug("进入编码");
    }


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        try{
            Debug.debug(ctx.channel().remoteAddress() + " 开始进行了解码");
            Packet packetData = PacketCodeC.Instance.decode(in);
            if(packetData == null){
                return;
            }

            out.add(packetData);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
