package org.example.netty.group_chat.engine;

public enum ClientRequestID implements IDEnum{
    Chat_Login(1,"聊天登录"),
    ;

    private int id;
    private String desc;

    ClientRequestID(int id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    @Override
    public int getId() {
        return 0;
    }

    public static ClientRequestID toEnum(int id){
        for (ClientRequestID value : values()) {
            if(value.id == id){
                return value;
            }
        }
        return null;
    }
}
