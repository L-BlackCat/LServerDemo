package org.example.netty.group_chat.client.handler;

import io.netty.channel.ChannelHandlerContext;
import org.example.netty.group_chat.ChatErrCodeEnum;
import org.example.netty.group_chat.bean.ResponsePacket;
import org.example.netty.group_chat.cli_main.CliNettyChatMainFrame;
import org.example.netty.group_chat.client.LNettyClient;
import org.example.netty.group_chat.engine.ChatClientResponseHandlerBase;
import org.example.netty.group_chat.logger.Debug;

import javax.swing.*;


public class ChatClientResponseHandler_Tick extends ChatClientResponseHandlerBase<ResponsePacket> {
    public static final ChatClientResponseHandler_Tick instance = new ChatClientResponseHandler_Tick();
    @Override
    public void onProcess(ChannelHandlerContext ctx, ResponsePacket packet, long now, CliNettyChatMainFrame mainFrame) {
        Debug.info("心跳");
    }
}
