package org.example.netty_chat.group_chat.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.example.netty_chat.group_chat.ChatErrCodeEnum;
import org.example.netty_chat.group_chat.bean.Packet;
import org.example.netty_chat.group_chat.bean.PacketPushTypeEnum;
import org.example.netty_chat.group_chat.bean.RequestPacket;
import org.example.netty_chat.group_chat.bean.ResponsePacket;
import org.example.netty_chat.group_chat.engine.ChatClientRequestHandlerBase;
import org.example.netty_chat.group_chat.engine.ClientProtocolID;
import org.example.netty_chat.group_chat.engine.entity.Session;
import org.example.netty_chat.group_chat.logger.Debug;
import org.example.netty_chat.group_chat.serialization.jackson.JackSonMap;
import org.example.netty_chat.group_chat.server.GlobalSessionMgr;

import java.util.List;

public class ChatClientRequestHandler_JoinGroup extends ChatClientRequestHandlerBase<RequestPacket> {
    public static final ChatClientRequestHandler_JoinGroup instance = new ChatClientRequestHandler_JoinGroup();
    @Override
    public Packet onProcess(ChannelHandlerContext ctx, RequestPacket packet, long now) throws InstantiationException, IllegalAccessException {
        JackSonMap map = packet.getMap();
        Channel channel = ctx.channel();
        Session session = GlobalSessionMgr.Instance.getSession(channel);
        String groupName = map.getString("group_name");
        ChatErrCodeEnum chatErrCodeEnum = GlobalSessionMgr.Instance.joinGroup(groupName, session);


        List<Session> groupSessionList = GlobalSessionMgr.Instance.getGroupSessionList(groupName);


        ResponsePacket responsePacket = new ResponsePacket();
        responsePacket.setRequestId(ClientProtocolID.Join_Group_Request.getId());
        responsePacket.setCodeEnum(chatErrCodeEnum);
        responsePacket.getMap().put("group_name",groupName);
        responsePacket.getMap().put("group_session_list",groupSessionList);

        Debug.info("加入聊天组：" +groupName);



        //  加入群聊响应
        ResponsePacket updateHallPacket = new ResponsePacket();
        updateHallPacket.setRequestId(ClientProtocolID.Chat_Message_Response.getId());
        updateHallPacket.setPushType(PacketPushTypeEnum.PUSH_TO_SPECIFIED_CHANNEL.toType());
        updateHallPacket.getMap().put("group_name",groupName);
        updateHallPacket.getMap().put("group_session_list",groupSessionList);
        GlobalSessionMgr.Instance.sendMsgToSpecified(groupName,updateHallPacket);

        return responsePacket;
    }
}
