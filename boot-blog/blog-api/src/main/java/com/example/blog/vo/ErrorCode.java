package com.example.blog.vo;

/**
 * @author hap
 * @date 2022/4/17 14:14
 * @describe 错误消息统一规范
 */

public enum ErrorCode {

    PARAMS_ERROR(10001, "参数有误"),
    ACCOUNT_PWD_NOT_EXIST(10002, "用户名或密码不存在"),
    NO_PERMISSION(10003, "无访问权限"),
    TOKEN_ERROR(10004,"token错误"),
    ACCOUNT_EXIST(10005,"账号已存在"),
    SESSION_TIME_OUT(10006, "会话超时"),
    NO_LOGIN(10007, "未登录"),
    ;

    private int code;
    private String msg;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

