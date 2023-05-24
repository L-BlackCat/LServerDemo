package org.example.netty.group_chat.data_pack;


public class ClientRequestData extends Packet {


    @Override
    int getRequestId() {
        return get_request_id();
    }



}
