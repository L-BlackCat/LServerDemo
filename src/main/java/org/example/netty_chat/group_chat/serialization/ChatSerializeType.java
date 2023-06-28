package org.example.netty_chat.group_chat.serialization;

public enum ChatSerializeType {
    JSON(1,JsonSerializeHandler.class),
    ;

    private int id;
    private Class<? extends IChatSerializer> serializeHandlerClass;

    ChatSerializeType(int id, Class<? extends IChatSerializer> serializeHandlerClass) {
        this.id = id;
        this.serializeHandlerClass = serializeHandlerClass;
    }

    public int toInt(){
        return this.id;
    }

    public static ChatSerializeType toEnum(int id){
        for (ChatSerializeType value : values()) {
            if(value.id == id){
                return value;
            }
        }
        return null;
    }

    public IChatSerializer newInstanceHandler() throws InstantiationException, IllegalAccessException {
        return this.serializeHandlerClass.newInstance();
    }
}
