package org.example.netty.group_chat.engine;

import org.example.netty.group_chat.data_pack.Packet;

public interface IRequestHandler {
    void onProcess(Packet requestPack, Packet responsePack, long now);
}
