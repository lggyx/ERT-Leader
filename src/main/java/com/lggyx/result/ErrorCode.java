package com.lggyx.result;

import lombok.Getter;

/**
 * 业务错误码枚举
 */
@Getter
public enum ErrorCode {

    /* 1xxx 用户相关 */
    USER_NOT_EXIST(1001, "用户不存在"),
    PASSWORD_ERROR(1002, "密码错误"),
    TOKEN_INVALID(1003, "Token过期或无效"),

    /* 2xxx 测评相关 */
    EVALUATION_NOT_EXIST(2001, "测评不存在"),
    NO_ACCESS_EVALUATION(2002, "无权访问该测评"),
    EVALUATION_NOT_FINISHED(2003, "题目未答完无法完成"),

    /* 3xxx 题目相关 */
    QUESTION_NOT_EXIST(3001, "题目不存在"),
    OPTION_NOT_MATCH(3002, "选项不属于该题目"),

    /* 4xxx 权限相关 */
    NO_ACCESS_ADMIN_API(4001, "无权访问管理员接口"),

    /* 5xxx 系统相关 */
    SYSTEM_ERROR(5000, "系统内部错误");

    private final int code;
    private final String msg;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}