package org.example.netty_chat.group_chat.engine.chat_channel;

import java.util.concurrent.atomic.AtomicInteger;

public class PrivateChatChannelHandler extends ChatChannelBaseHandler{
    private final AtomicInteger id = new AtomicInteger();
    @Override
    String channelKey() {
        return "PRIVATE_CHAT_" + id.incrementAndGet();
    }
}
