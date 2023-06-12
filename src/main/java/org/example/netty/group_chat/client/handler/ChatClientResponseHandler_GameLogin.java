package org.example.netty.group_chat.client.handler;

import io.netty.channel.ChannelHandlerContext;
import org.example.netty.group_chat.ChatErrCodeEnum;
import org.example.netty.group_chat.bean.ResponsePacket;
import org.example.netty.group_chat.cli_main.CliNettyChatMainFrame;
import org.example.netty.group_chat.client.ChannelAttrUtil;
import org.example.netty.group_chat.engine.ChatClientResponseHandlerBase;
import org.example.netty.group_chat.engine.entity.Session;
import org.example.netty.group_chat.logger.Debug;
import org.example.netty.group_chat.serialization.jackson.JackSonMap;

import java.util.List;

public class ChatClientResponseHandler_GameLogin extends ChatClientResponseHandlerBase<ResponsePacket> {

    @Override
    public void onProcess(ChannelHandlerContext ctx, ResponsePacket packet, long now, CliNettyChatMainFrame mainFrame) {
        if(packet.getCodeEnum() == ChatErrCodeEnum.SUCCESS){
            JackSonMap map = packet.getMap();
            List<Session> sessionList = map.getList("session_list", Session.class);
            Session session = map.get("session", Session.class);


            ChannelAttrUtil.Instance.markAsLogin(ctx.channel(),session);

            mainFrame.updateModel(sessionList);
            Debug.info(session + "->登录成功");
        }else {
            Debug.info("登录失败");
        }
    }
}
