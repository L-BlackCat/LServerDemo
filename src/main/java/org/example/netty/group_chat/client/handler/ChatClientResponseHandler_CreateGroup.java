package org.example.netty.group_chat.client.handler;

import io.netty.channel.ChannelHandlerContext;
import org.example.netty.group_chat.bean.ResponsePacket;
import org.example.netty.group_chat.cli_main.CliNettyChatMainFrame;
import org.example.netty.group_chat.client.LNettyClient;
import org.example.netty.group_chat.engine.ChatClientResponseHandlerBase;
import org.example.netty.group_chat.engine.entity.Session;
import org.example.netty.group_chat.serialization.jackson.JackSonMap;

import java.util.List;


public class ChatClientResponseHandler_CreateGroup extends ChatClientResponseHandlerBase<ResponsePacket> {
    public static final ChatClientResponseHandler_CreateGroup instance = new ChatClientResponseHandler_CreateGroup();
    @Override
    public void onProcess(ChannelHandlerContext ctx, ResponsePacket packet, long now, CliNettyChatMainFrame mainFrame) {
        JackSonMap map = packet.getMap();
        String groupName = map.getString("group_name");
        List<Session> sessionList = map.getList("session_list", Session.class);

        //  收到消息的人，创建聊天窗口
        CliNettyChatMainFrame mainChatFrame = LNettyClient.createMainFrame(groupName, sessionList);

        mainChatFrame.show(ctx.channel());
    }
}
