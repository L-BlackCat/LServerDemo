package org.example.netty.group_chat.bean;

import org.apache.log4j.spi.ErrorCode;
import org.example.netty.group_chat.ChatErrCodeEnum;
import org.example.netty.group_chat.data_pack.Packet;
import org.example.netty.group_chat.data_pack.PacketData;

public class LoginResponsePacket extends PacketData {
    ChatErrCodeEnum codeEnum = ChatErrCodeEnum.SUCCESS;


    public ChatErrCodeEnum getCodeEnum() {
        return codeEnum;
    }

    public void setCodeEnum(ChatErrCodeEnum codeEnum) {
        this.codeEnum = codeEnum;
    }
}
