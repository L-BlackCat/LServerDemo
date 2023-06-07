package org.example.netty.group_chat.engine.entity;

import java.util.Collection;
import java.util.Iterator;

public interface IArray {
    boolean contains (Object obj);

    Iterator<Object> iterator ();

    Object remove (int index);

    int size ();

    void addNull ();

    void add (boolean param);

    void add (byte param);

    void add (short param);

    void add (int param);

    void add (long param);

    void add (float param);

    void add (double param);

    void add (String param);

    void addBoolArray (Collection<Boolean> param);

    void addByteArray (byte[] param);

    void addShortArray (Collection<Short> param);

    void addIntArray (Collection<Integer> param);

    void addLongArray (Collection<Long> param);

    void addFloatArray (Collection<Float> param);

    void addDoubleArray (Collection<Double> param);

    void addStringArray (Collection<String> param);


    void add (IArray param);

    void add (IObject param);

    void addObject (Object param);



    boolean isNull (int index);

    Boolean getBool (int index);

    Byte getByte (int index);

    Short getShort (int index);

    Integer getInt (int index);

    Long getLong (int index);

    Float getFloat (int index);

    Double getDouble (int index);

    String getString (int index);

    Collection<Boolean> getBoolArray (int index);


    Collection<Short> getShortArray (int index);

    Collection<Integer> getIntArray (int index);

    Collection<Long> getLongArray (int index);

    Collection<Float> getFloatArray (int index);

    Collection<Double> getDoubleArray (int index);

    Collection<String> getStringArray (int index);


    Object getObject (int index);

    IArray getArray (int index);

    IObject getIObject (int index);

    String toJSONString ();
}
