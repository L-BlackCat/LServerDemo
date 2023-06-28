package org.example.netty_chat.group_chat.engine;


import org.example.netty_chat.group_chat.client.handler.ChatClientResponseHandler_ChatLogout;
import org.example.netty_chat.group_chat.client.handler.ChatClientResponseHandler_CreateGroup;
import org.example.netty_chat.group_chat.client.handler.ChatClientResponseHandler_GameLogin;
import org.example.netty_chat.group_chat.client.handler.ChatClientResponseHandler_JoinGroup;
import org.example.netty_chat.group_chat.client.handler.ChatClientResponseHandler_QuitGroup;
import org.example.netty_chat.group_chat.client.handler.ChatClientResponseHandler_SendMessage;
import org.example.netty_chat.group_chat.client.handler.ChatClientResponseHandler_Tick;
import org.example.netty_chat.group_chat.logger.Debug;
import org.example.netty_chat.group_chat.server.handler.ChatClientRequestHandler_ChatLogout;
import org.example.netty_chat.group_chat.server.handler.ChatClientRequestHandler_ChatLogin;
import org.example.netty_chat.group_chat.server.handler.ChatClientRequestHandler_CreateGroup;
import org.example.netty_chat.group_chat.server.handler.ChatClientRequestHandler_JoinGroup;
import org.example.netty_chat.group_chat.server.handler.ChatClientRequestHandler_QuitGroup;
import org.example.netty_chat.group_chat.server.handler.ChatClientRequestHandler_SendMessage;
import org.example.netty_chat.group_chat.server.handler.ChatClientRequestHandler_Tick;

import java.util.HashMap;
import java.util.Map;

public enum ClientProtocolMgr {
    Instance;

    /*
        通过他来识别出数据包是自定义协议的包，还是无效数据包
        在java字节码的二进制文件中，开头的4字节为0xcafebabe,用来表示一个字节码文件
     */
    public static final long MAGIC_NUM = 0x12345678;
    public final Map<ClientProtocolID,Class<? extends ChatClientRequestHandlerBase>> requestHandlerMap = new HashMap<>();
    public final Map<ClientProtocolID,Class<? extends ChatClientResponseHandlerBase>> responseHandlerMap = new HashMap<>();


    public void onServerStart(){
        loadRequest();
        loadResponse();
    }

    public void loadRequest(){
        regRequest(ClientProtocolID.Chat_Login_Request, ChatClientRequestHandler_ChatLogin.class);
        regRequest(ClientProtocolID.Chat_Message_Request, ChatClientRequestHandler_SendMessage.class);
        regRequest(ClientProtocolID.Chat_Logout_Request, ChatClientRequestHandler_ChatLogout.class);
        regRequest(ClientProtocolID.Chat_Create_Group_Request, ChatClientRequestHandler_CreateGroup.class);
        regRequest(ClientProtocolID.Join_Group_Request, ChatClientRequestHandler_JoinGroup.class);
        regRequest(ClientProtocolID.Quit_Group_Request, ChatClientRequestHandler_QuitGroup.class);
        regRequest(ClientProtocolID.Tick_Request, ChatClientRequestHandler_Tick.class);
    }

    public void loadResponse(){
        regResponse(ClientProtocolID.Chat_Login_Response, ChatClientResponseHandler_GameLogin.class);
        regResponse(ClientProtocolID.Chat_Message_Response, ChatClientResponseHandler_SendMessage.class);
        regResponse(ClientProtocolID.Chat_Logout_Response, ChatClientResponseHandler_ChatLogout.class);
        regResponse(ClientProtocolID.Chat_Create_Group_Response, ChatClientResponseHandler_CreateGroup.class);
        regResponse(ClientProtocolID.Join_Group_Response, ChatClientResponseHandler_JoinGroup.class);
        regResponse(ClientProtocolID.Quit_Group_Response, ChatClientResponseHandler_QuitGroup.class);
        regResponse(ClientProtocolID.Tick_Response, ChatClientResponseHandler_Tick.class);
    }

    public void regRequest(ClientProtocolID clientProtocolID, Class<? extends ChatClientRequestHandlerBase> handlerClass){
        if(clientProtocolID.getProtocolType() != ClientProtocolID.REQUEST_TYPE){
            Debug.err("[" + clientProtocolID.name() +"] protocolId: " + clientProtocolID.getId() + " not request protocol type");
            return;
        }
        requestHandlerMap.put(clientProtocolID,handlerClass);
    }

    public void regResponse(ClientProtocolID clientProtocolID, Class<? extends ChatClientResponseHandlerBase> handlerClass){
        if(clientProtocolID.getProtocolType() != ClientProtocolID.RESPONSE_TYPE){
            Debug.err("[" + clientProtocolID.name() +"] protocolId: " + clientProtocolID.getId() + " not response protocol type");
            return;
        }
        responseHandlerMap.put(clientProtocolID,handlerClass);
    }

    public ChatClientRequestHandlerBase createRequestById(int id) throws InstantiationException, IllegalAccessException {
        ClientProtocolID clientProtocolID = ClientProtocolID.toEnum(id);
        if (requestHandlerMap.containsKey(clientProtocolID)) {
            Class<? extends ChatClientRequestHandlerBase> clazz = requestHandlerMap.get(clientProtocolID);

            return clazz.newInstance();
        }

        return null;
    }

    public ChatClientResponseHandlerBase createResponseById(int id) throws InstantiationException, IllegalAccessException {
        ClientProtocolID clientProtocolID = ClientProtocolID.toEnum(id);
        if (responseHandlerMap.containsKey(clientProtocolID)) {
            Class<? extends ChatClientResponseHandlerBase> clazz = responseHandlerMap.get(clientProtocolID);

            return clazz.newInstance();
        }

        return null;
    }
}
