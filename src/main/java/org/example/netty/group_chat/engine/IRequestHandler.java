package org.example.netty.group_chat.engine;

import io.netty.channel.ChannelHandlerContext;
import org.example.netty.group_chat.data_pack.Packet;

public interface IRequestHandler<T extends Packet> {
    Packet onProcess(ChannelHandlerContext ctx, T packet, long now);
}