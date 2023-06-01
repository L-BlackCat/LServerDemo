package org.example.netty.group_chat.bean;

public class LoginResponsePacket extends PacketData{
    String allMsg;

    public String getAllMsg() {
        return allMsg;
    }

    public void setAllMsg(String allMsg) {
        this.allMsg = allMsg;
    }
}
