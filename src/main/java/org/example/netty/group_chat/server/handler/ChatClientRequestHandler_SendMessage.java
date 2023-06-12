package org.example.netty.group_chat.server.handler;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import org.example.netty.group_chat.ChatErrCodeEnum;
import org.example.netty.group_chat.bean.PacketPushTypeEnum;
import org.example.netty.group_chat.engine.utils.KDateUtil;
import org.example.netty.group_chat.bean.MessageRequestPacket;
import org.example.netty.group_chat.bean.Packet;
import org.example.netty.group_chat.bean.ResponsePacket;
import org.example.netty.group_chat.engine.ChatClientRequestHandlerBase;
import org.example.netty.group_chat.engine.ClientProtocolID;
import org.example.netty.group_chat.logger.Debug;
import org.example.netty.group_chat.serialization.jackson.JackSonMap;
import org.example.netty.group_chat.server.GlobalSessionMgr;

public class ChatClientRequestHandler_SendMessage extends ChatClientRequestHandlerBase<MessageRequestPacket> {



    @Override
    public Packet onProcess(ChannelHandlerContext ctx, MessageRequestPacket packet, long now) {
        String msg = packet.getMsg();

        String name = GlobalSessionMgr.Instance.getName(ctx.channel());
        //  将获得的消息推送给所有正处于连接的通道
        ResponsePacket responsePacket = new ResponsePacket();
        responsePacket.setRequestId(ClientProtocolID.Chat_Message_Response.getId());
        responsePacket.setCodeEnum(ChatErrCodeEnum.SUCCESS);
        JackSonMap map = responsePacket.getMap();
        msg = name + " " + KDateUtil.Instance.date() +"\n" + msg + "\n";
        Debug.info(msg);

        map.put("message",msg);
        map.put("session_list", GlobalSessionMgr.Instance.allSession());
        responsePacket.setPushType(PacketPushTypeEnum.PUSH_TO_EVERYONE.toType());

        Debug.info(JSON.toJSONString(responsePacket));
        return responsePacket;

    }
}
