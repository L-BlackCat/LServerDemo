package org.example.netty.group_chat.engine.entity;

import java.util.Collection;

public interface IObject{
    boolean containsKey(String key);

    int size();

    Boolean getBool(String key);

    Byte getByte(String key);

    Integer getInteger(String key);

    Long getLong(String Key);

    Float getFloat(String Key);

    Short getShort(String key);

    String getString(String key);

    Object get(String key);

    IArray getIArray(String key);
    IObject getIObject(String key);

    void put(String key,boolean value);

    void put(String key,byte value);

    void put(String key,int value);

    void put(String Key,long value);

    void put(String Key,float value);

    void put(String key,short value);

    void put(String key,String value);

    Object put(String key,Object obj);

    Collection<Boolean> getBoolArray (String key);

	Collection<Short> getShortArray (String key);

	Collection<Integer> getIntArray (String key);

	Collection<Long> getLongArray (String key);

	Collection<Float> getFloatArray (String key);

	Collection<Double> getDoubleArray (String key);

	Collection<String> getStringArray (String key);

    void putBooleanArray (String key, Collection<Boolean> val);

	void putShortArray (String key, Collection<Short> val);

	void putIntArray (String key, Collection<Integer> val);

	void putLongArray (String key, Collection<Long> val);

	void putFloatArray (String key, Collection<Float> val);

	void putDoubleArray (String key, Collection<Double> val);

	void putStringArray (String key, Collection<String> val);

    String toJSONString ();

    <T> T getObject(String key, Class<T> clazz);
}
