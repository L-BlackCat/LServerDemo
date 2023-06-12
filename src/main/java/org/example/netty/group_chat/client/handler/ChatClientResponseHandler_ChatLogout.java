package org.example.netty.group_chat.client.handler;

import io.netty.channel.ChannelHandlerContext;
import org.example.netty.group_chat.bean.ResponsePacket;
import org.example.netty.group_chat.cli_main.CliNettyChatMainFrame;
import org.example.netty.group_chat.engine.ChatClientResponseHandlerBase;
import org.example.netty.group_chat.logger.Debug;

public class ChatClientResponseHandler_ChatLogout extends ChatClientResponseHandlerBase<ResponsePacket> {

    @Override
    public void onProcess(ChannelHandlerContext ctx, ResponsePacket packet, long now, CliNettyChatMainFrame mainFrame) {
        //  通知其他连接着，更新列表状态
        ctx.channel().close();
        Debug.warn("断开连接");
    }

}
