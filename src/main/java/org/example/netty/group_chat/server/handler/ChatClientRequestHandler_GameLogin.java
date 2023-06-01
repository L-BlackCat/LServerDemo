package org.example.netty.group_chat.server.handler;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import org.example.netty.group_chat.ChatErrCodeEnum;
import org.example.netty.group_chat.bean.LoginRequestPacket;
import org.example.netty.group_chat.bean.ResponsePacket;
import org.example.netty.group_chat.bean.Packet;
import org.example.netty.group_chat.client.ChannelAttrUtil;
import org.example.netty.group_chat.engine.ChatClientRequestHandlerBase;
import org.example.netty.group_chat.engine.ClientProtocolID;
import org.example.netty.group_chat.logger.Debug;
import org.example.netty.group_chat.server.ChatUserMgr;

public class ChatClientRequestHandler_GameLogin extends ChatClientRequestHandlerBase<LoginRequestPacket> {

    @Override
    public Packet onProcess(ChannelHandlerContext ctx, LoginRequestPacket packet, long now) {
        String name = packet.getName();

        ChatUserMgr.Instance.getNames().add(name);


        ResponsePacket responsePacket = new ResponsePacket();
        responsePacket.setRequestId(ClientProtocolID.Chat_Login_Response.getId());
        responsePacket.setCodeEnum(ChatErrCodeEnum.SUCCESS);
        Debug.info(name + "is login success");
        Debug.info(JSON.toJSONString(responsePacket));
        ChannelAttrUtil.Instance.markAsName(ctx.channel(),name);
        return responsePacket;
    }
}
