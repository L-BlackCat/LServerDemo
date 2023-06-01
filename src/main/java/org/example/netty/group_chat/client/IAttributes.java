package org.example.netty.group_chat.client;

import io.netty.util.AttributeKey;

public interface IAttributes {
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
    AttributeKey<String> NAME = AttributeKey.newInstance("name");
}
