package org.example.netty_chat.group_chat.engine.chat_channel;

public enum GroupChatEnum {
    Public_Chat(1,"公共聊天",PublicChatChannelHandler.class),
    Private_Chat(2,"私人聊天",PrivateChatChannelHandler.class),
    ;

    private int id;
    private String desc;
    private Class<? extends ChatChannelBaseHandler> handlerClass;

    GroupChatEnum(int id, String desc, Class<? extends ChatChannelBaseHandler> handlerClass) {
        this.id = id;
        this.desc = desc;
        this.handlerClass = handlerClass;
    }

    public int toInt() {
        return id;
    }


    public GroupChatEnum toEnum(int id){
        for (GroupChatEnum value : values()) {
            if(value.toInt() == id){
                return value;
            }
        }

        return null;
    }


    public String getDesc() {
        return desc;
    }

    public Class<? extends ChatChannelBaseHandler> getHandlerClass() {
        return handlerClass;
    }

    public static ChatChannelBaseHandler newInstanceHandler(int id) throws InstantiationException, IllegalAccessException {
        for (GroupChatEnum value : values()) {
            if(value.toInt() == id){
                return value.getHandlerClass().newInstance();
            }
        }
        return null;
    }

}
