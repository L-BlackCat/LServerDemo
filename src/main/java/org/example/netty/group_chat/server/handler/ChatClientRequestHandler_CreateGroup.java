package org.example.netty.group_chat.server.handler;

import io.netty.channel.ChannelHandlerContext;
import org.example.netty.group_chat.ChatErrCodeEnum;
import org.example.netty.group_chat.bean.Packet;
import org.example.netty.group_chat.bean.PacketPushTypeEnum;
import org.example.netty.group_chat.bean.RequestPacket;
import org.example.netty.group_chat.bean.ResponsePacket;
import org.example.netty.group_chat.engine.ChatClientRequestHandlerBase;
import org.example.netty.group_chat.engine.ClientProtocolID;
import org.example.netty.group_chat.engine.entity.Session;
import org.example.netty.group_chat.logger.Debug;
import org.example.netty.group_chat.serialization.jackson.JackSonMap;
import org.example.netty.group_chat.server.GlobalSessionMgr;

import java.util.List;

public class ChatClientRequestHandler_CreateGroup extends ChatClientRequestHandlerBase<RequestPacket> {
    public static final ChatClientRequestHandler_CreateGroup instance = new ChatClientRequestHandler_CreateGroup();
    @Override
    public Packet onProcess(ChannelHandlerContext ctx, RequestPacket packet, long now) throws InstantiationException, IllegalAccessException {
        JackSonMap map = packet.getMap();
        int chatType = map.getInteger("chat_type");
        Session selfSession = GlobalSessionMgr.Instance.getSession(ctx.channel());
        List<Session> sessionList = map.getList("session_list", Session.class);
        sessionList.add(selfSession);
        String groupName = GlobalSessionMgr.Instance.createGroup(chatType, sessionList);

        ResponsePacket responsePacket = new ResponsePacket();
        responsePacket.setRequestId(ClientProtocolID.Chat_Create_Group_Response.getId());
        responsePacket.setPushType(PacketPushTypeEnum.PUSH_TO_SPECIFIED_CHANNEL.toType());
        responsePacket.getMap().put("group_name",groupName);
        responsePacket.getMap().put("session_list", GlobalSessionMgr.Instance.getGroupSessionList(groupName));

        Debug.info("创建聊天组：" +groupName);
        return responsePacket;
    }
}
