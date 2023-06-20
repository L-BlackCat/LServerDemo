package org.example.netty.group_chat.client.handler;

import io.netty.channel.ChannelHandlerContext;
import org.example.netty.group_chat.ChatErrCodeEnum;
import org.example.netty.group_chat.bean.ResponsePacket;
import org.example.netty.group_chat.cli_main.CliNettyChatMainFrame;
import org.example.netty.group_chat.client.LNettyClient;
import org.example.netty.group_chat.engine.ChatClientResponseHandlerBase;
import org.example.netty.group_chat.logger.Debug;

import javax.swing.*;


public class ChatClientResponseHandler_QuitGroup extends ChatClientResponseHandlerBase<ResponsePacket> {
    public static final ChatClientResponseHandler_QuitGroup instance = new ChatClientResponseHandler_QuitGroup();
    @Override
    public void onProcess(ChannelHandlerContext ctx, ResponsePacket packet, long now, CliNettyChatMainFrame mainFrame) {
        ChatErrCodeEnum errCodeEnum = packet.getCodeEnum();
        String groupName = packet.getMap().getString("group_name");
        if(errCodeEnum != ChatErrCodeEnum.SUCCESS){
            Debug.err(String.format("退出群组：%s 失败",groupName));
            //  创建一个弹出框
            CliNettyChatMainFrame targetMainFrame = LNettyClient.getMainFrame(groupName);
            JOptionPane.showMessageDialog(targetMainFrame.getFrame(),"quit group is err");
        }else {
            LNettyClient.shutdownMainFrame(groupName);
            Debug.info("退出群组：" + groupName + " 成功！");
        }
    }
}
