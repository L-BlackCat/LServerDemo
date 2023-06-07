package org.example.netty.group_chat.client.handler;

import io.netty.channel.ChannelHandlerContext;
import org.example.netty.group_chat.ChatErrCodeEnum;
import org.example.netty.group_chat.engine.entity.IObject;
import org.example.netty.group_chat.bean.ResponsePacket;
import org.example.netty.group_chat.cli_main.CliNettyChatMainFrame;
import org.example.netty.group_chat.engine.ChatClientResponseHandlerBase;
import org.example.netty.group_chat.engine.entity.Session;
import org.example.netty.group_chat.logger.Debug;

import java.util.Collection;
import java.util.List;

public class ChatClientResponseHandler_SendMessage extends ChatClientResponseHandlerBase<ResponsePacket> {

    @Override
    public void onProcess(ChannelHandlerContext ctx, ResponsePacket packet, long now, CliNettyChatMainFrame mainFrame) {
        if(packet.getCodeEnum() == ChatErrCodeEnum.SUCCESS){
            IObject map = packet.getMap();
            String content = map.getString("message");
            Session session = map.getObject("session", Session.class);
            Debug.info(content);

            List<Session> sessionsList = map.getObject("session_list", List.class);
            if(sessionsList != null && !sessionsList.isEmpty()){
                mainFrame.updateModel(sessionsList);
            }
            if(content != null && !content.isEmpty()){
                String str = mainFrame.readContext.getText() + content;
                mainFrame.readContext.setText(str);
                mainFrame.readContext.selectAll();
            }

        }else {
            Debug.err("消息发送失败");
        }
    }
}
