package com.example.demo.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{
    QUESTION_NOT_FOUND(2001,"你找的问题不在了，要不换个试试？"),
    TARGET_PARENT_NOT_FOUND(2002,"未选中任何问题和评论进行回复"),
    NO_LOG_IN(2003,"您还未登录"),
    SYS_ERROR(2004,"服务器冒烟啦，要不你稍后再试"),
    TYPE_PARAM_ERROR(2005,"评论 类型错误或不存在"),
    COMMENT_PARAM_ERROR(2006,"您要回复的评论不存在"),
    QUESTION_PARAM_ERROR(2007,"您要回复的问题不存在");

    @Override
    public String getMessage () {
        return message;
    }

    @Override
    public Integer getCode () {
        return code;
    }

    private String message;
    private Integer code;

    CustomizeErrorCode (Integer code, String message) {
        this.message = message;
        this.code = code;
    }
}
