package org.example.netty.group_chat.bean;

import org.example.netty.group_chat.data_pack.Packet;

public class LoginRequestPacket extends Packet {
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
