package org.example.netty_chat.group_chat.engine.chat_channel;

import java.util.concurrent.atomic.AtomicInteger;

public class PublicChatChannelHandler extends ChatChannelBaseHandler {

    private final AtomicInteger id = new AtomicInteger();
    @Override
    String channelKey() {
        return "PUBLIC_CHAT_" + id.incrementAndGet();
    }

}
