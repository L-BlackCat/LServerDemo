package org.example.netty.group_chat.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.example.netty.group_chat.IObject;
import org.example.netty.group_chat.data_pack.Packet;
import org.example.netty.group_chat.data_pack.PacketData;
import org.example.netty.group_chat.engine.ClientRequestMgr;
import org.example.netty.group_chat.serialization.ChatSerializeType;
import org.example.netty.group_chat.serialization.IChatSerializer;

public enum PacketCodeC {
    Instance;

    public ByteBuf encode(Packet packet){
        // 1. 创建 ByteBuf 对象

        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();

        // 2. 序列化 Java 对象

        byte[] bytes = IChatSerializer.DEFAULT.serialize(packet);

        // 3. 实际编码过程

        byteBuf.writeLong(ClientRequestMgr.MAGIC_NUM);

        byteBuf.writeInt(packet.version());

        byteBuf.writeByte(IChatSerializer.DEFAULT.getSerializeType());

//        byteBuf.writeInt(packet.getRequestId());

        byteBuf.writeInt(bytes.length);

        byteBuf.writeBytes(bytes);

        return byteBuf;
    }


    public Packet decode(ByteBuf buf) throws InstantiationException, IllegalAccessException {
        long magicNum = buf.readLong();
        if(magicNum != ClientRequestMgr.MAGIC_NUM){
            System.out.println("错误包，不进行解析");
            return null;
        }

        int version = buf.readInt();
        byte serializeType = buf.readByte();
//        int requestId = buf.readInt();
        int len = buf.readInt();
        byte[] bytes = new byte[len];
        buf.readBytes(bytes);
        ChatSerializeType chatSerializeType = ChatSerializeType.toEnum(serializeType);
        if(chatSerializeType == null){
            System.out.println("找不到序列化id");
            return null;
        }
        if(version != Packet.VERSION){
            System.out.println("版本不同");
            return null;
        }
        IChatSerializer handler = chatSerializeType.newInstanceHandler();


        return handler.deserialize(bytes,Packet.class);
    }
}
