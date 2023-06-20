package org.example.netty.group_chat.server;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.example.netty.group_chat.ChatErrCodeEnum;
import org.example.netty.group_chat.bean.Packet;
import org.example.netty.group_chat.client.IAttributes;
import org.example.netty.group_chat.engine.chat_channel.ChatChannelBaseHandler;
import org.example.netty.group_chat.engine.chat_channel.GroupChatEnum;
import org.example.netty.group_chat.engine.entity.Session;
import org.example.netty.group_chat.engine.utils.KDateUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public enum GlobalSessionMgr {
    Instance;

    private final AtomicInteger autoUid = new AtomicInteger(10000);

    private static final Map<Long,Channel> userIdChannelMap = new ConcurrentHashMap<>();

    private static final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private static final Map<String,List<Session>> groupChannelMap = new HashMap<>();

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

    public String createGroup(int chatType, List<Session> sessionList) throws InstantiationException, IllegalAccessException {
        ChatChannelBaseHandler handler = GroupChatEnum.newInstanceHandler(chatType);
        if(handler == null){
            return null;
        }
        String channelName = handler.channelPassive();
        groupChannelMap.put(channelName,sessionList);
        return channelName;
    }

    public ChatErrCodeEnum joinGroup(String groupName,Session selfSession){
        if(groupChannelMap.containsKey(groupName)){
            List<Session> sessions = groupChannelMap.get(groupName);
            boolean isCanJoin = true;
            for (Session session : sessions) {
                if(session.getUid() == selfSession.getUid()){
                    isCanJoin = false;
                    break;
                }
            }
            if(isCanJoin){
                sessions.add(selfSession);
                return ChatErrCodeEnum.SUCCESS;
            }
        }
        return ChatErrCodeEnum.ERR;
    }

    public ChatErrCodeEnum quitGroup(String groupName, Session selfSession){
        if(groupChannelMap.containsKey(groupName)){
            List<Session> sessions = groupChannelMap.get(groupName);
            sessions = sessions.stream().filter(session -> session.getUid() != selfSession.getUid()).collect(Collectors.toList());
            if(!sessions.isEmpty()){

                groupChannelMap.put(groupName,sessions);
            }else {
                groupChannelMap.remove(groupName);
            }
            return ChatErrCodeEnum.SUCCESS;
        }
        return ChatErrCodeEnum.ERR;
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

    public void sendMsgToEveryOne(Packet packet){
        channelGroup.forEach(channel -> {
                channel.writeAndFlush(packet);
        });
    }


    public void sendMsgToOther(Channel targetChannel,Packet packet){
        channelGroup.forEach(channel -> {
            if(channel != targetChannel){
                channel.writeAndFlush(packet);
            }
        });
    }

    public void sendMsgToSpecified(String groupName,Packet packet){
        if (groupChannelMap.containsKey(groupName)) {
            List<Session> sessions = groupChannelMap.get(groupName);
            for (Session session : sessions) {
                Channel channel = userIdChannelMap.get(session.getUid());
                if(channel != null){
                    channel.writeAndFlush(packet);
                }
            }
        }
    }

    public List<Session> getGroupSessionList(String groupName){
        if(groupChannelMap.containsKey(groupName)){
           return groupChannelMap.get(groupName);
        }
        return new ArrayList<>();
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
