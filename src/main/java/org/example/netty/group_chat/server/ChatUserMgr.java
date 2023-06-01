package org.example.netty.group_chat.server;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.example.netty.group_chat.bean.Packet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ChatUserMgr {
    Instance;

    List<String> names = new ArrayList<>();

    Map<Channel,String> channelNameMap = new HashMap<>();

    private static final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    //public static List<Channel> channels = new ArrayList<Channel>();

    //使用一个hashmap 管理
    //public static Map<String, Channel> channels = new HashMap<String,Channel>();
    public List<String> getNames() {
        return names;
    }

    public void put(Channel channel,String name){

    }

    public String getName(Channel channel){
        return channelNameMap.getOrDefault(channel, "无名氏");
    }

    public void addChannel(Channel channel){
        channelGroup.add(channel);
    }

    public void removeChannel(Channel channel){
        channelGroup.remove(channel);
    }

    public void sendMsg(Packet packet){
        channelGroup.writeAndFlush(packet);
    }
}
