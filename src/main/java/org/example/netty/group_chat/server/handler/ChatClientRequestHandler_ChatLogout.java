package org.example.netty.group_chat.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.example.netty.group_chat.ChatErrCodeEnum;
import org.example.netty.group_chat.bean.Packet;
import org.example.netty.group_chat.bean.PacketData;
import org.example.netty.group_chat.bean.ResponsePacket;
import org.example.netty.group_chat.engine.ChatClientRequestHandlerBase;
import org.example.netty.group_chat.engine.ClientProtocolID;
import org.example.netty.group_chat.engine.entity.Session;
import org.example.netty.group_chat.server.GlobalSessionMgr;

import java.util.ArrayList;
import java.util.List;


public class ChatClientRequestHandler_ChatLogout extends ChatClientRequestHandlerBase<PacketData> {

    @Override
    public Packet onProcess(ChannelHandlerContext ctx, PacketData packet, long now) {
        Channel channel = ctx.channel();
        ResponsePacket responsePacket = new ResponsePacket();
        responsePacket.setCodeEnum(ChatErrCodeEnum.SUCCESS);
        responsePacket.setRequestId(ClientProtocolID.Chat_Logout_Response.getId());

        //  更新其他人的大厅数据
        ResponsePacket updateHallPacket = new ResponsePacket();
        updateHallPacket.setRequestId(ClientProtocolID.Chat_Message_Response.getId());
        List<Session> allSession = GlobalSessionMgr.Instance.allSession();
        Session targetSession = GlobalSessionMgr.Instance.getSession(channel);
        List<Session> ret = new ArrayList<>();
        for (Session one : allSession) {
            if(one.getUid() != targetSession.getUid()){
                ret.add(one);
            }
        }
        updateHallPacket.getMap().put("session_list",ret);
        updateHallPacket.setCodeEnum(ChatErrCodeEnum.SUCCESS);
        GlobalSessionMgr.Instance.sendMsgToOther(channel,updateHallPacket);

        return responsePacket;
    }
}
