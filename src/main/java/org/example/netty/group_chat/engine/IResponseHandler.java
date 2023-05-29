package org.example.netty.group_chat.engine;

import io.netty.channel.ChannelHandlerContext;
import org.example.netty.group_chat.data_pack.Packet;

public interface IResponseHandler<T extends Packet> {
    void onProcess(ChannelHandlerContext ctx,T packet);
}
