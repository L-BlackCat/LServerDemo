package org.example.netty.group_chat.data_pack;

import org.example.netty.group_chat.IObject;
import org.example.netty.group_chat.JObject;
import org.example.netty.group_chat.serialization.ChatSerializeType;
import org.example.netty.group_chat.serialization.IChatSerializer;

public abstract class Packet {
    public static final int VERSION = 1;

    int requestId;
    IObject map = new JObject();
    ChatSerializeType chatSerializeType = ChatSerializeType.JSON;


    public int getRequestId() {
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
