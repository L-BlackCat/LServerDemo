import com.alibaba.fastjson2.JSONArray;

import java.io.Serializable;

public class JsonTextPacket implements Serializable {
    JsonMapText map;
    JsonArrayText array;

    public JsonTextPacket() {
        this.map = new JsonMapText();
        this.array = new JsonArrayText();
    }

    public JsonMapText getMap() {
        return map;
    }

    public void setMap(JsonMapText map) {
        this.map = map;
    }


    public JsonArrayText getArray() {
        return array;
    }

    public void setArray(JsonArrayText array) {
        this.array = array;
    }
}
