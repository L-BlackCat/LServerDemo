package org.example.netty.group_chat.engine;

import org.example.netty.group_chat.bean.RequestPacket;
import org.example.netty.group_chat.server.handler.ChatClientRequestHandler_ChatLogin;
import org.example.netty.group_chat.server.handler.ChatClientRequestHandler_ChatLogout;
import org.example.netty.group_chat.server.handler.ChatClientRequestHandler_CreateGroup;
import org.example.netty.group_chat.server.handler.ChatClientRequestHandler_JoinGroup;
import org.example.netty.group_chat.server.handler.ChatClientRequestHandler_QuitGroup;
import org.example.netty.group_chat.server.handler.ChatClientRequestHandler_SendMessage;
import org.example.netty.group_chat.server.handler.ChatClientRequestHandler_UpdateGroupMembers;

public enum ChatRequestID {    //  请求
    Chat_Login(1,"聊天登录", ChatClientRequestHandler_ChatLogin.instance),

    Chat_Message(2,"发送消息", ChatClientRequestHandler_SendMessage.instance),

    Chat_Logout(3,"退出聊天", ChatClientRequestHandler_ChatLogout.instance),
    Chat_Create_Group(4,"创建聊天组", ChatClientRequestHandler_CreateGroup.instance),
    Join_Group(5,"加入聊天组", ChatClientRequestHandler_JoinGroup.instance),
    Quit_Group(6,"退出聊天组", ChatClientRequestHandler_QuitGroup.instance),
    Update_Group_Members(7,"更新组成员请求", ChatClientRequestHandler_UpdateGroupMembers.instance),
    ;

    private int id;
    private String desc;

    private ChatClientRequestHandlerBase<RequestPacket> instance;

    ChatRequestID(int id, String desc, ChatClientRequestHandlerBase<RequestPacket> instance) {
        this.id = id;
        this.desc = desc;
        this.instance = instance;
    }

    public int getId() {
        return id;
    }

    public static ChatRequestID toEnum(int id){
        for (ChatRequestID value : values()) {
            if(value.id == id){
                return value;
            }
        }
        return null;
    }

    public String getDesc() {
        return desc;
    }

    public ChatClientRequestHandlerBase<RequestPacket> getInstance() {
        return instance;
    }
}
