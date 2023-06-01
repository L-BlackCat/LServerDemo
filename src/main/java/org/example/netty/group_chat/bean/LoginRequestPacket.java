package org.example.netty.group_chat.bean;

public class LoginRequestPacket extends PacketData {
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
