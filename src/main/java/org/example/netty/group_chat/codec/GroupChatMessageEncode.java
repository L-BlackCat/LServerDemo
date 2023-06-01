package org.example.netty.group_chat.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.example.netty.group_chat.bean.Packet;
import org.example.netty.group_chat.logger.Debug;

public class GroupChatMessageEncode extends MessageToByteEncoder<Packet> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet pack, ByteBuf out) throws Exception {
        Debug.info("开始进行编码----");
        ByteBuf buf = PacketCodeC.Instance.encode(pack);
        out.writeBytes(buf);
    }
}
