import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.example.netty.group_chat.engine.entity.Session;


import java.io.Serializable;

public class JsonArrayText implements Serializable {

    JSONArray json;

    public JsonArrayText() {
        this.json = new JSONArray();
    }

    public JsonArrayText(JSONArray json) {
        this.json = json;
    }

    public JSONArray getFastJson(){
        return json;
    }

    public void add(Object value){
        json.add(value);
    }


    public Object get(int index){
        return json.get(index);
    }


    public static void main(String[] args) {
//        JsonArrayText oldArray = new JsonArrayText();
//        oldArray.add("hello");
//        String str = JSON.toJSONString(oldArray);
//        System.out.println(str);
//        byte[] bytes = JSON.toJSONBytes(oldArray);
//
//
//        JsonArrayText newArray = JSON.parseObject(bytes, JsonArrayText.class);
//        System.out.println(JSON.toJSONString(newArray));


//        List<Session> list = new ArrayList<>();
//        list.add(Session.create(1,"li"));
//        list.add(Session.create(2,"wang"));
//        JsonTextPacket packet = new JsonTextPacket();
//        packet.getArray().add("hello");
//        packet.getMap().put("session_list",list);
//        String packetStr = JSON.toJSONString(packet);
//        System.out.println(packetStr);
//        byte[] packetBytes = JSON.toJSONBytes(packet);
//
//
//
//        JsonTextPacket newPacket = JSON.parseObject(packetBytes, JsonTextPacket.class);
//        System.out.println(JSON.toJSONString(newPacket));

//        PacketData packet = new PacketData();
//        packet.getMap().put("message", "hello");
//        List<Integer> sessionList = new ArrayList<>();
//        sessionList.add(1);
//        sessionList.add(2);
//        packet.getMap().put("session_list", sessionList);
//
//
//        JArray array = JArray.toList(sessionList);
//        packet.setList(array);
//        String packetStr = JSON.toJSONString(packet);
//        System.out.println(packetStr);

//        byte[] packetBytes = JSON.toJSONBytes(packet);
//
//
//        PacketData newPacket = JSON.parseObject(packetBytes, PacketData.class);
//        System.out.println(JSON.toJSONString(newPacket));

//        JSONArray jsonArray = new JSONArray();
//        jsonArray.add(1);
//        jsonArray.add(2);
//        JSONObject json = new JSONObject();
//        json.put("hello","world");
//        json.put("list",jsonArray);
//
//        String jsonStr = JSON.toJSONString(json);
//
//
//        JSONObject newJson = JSONObject.parseObject(jsonStr);
//
//        System.out.println(JSON.toJSONString(newJson));
//
//
//
//        JSONArray sessionList = new JSONArray();
//        sessionList.add(Session.create(1,"li"));
//        json.put("session_list",sessionList);
//        JsonMapText jsonMapText = new JsonMapText(json);
//        String jsonMapTextStr = JSON.toJSONString(jsonMapText);
//
//        JsonMapText newJsonMap = JSONObject.parseObject(jsonMapTextStr, JsonMapText.class);
//        System.out.println(newJsonMap);

    }
}
