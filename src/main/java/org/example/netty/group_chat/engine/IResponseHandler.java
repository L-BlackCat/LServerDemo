package org.example.netty.group_chat.engine;

import io.netty.channel.ChannelHandlerContext;
import org.example.netty.group_chat.bean.Packet;

public interface IResponseHandler<T extends Packet> {
    void onProcess(ChannelHandlerContext ctx, T packet, long now);
}
