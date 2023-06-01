package org.example.netty.group_chat.server;


import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

import java.io.Serializable;

public class JsonTextPacket implements Serializable {
    JsonText map;

    public JsonTextPacket() {
        this.map = new JsonText();
    }

    public JsonText getMap() {
        return map;
    }

    public void setMap(JsonText map) {
        this.map = map;
    }
}
