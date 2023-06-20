package org.example.netty.group_chat.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.example.netty.group_chat.bean.PacketTypeEnum;
import org.example.netty.group_chat.bean.Packet;
import org.example.netty.group_chat.bean.PacketData;
import org.example.netty.group_chat.engine.ClientProtocolMgr;
import org.example.netty.group_chat.logger.Debug;
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

        byteBuf.writeLong(ClientProtocolMgr.MAGIC_NUM);

        byteBuf.writeByte(packet.version());

        byteBuf.writeByte(IChatSerializer.DEFAULT.getSerializeType());

        byteBuf.writeInt(packet.getRequestId());

        byteBuf.writeInt(bytes.length);

        byteBuf.writeBytes(bytes);

        return byteBuf;
    }


    public Packet decode(ByteBuf buf) throws InstantiationException, IllegalAccessException {
        long magicNum = buf.readLong();
        if(magicNum != ClientProtocolMgr.MAGIC_NUM){
            Debug.info("错误包，不进行解析");
            return null;
        }

        byte version = buf.readByte();
        byte serializeType = buf.readByte();
        int requestId = buf.readInt();
        int len = buf.readInt();
        byte[] bytes = new byte[len];
        buf.readBytes(bytes);
        ChatSerializeType chatSerializeType = ChatSerializeType.toEnum(serializeType);
        if(chatSerializeType == null){
            Debug.info("找不到序列化id");
            return null;
        }
        if(version != Packet.VERSION){
            Debug.info("版本不同");
            return null;
        }
        IChatSerializer handler = chatSerializeType.newInstanceHandler();
        //  LFH 需求：这里分辨包是一个请求包还是响应包
        PacketTypeEnum packetTypeEnum = PacketTypeEnum.toEnum(requestId);
        if(packetTypeEnum == null){
            Debug.err("请求对应的数据类型不存在,packet_id: " + requestId);
            return null;
        }

        Class<? extends PacketData> handlerClass = packetTypeEnum.getHandlerClass();
        return handler.deserialize(bytes,handlerClass);
    }

}
