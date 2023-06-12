package org.example.netty.group_chat.engine;

public enum ChatTypeEnum {
    Public_Chat(1,"公共聊天"),
    Private_Chat(2,"私人聊天"),
    ;

    private int id;
    private String desc;

    ChatTypeEnum(int id, String desc) {
        this.id = id;
        this.desc = desc;
    }


    public int toInt() {
        return id;
    }


    public ChatTypeEnum toEnum(int id){
        for (ChatTypeEnum value : values()) {
            if(value.toInt() == id){
                return value;
            }
        }

        return null;
    }


    public String getDesc() {
        return desc;
    }


}
