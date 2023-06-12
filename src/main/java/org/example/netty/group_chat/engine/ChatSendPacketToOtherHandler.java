package org.example.netty.group_chat.engine;

import io.netty.channel.ChannelHandlerContext;
import org.example.netty.group_chat.bean.Packet;
import org.example.netty.group_chat.server.GlobalSessionMgr;

public class ChatSendPacketToOtherHandler implements ISendPacketHandler{
    @Override
    public void sendData(ChannelHandlerContext ctx, Packet packet, long now) {
        GlobalSessionMgr.Instance.sendMsgToOther(ctx.channel(),packet);
    }
}
