package org.example.netty.group_chat.client.handler;

import io.netty.channel.ChannelHandlerContext;
import org.example.netty.group_chat.bean.Packet;
import org.example.netty.group_chat.engine.ChatClientRequestHandlerBase;
import org.example.netty.group_chat.engine.ChatClientResponseHandlerBase;

public class ChatClientResponseHandler_ChatLogout extends ChatClientResponseHandlerBase {

    @Override
    public void onProcess(ChannelHandlerContext ctx, Packet packet, long now) {

    }
}
