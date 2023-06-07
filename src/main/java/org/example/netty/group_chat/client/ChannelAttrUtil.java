package org.example.netty.group_chat.client;

import io.netty.channel.Channel;
import io.netty.util.Attribute;

public enum ChannelAttrUtil {
    Instance;


    public void markAsLogin(Channel channel){
        channel.attr(IAttributes.LOGIN).set(true);
    }


    public boolean hasLogin(Channel channel){
        Attribute<Boolean> loginAttr = channel.attr(IAttributes.LOGIN);
        return loginAttr.get() != null && loginAttr.get();
    }


    public void markAsName(Channel channel,String name){
        channel.attr(IAttributes.NAME).set(name);
    }

    public String getName(Channel channel){
        Attribute<String> nameAttr = channel.attr(IAttributes.NAME);
        String defaultName = "无名氏";
        if(nameAttr.get() != null){
            defaultName = nameAttr.get();
        }
        return defaultName;
    }


}
