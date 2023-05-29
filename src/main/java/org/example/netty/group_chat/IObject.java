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

    String getString(String key);



    void put(String key,boolean value);

    void put(String key,byte value);

    void put(String key,int value);

    void put(String Key,long value);

    void put(String Key,float value);

    void put(String key,short value);

    void put(String key,String value);
}
