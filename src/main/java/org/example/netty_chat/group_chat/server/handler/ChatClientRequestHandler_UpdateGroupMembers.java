package org.example.netty_chat.group_chat.server.handler;

import io.netty.channel.ChannelHandlerContext;
import org.example.netty_chat.group_chat.bean.Packet;
import org.example.netty_chat.group_chat.bean.RequestPacket;
import org.example.netty_chat.group_chat.engine.ChatClientRequestHandlerBase;

public class ChatClientRequestHandler_UpdateGroupMembers extends ChatClientRequestHandlerBase<RequestPacket> {

    public static final ChatClientRequestHandler_UpdateGroupMembers instance = new ChatClientRequestHandler_UpdateGroupMembers();

    @Override
    public Packet onProcess(ChannelHandlerContext ctx, RequestPacket packet, long now) {

        return null;

    }
}
