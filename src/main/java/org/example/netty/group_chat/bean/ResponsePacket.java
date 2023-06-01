package org.example.netty.group_chat.bean;

import org.example.netty.group_chat.ChatErrCodeEnum;

public class ResponsePacket extends PacketData {
    ChatErrCodeEnum codeEnum = ChatErrCodeEnum.SUCCESS;


    public ChatErrCodeEnum getCodeEnum() {
        return codeEnum;
    }

    public void setCodeEnum(ChatErrCodeEnum codeEnum) {
        this.codeEnum = codeEnum;
    }
}
