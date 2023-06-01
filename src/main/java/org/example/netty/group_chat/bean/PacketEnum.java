package org.example.netty.group_chat.bean;

import org.example.netty.group_chat.engine.ClientProtocolID;

public enum PacketEnum {
    Login_Request(ClientProtocolID.Chat_Login_Request.getId(),"登录请求",LoginRequestPacket.class),
    Login_Response(ClientProtocolID.Chat_Login_Response.getId(),"登录响应", ResponsePacket.class),

    Message_Request(ClientProtocolID.Chat_Message_Request.getId(),"消息请求",MessageRequestPacket.class),
    Message_Response(ClientProtocolID.Chat_Message_Response.getId(),"发送消息响应", ResponsePacket.class),
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
