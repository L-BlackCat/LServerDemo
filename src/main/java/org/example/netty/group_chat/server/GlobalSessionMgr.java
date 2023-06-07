package org.example.netty.group_chat.server;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.example.netty.group_chat.bean.Packet;
import org.example.netty.group_chat.client.IAttributes;
import org.example.netty.group_chat.engine.entity.Session;
import org.example.netty.group_chat.engine.utils.KDateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public enum GlobalSessionMgr {
    Instance;

    private final AtomicInteger autoUid = new AtomicInteger(10000);

    private static final Map<Long,Channel> userIdChannelMap = new ConcurrentHashMap<>();

    private static final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    public Session bind(String name,Channel channel){
        long uid = createUUid();
        Session session = Session.create(uid, name);
        bind(session,channel);
        return session;
    }

    public void bind(Session session, Channel channel){
        userIdChannelMap.put(session.getUid(),channel);
        channel.attr(IAttributes.SESSION).set(session);
    }

    public void unbind(Channel channel){
        if(hasLogin(channel)){
            Session session = getSession(channel);
            userIdChannelMap.remove(session.getUid());
        }
    }

    public long createUUid(){
        long now = KDateUtil.Instance.now();
        return ((now & 0x1FFFFF) << 12) | (autoUid.incrementAndGet() & 0xFFF);
    }

    public boolean hasLogin(Channel channel){
        return channel.hasAttr(IAttributes.SESSION);
    }


    public Session getSession(Channel channel){
        return channel.attr(IAttributes.SESSION).get();
    }

    public Channel getChannel(long userId){
        return userIdChannelMap.get(userId);
    }

    public String getName(Channel channel){
        if (hasLogin(channel)) {
            Session session = getSession(channel);
            return session.getName();
        }
        return "无名氏";
    }

    public void addChannel(Channel channel){
        channelGroup.add(channel);
    }

    public void sendMsg(Channel targetChannel,Packet packet){
        channelGroup.forEach(channel -> {
            if(channel != targetChannel){
                channel.writeAndFlush(packet);
            }
        });
    }

    public List<Session> allSession(){
        List<Session> sessionList = new ArrayList<>();
        for (Long uid : userIdChannelMap.keySet()) {
            Channel channel = userIdChannelMap.get(uid);
            sessionList.add(getSession(channel));
        }

        return sessionList;
    }
}
