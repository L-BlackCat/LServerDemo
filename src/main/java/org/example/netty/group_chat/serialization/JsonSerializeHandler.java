package org.example.netty.group_chat.serialization;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.example.netty.group_chat.IObject;
import org.example.netty.group_chat.data_pack.Packet;

public class JsonSerializeHandler implements IChatSerializer {
    @Override
    public int getSerializeType() {
        return ChatSerializeType.JSON.toInt();
    }

    @Override
    public byte[] serialize(Object obj) {
        return JSONObject.toJSONBytes(obj);
    }

    @Override
    public <T> T deserialize(byte[] bytes,Class<T> clazz) {
        return JSON.parseObject(bytes, clazz);
    }
}
