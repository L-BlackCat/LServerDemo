package org.example.netty.group_chat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.netty.group_chat.data_pack.ClientResponseData;
import org.example.netty.group_chat.data_pack.Packet;
import org.example.netty.group_chat.engine.ClientRequestMgr;
import org.example.netty.group_chat.engine.IRequestHandler;
import org.example.netty.group_chat.serialization.ChatSerializeType;

public class GroupChatServerDistributorHandler extends SimpleChannelInboundHandler<Packet> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet requestPack) throws Exception {
        int requestId = requestPack.get_request_id();
        long now = System.currentTimeMillis() / 1000;

        IRequestHandler handler = ClientRequestMgr.Instance.createById(requestId);
        if(handler == null){
            System.out.println("找不到requestId对应的handler");
            return;
        }

        Packet responsePack = new ClientResponseData();
        responsePack.setRequestId(requestId);
        responsePack.setMap(new JObject());
        responsePack.setChatSerializeType(ChatSerializeType.JSON);

        try{
            //  对协议进行处理，应该有一个返回数据
            handler.onProcess(requestPack,responsePack,now);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
