package org.example.netty.group_chat.client;

import io.netty.util.AttributeKey;
import org.example.netty.group_chat.engine.entity.Session;

public interface IAttributes {
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
    AttributeKey<String> NAME = AttributeKey.newInstance("name");

    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");

}
