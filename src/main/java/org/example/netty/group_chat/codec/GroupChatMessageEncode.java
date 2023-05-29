package org.example.netty.group_chat.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.example.netty.group_chat.IObject;
import org.example.netty.group_chat.data_pack.Packet;
import org.example.netty.group_chat.engine.ClientRequestMgr;
import org.example.netty.group_chat.serialization.ChatSerializeType;
import org.example.netty.group_chat.serialization.IChatSerializer;

public class GroupChatMessageEncode extends MessageToByteEncoder<Packet> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet pack, ByteBuf out) throws Exception {
        ByteBuf buf = PacketCodeC.Instance.encode(pack);
        out.writeBytes(buf);
    }
}
