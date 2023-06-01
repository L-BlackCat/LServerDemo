package org.example.netty.group_chat.bean;

import org.example.netty.group_chat.data_pack.Packet;
import org.example.netty.group_chat.data_pack.PacketData;

public class LoginRequestPacket extends PacketData {
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
