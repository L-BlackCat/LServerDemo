package org.example.netty.group_chat.server.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import io.netty.channel.ChannelHandlerContext;
import org.example.netty.group_chat.ChatErrCodeEnum;
import org.example.netty.group_chat.IObject;
import org.example.netty.group_chat.KDateUtil;
import org.example.netty.group_chat.bean.LoginRequestPacket;
import org.example.netty.group_chat.bean.MessageRequestPacket;
import org.example.netty.group_chat.bean.Packet;
import org.example.netty.group_chat.bean.ResponsePacket;
import org.example.netty.group_chat.client.ChannelAttrUtil;
import org.example.netty.group_chat.engine.ChatClientRequestHandlerBase;
import org.example.netty.group_chat.engine.ClientProtocolID;
import org.example.netty.group_chat.logger.Debug;
import org.example.netty.group_chat.server.ChatUserMgr;

public class ChatClientRequestHandler_SendMessage extends ChatClientRequestHandlerBase<MessageRequestPacket> {



    @Override
    public Packet onProcess(ChannelHandlerContext ctx, MessageRequestPacket packet, long now) {
        String msg = packet.getMsg();

        String name = ChannelAttrUtil.Instance.getName(ctx.channel());
        //  将获得的消息推送给所有正处于连接的通道
        ResponsePacket responsePacket = new ResponsePacket();
        responsePacket.setRequestId(ClientProtocolID.Chat_Message_Response.getId());
        responsePacket.setCodeEnum(ChatErrCodeEnum.SUCCESS);
        IObject map = responsePacket.getMap();
        msg = KDateUtil.Instance.date() + " [" + name + "] 发送消息:\n " + msg;
        Debug.info(msg);

        map.put("message",msg);

        Debug.info(JSON.toJSONString(responsePacket));
        ChatUserMgr.Instance.sendMsg(packet);
        return responsePacket;

    }
}
