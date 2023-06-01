package org.example.netty.group_chat.client.handler;

import io.netty.channel.ChannelHandlerContext;
import org.example.netty.group_chat.ChatErrCodeEnum;
import org.example.netty.group_chat.IObject;
import org.example.netty.group_chat.bean.ResponsePacket;
import org.example.netty.group_chat.engine.ChatClientResponseHandlerBase;
import org.example.netty.group_chat.logger.Debug;

public class ChatClientResponseHandler_SendMessage extends ChatClientResponseHandlerBase<ResponsePacket> {

    @Override
    public void onProcess(ChannelHandlerContext ctx, ResponsePacket packet, long now) {
        if(packet.getCodeEnum() == ChatErrCodeEnum.SUCCESS){
            IObject map = packet.getMap();
            String message = map.getString("message");
            Debug.info(message);
        }else {
            Debug.err("消息发送失败");
        }
    }
}
