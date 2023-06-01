package org.example.netty.group_chat.client.handler;

import io.netty.channel.ChannelHandlerContext;
import org.example.netty.group_chat.ChatErrCodeEnum;
import org.example.netty.group_chat.bean.ResponsePacket;
import org.example.netty.group_chat.client.ChannelAttrUtil;
import org.example.netty.group_chat.engine.ChatClientResponseHandlerBase;
import org.example.netty.group_chat.logger.Debug;

public class ChatClientResponseHandler_GameLogin extends ChatClientResponseHandlerBase<ResponsePacket> {

    @Override
    public void onProcess(ChannelHandlerContext ctx, ResponsePacket packet, long now) {
        if(packet.getCodeEnum() == ChatErrCodeEnum.SUCCESS){
            ChannelAttrUtil.Instance.markAsLogin(ctx.channel());
            Debug.info("登录成功");
        }else {
            Debug.info("登录失败");
        }
    }
}
