package org.example.netty_chat.group_chat.client.handler;

import io.netty.channel.ChannelHandlerContext;
import org.example.netty_chat.group_chat.bean.ResponsePacket;
import org.example.netty_chat.group_chat.cli_main.CliNettyChatMainFrame;
import org.example.netty_chat.group_chat.engine.ChatClientResponseHandlerBase;
import org.example.netty_chat.group_chat.logger.Debug;

public class ChatClientResponseHandler_ChatLogout extends ChatClientResponseHandlerBase<ResponsePacket> {
    public static final ChatClientResponseHandler_ChatLogout instance = new ChatClientResponseHandler_ChatLogout();
    @Override
    public void onProcess(ChannelHandlerContext ctx, ResponsePacket packet, long now, CliNettyChatMainFrame mainFrame) {
        //  通知其他连接着，更新列表状态
        Debug.warn("断开连接");
        ctx.channel().close();
    }

}
