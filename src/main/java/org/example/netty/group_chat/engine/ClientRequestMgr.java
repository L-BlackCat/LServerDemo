package org.example.netty.group_chat.engine;


import org.example.netty.group_chat.handler.ChatClientRequestHandler_GameLogin;

import java.util.HashMap;
import java.util.Map;

public enum ClientRequestMgr {
    Instance;

    /*
        通过他来识别出数据包是自定义协议的包，还是无效数据包
        在java字节码的二进制文件中，开头的4字节为0xcafebabe,用来表示一个字节码文件
     */
    private static final long MAGIC_NUM = 0x12345678;
    Map<ClientRequestID,Class<? extends ChatClientRequestHandlerBase>> handlerMap = new HashMap<>();


    public void onServerStart(){
        regHandler(ClientRequestID.Chat_Login, ChatClientRequestHandler_GameLogin.class);

    }

    public void regHandler(ClientRequestID clientRequestID,Class<? extends ChatClientRequestHandlerBase> handlerClass){
        handlerMap.put(clientRequestID,handlerClass);
    }


    public IRequestHandler createById(int id) throws InstantiationException, IllegalAccessException {
        ClientRequestID clientRequestID = ClientRequestID.toEnum(id);
        if (handlerMap.containsKey(clientRequestID)) {
            Class<? extends ChatClientRequestHandlerBase> clazz = handlerMap.get(clientRequestID);

            return clazz.newInstance();
        }

        return null;
    }
}
