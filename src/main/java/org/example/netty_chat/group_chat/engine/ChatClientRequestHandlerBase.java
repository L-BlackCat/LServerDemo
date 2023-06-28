package org.example.netty_chat.group_chat.engine;

import io.netty.channel.ChannelHandlerContext;
import org.example.netty_chat.group_chat.bean.Packet;
import org.example.netty_chat.group_chat.bean.PacketPushTypeEnum;
import org.example.netty_chat.group_chat.logger.Debug;

public abstract class ChatClientRequestHandlerBase<T extends Packet> implements IRequestHandler<T> {

    public void process(ChannelHandlerContext ctx, T packet, long now) {
        try{
            Packet responsePacket = onProcess(ctx, packet, now);
            ISendPacketHandler handler = PacketPushTypeEnum.newInstance(responsePacket.getPushType());
            if(handler != null){
                handler.sendData(ctx,responsePacket,now);
            }
        }catch (Exception e){
            Debug.err("处理请求失败，[protocol_id]: " + packet.getRequestId() ,e);
        }
    }
}
