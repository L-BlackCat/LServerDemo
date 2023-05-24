package org.example.netty.group_chat.serialization;

import org.example.netty.group_chat.IObject;

public interface IChatSerializer {
    IChatSerializer DEFAULT = new JsonSerializeHandler();
    int getSerializeType();

    byte[] serialize(Object obj);

    IObject deserialize(byte[] bytes);
}
