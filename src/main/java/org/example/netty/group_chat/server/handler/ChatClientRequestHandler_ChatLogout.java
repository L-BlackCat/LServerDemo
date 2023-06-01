package org.example.netty.group_chat.server.handler;

import io.netty.channel.ChannelHandlerContext;
import org.example.netty.group_chat.bean.Packet;
import org.example.netty.group_chat.engine.ChatClientRequestHandlerBase;

public class ChatClientRequestHandler_ChatLogout extends ChatClientRequestHandlerBase {
    @Override
    public Packet onProcess(ChannelHandlerContext ctx, Packet packet, long now) {
        return null;
    }
}
