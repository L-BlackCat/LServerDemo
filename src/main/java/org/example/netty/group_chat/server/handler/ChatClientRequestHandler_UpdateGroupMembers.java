package org.example.netty.group_chat.server.handler;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import org.example.netty.group_chat.ChatErrCodeEnum;
import org.example.netty.group_chat.bean.MessageRequestPacket;
import org.example.netty.group_chat.bean.Packet;
import org.example.netty.group_chat.bean.PacketPushTypeEnum;
import org.example.netty.group_chat.bean.RequestPacket;
import org.example.netty.group_chat.bean.ResponsePacket;
import org.example.netty.group_chat.engine.ChatClientRequestHandlerBase;
import org.example.netty.group_chat.engine.ClientProtocolID;
import org.example.netty.group_chat.engine.utils.KDateUtil;
import org.example.netty.group_chat.logger.Debug;
import org.example.netty.group_chat.serialization.jackson.JackSonMap;
import org.example.netty.group_chat.server.GlobalSessionMgr;

public class ChatClientRequestHandler_UpdateGroupMembers extends ChatClientRequestHandlerBase<RequestPacket> {

    public static final ChatClientRequestHandler_UpdateGroupMembers instance = new ChatClientRequestHandler_UpdateGroupMembers();

    @Override
    public Packet onProcess(ChannelHandlerContext ctx, RequestPacket packet, long now) {

        return null;

    }
}
