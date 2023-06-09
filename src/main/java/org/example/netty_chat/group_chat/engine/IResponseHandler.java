package org.example.netty_chat.group_chat.engine;

import io.netty.channel.ChannelHandlerContext;
import org.example.netty_chat.group_chat.bean.Packet;
import org.example.netty_chat.group_chat.cli_main.CliNettyChatMainFrame;

public interface IResponseHandler<T extends Packet> {
    void onProcess(ChannelHandlerContext ctx, T packet, long now, CliNettyChatMainFrame mainFrame);
}
