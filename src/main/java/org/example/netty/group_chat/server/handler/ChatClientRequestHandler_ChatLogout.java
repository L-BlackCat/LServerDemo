package org.example.netty.group_chat.server.handler;

import io.netty.channel.ChannelHandlerContext;
import org.example.netty.group_chat.ChatErrCodeEnum;
import org.example.netty.group_chat.bean.Packet;
import org.example.netty.group_chat.bean.PacketData;
import org.example.netty.group_chat.bean.ResponsePacket;
import org.example.netty.group_chat.engine.ChatClientRequestHandlerBase;
import org.example.netty.group_chat.engine.ClientProtocolID;

public class ChatClientRequestHandler_ChatLogout extends ChatClientRequestHandlerBase<PacketData> {

    @Override
    public Packet onProcess(ChannelHandlerContext ctx, PacketData packet, long now) {
        ResponsePacket responsePacket = new ResponsePacket();
        responsePacket.setCodeEnum(ChatErrCodeEnum.SUCCESS);
        responsePacket.setRequestId(ClientProtocolID.Chat_Logout_Response.getId());
        ctx.channel().writeAndFlush(responsePacket);
        return responsePacket;
    }
}
