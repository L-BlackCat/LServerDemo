package org.example.netty.group_chat.client.handler;

import io.netty.channel.ChannelHandlerContext;
import org.example.netty.group_chat.ChatErrCodeEnum;
import org.example.netty.group_chat.bean.ResponsePacket;
import org.example.netty.group_chat.cli_main.CliNettyChatMainFrame;
import org.example.netty.group_chat.client.LNettyClient;
import org.example.netty.group_chat.engine.ChatClientResponseHandlerBase;
import org.example.netty.group_chat.engine.entity.Session;
import org.example.netty.group_chat.logger.Debug;
import org.example.netty.group_chat.serialization.jackson.JackSonMap;

import java.util.List;

public class ChatClientResponseHandler_UpdateGroupMembers extends ChatClientResponseHandlerBase<ResponsePacket> {
    public static final ChatClientResponseHandler_UpdateGroupMembers instance = new ChatClientResponseHandler_UpdateGroupMembers();
    @Override
    public void onProcess(ChannelHandlerContext ctx, ResponsePacket packet, long now, CliNettyChatMainFrame mainFrame) {
        if(packet.getCodeEnum() == ChatErrCodeEnum.SUCCESS){
            JackSonMap map = packet.getMap();
            String content = map.getString("message");
            Debug.info(content);

            List<Session> sessionsList = map.getList("session_list", Session.class);
            if(sessionsList != null && !sessionsList.isEmpty()){
                LNettyClient.hallFrame.updateModel(sessionsList);
            }
            String groupName = map.getString("group_name");
            if(groupName != null && !groupName.isEmpty()){
                CliNettyChatMainFrame targetMainFrame = LNettyClient.getMainFrame(groupName);
                List<Session> groupSessionList = map.getList("group_session_list", Session.class);
                if(groupSessionList != null && !groupSessionList.isEmpty()){
                    targetMainFrame.updateModel(groupSessionList);
                }
                if(content != null && !content.isEmpty()){
                    String str = targetMainFrame.readContext.getText() + content;
                    targetMainFrame.readContext.setText(str);
                    targetMainFrame.readContext.selectAll();

                }
            }


        }else {
            Debug.err("消息发送失败");
        }
    }
}
