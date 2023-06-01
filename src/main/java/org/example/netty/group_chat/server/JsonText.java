package org.example.netty.group_chat.server;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.example.netty.group_chat.bean.Packet;
import org.example.netty.group_chat.bean.PacketData;


import java.io.Serializable;

public class JsonText implements Serializable {

    JSONObject json;

    public JsonText() {
        this.json = new JSONObject();
    }

    public JsonText(JSONObject json) {
        this.json = json;
    }

    public JSONObject getFastJson(){
        return json;
    }

    public void put(String key, String value) {

        json.put(key,value);
    }

    public Object get(String key){
        return json.get(key);
    }


    public static void main(String[] args) {
//        JsonText oldMap = new JsonText();
//        oldMap.put("message", "hello");
//        String str = JSON.toJSONString(oldMap);
//        System.out.println(str);
//        byte[] bytes = JSON.toJSONBytes(oldMap);
//
//
//        JsonText newMap = JSON.parseObject(bytes, JsonText.class);
//        System.out.println(JSON.toJSONString(newMap));



//        JsonTextPacket packet = new JsonTextPacket();
//        packet.getMap().put("message", "hello");
//        String packetStr = JSON.toJSONString(packet);
//        System.out.println(packetStr);
//        byte[] packetBytes = JSON.toJSONBytes(packet);
//
//
//
//        JsonTextPacket newPacket = JSON.parseObject(packetBytes, JsonTextPacket.class);
//        System.out.println(JSON.toJSONString(newPacket));

        Packet packet = new PacketData();
        packet.getMap().put("message", "hello");
        String packetStr = JSON.toJSONString(packet);
        System.out.println(packetStr);
        byte[] packetBytes = JSON.toJSONBytes(packet);



        Packet newPacket = JSON.parseObject(packetBytes, PacketData.class);
        System.out.println(JSON.toJSONString(newPacket));
    }

}
