package org.example.netty_chat.group_chat.engine;

import io.netty.channel.ChannelHandlerContext;
import org.example.netty_chat.group_chat.bean.Packet;

public class ChatSendPacketToSelfHandler implements ISendPacketHandler{
    @Override
    public void sendData(ChannelHandlerContext ctx, Packet packet, long now) {
        ctx.channel().writeAndFlush(packet);
    }
}
