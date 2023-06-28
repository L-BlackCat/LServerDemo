package org.example.netty_chat.group_chat.server.handler;

import io.netty.channel.ChannelHandlerContext;
import org.example.netty_chat.group_chat.ChatErrCodeEnum;
import org.example.netty_chat.group_chat.bean.Packet;
import org.example.netty_chat.group_chat.bean.RequestPacket;
import org.example.netty_chat.group_chat.bean.ResponsePacket;
import org.example.netty_chat.group_chat.engine.ChatClientRequestHandlerBase;
import org.example.netty_chat.group_chat.engine.ClientProtocolID;

public class ChatClientRequestHandler_Tick extends ChatClientRequestHandlerBase<RequestPacket> {
    @Override
    public Packet onProcess(ChannelHandlerContext ctx, RequestPacket packet, long now) throws Exception {
        ResponsePacket responsePacket = new ResponsePacket();
        responsePacket.setRequestId(ClientProtocolID.Tick_Response.getId());
        responsePacket.setCodeEnum(ChatErrCodeEnum.SUCCESS);
        return responsePacket;
    }
}
