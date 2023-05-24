package org.example.netty.group_chat.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.example.netty.group_chat.data_pack.Packet;
import org.example.netty.group_chat.serialization.IChatSerializer;

public class PacketCodeC {
    public static final long MAGIC_NUM = 0x12345678;

    public ByteBuf encode(Packet packet){
        // 1. 创建 ByteBuf 对象

        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();

        // 2. 序列化 Java 对象

        byte[] bytes = IChatSerializer.DEFAULT.serialize(packet);

        // 3. 实际编码过程

        byteBuf.writeLong(MAGIC_NUM);

        byteBuf.writeByte(packet.version());

        byteBuf.writeByte(IChatSerializer.DEFAULT.getSerializeType());

        byteBuf.writeByte(packet.get_request_id());

        byteBuf.writeInt(bytes.length);

        byteBuf.writeBytes(bytes);

        return byteBuf;
    }
}
