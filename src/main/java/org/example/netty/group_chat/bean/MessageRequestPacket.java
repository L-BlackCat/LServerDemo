package org.example.netty.group_chat.bean;

public class MessageRequestPacket extends PacketData{
    String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
