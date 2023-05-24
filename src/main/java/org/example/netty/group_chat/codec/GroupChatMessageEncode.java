package org.example.netty.group_chat.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.example.netty.group_chat.data_pack.Packet;

public class GroupChatMessageEncode extends MessageToByteEncoder<Packet> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet pack, ByteBuf out) throws Exception {
//        long magicNum = pack.magicNum();
//        int version = pack.version();
//        int requestId = pack.get_request_id();
//        IObject map = pack.getMap();
//        ChatSerializeType serializeType = pack.getChatSerializeType();
//        IChatSerializeHandler handler = serializeType.newInstanceHandler();
//
//        byte[] bytes = handler.serialize(map);
//        long len = bytes.length;
//
//        out.writeLong(magicNum);
//        out.writeInt(version);
//        out.writeInt(requestId);
//        out.writeByte(serializeType.toInt());
//        out.writeLong(len);
//        out.writeBytes(bytes);
    }
}
