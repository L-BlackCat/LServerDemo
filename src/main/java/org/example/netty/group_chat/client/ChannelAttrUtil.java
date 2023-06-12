package org.example.netty.group_chat.client;

import io.netty.channel.Channel;
import io.netty.util.Attribute;
import org.example.netty.group_chat.engine.entity.Session;

public enum ChannelAttrUtil {
    Instance;


    public void markAsLogin(Channel channel, Session session){
        channel.attr(IAttributes.SESSION).set(session);
    }


    public boolean hasLogin(Channel channel){
        Attribute<Session> loginAttr = channel.attr(IAttributes.SESSION);
        return loginAttr.get() != null;
    }

    public Session getSession(Channel channel){
        Attribute<Session> attr = channel.attr(IAttributes.SESSION);
        return attr.get();
    }
}
