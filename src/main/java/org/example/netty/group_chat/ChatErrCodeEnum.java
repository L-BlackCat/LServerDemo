package org.example.netty.group_chat;

public enum ChatErrCodeEnum {
    SUCCESS(1,"成功"),
    ERR(2,"失败"),


    ;

    private int id;
    private String desc;

    ChatErrCodeEnum(int id, String desc) {
        this.id = id;
        this.desc = desc;
    }


    public int toInt(){
        return this.id;
    }
}
