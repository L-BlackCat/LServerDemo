package org.example.netty_chat.group_chat.engine;

import io.netty.channel.ChannelHandlerContext;
import org.example.netty_chat.group_chat.bean.Packet;
import org.example.netty_chat.group_chat.server.GlobalSessionMgr;

public class ChatSendPacketToSpecifiedChannel implements ISendPacketHandler{
    @Override
    public void sendData(ChannelHandlerContext ctx, Packet packet, long now) {
        String groupName = packet.getMap().getString("group_name");
        GlobalSessionMgr.Instance.sendMsgToSpecified(groupName,packet);
    }
}
