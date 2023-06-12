package serizable;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.netty.group_chat.engine.entity.Session;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JackSonText {
    public static void main(String[] args) {
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonString = "{\"name\":\"Mahesh\", \"uid\":21}";
//
//        try {
//            //  将
//            Session session = mapper.readValue(jsonString, Session.class);
//            System.out.println(session);
//
//            jsonString = mapper.writeValueAsString(session);
//            System.out.println(jsonString);
//
//        } catch (JsonParseException e) {
//            e.printStackTrace();
//        } catch (JsonMappingException e1) {
//            e1.printStackTrace();
//        } catch (IOException e2) {
//            e2.printStackTrace();
//        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String,Object> studentDataMap = new HashMap<String,Object>();
            int[] marks = {1,2,3};
            List<Session> list = new ArrayList<>();
            list.add(Session.create(1,"wang"));
            list.add(Session.create(2,"hello"));

            Session session = Session.create(21,"li");
            // JAVA Object
            studentDataMap.put("session", session);
            // JAVA String
            studentDataMap.put("name", "Mahesh Kumar");
            // JAVA Boolean
            studentDataMap.put("verified", Boolean.FALSE);
            // Array
            studentDataMap.put("marks", marks);
            studentDataMap.put("list",list);
            mapper.writeValue(new File("student.json"), studentDataMap);
            //result student.json
            //{
            //   "student":{"name":"Mahesh","age":10},
            //   "marks":[1,2,3],
            //   "verified":false,
            //   "name":"Mahesh Kumar"
            //}
            studentDataMap = mapper.readValue(new File("student.json"), Map.class);
            System.out.println(studentDataMap.get("session"));
            System.out.println(studentDataMap.get("name"));
            System.out.println(studentDataMap.get("verified"));
            System.out.println(studentDataMap.get("marks"));
            System.out.println(studentDataMap.get("list"));
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
