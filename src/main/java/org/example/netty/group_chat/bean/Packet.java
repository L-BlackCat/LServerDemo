package org.example.netty.group_chat.bean;

import org.example.netty.group_chat.engine.entity.IObject;
import org.example.netty.group_chat.engine.entity.JObject;
import org.example.netty.group_chat.serialization.ChatSerializeType;

import java.io.Serializable;

public abstract class Packet implements Serializable {
    public static final int VERSION = 1;

    int requestId;
    //  LFM JsonObject序列化前和序列化后结构变化
    IObject map;
    ChatSerializeType chatSerializeType = ChatSerializeType.JSON;

    public Packet() {
        map = new JObject();
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public IObject getMap() {
        return map;
    }

    public void setMap(JObject map) {
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
