package org.example.netty.group_chat.data_pack;

import org.example.netty.group_chat.ChatErrCodeEnum;

public class ClientResponseData extends Packet {
    ChatErrCodeEnum errCodeEnum;

    @Override
    int getRequestId() {
        return get_request_id();
    }

    public ChatErrCodeEnum getErrCodeEnum() {
        return errCodeEnum;
    }

    public void setErrCodeEnum(ChatErrCodeEnum errCodeEnum) {
        this.errCodeEnum = errCodeEnum;
    }
}
