package org.example.netty.group_chat.serialization;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.netty.group_chat.logger.Debug;

import java.io.IOException;

public class JsonSerializeHandler implements IChatSerializer {
    ObjectMapper mapper = new ObjectMapper();

    @Override
    public int getSerializeType() {
        return ChatSerializeType.JSON.toInt();
    }

    @Override
    public byte[] serialize(Object obj) {
        try {
            return mapper.writeValueAsBytes(obj);
        } catch (JsonProcessingException e) {
            Debug.err("serialize is err",e);
        }
        return null;
    }

    @Override
    public <T> T deserialize(byte[] bytes,Class<T> clazz) {
        try {
            return mapper.readValue(bytes,clazz);
        } catch (IOException e) {
            Debug.err("deserialize is err",e);
        }
        return null;
    }
}
