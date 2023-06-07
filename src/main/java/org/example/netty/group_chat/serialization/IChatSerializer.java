package org.example.netty.group_chat.serialization;

public interface IChatSerializer {
    IChatSerializer DEFAULT = new JsonSerializeHandler();
    default int getSerializeType(){
        return DEFAULT.getSerializeType();
    }

    byte[] serialize(Object obj);

    <T> T deserialize(byte[] bytes,Class<T> clazz);
}
