package org.example.netty_chat.group_chat.engine;

public enum ClientProtocolID implements IDEnum{
    //  请求
    Chat_Login_Request(1,"聊天登录",REQUEST_TYPE),

    Chat_Message_Request(2,"发送消息",REQUEST_TYPE),

    Chat_Logout_Request(3,"退出聊天",REQUEST_TYPE),
    Chat_Create_Group_Request(4,"创建聊天组",REQUEST_TYPE),
    Join_Group_Request(5,"加入聊天组",REQUEST_TYPE),
    Quit_Group_Request(6,"退出聊天组",REQUEST_TYPE),
    Update_Group_Members_Request(7,"更新组成员请求",REQUEST_TYPE),
    Tick_Request(8,"心跳请求",REQUEST_TYPE),



    //  响应
    Chat_Login_Response(Chat_Login_Request.id + RESPONSE_ID,"聊天登录响应",RESPONSE_TYPE ),
    Chat_Message_Response(Chat_Message_Request.id + RESPONSE_ID,"发送消息响应",RESPONSE_TYPE ),

    Chat_Logout_Response(Chat_Logout_Request.id + RESPONSE_ID,"聊天退出响应",RESPONSE_TYPE ),

    Chat_Create_Group_Response(Chat_Create_Group_Request.id + RESPONSE_ID,  "创建聊天组响应",RESPONSE_TYPE),
    Join_Group_Response(Join_Group_Request.id + RESPONSE_ID, "加入聊天组响应",RESPONSE_TYPE),
    Quit_Group_Response(Quit_Group_Request.id + RESPONSE_ID,"退出聊天组响应",RESPONSE_TYPE),


    Update_Group_Members_Response(Update_Group_Members_Request.getId() + RESPONSE_ID, "更新组成员响应",REQUEST_TYPE),

    Tick_Response(Tick_Request.getId() + RESPONSE_ID,"心跳响应",RESPONSE_TYPE),
    ;

    private int id;
    private String desc;
    private int protocolType;

    ClientProtocolID(int id, String desc, int protocolType) {
        this.id = id;
        this.desc = desc;
        this.protocolType = protocolType;
    }

    @Override
    public int getId() {
        return id;
    }

    public static ClientProtocolID toEnum(int id){
        for (ClientProtocolID value : values()) {
            if(value.id == id){
                return value;
            }
        }
        return null;
    }

    public String getDesc() {
        return desc;
    }

    public int getProtocolType() {
        return protocolType;
    }
}
