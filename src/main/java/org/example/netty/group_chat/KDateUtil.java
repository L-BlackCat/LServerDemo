package org.example.netty.group_chat;

import java.text.SimpleDateFormat;
import java.util.Date;

public enum KDateUtil {
    Instance;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public long now(){
        return System.currentTimeMillis() / 1000;
    }

    public String date(){
        return sdf.format(new Date());
    }

}
