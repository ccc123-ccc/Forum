package com.example.demo.enums;

public enum NotificationTypeEnums {
    QUESTION_REPLY(1,"回复了问题"),
    COMMENT_REPLY(2,"回复了评论");
    private int type;
    private String name;

    NotificationTypeEnums (int type, String name) {
        this.type = type;
        this.name = name;
    }

    public int getType () {
        return type;
    }

    public String getName () {
        return name;
    }
    public static String nameOfType (Integer type){
        if(type==1){
            return QUESTION_REPLY.getName ();
        }else{
            return COMMENT_REPLY.getName ();
        }
    }
}
