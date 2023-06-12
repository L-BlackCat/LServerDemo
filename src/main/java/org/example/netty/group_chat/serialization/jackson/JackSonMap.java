package org.example.netty.group_chat.serialization.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.netty.group_chat.engine.entity.Session;
import org.example.netty.group_chat.logger.Debug;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JackSonMap implements Serializable {
    static ObjectMapper mapper = new ObjectMapper();
    Map<String,Object> map = new HashMap<>();
    public Boolean getBool(String key) {
        return (Boolean) map.get(key);
    }

    public Byte getByte(String key) {
        return (Byte) map.get(key);
    }

    public Integer getInteger(String key) {
        return (Integer) map.get(key);
    }

    public Long getLong(String Key) {
        return (Long) map.get(Key);
    }

    public Float getFloat(String Key) {
        return (Float) map.get(Key);
    }

    public Short getShort(String key) {
        return (Short) map.get(key);
    }

    public String getString(String key) {
        return (String) map.get(key);
    }

//    public void put(String key, boolean value) {
//        map.put(key,value);
//    }
//
//    public void put(String key, byte value) {
//        map.put(key,value);
//    }
//
//    public void put(String key, int value) {
//        map.put(key,value);
//    }
//
//    public void put(String key, long value) {
//        map.put(key,value);
//    }
//
//    public void put(String key, float value) {
//        map.put(key,value);
//    }
//
//    public void put(String key, short value) {
//        map.put(key,value);
//    }
//
//    public void put(String key, String value) {
//        map.put(key,value);
//    }


    public void put(String key,Object value){
        map.put(key,value);
    }

    public Object get(String key){
        return map.get(key);
    }

    public Map<String, Object> getMap() {
        return map;
    }


    public <T> T get(String key,Class<T> clazz){
        Object obj = map.get(key);
        try {
            String jsonString = mapper.writeValueAsString(obj);
            return mapper.readValue(jsonString,mapper.getTypeFactory().constructType(clazz));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> List<T> getList(String key,Class<T> clazz){
        Object obj = map.get(key);
        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(obj);
            return jsonToList(jsonString,clazz);
        } catch (JsonProcessingException e) {
            String className = clazz.getSimpleName();
            Debug.err(String.format("parse json %s to %s is err",jsonString,className),e);
        }
        return null;
    }

    public <T> List<T> jsonToList(String jsonString,Class<T> clazz) throws JsonProcessingException {
        return mapper.readValue(jsonString,mapper.getTypeFactory().constructParametricType(List.class,clazz));
    }


    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();


        JackSonMap jackSonMap = new JackSonMap();
        int[] marks = {1,2,3};
        List<Session> list = new ArrayList<>();
        list.add(Session.create(1,"wang"));
        list.add(Session.create(2,"hello"));

        Session session = Session.create(21,"li");
        // JAVA Object
        jackSonMap.put("session", session);
        // JAVA String
        jackSonMap.put("name", "Mahesh Kumar");
        // JAVA Boolean
        jackSonMap.put("verified", Boolean.FALSE);
        // Array
        jackSonMap.put("marks", marks);
        jackSonMap.put("list",list);

        byte[] bytes = mapper.writeValueAsBytes(jackSonMap);

        System.out.println(new String(bytes));


        JackSonMap map1 = mapper.readValue(bytes, JackSonMap.class);
        Object obj = map1.get("list");
        String listStr = mapper.writeValueAsString(obj);
        System.out.println("toString:" + obj.toString());

        List<Session> tempList = mapper.readValue(listStr, mapper.getTypeFactory().constructParametricType(List.class,Session.class));

        System.out.println(obj);


    }
}
