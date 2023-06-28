package org.example.netty_chat.group_chat.bean;

import org.example.netty_chat.group_chat.engine.ClientProtocolID;

public enum PacketTypeEnum {
    Login_Request(ClientProtocolID.Chat_Login_Request.getId(),"登录请求",RequestPacket.class),
    Login_Response(ClientProtocolID.Chat_Login_Response.getId(),"登录响应", ResponsePacket.class),

    Message_Request(ClientProtocolID.Chat_Message_Request.getId(),"消息请求",RequestPacket.class),
    Message_Response(ClientProtocolID.Chat_Message_Response.getId(),"发送消息响应", ResponsePacket.class),



    Logout_Request(ClientProtocolID.Chat_Logout_Request.getId(),"登出请求", RequestPacket.class),
    Logout_Response(ClientProtocolID.Chat_Logout_Response.getId(),"登出响应", ResponsePacket.class),

    Create_Group_Request(ClientProtocolID.Chat_Create_Group_Request.getId(),"创建聊天组请求", RequestPacket.class),
    Create_Group_Response(ClientProtocolID.Chat_Create_Group_Response.getId(),"创建聊天组响应", ResponsePacket.class),

    Join_Group_Request(ClientProtocolID.Join_Group_Request.getId(),"加入聊天组请求", RequestPacket.class),
    Join_Group_Response(ClientProtocolID.Join_Group_Response.getId(),"加入聊天组响应", ResponsePacket.class),


    Quit_Group_Request(ClientProtocolID.Quit_Group_Request.getId(),"离开聊天组请求", RequestPacket.class),
    Quit_Group_Response(ClientProtocolID.Quit_Group_Response.getId(),"离开聊天组响应", ResponsePacket.class),

    Update_Group_Members_Request(ClientProtocolID.Update_Group_Members_Request.getId(),"更新组员请求",RequestPacket.class),
    Update_Group_Members_Response(ClientProtocolID.Update_Group_Members_Response.getId(),"更新组员响应",ResponsePacket.class),

    Tick_Request(ClientProtocolID.Tick_Request.getId(),"心跳请求",RequestPacket.class),
    Tick_Response(ClientProtocolID.Tick_Response.getId(),"心跳响应",ResponsePacket.class),
    ;

    private int id;

    private String desc;

    private Class<? extends PacketData> handlerClass;


    PacketTypeEnum(int id, String desc, Class<? extends PacketData> handlerClass) {
        this.id = id;
        this.desc = desc;
        this.handlerClass = handlerClass;
    }

    public int toInt(){
        return id;
    }

    public static PacketTypeEnum toEnum(int id){
        for (PacketTypeEnum value : values()) {
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
        PacketTypeEnum packetTypeEnum = toEnum(id);
        if(packetTypeEnum != null){
            T ret = (T) packetTypeEnum.getHandlerClass().newInstance();
            return ret;
        }
        return null;
    }
}
