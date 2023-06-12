package org.example.netty.group_chat.bean;

import org.checkerframework.checker.units.qual.A;
import org.example.netty.group_chat.engine.ChatSendPacketToEveryHandler;
import org.example.netty.group_chat.engine.ChatSendPacketToOtherHandler;
import org.example.netty.group_chat.engine.ChatSendPacketToSelfHandler;
import org.example.netty.group_chat.engine.ISendPacketHandler;

public enum PacketPushTypeEnum {
    PUSH_TO_EVERYONE(1,"推送给所有人", ChatSendPacketToEveryHandler.class),
    PUSH_TO_SELF(2,"推送给自己", ChatSendPacketToSelfHandler.class),

    PUSH_TO_OTHER(3,"推送给其他人",ChatSendPacketToOtherHandler.class),
    ;

    private int id;

    private String desc;

    private Class<? extends ISendPacketHandler> handlerClass;


    PacketPushTypeEnum(int id, String desc, Class<? extends ISendPacketHandler> handlerClass) {
        this.id = id;
        this.desc = desc;
        this.handlerClass = handlerClass;
    }

    public int toType(){
        return id;
    }

    public String getDesc() {
        return desc;
    }

    public static PacketPushTypeEnum toEnum(int type){
        for (PacketPushTypeEnum value : values()) {
            if(value.toType() == type){
                return value;
            }
        }
        return null;
    }

    public Class<? extends ISendPacketHandler> getHandlerClass() {
        return handlerClass;
    }

    public static ISendPacketHandler newInstance(int type) throws InstantiationException, IllegalAccessException {
        PacketPushTypeEnum packetPushTypeEnum = toEnum(type);
        if(packetPushTypeEnum != null){
            return packetPushTypeEnum.getHandlerClass().newInstance();
        }
        return null;
    }
}
