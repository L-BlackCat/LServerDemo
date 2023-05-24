package org.example.netty.group_chat.data_pack;

import org.example.netty.group_chat.IObject;
import org.example.netty.group_chat.serialization.ChatSerializeType;

public abstract class Packet {
    public static final int VERSION = 1;

    int requestId;
    IObject map;
    ChatSerializeType chatSerializeType;

    abstract int getRequestId();


    public int get_request_id() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public IObject getMap() {
        return map;
    }

    public void setMap(IObject map) {
        this.map = map;
    }

    public ChatSerializeType getChatSerializeType() {
        return chatSerializeType;
    }

    public void setChatSerializeType(ChatSerializeType chatSerializeType) {
        this.chatSerializeType = chatSerializeType;
    }

    public int version(){
        return VERSION;
    }
}
