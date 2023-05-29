package org.example.netty.group_chat.client.handler;

import io.netty.channel.ChannelHandlerContext;
import org.example.netty.group_chat.ChatErrCodeEnum;
import org.example.netty.group_chat.bean.LoginResponsePacket;
import org.example.netty.group_chat.engine.ChatClientResponseHandlerBase;

public class ChatClientResponseHandler_GameLogin extends ChatClientResponseHandlerBase<LoginResponsePacket> {

    @Override
    public void onProcess(ChannelHandlerContext ctx, LoginResponsePacket packet) {
        if(packet.getCodeEnum() == ChatErrCodeEnum.SUCCESS){
            System.out.println("登录成功");
        }else {
            System.out.println("登录失败");
        }
    }
}
