package org.example.netty_chat.group_chat.engine.chat_channel;

public abstract class ChatChannelBaseHandler implements IChatChannelHandler{
    @Override
    public String channelPassive() {
        return channelKey();
    }

    abstract String channelKey();
}
