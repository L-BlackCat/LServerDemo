package org.example.netty.group_chat.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import org.example.netty.group_chat.data_pack.Packet;

import java.util.List;

public class GroupChatMessageDecode extends ReplayingDecoder<Packet> {
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("进入了解码");
    }


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        try{
            System.out.println(ctx.channel().remoteAddress() + "开始进行了解码");
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
