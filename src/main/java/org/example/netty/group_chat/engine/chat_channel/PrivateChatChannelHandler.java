package org.example.netty.group_chat.engine.chat_channel;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class PrivateChatChannelHandler extends ChatChannelBaseHandler{
    private final AtomicInteger id = new AtomicInteger();
    @Override
    String channelKey() {
        return "PRIVATE_CHAT_" + id.incrementAndGet();
    }
}
