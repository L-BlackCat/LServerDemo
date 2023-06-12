package org.example.netty.group_chat.engine;

import io.netty.channel.ChannelHandlerContext;
import org.example.netty.group_chat.bean.Packet;

public interface ISendPacketHandler {
    void sendData(ChannelHandlerContext ctx,Packet packet,long now);
}
