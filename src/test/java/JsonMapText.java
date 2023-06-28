import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.example.netty_chat.group_chat.bean.Packet;
import org.example.netty_chat.group_chat.bean.PacketData;
import org.example.netty_chat.group_chat.engine.entity.JArray;
import org.example.netty_chat.group_chat.engine.entity.Session;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class JsonMapText implements Serializable {

    JSONObject json;

    public JsonMapText() {
        this.json = new JSONObject();
    }

    public JsonMapText(JSONObject json) {
        this.json = json;
    }

    public JSONObject getFastJson(){
        return json;
    }

    public void put(String key, String value) {

        json.put(key,value);
    }

    public void put(String key, List<Session> list){
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(list);
        json.put(key,jsonArray);
    }

    public Object get(String key){
        return json.get(key);
    }


    public static void main(String[] args) {
//        JsonMapText oldMap = new JsonMapText();
//        oldMap.put("message", "hello");
//        String str = JSON.toJSONString(oldMap);
//        System.out.println(str);
//        byte[] bytes = JSON.toJSONBytes(oldMap);
//
//
//        JsonMapText newMap = JSON.parseObject(bytes, JsonMapText.class);
//        System.out.println(JSON.toJSONString(newMap));
//
//
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
        List<Integer> sessionList = new ArrayList<>();
        sessionList.add(1);
        sessionList.add(2);
        packet.getMap().put("session_list", JArray.toList(sessionList).getArrayJson());

        String packetStr = JSON.toJSONString(packet);
        System.out.println(packetStr);


        //JSONObject中JSONArray被转换成了JSONObject
        byte[] packetBytes = JSON.toJSONBytes(packet);
        Packet newPacket = JSON.parseObject(packetBytes, PacketData.class);
        System.out.println(JSON.toJSONString(newPacket));
    }
}
