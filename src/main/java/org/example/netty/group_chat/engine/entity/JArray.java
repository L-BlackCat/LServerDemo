package org.example.netty.group_chat.engine.entity;

import com.alibaba.fastjson2.JSONArray;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class JArray implements IArray{
    private JSONArray jsonArray = new JSONArray();

    public static IArray newInstance(){
        return new JArray();
    }

    public JArray() {
    }


    public JArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    public static JArray toList(List list){
        JArray ret = new JArray();
        for (Object o : list) {
            if(o instanceof JObject) {
                ret.jsonArray.add(((JObject) o).json);
            }
            else {
                ret.jsonArray.add(o);
            }
        }
        return ret;
    }

    @Override
    public boolean contains(Object obj) {
        return jsonArray.contains(obj);
    }

    @Override
    public Iterator<Object> iterator() {
        return jsonArray.iterator();
    }

    @Override
    public Object remove(int index) {
        return jsonArray.remove(index);
    }

    @Override
    public int size() {
        return jsonArray.size();
    }

    @Override
    public void addNull() {
        jsonArray.add(null);
    }

    @Override
    public void add(boolean param) {
        jsonArray.add(param);
    }

    @Override
    public void add(byte param) {

        jsonArray.add(param);
    }

    @Override
    public void add(short param) {

        jsonArray.add(param);
    }

    @Override
    public void add(int param) {
        jsonArray.add(param);

    }

    @Override
    public void add(long param) {

        jsonArray.add(param);
    }

    @Override
    public void add(float param) {

        jsonArray.add(param);
    }

    @Override
    public void add(double param) {

        jsonArray.add(param);
    }

    @Override
    public void add(String param) {

        jsonArray.add(param);
    }

    @Override
    public void addBoolArray(Collection<Boolean> param) {
        jsonArray.add(param);
    }

    @Override
    public void addByteArray(byte[] param) {
        jsonArray.add(param);
    }

    @Override
    public void addShortArray(Collection<Short> param) {

        jsonArray.add(param);
    }

    @Override
    public void addIntArray(Collection<Integer> param) {

        jsonArray.add(param);
    }

    @Override
    public void addLongArray(Collection<Long> param) {

        jsonArray.add(param);
    }

    @Override
    public void addFloatArray(Collection<Float> param) {

        jsonArray.add(param);
    }

    @Override
    public void addDoubleArray(Collection<Double> param) {

        jsonArray.add(param);
    }

    @Override
    public void addStringArray(Collection<String> param) {

        jsonArray.add(param);
    }

    @Override
    public void add(IArray param) {

        jsonArray.add(param);
    }

    @Override
    public void add(IObject param) {

        jsonArray.add(param);
    }

    @Override
    public void addObject(Object param) {

        jsonArray.add(param);
    }

    @Override
    public boolean isNull(int index) {
        return jsonArray.get(index) == null;
    }

    @Override
    public Boolean getBool(int index) {
        return jsonArray.getBoolean(index);
    }

    @Override
    public Byte getByte(int index) {
        return jsonArray.getByte(index);
    }

    @Override
    public Short getShort(int index) {
        return jsonArray.getShort(index);
    }

    @Override
    public Integer getInt(int index) {
        return jsonArray.getInteger(index);
    }


    @Override
    public Long getLong(int index) {
        return jsonArray.getLong(index);
    }

    @Override
    public Float getFloat(int index) {
        return jsonArray.getFloat(index);
    }

    @Override
    public Double getDouble(int index) {
        return jsonArray.getDouble(index);
    }

    @Override
    public String getString(int index) {
        return jsonArray.getString(index);
    }

    @Override
    public Collection<Boolean> getBoolArray(int index) {
        return jsonArray.getJSONArray(index).toJavaList(Boolean.class);
    }

    @Override
    public Collection<Short> getShortArray(int index) {
        return jsonArray.getJSONArray(index).toJavaList(Short.class);
    }

    @Override
    public Collection<Integer> getIntArray(int index) {
        return jsonArray.getJSONArray(index).toJavaList(Integer.class);
    }

    @Override
    public Collection<Long> getLongArray(int index) {
        return jsonArray.getJSONArray(index).toJavaList(Long.class);
    }

    @Override
    public Collection<Float> getFloatArray(int index) {
        return jsonArray.getJSONArray(index).toJavaList(Float.class);
    }

    @Override
    public Collection<Double> getDoubleArray(int index) {
        return jsonArray.getJSONArray(index).toJavaList(Double.class);
    }

    @Override
    public Collection<String> getStringArray(int index) {
        return jsonArray.getJSONArray(index).toJavaList(String.class);
    }

    @Override
    public Object getObject(int index) {
        return jsonArray.get(index);
    }

    @Override
    public IArray getArray(int index) {
        return null;
    }

    @Override
    public IObject getIObject(int index) {
        return null;
    }

    @Override
    public String toJSONString() {
        return null;
    }
}
