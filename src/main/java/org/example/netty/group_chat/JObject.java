package org.example.netty.group_chat;



import com.alibaba.fastjson2.JSONObject;

import java.io.Serializable;


public class JObject implements IObject, Serializable {
    JSONObject json;

    public JObject() {
        this.json = new JSONObject();
    }

    public JSONObject getFastJson(){
        return json;
    }


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

    @Override
    public String getString(String key) {
        return json.getString(key);
    }

    @Override
    public void put(String key, boolean value) {
        json.put(key,value);
    }

    @Override
    public void put(String key, byte value) {
        json.put(key,value);
    }

    @Override
    public void put(String key, int value) {
        json.put(key,value);
    }

    @Override
    public void put(String key, long value) {
        json.put(key,value);
    }

    @Override
    public void put(String key, float value) {

        json.put(key,value);
    }

    @Override
    public void put(String key, short value) {

        json.put(key,value);
    }

    @Override
    public void put(String key, String value) {

        json.put(key,value);
    }

    @Override
    public Object get(String key){
        return json.get(key);
    }

    @Override
    public String toJSONString () {
//		return json.toString(SerializerFeature.WriteNonStringKeyAsString);

        //SerializerFeature.WriteNonStringKeyAsString AHT 保证key 有引号
        //SerializerFeature.DisableCircularReferenceDetect AHT 解决了循环应用的问题，不过用的不好，可能会死循环  ADELAY Z_66 未来将协议输出加个标签来是否使用Disable循环检测
        return json.toString();
    }


    @Override
    public String toString() {
        return toJSONString();
    }

}
