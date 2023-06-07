package org.example.netty.group_chat.engine.entity;



import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.example.netty.group_chat.logger.Debug;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;


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
    public Object put(String key, Object obj) {
        if(obj == this){
            Debug.err("JObject can't put self ");
            return null;
        }

        if(obj instanceof JObject){
            return json.put(key, ((JObject) obj).json);
        }
        else if(obj instanceof List){
            List list = (List) obj;
            return json.put(key,list);
        }
        else if(obj instanceof Map){
            return json.put(key, JSON.toJSON(  obj));
        }
        return  json.put(key,obj);
    }


    @Override
    public Collection<Boolean> getBoolArray(String key) {
        return json.getJSONArray(key).toJavaList(Boolean.class);
    }



    @Override
    public Collection<Short> getShortArray(String key) {
        return json.getJSONArray(key).toJavaList(Short.class);
    }

    @Override
    public Collection<Integer> getIntArray(String key) {
        return json.getJSONArray(key).toJavaList(Integer.class);
    }

    @Override
    public Collection<Long> getLongArray(String key) {
        return json.getJSONArray(key).toJavaList(Long.class);
    }

    @Override
    public Collection<Float> getFloatArray(String key) {
        return json.getJSONArray(key).toJavaList(Float.class);
    }

    @Override
    public Collection<Double> getDoubleArray(String key) {
        return json.getJSONArray(key).toJavaList(Double.class);
    }

    @Override
    public Collection<String> getStringArray(String key) {
        return json.getJSONArray(key).toJavaList(String.class);
    }


    @Override
	public void putBooleanArray(String key, Collection<Boolean> val) {
		json.put(key, val);
	}

	@Override
	public void putShortArray(String key, Collection<Short> val) {
		json.put(key, val);
	}

	@Override
	public void putIntArray(String key, Collection<Integer> val) {
		json.put(key, val);
	}

	@Override
	public void putLongArray(String key, Collection<Long> val) {
		json.put(key, val);
	}

	@Override
	public void putFloatArray(String key, Collection<Float> val) {
		json.put(key, val);
	}

	@Override
	public void putDoubleArray(String key, Collection<Double> val) {
		json.put(key, val);
	}

	@Override
	public void putStringArray(String key, Collection<String> val) {
		json.put(key, val);
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


    @Override
    public <T> T getObject(String key, Class<T> clazz) {
        return this.json.getObject(key,clazz);
    }
}
