package org.example.netty.group_chat.bean;

import org.example.netty.group_chat.data_pack.PacketData;

public enum PacketEnum {
    Login_Request(1,"登录请求",LoginRequestPacket.class),
    Login_Response(1000001,"登录响应",LoginResponsePacket.class),

    ;

    private int id;

    private String desc;

    private Class<? extends PacketData> handlerClass;


    PacketEnum(int id, String desc, Class<? extends PacketData> handlerClass) {
        this.id = id;
        this.desc = desc;
        this.handlerClass = handlerClass;
    }

    public int toInt(){
        return id;
    }

    public static PacketEnum toEnum(int id){
        for (PacketEnum value : values()) {
            if(value.toInt() == id){
                return value;
            }
        }
        return null;
    }


    public String getDesc() {
        return desc;
    }

    public Class<? extends PacketData> getHandlerClass() {
        return handlerClass;
    }

    public static  <T extends PacketData> T newHandlerInstance(int id) throws InstantiationException, IllegalAccessException {
        PacketEnum packetEnum = toEnum(id);
        if(packetEnum != null){
            T ret = (T) packetEnum.getHandlerClass().newInstance();
            return ret;
        }
        return null;
    }
}
