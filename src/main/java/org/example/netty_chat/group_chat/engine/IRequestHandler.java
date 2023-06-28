package org.example.netty_chat.group_chat.engine;

import io.netty.channel.ChannelHandlerContext;
import org.example.netty_chat.group_chat.bean.Packet;

public interface IRequestHandler<T extends Packet> {
    Packet onProcess(ChannelHandlerContext ctx, T packet, long now) throws Exception;

}
