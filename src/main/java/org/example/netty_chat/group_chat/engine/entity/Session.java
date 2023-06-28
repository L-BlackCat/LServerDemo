package org.example.netty_chat.group_chat.engine.entity;

import com.alibaba.fastjson.JSON;

public class Session {
    long uid;
    String name;


    public  static Session create(long uid, String name) {
        Session session = new Session();
        session.uid = uid;
        session.name = name;
        return session;
    }


    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
