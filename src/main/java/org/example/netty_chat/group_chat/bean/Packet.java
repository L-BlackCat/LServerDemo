package org.example.netty_chat.group_chat.bean;

import org.example.netty_chat.group_chat.serialization.ChatSerializeType;
import org.example.netty_chat.group_chat.serialization.jackson.JackSonMap;

import java.io.Serializable;

public abstract class Packet implements Serializable {
    public static final int VERSION = 1;

    int requestId;
    //  LFM JsonObject序列化前和序列化后结构变化
    JackSonMap map;
    ChatSerializeType chatSerializeType = ChatSerializeType.JSON;

    int pushType = PacketPushTypeEnum.PUSH_TO_SELF.toType();

    public Packet() {
        map = new JackSonMap();
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public JackSonMap getMap() {
        return map;
    }

    public void setMap(JackSonMap map) {
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

    public int getPushType() {
        return pushType;
    }

    public void setPushType(int pushType) {
        this.pushType = pushType;
    }
}
