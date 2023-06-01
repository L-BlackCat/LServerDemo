package org.example.netty.group_chat.server.handler;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import org.example.netty.group_chat.ChatErrCodeEnum;
import org.example.netty.group_chat.bean.LoginRequestPacket;
import org.example.netty.group_chat.bean.LoginResponsePacket;
import org.example.netty.group_chat.data_pack.Packet;
import org.example.netty.group_chat.engine.ChatClientRequestHandlerBase;
import org.example.netty.group_chat.logger.Debug;
import org.example.netty.group_chat.server.ChatUserMgr;

public class ChatClientRequestHandler_GameLogin extends ChatClientRequestHandlerBase<LoginRequestPacket> {

    @Override
    public Packet onProcess(ChannelHandlerContext ctx, LoginRequestPacket packet, long now) {
        String name = packet.getName();

        ChatUserMgr.Instance.getNames().add(name);


        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setRequestId(packet.getRequestId());
        loginResponsePacket.setCodeEnum(ChatErrCodeEnum.SUCCESS);
        Debug.info(name + "is login success");
        System.out.println(JSON.toJSONString(loginResponsePacket));
        return loginResponsePacket;
    }
}
