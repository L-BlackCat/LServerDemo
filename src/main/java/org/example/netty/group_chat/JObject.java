package org.example.netty.group_chat;

import com.alibaba.fastjson.JSONObject;

public class JObject implements IObject{
    JSONObject json;

    @Override
    public boolean containsKey(String key) {
        return json.containsKey(key);
    }

    @Override
    public int size() {
        return json.size();
    }

    @Override
    public Boolean getBool(String key) {
        return json.getBoolean(key);
    }

    @Override
    public Byte getByte(String key) {
        return json.getByte(key);
    }

    @Override
    public Integer getInteger(String key) {
        return json.getInteger(key);
    }

    @Override
    public Long getLong(String Key) {
        return json.getLong(Key);
    }

    @Override
    public Float getFloat(String Key) {
        return json.getFloat(Key);
    }

    @Override
    public Short getShort(String key) {
        return json.getShort(key);
    }
}
