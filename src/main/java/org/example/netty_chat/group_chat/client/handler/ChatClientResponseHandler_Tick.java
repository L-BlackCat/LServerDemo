package org.example.netty_chat.group_chat.client.handler;

import io.netty.channel.ChannelHandlerContext;
import org.example.netty_chat.group_chat.bean.ResponsePacket;
import org.example.netty_chat.group_chat.cli_main.CliNettyChatMainFrame;
import org.example.netty_chat.group_chat.engine.ChatClientResponseHandlerBase;
import org.example.netty_chat.group_chat.logger.Debug;


public class ChatClientResponseHandler_Tick extends ChatClientResponseHandlerBase<ResponsePacket> {
    public static final ChatClientResponseHandler_Tick instance = new ChatClientResponseHandler_Tick();
    @Override
    public void onProcess(ChannelHandlerContext ctx, ResponsePacket packet, long now, CliNettyChatMainFrame mainFrame) {
        Debug.info("心跳");
    }
}
