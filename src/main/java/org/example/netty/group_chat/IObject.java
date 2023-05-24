package org.example.netty.group_chat;

public interface IObject {
    boolean containsKey(String key);

    int size();

    Boolean getBool(String key);

    Byte getByte(String key);

    Integer getInteger(String key);

    Long getLong(String Key);

    Float getFloat(String Key);

    Short getShort(String key);
}
