package org.example.netty.group_chat.server.handler;

import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.example.netty.group_chat.ChatErrCodeEnum;
import org.example.netty.group_chat.bean.LoginRequestPacket;
import org.example.netty.group_chat.bean.ResponsePacket;
import org.example.netty.group_chat.bean.Packet;
import org.example.netty.group_chat.engine.ChatClientRequestHandlerBase;
import org.example.netty.group_chat.engine.ClientProtocolID;
import org.example.netty.group_chat.engine.entity.Session;
import org.example.netty.group_chat.logger.Debug;
import org.example.netty.group_chat.server.GlobalSessionMgr;

import java.util.List;

public class ChatClientRequestHandler_GameLogin extends ChatClientRequestHandlerBase<LoginRequestPacket> {

    @Override
    public Packet onProcess(ChannelHandlerContext ctx, LoginRequestPacket packet, long now) {
        String name = packet.getName();
        Channel channel = ctx.channel();
        Session session = GlobalSessionMgr.Instance.bind(name, channel);

        List<Session> sessionList = GlobalSessionMgr.Instance.allSession();
        ResponsePacket responsePacket = new ResponsePacket();
        responsePacket.setRequestId(ClientProtocolID.Chat_Login_Response.getId());
        responsePacket.setCodeEnum(ChatErrCodeEnum.SUCCESS);
        responsePacket.getMap().put("session_list", sessionList);
        responsePacket.getMap().put("session", session);
        Debug.info(session + " is login success");
        Debug.info(JSON.toJSONString(responsePacket));
        channel.writeAndFlush(responsePacket);



        //  登录响应
        ResponsePacket loginPushMessage = new ResponsePacket();
        loginPushMessage.setRequestId(ClientProtocolID.Chat_Message_Response.getId());
        loginPushMessage.setCodeEnum(ChatErrCodeEnum.SUCCESS);
        GlobalSessionMgr.Instance.sendMsg(channel,loginPushMessage);

        return responsePacket;
    }

    /**
     * 目的：
     * 在玩家在线时，将玩家唯一id和名称进行封装（session会话）
     * 在玩家不在线时，将玩家会话进行删除
     */
}
