package org.example.netty_chat.group_chat.client.handler;

import io.netty.channel.ChannelHandlerContext;
import org.example.netty_chat.group_chat.ChatErrCodeEnum;
import org.example.netty_chat.group_chat.bean.ResponsePacket;
import org.example.netty_chat.group_chat.cli_main.CliNettyChatMainFrame;
import org.example.netty_chat.group_chat.client.LNettyClient;
import org.example.netty_chat.group_chat.engine.ChatClientResponseHandlerBase;
import org.example.netty_chat.group_chat.engine.entity.Session;
import org.example.netty_chat.group_chat.logger.Debug;

import javax.swing.*;
import java.util.List;


public class ChatClientResponseHandler_JoinGroup extends ChatClientResponseHandlerBase<ResponsePacket> {
    public static final ChatClientResponseHandler_JoinGroup instance = new ChatClientResponseHandler_JoinGroup();
    @Override
    public void onProcess(ChannelHandlerContext ctx, ResponsePacket packet, long now, CliNettyChatMainFrame mainFrame) {
        ChatErrCodeEnum errCodeEnum = packet.getCodeEnum();
        String groupName = packet.getMap().getString("group_name");
        List<Session> sessionList = packet.getMap().getList("group_session_list", Session.class);
        if(errCodeEnum != ChatErrCodeEnum.SUCCESS){
            Debug.err(String.format("退出群组：%s 失败",groupName));
            //  创建一个弹出框
            JOptionPane.showMessageDialog(LNettyClient.hallFrame.getFrame(),"join group is err");
        }else {
            CliNettyChatMainFrame mainChatFrame = LNettyClient.createMainFrame(groupName, sessionList);
            mainChatFrame.show(ctx.channel());
            Debug.info("加入群组：" + groupName + " 成功！");
        }
    }
}
