package org.example.netty.group_chat.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import org.example.netty.group_chat.IObject;
import org.example.netty.group_chat.data_pack.ClientRequestData;
import org.example.netty.group_chat.data_pack.Packet;
import org.example.netty.group_chat.serialization.ChatSerializeType;
import org.example.netty.group_chat.serialization.IChatSerializer;

import java.util.List;

public class GroupChatMessageDecode extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        long magicNum = in.readLong();
        if(magicNum != PacketCodeC.MAGIC_NUM){
            System.out.println("错误包，不进行解析");
            return;
        }

        int version = in.readInt();
        int requestId = in.readInt();
        byte serializeType = in.readByte();
        int len = in.readInt();
        byte[] bytes = new byte[len];
        in.readBytes(bytes);
        ChatSerializeType chatSerializeType = ChatSerializeType.toEnum(serializeType);
        if(chatSerializeType == null){
            System.out.println("找不到序列化id");
            return;
        }
        IChatSerializer handler = chatSerializeType.newInstanceHandler();

        IObject map = handler.deserialize(bytes);

        Packet requestData = new ClientRequestData();
        requestData.setRequestId(requestId);
        requestData.setMap(map);
        requestData.setChatSerializeType(chatSerializeType);
        out.add(requestData);

        /*
            客户端和服务器端互相发送消息：
                发送消息    触发  编码
                接受消息    触发  解码
        * */
    }
}
