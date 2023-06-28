package org.example.netty_chat.group_chat.engine;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kun on 2021/10/29.
 * id 枚举
 */
public interface IDEnum {
    int REQUEST_TYPE =1;
    int RESPONSE_TYPE = 2;

    int RESPONSE_ID = 100000;

    int getId();
    
    static void checkIDDuplicate(IDEnum[] values) throws Exception {
        if(values == null || values.length == 0){
            return;
        }
        List<Integer> existList = new ArrayList<>(  );
        String className = values[0].getClass().getSimpleName();
        for ( IDEnum value : values ) {
            int id = value.getId();
            if(existList.contains( id )){
                throw new Exception( "duplicate id class: "+className+", id :"+id );
            }
            existList.add( id );
        }
    }
}
