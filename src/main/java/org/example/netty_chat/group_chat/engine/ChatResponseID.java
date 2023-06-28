package org.example.netty_chat.group_chat.engine;

import org.example.netty_chat.group_chat.bean.ResponsePacket;
import org.example.netty_chat.group_chat.client.handler.ChatClientResponseHandler_ChatLogout;
import org.example.netty_chat.group_chat.client.handler.ChatClientResponseHandler_CreateGroup;
import org.example.netty_chat.group_chat.client.handler.ChatClientResponseHandler_GameLogin;
import org.example.netty_chat.group_chat.client.handler.ChatClientResponseHandler_JoinGroup;
import org.example.netty_chat.group_chat.client.handler.ChatClientResponseHandler_QuitGroup;
import org.example.netty_chat.group_chat.client.handler.ChatClientResponseHandler_SendMessage;
import org.example.netty_chat.group_chat.client.handler.ChatClientResponseHandler_UpdateGroupMembers;

public enum ChatResponseID {
     Chat_Login(1,"聊天登录", ChatClientResponseHandler_GameLogin.instance),

    Chat_Message(2,"发送消息", ChatClientResponseHandler_SendMessage.instance),

    Chat_Logout(3,"退出聊天", ChatClientResponseHandler_ChatLogout.instance),
    Chat_Create_Group(4,"创建聊天组", ChatClientResponseHandler_CreateGroup.instance),
    Join_Group(5,"加入聊天组", ChatClientResponseHandler_JoinGroup.instance),
    Quit_Group(6,"退出聊天组", ChatClientResponseHandler_QuitGroup.instance),
    Update_Group_Members(7,"更新组成员请求", ChatClientResponseHandler_UpdateGroupMembers.instance),
    ;

    private int id;
    private String desc;

    private ChatClientResponseHandlerBase<ResponsePacket> instance;

    ChatResponseID(int id, String desc, ChatClientResponseHandlerBase<ResponsePacket> instance) {
        this.id = id;
        this.desc = desc;
        this.instance = instance;
    }

    public int getId() {
        return id;
    }

    public static ChatResponseID toEnum(int id){
        for (ChatResponseID value : values()) {
            if(value.id == id){
                return value;
            }
        }
        return null;
    }

    public String getDesc() {
        return desc;
    }

    public ChatClientResponseHandlerBase<ResponsePacket> getInstance() {
        return instance;
    }
}
