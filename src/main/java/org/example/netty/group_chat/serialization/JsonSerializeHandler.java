package org.example.netty.group_chat.serialization;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.example.netty.group_chat.IObject;

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
    public IObject deserialize(byte[] bytes) {
        return JSON.parseObject(bytes,IObject.class);
    }
}
