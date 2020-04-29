package com.bk.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND(2001,"你找的问题不在了，要不要换个试试？"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或评论进行回复"),
    NOT_LOGIN(2003,"当前操作需要登陆，请前往登陆"),
    SYSTEM_ERROR(2004,"服务冒烟了，要不你稍后再试试！！！"),
    TYPE_PARAM_NOT_FOUND(2005,"评论类型不存在"),
    COMMENT_NOT_FOUND(2006,"评论不存在"),
    COMMENT_IS_EMPTY(2007,"输入内容不能为空"),
    ;


    private String message;
    private Integer code;

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }
}
