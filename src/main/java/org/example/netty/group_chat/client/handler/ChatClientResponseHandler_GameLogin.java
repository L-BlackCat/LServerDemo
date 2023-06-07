package org.example.netty.group_chat.client.handler;

import io.netty.channel.ChannelHandlerContext;
import org.example.netty.group_chat.ChatErrCodeEnum;
import org.example.netty.group_chat.bean.ResponsePacket;
import org.example.netty.group_chat.cli_main.CliNettyChatMainFrame;
import org.example.netty.group_chat.client.ChannelAttrUtil;
import org.example.netty.group_chat.engine.ChatClientResponseHandlerBase;
import org.example.netty.group_chat.engine.entity.IObject;
import org.example.netty.group_chat.engine.entity.Session;
import org.example.netty.group_chat.logger.Debug;

import java.util.List;

public class ChatClientResponseHandler_GameLogin extends ChatClientResponseHandlerBase<ResponsePacket> {

    @Override
    public void onProcess(ChannelHandlerContext ctx, ResponsePacket packet, long now, CliNettyChatMainFrame mainFrame) {
        if(packet.getCodeEnum() == ChatErrCodeEnum.SUCCESS){
            ChannelAttrUtil.Instance.markAsLogin(ctx.channel());
            IObject map = packet.getMap();
            List<Session> sessionList = map.getObject("session_list", List.class);
            Session session = map.getObject("session", Session.class);
            mainFrame.updateModel(sessionList);
            Debug.info(session + "->登录成功");
        }else {
            Debug.info("登录失败");
        }
    }
}
